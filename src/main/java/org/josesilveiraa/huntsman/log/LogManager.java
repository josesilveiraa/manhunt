package org.josesilveiraa.huntsman.log;

import org.bukkit.Bukkit;

public class LogManager {

    public static void log(String message, LogType logType) {
        switch (logType) {
            case INFO: {
                Bukkit.getConsoleSender().sendMessage("§a[Manhunt] §f" + message);
                break;
            }

            case WARN: {
                Bukkit.getConsoleSender().sendMessage("§e[Manhunt] §f" + message);
                break;
            }

            case ERROR: {
                Bukkit.getConsoleSender().sendMessage("§c[Manhunt] " + message);
                break;
            }

            default: {
                break;
            }
        }
    }

}
