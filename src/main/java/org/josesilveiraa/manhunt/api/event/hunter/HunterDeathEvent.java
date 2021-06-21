package org.josesilveiraa.manhunt.api.event.hunter;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.josesilveiraa.manhunt.object.Game;

public class HunterDeathEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled;
    private final Player killer;
    private final Player target;
    private final Game game;

    public HunterDeathEvent(Player killer, Player target, Game game) {
        this.killer = killer;
        this.target = target;
        this.game = game;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public Player getKiller() {
        return killer;
    }

    public Player getTarget() {
        return target;
    }

    public Game getGame() {
        return game;
    }
}
