package dev.zerek.feathersleep;

import dev.zerek.feathersleep.listeners.*;
import dev.zerek.feathersleep.managers.SleepManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class FeatherSleep extends JavaPlugin {

    private SleepManager sleepManager;

    @Override
    public void onEnable() {
        this.sleepManager = new SleepManager(this);

        this.getServer().getPluginManager().registerEvents(new AfkStatusChangeListener(this),this);
        this.getServer().getPluginManager().registerEvents(new PlayerBedEnterListener(this),this);
        this.getServer().getPluginManager().registerEvents(new PlayerBedLeaveListener(this),this);
        this.getServer().getPluginManager().registerEvents(new PlayerJoinListener(this),this);
        this.getServer().getPluginManager().registerEvents(new PlayerQuitListener(this),this);
    }

    @Override
    public void onDisable() {
    }

    public SleepManager getSleepManager() {
        return sleepManager;
    }
}
