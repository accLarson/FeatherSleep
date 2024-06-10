package dev.zerek.feathersleep.listeners;

import dev.zerek.feathersleep.FeatherSleep;
import net.ess3.api.events.AfkStatusChangeEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class AfkStatusChangeListener implements Listener {

    private final FeatherSleep plugin;

    public AfkStatusChangeListener(FeatherSleep plugin) {
        this.plugin = plugin;
    }

    // This event fires when an AFK player disconnects. Useful info - no disconnect listener is required to track afk status.
    @EventHandler
    public void onAfkStatusChange(AfkStatusChangeEvent event) {
        plugin.getSleepManager().storePlayerAfkStatus(event.getAffected().getBase(),event.getValue());
        plugin.getSleepManager().recalculate();
    }
}
