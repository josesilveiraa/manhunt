package org.josesilveiraa.manhunt.api;

import org.josesilveiraa.manhunt.Main;
import org.josesilveiraa.manhunt.manager.GameManager;
import org.josesilveiraa.manhunt.object.Game;

public class ManhuntAPI {

    public static Game getGame() {
        return Main.getGame();
    }

    public static GameManager getGameManager() {
        return Main.getGameManager();
    }

}
