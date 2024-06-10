package dev.zerek.feathersleep.listeners;

import dev.zerek.feathersleep.FeatherSleep;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;

public class PlayerBedEnterListener implements Listener {

    private final FeatherSleep plugin;

    public PlayerBedEnterListener(FeatherSleep plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerBedEnter (PlayerBedEnterEvent event) {

        // confirm player is entering bed, this event is called right before entering
        if (event.getBedEnterResult() != PlayerBedEnterEvent.BedEnterResult.OK) return;

        plugin.getSleepManager().storeSleepingPlayerStatus(event.getPlayer(), true);
        plugin.getSleepManager().recalculate();
    }
}
