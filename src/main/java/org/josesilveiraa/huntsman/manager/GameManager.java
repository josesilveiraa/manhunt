package org.josesilveiraa.huntsman.manager;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.josesilveiraa.huntsman.Main;
import org.josesilveiraa.huntsman.object.Game;

import java.util.ArrayList;
import java.util.Collection;

public class GameManager {

    public void setupGame(Collection<? extends Player> runners, Player runner) {
        Main.getGame().setOccurring(true);

        ItemStack compass = new ItemStack(Material.COMPASS, 1);

        for(Player player : runners) {

            if(player.getName().equals(runner.getName())) {
                continue;
            }

            Main.getGame().getHunters().add(player);
            player.getInventory().addItem(compass);
            player.sendTitle("§aYou're the hunter!", "§7Hunt the runner!", 20, 20, 20);
            player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_DEATH, 1f, 1f);
        }

        Main.getGame().setRunner(runner);
        runner.sendTitle("§cYou're the runner!", "§7Run from the hunters!", 20, 20 ,20);
        runner.playSound(runner.getLocation(), Sound.ENTITY_WITHER_AMBIENT, 1f, 1f);
    }

    public void stopGame(Game game) {
        if(game.isOccurring()) {
            game.setOccurring(false);
            game.setHunters(new ArrayList<>());
            game.setRunner(null);
        }

        for(Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(new String[] {
                    "",
                    "§c§lMANHUNT",
                    "§7* The game has stopped.",
                    ""
            });
        }
    }

}
