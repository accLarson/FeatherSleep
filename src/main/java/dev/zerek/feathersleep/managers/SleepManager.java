package dev.zerek.feathersleep.managers;

import dev.zerek.feathersleep.FeatherSleep;
import dev.zerek.feathersleep.tasks.AccelerateNightTask;
import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.Set;

public class SleepManager {

    private final FeatherSleep plugin;
    private final Set<Player> sleepingPlayers = new HashSet<>();
    private final Set<Player> afkPlayers = new HashSet<>();
    private BukkitRunnable accelerateNightTask;
    private final ConfigManager config;
    private Long additionalTime = 0L;
    private int taskId = -1;

    public SleepManager(FeatherSleep plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfigManager();
        this.accelerateNightTask = new AccelerateNightTask(plugin);
    }

    public void recalculate() {
        //kill condition
        if (this.getSleepingCount() == 0) {
            plugin.getLogger().info("0 players are sleeping");
            this.additionalTime = 0L;
            if (this.taskId > 0) {
                plugin.getLogger().info("running accelerator but no one is sleeping - stopping.");
                this.accelerateNightTask.cancel();
                this.accelerateNightTask = new AccelerateNightTask(plugin);
//                plugin.getServer().getScheduler().cancelTasks(plugin);
                this.taskId = -1;
            }
        }
        else {
            plugin.getLogger().info(sleepingPlayers.size() + " players are sleeping");
            int percentSleeping = this.getPercentageSleeping(config.isIgnoreAfk(), config.isIgnoreVanished(), config.isIgnoreBypass());

            this.additionalTime = (long) Math.max(2,Math.pow(percentSleeping,2)/100);
            if (this.taskId == -1) {
                plugin.getLogger().info("at least 1 is sleeping - Starting");
                this.taskId = accelerateNightTask.runTaskTimer(plugin,0L,1L).getTaskId();
            }
        }
    }

    public int getPercentageSleeping(boolean ignoreAfk, boolean ignoreVanished, boolean ignoreBypass) {
        Set<Player> Players = new HashSet<>(plugin.getServer().getOnlinePlayers());

        if (ignoreAfk) Players.removeAll(afkPlayers);
        if (ignoreVanished) Players.removeIf(this::isVanished);
        if (ignoreBypass) Players.removeIf(player -> player.hasPermission("feather.sleep.bypass"));

        float totalCount = Players.size();
        float sleepingCount = sleepingPlayers.size();

        // Avoids division by zero or calculating if no one is sleeping
        if (totalCount == 0 || sleepingCount == 0) return 0;
        return Math.round((sleepingCount / totalCount) * 100);
    }

    public void storeSleepingPlayerStatus(Player player, boolean sleeping) {
        if (sleeping) this.sleepingPlayers.add(player);
        else this.sleepingPlayers.remove(player);
    }

    public void storePlayerAfkStatus(Player player, boolean afk) {
        if (afk) this.afkPlayers.add(player);
        else this.afkPlayers.remove(player);
    }

    private boolean isVanished(Player player) {
        for (MetadataValue meta : player.getMetadata("vanished")) {
            if (meta.asBoolean()) return true;
        }
        return false;
    }

    public int getSleepingCount() {
        return this.sleepingPlayers.size();
    }

    public long getAdditionalTime() {
        return this.additionalTime;
    }
}
