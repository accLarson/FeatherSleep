package dev.zerek.feathersleep.tasks;

import dev.zerek.feathersleep.FeatherSleep;
import dev.zerek.feathersleep.managers.ConfigManager;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

public class AccelerateNightTask extends BukkitRunnable {

    private final FeatherSleep plugin;
    private final ConfigManager config;

    public AccelerateNightTask(FeatherSleep plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfigManager();
    }

    @Override
    public void run() {

        World world = plugin.getServer().getWorlds().get(0);
        world.setFullTime(world.getFullTime() + plugin.getSleepManager().getAdditionalTime());



    }

}
