package org.josesilveiraa.huntsman.object;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class Game {
    private List<Player> hunters = new ArrayList<>();
    private Player runner;
    private boolean occurring = false;
}
