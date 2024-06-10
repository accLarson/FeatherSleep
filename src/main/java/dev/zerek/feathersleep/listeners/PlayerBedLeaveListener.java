package dev.zerek.feathersleep.listeners;

import dev.zerek.feathersleep.FeatherSleep;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedLeaveEvent;

public class PlayerBedLeaveListener implements Listener {

    private final FeatherSleep plugin;

    public PlayerBedLeaveListener(FeatherSleep plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerBedLeave(PlayerBedLeaveEvent event) {

        plugin.getSleepManager().storeSleepingPlayerStatus(event.getPlayer(),false);
        plugin.getServer().getScheduler().runTaskLater(plugin, () -> plugin.getSleepManager().recalculate(), 1L);
    }
}
