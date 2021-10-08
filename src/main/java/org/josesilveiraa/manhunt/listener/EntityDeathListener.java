package org.josesilveiraa.manhunt.listener;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.jetbrains.annotations.NotNull;
import org.josesilveiraa.manhunt.Manhunt;
import org.josesilveiraa.manhunt.api.event.game.GameEndEvent;
import org.josesilveiraa.manhunt.config.Messages;
import org.josesilveiraa.manhunt.util.ArrayListToArray;

import java.util.List;
import java.util.stream.Collectors;

public class EntityDeathListener implements Listener {

    @EventHandler
    public void on(EntityDeathEvent e) {
        if(e.getEntity() instanceof EnderDragon) {
            if(Manhunt.getGame().isOccurring()) {
                if(e.getEntity().getKiller() == Manhunt.getGame().getRunner()) {

                    GameEndEvent event = new GameEndEvent(Manhunt.getGame().getRunner(), Manhunt.getGame());
                    Bukkit.getServer().getPluginManager().callEvent(event);

                    if(event.isCancelled()) {
                        e.setCancelled(true);
                        return;
                    }

                    Manhunt.getGameManager().stopGame();

                    List<String> messages = Messages.GAME_OVER.stream().map(it -> it.replace("{runner}", Manhunt.getGame().getRunner().getName())).collect(Collectors.toList());

                    for(Player p : Bukkit.getServer().getOnlinePlayers()) {
                        p.sendMessage(ArrayListToArray.transform(messages));
                        p.sendTitle(Messages.GAME_OVER_TITLE, Messages.GAME_OVER_SUBTITLE, 20, 20, 20);
                        p.playSound(p.getLocation(), Sound.ENTITY_ENDER_DRAGON_DEATH, 1f, 1f);
                    }
                }
            }
        }
    }

}
