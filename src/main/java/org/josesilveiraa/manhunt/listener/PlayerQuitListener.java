package org.josesilveiraa.manhunt.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.josesilveiraa.manhunt.Manhunt;

public class PlayerQuitListener implements Listener {
    @EventHandler
    public void on(PlayerQuitEvent e) {

        Player p = e.getPlayer();

        if(Manhunt.getGame().isOccurring()) {
            Manhunt.getGameManager().removeHunter(p);
        }
    }

}
