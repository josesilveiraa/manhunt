package org.josesilveiraa.manhunt.task;

import org.bukkit.scheduler.BukkitRunnable;
import org.josesilveiraa.manhunt.Main;
import org.josesilveiraa.manhunt.update.UpdateChecker;

public class UpdateCheckerTask extends BukkitRunnable {
    @Override
    public void run() {
        new UpdateChecker(Main.getPlugin()).check();
    }
}
