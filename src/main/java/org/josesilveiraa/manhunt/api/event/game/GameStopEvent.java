package org.josesilveiraa.manhunt.api.event.game;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.josesilveiraa.manhunt.object.Game;

public class GameStopEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private final Game game;
    private boolean cancelled;

    public GameStopEvent(Game game) {
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

    public Game getGame() {
        return game;
    }
}
