package dev.zerek.feathersleep.listeners;

import dev.zerek.feathersleep.FeatherSleep;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    private final FeatherSleep plugin;

    public PlayerQuitListener(FeatherSleep plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        plugin.getServer().getScheduler().runTaskLater(plugin, () -> plugin.getSleepManager().recalculate(), 1L);

    }
}
