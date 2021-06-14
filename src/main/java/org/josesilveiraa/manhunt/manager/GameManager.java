package org.josesilveiraa.manhunt.manager;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.josesilveiraa.manhunt.Main;
import org.josesilveiraa.manhunt.config.Messages;
import org.josesilveiraa.manhunt.object.Game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GameManager {

    public void setupGame(Collection<? extends Player> players, Player runner) {
        Main.getGame().setOccurring(true);

        ItemStack compass = new ItemStack(Material.COMPASS, 1);

        for(Player player : players) {

            player.sendMessage(arrayListToArray(Messages.GAME_STARTED_MESSAGE));

            if(player.getName().equals(runner.getName())) {
                continue;
            }

            Main.getGame().getHunters().add(player);
            player.getInventory().addItem(compass);
            player.sendTitle(Messages.STARTED_TITLE_HUNTER, Messages.STARTED_SUBTITLE_HUNTER, 20, 20, 20);
            player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_DEATH, 1f, 1f);
        }

        Main.getGame().setRunner(runner);
        runner.sendTitle(Messages.STARTED_TITLE_RUNNER, Messages.STARTED_SUBTITLE_RUNNER, 20, 20 ,20);
        runner.playSound(runner.getLocation(), Sound.ENTITY_WITHER_AMBIENT, 1f, 1f);
    }

    public void stopGame(Game game) {
        if(game.isOccurring()) {
            game.setOccurring(false);
            game.setHunters(new ArrayList<>());
            game.setRunner(null);
        }

        for(Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(arrayListToArray(Messages.GAME_STOPPED_MESSAGE));
        }
    }

    public void addHunter(Player player) {
        Main.getGame().getHunters().add(player);
        player.getInventory().addItem(new ItemStack(Material.COMPASS, 1));
    }

    public void removeHunter(Player player) {
        Main.getGame().getHunters().remove(player);
    }

    public String[] arrayListToArray(List<String> arrayList) {
        return arrayList.toArray(new String[0]);
    }

    public boolean isRunner(Player player) {
        return Main.getGame().getRunner().getName().equals(player.getName());
    }

}
