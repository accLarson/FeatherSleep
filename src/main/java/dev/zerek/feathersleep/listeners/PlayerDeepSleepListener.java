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
    public void onPlayerDeepSleep(PlayerDeepSleepEvent event) {
        if (plugin.getConfigManager().isClearWeatherOnWake()) {
            event.getPlayer().getWorld().setStorm(false);
            event.getPlayer().getWorld().setThundering(false);
        }
    }
}
