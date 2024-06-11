package dev.zerek.feathersleep.tasks;

import dev.zerek.feathersleep.FeatherSleep;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.scheduler.BukkitRunnable;

public class InformTask extends BukkitRunnable {

    private final FeatherSleep plugin;

    public InformTask(FeatherSleep plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {

        long additionalTime = plugin.getSleepManager().getAdditionalTime();
        long sleepingCount = plugin.getSleepManager().getSleepingCount();
        int relevantOnline = plugin.getSleepManager().getRelevantOnline().size();
        String time = parseTime(plugin.getServer().getWorlds().get(0).getTime());

        plugin.getServer().getOnlinePlayers().forEach(p -> p.sendActionBar(Component.text(sleepingCount + "/" + relevantOnline + " sleeping - " + additionalTime + "x speed | " + time)));
    }

    public String parseTime(long input) {
        // Ensure the input is always considered as 5 digits by formatting it as a zero-padded string
        String inputString = String.format("%05d", input);

        // Extract the hour part from the first two digits and add 6
        int hourPart = Integer.parseInt(inputString.substring(0, 2)) + 6;

        // Adjust the hour part if it exceeds 24
        hourPart = (hourPart >= 24) ? (hourPart - 24) : hourPart;

        // Determine if it is PM
        boolean isPM = hourPart >= 12;

        // Calculate the hour for AM/PM format
        int hour = hourPart % 12;
        hour = (hour == 0) ? 12 : hour;

        // Format the time string
        String timeString = String.format("%d %s", hour, isPM ? "PM" : "AM");

        return timeString;
    }

}
