package org.josesilveiraa.huntsman;

import co.aikar.commands.PaperCommandManager;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.josesilveiraa.huntsman.command.ManhuntCommand;
import org.josesilveiraa.huntsman.listener.PlayerDeathListener;
import org.josesilveiraa.huntsman.manager.GameManager;
import org.josesilveiraa.huntsman.object.Game;

public final class Main extends JavaPlugin {

    @Getter private static final Game game = new Game();

    @Getter private PaperCommandManager commandManager;

    @Getter private static GameManager gameManager;

    @Getter private static Main plugin;

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
        initRunnable();
    }

    private void initRunnable() {
        new BukkitRunnable() {
            @Override
            public void run() {
                if(Main.getGame().isOccurring()) {
                    for(Player hunter : Main.getGame().getHunters()) {
                        hunter.setCompassTarget(Main.getGame().getRunner().getLocation());
                        Bukkit.getConsoleSender().sendMessage("§a[Manhunt] §fSet " + hunter.getName() + "'s compass target location to " + hunter.getCompassTarget());
                    }
                }
            }
        }.runTaskTimerAsynchronously(getPlugin(), 10, 10);
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
