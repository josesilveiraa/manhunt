package org.josesilveiraa.huntsman.listener;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.josesilveiraa.huntsman.Main;
import org.josesilveiraa.huntsman.util.ItemBuilder;

public class PlayerDeathListener implements Listener {

    @EventHandler
    public void on(PlayerDeathEvent e) {
        Player p = e.getEntity();

        if (Main.getGame().isOccurring()) {
            if (p.getName().equals(Main.getGame().getRunner().getName())) {
                p.getInventory().clear();
                Main.getGame().setOccurring(false);
                return;
            }
            ItemStack compass = new ItemBuilder(Material.COMPASS, 1)
                    .name("§aTracker")
                    .lore("§7Use it to track the victim.")
                    .addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 1)
                    .flags(ItemFlag.HIDE_ENCHANTS)
                    .build();

            p.getInventory().addItem(compass);
        }
    }

}
