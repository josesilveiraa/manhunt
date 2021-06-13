package org.josesilveiraa.manhunt.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.josesilveiraa.manhunt.Main;

public class PlayerLeaveListener implements Listener {

    @EventHandler
    public void on(PlayerQuitEvent e) {

        Player p = e.getPlayer();

        if(Main.getGame().isOccurring()) {
            Main.getGameManager().removeHunter(p);
        }

    }

}
