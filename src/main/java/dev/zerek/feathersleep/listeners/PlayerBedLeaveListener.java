package dev.zerek.feathersleep.listeners;

import dev.zerek.feathersleep.FeatherSleep;
import net.kyori.adventure.text.minimessage.MiniMessage;
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

        long time = event.getPlayer().getWorld().getTime();
        if (time == 0 || (time >= 23500 && time <= 23600)) {
            event.getPlayer().sendActionBar(MiniMessage.miniMessage().deserialize(this.plugin.getConfigManager().getWakeUpMessage()));
        }
    }
}