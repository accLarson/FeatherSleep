package dev.zerek.feathersleep.listeners;

import dev.zerek.feathersleep.FeatherSleep;
import io.papermc.paper.event.player.PlayerDeepSleepEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerDeepSleepListener implements Listener {

    private final FeatherSleep plugin;

    public PlayerDeepSleepListener(FeatherSleep plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDeepSleepEvent(PlayerDeepSleepEvent event) {
        plugin.getLogger().info("deep sleep event");
    }
}
