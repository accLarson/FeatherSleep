package dev.zerek.feathersleep.listeners;

import dev.zerek.feathersleep.FeatherSleep;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private final FeatherSleep plugin;

    public PlayerJoinListener(FeatherSleep plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        plugin.getSleepManager().calculate();
    }
}
