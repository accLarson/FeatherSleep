package dev.zerek.feathersleep.managers;

import dev.zerek.feathersleep.FeatherSleep;

import java.util.HashMap;
import java.util.Map;

public class ConfigManager {
    private final FeatherSleep plugin;
    private final Map<String,Object> config = new HashMap<>();
    public ConfigManager(FeatherSleep plugin) {
        this.plugin = plugin;
    }
}
