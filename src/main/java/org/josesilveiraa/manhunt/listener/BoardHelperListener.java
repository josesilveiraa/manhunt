package org.josesilveiraa.manhunt.listener;

import fr.mrmicky.fastboard.FastBoard;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.josesilveiraa.manhunt.Main;
import org.josesilveiraa.manhunt.config.ScoreboardConfig;

public class BoardHelperListener implements Listener {

    @EventHandler
    public void on(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        FastBoard board = new FastBoard(p);
        board.updateTitle(ScoreboardConfig.TITLE);
        Main.getBoards().put(p.getUniqueId(), board);
    }

    @EventHandler
    public void on(PlayerQuitEvent e) {
        Player p = e.getPlayer();

        FastBoard board = Main.getBoards().remove(p.getUniqueId());

        if(board != null) {
            board.delete();
        }
    }

}
