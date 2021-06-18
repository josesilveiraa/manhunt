package org.josesilveiraa.manhunt.object;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public final class Game {
    private List<Player> hunters = new ArrayList<>();
    @Nullable private Player runner;
    private boolean occurring = false;
}
