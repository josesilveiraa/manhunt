package org.josesilveiraa.manhunt.task;

import org.bukkit.scheduler.BukkitRunnable;
import org.josesilveiraa.manhunt.Main;
import org.josesilveiraa.manhunt.log.LogLevel;
import org.josesilveiraa.manhunt.log.LogManager;
import org.josesilveiraa.manhunt.update.UpdateChecker;

import java.io.IOException;

public class UpdateCheckerTask extends BukkitRunnable {
    @Override
    public void run() {
        try {
            new UpdateChecker(Main.getPlugin()).check();
        } catch (IOException e) {
            LogManager.log("Couldn't check for new updates.", LogLevel.ERROR);
        }
    }
}
