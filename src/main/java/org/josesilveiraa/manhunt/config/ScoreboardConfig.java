package org.josesilveiraa.manhunt.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.josesilveiraa.manhunt.Main;

import java.util.List;
import java.util.stream.Collectors;

public class ScoreboardConfig {

    private static final FileConfiguration config = Main.getScoreboardConfig().getConfig();

    @SuppressWarnings("all")
    public static final String TITLE = config.getString("scoreboard.title").replace("&", "§");

    public static final List<String> RUNNER_LINES = config.getStringList("scoreboard.lines.runner").stream().map(it -> it.replace("&", "§")).collect(Collectors.toList());
    public static final List<String> HUNTER_LINES = config.getStringList("scoreboard.lines.hunter").stream().map(it -> it.replace("&", "§")).collect(Collectors.toList());
    public static final List<String> NO_GAME_OCCURRING_LINES = config.getStringList("scoreboard.lines.no-game-occurring").stream().map(it -> it.replace("&", "§")).collect(Collectors.toList());

}
