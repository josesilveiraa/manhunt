package org.josesilveiraa.manhunt.api;

import org.jetbrains.annotations.NotNull;
import org.josesilveiraa.manhunt.Manhunt;
import org.josesilveiraa.manhunt.manager.GameManager;
import org.josesilveiraa.manhunt.object.Game;

public final class ManhuntAPI {

    @NotNull
    public final Game getGame() {
        return Manhunt.getGame();
    }

    @NotNull
    public final GameManager getGameManager() {
        return Manhunt.getGameManager();
    }

}
