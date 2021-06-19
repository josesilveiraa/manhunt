package org.josesilveiraa.manhunt.task;

import org.bukkit.scheduler.BukkitRunnable;
import org.josesilveiraa.manhunt.Main;

public class GameTimeTask extends BukkitRunnable {
    @Override
    public void run() {
        if(Main.getGame().isOccurring()) {
            Main.getGame().setTotalSeconds(Main.getGame().getTotalSeconds() + 1);
        }
    }
}
