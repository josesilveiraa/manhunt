package org.josesilveiraa.manhunt.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.josesilveiraa.manhunt.Main;

@SuppressWarnings("all")
public class ConventionalConfig {

    private static final FileConfiguration config = Main.getPlugin().getConfig();

    public static final int MIN_PLAYERS = config.getInt("general.min-players");


}
