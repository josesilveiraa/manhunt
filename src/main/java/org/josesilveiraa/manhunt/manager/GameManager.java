package org.josesilveiraa.manhunt.manager;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.josesilveiraa.manhunt.Main;
import org.josesilveiraa.manhunt.object.Game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class GameManager {

    public void setupGame(Collection<? extends Player> runners, Player runner) {
        Main.getGame().setOccurring(true);

        ItemStack compass = new ItemStack(Material.COMPASS, 1);

        for(Player player : runners) {

            if(player.getName().equals(runner.getName())) {
                continue;
            }

            String hunterTitle = Objects.requireNonNull(Main.getPlugin().getConfig().getString("messages.title.hunter.title")).replace("&", "§");
            String hunterSubtitle = Objects.requireNonNull(Main.getPlugin().getConfig().getString("messages.title.hunter.subtitle")).replace("&", "§");

            Main.getGame().getHunters().add(player);
            player.getInventory().addItem(compass);
            player.sendTitle(hunterTitle, hunterSubtitle, 20, 20, 20);
            player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_DEATH, 1f, 1f);
        }

        String runnerTitle = Objects.requireNonNull(Main.getPlugin().getConfig().getString("messages.title.runner.title")).replace("&", "§");
        String runnerSubtitle = Objects.requireNonNull(Main.getPlugin().getConfig().getString("messages.title.runner.subtitle")).replace("&", "§");

        Main.getGame().setRunner(runner);
        runner.sendTitle(runnerTitle, runnerSubtitle, 20, 20 ,20);
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

    public boolean isRunner(Player player) {
        return Main.getGame().getRunner().getName().equals(player.getName());
    }

}
