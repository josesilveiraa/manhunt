package org.josesilveiraa.manhunt.config.api;

import lombok.SneakyThrows;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class Configuration {

    private final JavaPlugin plugin;
    private final String name;
    private final File file;
    private FileConfiguration fileConfiguration;

    public <T extends JavaPlugin> Configuration(@NotNull T plugin, @NotNull String name) {
        this(plugin, new File(plugin.getDataFolder(), name));
    }

    public <T extends JavaPlugin> Configuration(@NotNull T plugin, @NotNull File file) {
        this.plugin = plugin;
        this.name = file.getName();
        this.file = file;
    }

    public final void reloadConfig() {
        this.fileConfiguration = YamlConfiguration.loadConfiguration(this.file);
    }

    public final FileConfiguration getConfig() {
        if(this.fileConfiguration == null) {
            reloadConfig();
        }
        return this.fileConfiguration;
    }

    @SneakyThrows
    public final void saveConfig() {
        if(this.fileConfiguration == null || this.file == null) {
            return;
        }

        this.getConfig().save(this.file);
    }

    public final void saveDefaultConfig() {
        if(!this.file.exists()) {
            this.plugin.saveResource(this.name, false);
        }
    }

    @SneakyThrows @SuppressWarnings("all")
    public final void createNewFile(@NotNull boolean replaceExisting) {
        if(this.file.exists() && replaceExisting) {
            this.file.delete();
        }
        this.file.createNewFile();
    }

}
