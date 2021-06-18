package org.josesilveiraa.manhunt.task;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.josesilveiraa.manhunt.Main;

public class CompassSetTask extends BukkitRunnable {
    @Override
    public void run() {
        if(Main.getGame().isOccurring()) {
            for(Player hunter : Main.getGame().getHunters()) {
                hunter.setCompassTarget(Main.getGame().getRunner().getLocation());
            }
        }
    }
}
