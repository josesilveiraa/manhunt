package org.josesilveiraa.manhunt.listener;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.josesilveiraa.manhunt.Main;
import org.josesilveiraa.manhunt.api.event.hunter.HunterDeathEvent;
import org.josesilveiraa.manhunt.api.event.runner.RunnerDeathEvent;

public class PlayerDeathListener implements Listener {

    @EventHandler
    public void on(PlayerDeathEvent e) {
        Player p = e.getEntity();

        if (Main.getGame().isOccurring()) {
            if (Main.getGameManager().isRunner(p)) {

                RunnerDeathEvent event = new RunnerDeathEvent(p.getKiller(), p, Main.getGame());
                Bukkit.getServer().getPluginManager().callEvent(event);

                if(event.isCancelled()) {
                    e.setCancelled(true);
                    return;
                }

                p.getInventory().clear();
                Main.getGameManager().stopGame(Main.getGame());
                return;
            }

            HunterDeathEvent event = new HunterDeathEvent(p.getKiller(), p, Main.getGame());
            Bukkit.getServer().getPluginManager().callEvent(event);

            if(event.isCancelled()) {
                e.setCancelled(true);
                return;
            }

            ItemStack compass = new ItemStack(Material.COMPASS, 1);
            p.getInventory().addItem(compass);
        }
    }
}
