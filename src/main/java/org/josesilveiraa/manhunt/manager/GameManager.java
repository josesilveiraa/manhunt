package org.josesilveiraa.manhunt.manager;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.josesilveiraa.manhunt.Manhunt;
import org.josesilveiraa.manhunt.api.event.game.GameStartEvent;
import org.josesilveiraa.manhunt.api.event.game.GameStopEvent;
import org.josesilveiraa.manhunt.api.event.hunter.HunterAddedEvent;
import org.josesilveiraa.manhunt.api.event.hunter.HunterRemovedEvent;
import org.josesilveiraa.manhunt.config.Messages;
import org.josesilveiraa.manhunt.object.Game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class GameManager {

    /**
     * Setups a game to play.
     * @param players the hunters
     * @param runner the speedrunner
     */
    public final void setupGame(@NotNull Collection<? extends Player> players, @NotNull Player runner) {
        GameStartEvent event = new GameStartEvent(Manhunt.getGame());
        Bukkit.getServer().getPluginManager().callEvent(event);

        if(event.isCancelled()) {
            return;
        }

        Manhunt.getGame().setOccurring(true);

        ItemStack compass = new ItemStack(Material.COMPASS, 1);

        for(Player player : players) {

            player.sendMessage(arrayListToArray(Messages.GAME_STARTED_MESSAGE));

            if(player.getName().equals(runner.getName())) {
                continue;
            }

            Manhunt.getGame().getHunters().add(player);

            player.getInventory().addItem(compass);
            player.sendTitle(Messages.STARTED_TITLE_HUNTER, Messages.STARTED_SUBTITLE_HUNTER, 20, 20, 20);
            player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_DEATH, 1f, 1f);
        }

        Manhunt.getGame().setRunner(runner);
        runner.sendTitle(Messages.STARTED_TITLE_RUNNER, Messages.STARTED_SUBTITLE_RUNNER, 20, 20 ,20);
        runner.playSound(runner.getLocation(), Sound.ENTITY_WITHER_AMBIENT, 1f, 1f);
    }

    /**
     * Stops the occurring game.
     * @param game the game to be stopped (usually the Main class one)
     */
    public final void stopGame(@NotNull Game game) {
        if(game.isOccurring()) {

            GameStopEvent event = new GameStopEvent(game);
            Bukkit.getServer().getPluginManager().callEvent(event);

            if(event.isCancelled()) {
                return;
            }

            game.setOccurring(false);
            game.setHunters(new ArrayList<>());
            game.setRunner(null);
        }

        for(Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(arrayListToArray(Messages.GAME_STOPPED_MESSAGE));
        }
    }

    /**
     * Adds a player to the hunter list.
     * @param player the player to be added
     */
    public final void addHunter(@NotNull Player player) {

        HunterAddedEvent event = new HunterAddedEvent(player);
        Bukkit.getServer().getPluginManager().callEvent(event);

        if(event.isCancelled()) {
            return;
        }

        Manhunt.getGame().getHunters().add(player);
        player.getInventory().addItem(new ItemStack(Material.COMPASS, 1));
    }


    /**
     * Removes a player from the hunter list
     * @param player the player to be removed
     */
    public final void removeHunter(@NotNull Player player) {

        HunterRemovedEvent event = new HunterRemovedEvent(player);
        Bukkit.getServer().getPluginManager().callEvent(event);

        if(event.isCancelled()) {
            return;
        }

        Manhunt.getGame().getHunters().remove(player);
    }

    public final String[] arrayListToArray(@NotNull List<String> arrayList) {
        return arrayList.toArray(new String[0]);
    }

    public boolean isRunner(Player player) {
        if(Manhunt.getGame().isOccurring()) {
            return Manhunt.getGame().getRunner().getName().equals(player.getName());
        }
        return false;
    }

}
