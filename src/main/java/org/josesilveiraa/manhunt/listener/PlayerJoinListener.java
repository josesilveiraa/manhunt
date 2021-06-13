package org.josesilveiraa.manhunt.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.josesilveiraa.manhunt.Main;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void on(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        if(Main.getGame().isOccurring()) {
            Main.getGameManager().addHunter(p);
        }
    }

}
