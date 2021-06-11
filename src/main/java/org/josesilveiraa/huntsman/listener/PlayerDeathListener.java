package org.josesilveiraa.huntsman.listener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.josesilveiraa.huntsman.Main;

public class PlayerDeathListener implements Listener {

    @EventHandler
    public void on(PlayerDeathEvent e) {
        Player p = e.getEntity();

        if (Main.getGame().isOccurring()) {
            if (Main.getGameManager().isRunner(p)) {
                p.getInventory().clear();
                Main.getGameManager().stopGame(Main.getGame());
                return;
            }

            ItemStack compass = new ItemStack(Material.COMPASS, 1);
            p.getInventory().addItem(compass);
        }
    }

}
