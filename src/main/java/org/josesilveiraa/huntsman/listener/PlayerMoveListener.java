package org.josesilveiraa.huntsman.listener;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CompassMeta;
import org.josesilveiraa.huntsman.Main;

public class PlayerMoveListener implements Listener {

    @EventHandler
    public void on(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        String name = p.getName();

        if(Main.getGame().isOccurring()) {
            if(name.equals(Main.getGame().getRunner().getName())) {
                for(Player hunter : Main.getGame().getHunters()) {
                    for(ItemStack is : hunter.getInventory()) {

                        if(is.getType().equals(Material.COMPASS) && is.getItemMeta() instanceof CompassMeta && is.getEnchantments().containsKey(Enchantment.ARROW_DAMAGE) && is.getItemMeta().hasDisplayName()) {
                            CompassMeta meta = (CompassMeta) is.getItemMeta();
                            meta.setLodestone(p.getLocation());
                        }
                    }
                }
            }
        }
    }

}
