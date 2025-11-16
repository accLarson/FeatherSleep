package dev.zerek.feathersleep.managers;

import dev.zerek.feathersleep.FeatherSleep;
import dev.zerek.feathersleep.tasks.AccelerateNightTask;
import dev.zerek.feathersleep.tasks.InformTask;
import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.Set;

public class SleepManager {

    private final FeatherSleep plugin;
    private final Set<Player> sleepingPlayers = new HashSet<>();
    private final Set<Player> afkPlayers = new HashSet<>();
    private Set<Player> relevantOnline = new HashSet<>();
    private BukkitRunnable accelerateNightTask;
    private BukkitRunnable informTask;
    private final ConfigManager config;
    private Long additionalTime = 0L;
    private int percentSleeping = 0;
    private int taskId = -1;

    public SleepManager(FeatherSleep plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfigManager();
        this.accelerateNightTask = new AccelerateNightTask(plugin);
        this.informTask = new InformTask(plugin);
    }

    public void recalculate() {
        //kill condition
        if (this.getSleepingCount() == 0) {
            this.additionalTime = 0L;
            if (this.taskId > 0) {
                plugin.getServer().getScheduler().cancelTasks(plugin);
                this.accelerateNightTask = new AccelerateNightTask(plugin);
                this.informTask = new InformTask(plugin);
                this.taskId = -1;
            }
        }
        else {
            this.percentSleeping = this.getPercentageSleeping(config.isIgnoreAfk(), config.isIgnoreVanished(), config.isIgnoreBypass());
            this.additionalTime = (long) Math.max(2,Math.pow(this.percentSleeping,2)/100);

            if (this.taskId == -1) {
                this.taskId = accelerateNightTask.runTaskTimer(plugin,0L,1L).getTaskId();
                informTask.runTaskTimer(plugin,0L,30L);
            }
        }
    }

    private int getPercentageSleeping(boolean ignoreAfk, boolean ignoreVanished, boolean ignoreBypass) {
        Set<Player> Players = new HashSet<>(plugin.getServer().getOnlinePlayers());

        // Only remove AFK players who aren't sleeping
        if (ignoreAfk) {
            Set<Player> nonSleepingAfk = new HashSet<>(afkPlayers);
            nonSleepingAfk.removeAll(sleepingPlayers);
            Players.removeAll(nonSleepingAfk);
        }
        if (ignoreVanished) Players.removeIf(this::isVanished);
        if (ignoreBypass) Players.removeIf(player -> player.hasPermission("feather.sleep.bypass"));


        this.relevantOnline = Players;
        float sleepingCount = sleepingPlayers.size();

        // Avoids division by zero or calculating if no one is sleeping
        if (relevantOnline.isEmpty() || sleepingCount == 0) return 0;

        return Math.round((sleepingCount / relevantOnline.size()) * 100);
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

    public Set<Player> getSleepingPlayers() {
        return this.sleepingPlayers;
    }
    public Set<Player> getRelevantOnline() {
        return this.relevantOnline;
    }
    public int getSleepingCount() {
        return this.sleepingPlayers.size();
    }
    public int getPercentSleeping() {
        return this.percentSleeping;
    }
    public long getAdditionalTime() {
        return this.additionalTime;
    }

}
