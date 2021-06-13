package org.josesilveiraa.manhunt;

import co.aikar.commands.PaperCommandManager;
import fr.mrmicky.fastboard.FastBoard;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;
import org.josesilveiraa.manhunt.command.ManhuntCommand;
import org.josesilveiraa.manhunt.listener.*;
import org.josesilveiraa.manhunt.log.*;
import org.josesilveiraa.manhunt.manager.GameManager;
import org.josesilveiraa.manhunt.object.Game;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class Main extends JavaPlugin {

    @Getter private static final Game game = new Game();

    @Getter private PaperCommandManager commandManager;

    @Getter private static GameManager gameManager;

    @Getter private static Main plugin;

    @Getter private static final Map<UUID, FastBoard> boards = new HashMap<>();

    @Override
    public void onEnable() {
        plugin = this;
        init();
        Bukkit.getConsoleSender().sendMessage("§a[Manhunt] §fEnabled successfully.");
    }

    private void init() {
        LogManager.log("Initializing command manager...", LogType.INFO);
        initCommandManager();

        LogManager.log("Enabling unstable APIs...", LogType.INFO);
        enableUnstableApis();

        LogManager.log("Initializing commands...", LogType.INFO);
        initCommands();

        LogManager.log("Initializing listeners...", LogType.INFO);
        initListeners();

        LogManager.log("Initializing game manager...", LogType.INFO);
        initGameManager();

        LogManager.log("Initializing config...", LogType.INFO);
        initConfig();

        LogManager.log("Initializing runnable task...", LogType.INFO);
        initRunnable();

        LogManager.log("Initializing scoreboard...", LogType.INFO);
        initBoard();
    }

    private void initRunnable() {

        getServer().getScheduler().runTaskTimer(getPlugin(), () -> {
            if(Main.getGame().isOccurring()) {
                for(Player hunter : Main.getGame().getHunters()) {
                    hunter.setCompassTarget(Main.getGame().getRunner().getLocation());
                }
            }
        }, 0, 20);
    }

    private void initBoard() {
        getServer().getScheduler().runTaskTimer(getPlugin(), () -> {
            for(FastBoard board : boards.values()) {
                updateBoard(board);
            }
        }, 0, 20);
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
        Bukkit.getPluginManager().registerEvents(new BoardHelperListener(), getPlugin());
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), getPlugin());
        Bukkit.getPluginManager().registerEvents(new PlayerLeaveListener(), getPlugin());
    }

    private void initGameManager() {
        gameManager = new GameManager();
    }

    private void initCommandManager() {
        commandManager = new PaperCommandManager(getPlugin());
    }

    private void updateBoard(FastBoard board) {
        Player p = board.getPlayer();

        if(getGame().isOccurring()) {
            boolean isRunner = getGameManager().isRunner(p);

            if(isRunner) {
                board.updateLines(
                        "",
                        "§fPlayer name: §a" + p.getName(),
                        "§fYour status: §arunner",
                        "",
                        "§fGame status: §aon",
                        "§fGoal: §arun",
                        "",
                        "§eamoojosesilveira.com"
                );
            } else {
                board.updateLines(
                        "",
                        "§fPlayer name: §a" + p.getName(),
                        "§fYour status: §ahunter",
                        "",
                        "§fGame status: §aon",
                        "§fTarget: §a" + getGame().getRunner().getName(),
                        "",
                        "§eamoojosesilveira.com"
                );
            }
        } else {
            board.updateLines(
                    "",
                    "§fPlayer name: §a" + p.getName(),
                    "§fGame status: §coff",
                    "",
                    "§eamoojosesilveira.com"
            );
        }
    }


    @Override
    public void onDisable() {
        HandlerList.unregisterAll();
        LogManager.log("Disabled successfully", LogType.INFO);
    }
}
