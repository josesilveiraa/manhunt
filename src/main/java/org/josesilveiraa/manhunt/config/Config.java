package org.josesilveiraa.manhunt.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.josesilveiraa.manhunt.Manhunt;

public class Config {

    private static final FileConfiguration config = Manhunt.getGeneralConfig().getConfig();

    public static final int MIN_PLAYERS = config.getInt("general.min-players");

}
