package org.josesilveiraa.manhunt.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.josesilveiraa.manhunt.Main;

import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("all")
public class Messages {

    private static final FileConfiguration config = Main.getMessages().getConfig();

    public static final List<String> GAME_STOPPED_MESSAGE = config.getStringList("messages.game-stopped").stream().map(a -> a.replace("&", "§")).collect(Collectors.toList());
    public static final List<String> GAME_STARTED_MESSAGE = config.getStringList("messages.game-started").stream().map(a -> a.replace("&", "§")).collect(Collectors.toList());
    public static final List<String> GAME_INFO = config.getStringList("messages.game-info").stream().map(a -> a.replace("&", "§")).collect(Collectors.toList());
    public static final List<String> GAME_OVER = config.getStringList("messages.game-over").stream().map(a -> a.replace("&", "§")).collect(Collectors.toList());

    public static final String STARTED_TITLE_RUNNER = config.getString("messages.title.runner.title").replace("&", "§");
    public static final String STARTED_SUBTITLE_RUNNER = config.getString("messages.title.runner.subtitle").replace("&", "§");
    public static final String STARTED_TITLE_HUNTER = config.getString("messages.title.hunter.title").replace("&", "§");
    public static final String STARTED_SUBTITLE_HUNTER = config.getString("messages.title.hunter.subtitle").replace("&", "§");
    public static final String UNKNOWN_COMMAND = config.getString("messages.unknown-subcommand").replace("&", "§");
    public static final String NO_GAME_OCCURRING = config.getString("messages.no-game-occurring").replace("&", "§");
    public static final String GAME_ALREADY_OCCURRING = config.getString("messages.game-already-occurring").replace("&", "§");
    public static final String MIN_PLAYERS = config.getString("messages.not-enough-players").replace("&", "§").replace("{min}", String.valueOf(Config.MIN_PLAYERS));
    public static final String CONFIG_RELOADED = config.getString("messages.config-reloaded").replace("&", "§");


}
