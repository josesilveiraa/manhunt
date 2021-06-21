package org.josesilveiraa.manhunt.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.josesilveiraa.manhunt.Main;

public class Config {

    private static final FileConfiguration config = Main.getGeneralConfig().getConfig();

    public static final int MIN_PLAYERS = config.getInt("general.min-players");

}
