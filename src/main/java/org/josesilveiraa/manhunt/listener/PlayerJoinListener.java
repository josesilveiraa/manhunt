package org.josesilveiraa.manhunt.listener;

import fr.mrmicky.fastboard.FastBoard;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.josesilveiraa.manhunt.Main;
import org.josesilveiraa.manhunt.config.ScoreboardConfig;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void on(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        if(Main.getGame().isOccurring()) {
            Main.getGameManager().addHunter(p);
        }

        FastBoard board = new FastBoard(p);
        board.updateTitle(ScoreboardConfig.TITLE);
        Main.getBoards().put(p.getUniqueId(), board);
    }

}
