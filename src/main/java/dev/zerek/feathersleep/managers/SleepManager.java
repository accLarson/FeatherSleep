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
    private final BukkitRunnable accelerateNightTask;

    private final ConfigManager config;

    Long additionalTime = 0L;

    public SleepManager(FeatherSleep plugin) {
        this.plugin = plugin;
        this.accelerateNightTask = new AccelerateNightTask(plugin);
        this.config = plugin.getConfigManager();
    }

    public void recalculate() {
        //kill condition
        if (this.getSleepingCount() == 0) {
            this.additionalTime = 0L;
            this.accelerateNightTask.cancel();
            return;
        }

        int percentSleeping = this.getPercentageSleeping(config.isIgnoreAfk(), config.isIgnoreVanished(), config.isIgnoreBypass());

        this.additionalTime = (long) Math.max(2,Math.pow(percentSleeping,2)/100);
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
