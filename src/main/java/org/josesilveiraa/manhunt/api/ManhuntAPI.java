package org.josesilveiraa.manhunt.api;

import org.jetbrains.annotations.NotNull;
import org.josesilveiraa.manhunt.Main;
import org.josesilveiraa.manhunt.manager.GameManager;
import org.josesilveiraa.manhunt.object.Game;

public final class ManhuntAPI {

    @NotNull
    public final Game getGame() {
        return Main.getGame();
    }

    @NotNull
    public final GameManager getGameManager() {
        return Main.getGameManager();
    }

}
