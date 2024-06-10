package dev.zerek.feathersleep.tasks;

import dev.zerek.feathersleep.FeatherSleep;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

public class AccelerateNightTask extends BukkitRunnable {

    private final FeatherSleep plugin;

    public AccelerateNightTask(FeatherSleep plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        World world = plugin.getServer().getWorlds().get(0);
        world.setFullTime(world.getFullTime() + plugin.getSleepManager().getAdditionalTime());
        plugin.getLogger().info("Increasing Speed! - " + plugin.getSleepManager().getAdditionalTime() + "x");

    }
}
