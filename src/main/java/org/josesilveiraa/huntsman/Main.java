package org.josesilveiraa.huntsman;

import co.aikar.commands.PaperCommandManager;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;
import org.josesilveiraa.huntsman.command.ManhuntCommand;
import org.josesilveiraa.huntsman.listener.PlayerDeathListener;
import org.josesilveiraa.huntsman.listener.PlayerMoveListener;
import org.josesilveiraa.huntsman.manager.GameManager;
import org.josesilveiraa.huntsman.object.Game;

public final class Main extends JavaPlugin {

    @Getter private static Main plugin;

    @Getter private static final Game game = new Game();

    @Getter private PaperCommandManager commandManager;

    @Getter private static GameManager gameManager;

    @Override
    public void onEnable() {
        plugin = this;
        init();
        Bukkit.getConsoleSender().sendMessage("§a[Manhunt] §fEnabled successfully.");
    }

    private void init() {
        initCommandManager();
        enableUnstableApis();
        initCommands();
        initListeners();
        initGameManager();
        initConfig();
    }

    private void initConfig() {
        saveDefaultConfig();
    }

    private void initCommands() {
        getCommandManager().registerCommand(new ManhuntCommand());
    }

    private void enableUnstableApis() {
        getCommandManager().enableUnstableAPI("help");
    }

    private void initListeners() {
        Bukkit.getPluginManager().registerEvents(new PlayerMoveListener(), getPlugin());
        Bukkit.getPluginManager().registerEvents(new PlayerDeathListener(), getPlugin());
    }

    private void initGameManager() {
        gameManager = new GameManager();
    }

    private void initCommandManager() {
        commandManager = new PaperCommandManager(getPlugin());
    }


    @Override
    public void onDisable() {
        HandlerList.unregisterAll();
        Bukkit.getConsoleSender().sendMessage("§a[Manhunt] §fDisabled successfully.");
    }
}
