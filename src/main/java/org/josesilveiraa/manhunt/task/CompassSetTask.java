package org.josesilveiraa.manhunt.task;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.josesilveiraa.manhunt.Manhunt;

public class CompassSetTask extends BukkitRunnable {
    @Override
    public void run() {
        if (Manhunt.getGame().isOccurring()) {
            for (Player hunter : Manhunt.getGame().getHunters()) {
                hunter.setCompassTarget(Manhunt.getGame().getRunner().getLocation());
            }
        }
    }
}
