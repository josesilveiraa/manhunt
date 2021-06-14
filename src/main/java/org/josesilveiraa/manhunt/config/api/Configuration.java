package org.josesilveiraa.manhunt.config.api;

import lombok.SneakyThrows;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class Configuration {

    private final JavaPlugin plugin;
    private final String name;
    private final File file;
    private FileConfiguration fileConfiguration;

    public <T extends JavaPlugin> Configuration(T plugin, String name) {
        this(plugin, new File(plugin.getDataFolder(), name));
    }

    public <T extends JavaPlugin> Configuration(T plugin, File file) {
        this.plugin = plugin;
        this.name = file.getName();
        this.file = file;
    }

    public void reloadConfig() {
        fileConfiguration = YamlConfiguration.loadConfiguration(this.file);
    }

    public FileConfiguration getConfig() {
        if(fileConfiguration == null) {
            reloadConfig();
        }
        return fileConfiguration;
    }

    @SneakyThrows
    public void saveConfig() {
        if(fileConfiguration == null || file == null) {
            return;
        }

        getConfig().save(file);
    }

    public void saveDefaultConfig() {
        if(!file.exists()) {
            plugin.saveResource(name, false);
        }
    }

    @SneakyThrows @SuppressWarnings("all")
    public void createNewFile(boolean replaceExisting) {
        if(file.exists() && replaceExisting) {
            file.delete();
        }
        file.createNewFile();
    }

}
