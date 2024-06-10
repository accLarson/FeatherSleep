package dev.zerek.feathersleep.managers;

import dev.zerek.feathersleep.FeatherSleep;

import java.util.HashMap;
import java.util.Map;

public class ConfigManager {
    private final FeatherSleep plugin;

    private boolean ignoreAfk;
    private boolean ignoreVanished;
    private boolean ignoreBypass;
    private double maxSpeed;
    private long nightStart;
    private long nightEnd;
    public ConfigManager(FeatherSleep plugin) {
        this.plugin = plugin;
        this.loadConfig();
    }

    private void loadConfig() {
        this.ignoreAfk = plugin.getConfig().getBoolean("Sleeping-calculation-settings.ignore-afk");
        this.ignoreVanished = plugin.getConfig().getBoolean("Sleeping-calculation-settings.ignore-vanished");
        this.ignoreBypass = plugin.getConfig().getBoolean("Sleeping-calculation-settings.ignore-bypass");

        this.nightStart = plugin.getConfig().getLong("night-timing-in-ticks.start");
        this.nightEnd = plugin.getConfig().getLong("night-timing-in-ticks.end");
    }

    public boolean isIgnoreAfk() {
        return ignoreAfk;
    }
    public boolean isIgnoreVanished() {
        return ignoreVanished;
    }
    public boolean isIgnoreBypass() {
        return ignoreBypass;
    }
    public long getNightStart() {
        return nightStart;
    }
    public long getNightEnd() {
        return nightEnd;
    }
}
