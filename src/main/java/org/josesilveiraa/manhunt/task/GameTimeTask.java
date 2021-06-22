package org.josesilveiraa.manhunt.task;

import org.bukkit.scheduler.BukkitRunnable;
import org.josesilveiraa.manhunt.Manhunt;

public class GameTimeTask extends BukkitRunnable {
    @Override
    public void run() {
        if(Manhunt.getGame().isOccurring()) {
            Manhunt.getGame().setTotalSeconds(Manhunt.getGame().getTotalSeconds() + 1);
        }
    }
}
