package org.josesilveiraa.manhunt.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.josesilveiraa.manhunt.Main;

import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("all")
public class Config {

    private static final FileConfiguration config = Main.getPlugin().getConfig();

    public static final int MIN_PLAYERS = config.getInt("general.min-players");

    public static final List<String> GAME_STOPPED_MESSAGE = config.getStringList("messages.game-stopped").stream().map((a) -> a.replace("&", "§")).collect(Collectors.toList());
    public static final List<String> GAME_STARTED_MESSAGE = config.getStringList("messages.game-started").stream().map((a) -> a.replace("&", "§")).collect(Collectors.toList());

    public static final String STARTED_TITLE_RUNNER = config.getString("messages.title.runner.title").replace("&", "§");
    public static final String STARTED_SUBTITLE_RUNNER = config.getString("messages.title.runner.subtitle").replace("&", "§");
    public static final String STARTED_TITLE_HUNTER = config.getString("messages.title.hunter.title").replace("&", "§");
    public static final String STARTED_SUBTITLE_HUNTER = config.getString("messages.title.hunter.subtitle").replace("&", "§");

}
