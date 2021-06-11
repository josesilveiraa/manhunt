package org.josesilveiraa.manhunt.api;

import org.josesilveiraa.manhunt.Main;
import org.josesilveiraa.manhunt.log.LogManager;
import org.josesilveiraa.manhunt.manager.GameManager;
import org.josesilveiraa.manhunt.object.Game;

public class ManhuntAPI {

    public Game getGame() {
        return Main.getGame();
    }

    public GameManager getGameManager() {
        return Main.getGameManager();
    }


}
