package org.josesilveiraa.huntsman.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.josesilveiraa.huntsman.Main;
import org.josesilveiraa.huntsman.util.ItemBuilder;

import java.util.Random;
import java.util.UUID;

@CommandAlias("manhunt")
@CommandPermission("manhunt.admin")
public class StartCommand extends BaseCommand {

    @Default @Subcommand("start") @Syntax("<+tag> start") @Description("Starts a game.")
    public static void onStart(Player p, String[] args) {

        if(Main.getGame().isOccurring()) {
            p.sendMessage("§cThere's already a game occurring.");
            return;
        }

        int playerAmount = Bukkit.getOnlinePlayers().size();

        if(playerAmount < 3) {
            p.sendMessage("§cThe plugin needs at least three players to work properly.");
            return;
        }


        Player random = getRandomPlayer();

        ItemStack compass = new ItemBuilder(Material.COMPASS, 1)
                .name("§aTracker")
                .lore("§7Use it to track the victim.")
                .addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 1)
                .flags(ItemFlag.HIDE_ENCHANTS)
                .build();

        Main.getGame().setOccurring(true);

        for(Player player : Bukkit.getOnlinePlayers()) {
            UUID uuid = player.getUniqueId();


            if(!(uuid.toString().equals(random.getUniqueId().toString()))) {
                Main.getGame().getHunters().add(player);
                player.getInventory().addItem(compass);
                player.sendTitle("§aYou're the hunter!", "§7Hunt the runner!", 20, 20, 20);
            } else {
                Main.getGame().setRunner(player);
                player.sendTitle("§cYou're the runner!", "§7Run from the hunters!", 20, 20 ,20);
            }
        }
    }


    private static Player getRandomPlayer() {
        Random rnd = new Random();
        int i = rnd.nextInt(Bukkit.getOnlinePlayers().size());
        return (Player) Bukkit.getOnlinePlayers().toArray()[i];
    }

}
