package org.josesilveiraa.manhunt.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.jetbrains.annotations.NotNull;
import org.josesilveiraa.manhunt.Main;
import org.josesilveiraa.manhunt.api.event.game.GameEndEvent;
import org.josesilveiraa.manhunt.config.Messages;

import java.util.List;
import java.util.stream.Collectors;

public class EntityDeathListener implements Listener {

    @EventHandler
    public void on(EntityDeathEvent e) {
        if(e.getEntity() instanceof EnderDragon) {
            if(Main.getGame().isOccurring()) {
                if(e.getEntity().getKiller() == Main.getGame().getRunner()) {

                    GameEndEvent event = new GameEndEvent(Main.getGame().getRunner(), Main.getGame());
                    Bukkit.getServer().getPluginManager().callEvent(event);

                    if(event.isCancelled()) {
                        e.setCancelled(true);
                        return;
                    }

                    Main.getGameManager().stopGame(Main.getGame());

                    List<String> messages = Messages.GAME_OVER.stream().map(a -> {
                        if (Main.getGame().getRunner() != null) {
                            return a.replace("{runner}", Main.getGame().getRunner().getName());
                        }
                        return null;
                    }).collect(Collectors.toList());

                    for(Player p : Bukkit.getServer().getOnlinePlayers()) {
                        p.sendMessage(arrayListToArray(messages));
                    }
                }
            }
        }
    }

    private static String[] arrayListToArray(@NotNull List<String> arrayList) {
        return arrayList.toArray(new String[0]);
    }

}
