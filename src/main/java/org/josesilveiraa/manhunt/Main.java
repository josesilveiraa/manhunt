package org.josesilveiraa.manhunt;

import co.aikar.commands.PaperCommandManager;
import fr.mrmicky.fastboard.FastBoard;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;
import org.josesilveiraa.manhunt.command.ManhuntCommand;
import org.josesilveiraa.manhunt.config.api.Configuration;
import org.josesilveiraa.manhunt.config.ScoreboardConfig;
import org.josesilveiraa.manhunt.listener.*;
import org.josesilveiraa.manhunt.log.*;
import org.josesilveiraa.manhunt.manager.*;
import org.josesilveiraa.manhunt.object.Game;
import org.josesilveiraa.manhunt.task.*;
import org.josesilveiraa.manhunt.update.UpdateChecker;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public final class Main extends JavaPlugin {

    @Getter private static final Game game = new Game();

    @Getter private PaperCommandManager commandManager;

    @Getter private CommandRegisterer commandRegisterer;

    @Getter private static GameManager gameManager;

    @Getter private static Main plugin;

    @Getter private static final Map<UUID, FastBoard> boards = new HashMap<>();

    @Getter private static Configuration messages;

    @Getter private static Configuration scoreboardConfig;

    @Getter private static Configuration generalConfig;

    @Override
    public void onEnable() {
        plugin = this;
        init();
        initUpdateCheckerTask();
        getServer().getConsoleSender().sendMessage("§a[Manhunt] §fEnabled successfully.");
    }

    private void initUpdateCheckerTask() {
        new UpdateChecker(getPlugin()).check();
        new UpdateCheckerTask().runTaskTimerAsynchronously(getPlugin(), 20 * 60 * 10, 20 * 60 * 10);
    }

    private void init() {
        LogManager.log("Initializing command manager...", LogLevel.INFO);
        initCommandManager();

        LogManager.log("Enabling unstable APIs...", LogLevel.INFO);
        enableUnstableApis();

        LogManager.log("Initializing commands...", LogLevel.INFO);
        initCommands();

        LogManager.log("Initializing completions...", LogLevel.INFO);
        initCompletions();

        LogManager.log("Initializing listeners...", LogLevel.INFO);
        initListeners();

        LogManager.log("Initializing game manager...", LogLevel.INFO);
        initGameManager();

        LogManager.log("Initializing config...", LogLevel.INFO);
        initConfig();

        LogManager.log("Initializing runnable task...", LogLevel.INFO);
        initRunnable();

        LogManager.log("Initializing scoreboard...", LogLevel.INFO);
        initBoard();
    }

    private void initRunnable() {
        new CompassSetTask().runTaskTimerAsynchronously(getPlugin(), 0L, 20L);
        new GameTimeTask().runTaskTimerAsynchronously(getPlugin(), 20L, 20L);
    }

    private void initBoard() {
        getServer().getScheduler().runTaskTimer(getPlugin(), () -> {
            for(FastBoard board : boards.values()) {
                updateBoard(board);
            }
        }, 0, 20);
    }

    private void initConfig() {
        messages = new Configuration(this, "messages.yml");
        scoreboardConfig = new Configuration(this, "scoreboard.yml");
        generalConfig = new Configuration(this, "general.yml");

        messages.saveDefaultConfig();
        scoreboardConfig.saveDefaultConfig();
        generalConfig.saveDefaultConfig();
    }

    private void initCommands() {
        commandRegisterer = new CommandRegisterer(getCommandManager());
        commandRegisterer.register(new ManhuntCommand());
    }

    private void enableUnstableApis() {
        getCommandManager().enableUnstableAPI("help");
    }

    private void initListeners() {
        getServer().getPluginManager().registerEvents(new PlayerDeathListener(), getPlugin());
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), getPlugin());
        getServer().getPluginManager().registerEvents(new PlayerQuitListener(), getPlugin());
        getServer().getPluginManager().registerEvents(new EntityDeathListener(), getPlugin());
    }

    private void initGameManager() {
        gameManager = new GameManager();
    }

    private void initCommandManager() {
        commandManager = new PaperCommandManager(getPlugin());
    }

    private void initCompletions() {
        getCommandRegisterer().registerCompletion("configs", "default", "scoreboard", "messages");
    }

    private void updateBoard(FastBoard board) {
        Player p = board.getPlayer();

        if(getGame().isOccurring()) {
            boolean isRunner = getGameManager().isRunner(p);

            if(isRunner) {
                board.updateLines(ScoreboardConfig.RUNNER_LINES.stream().map(it -> it.replace("{name}", p.getName())).collect(Collectors.toList()));
            } else {
                board.updateLines(ScoreboardConfig.HUNTER_LINES.stream().map(it -> it.replace("{name}", p.getName()).replace("{target}", getGame().getRunner().getName())).collect(Collectors.toList()));
            }
        } else {
            board.updateLines(ScoreboardConfig.NO_GAME_OCCURRING_LINES.stream().map(it -> it.replace("{name}", p.getName())).collect(Collectors.toList()));
        }
    }


    @Override
    public void onDisable() {
        HandlerList.unregisterAll();
        getServer().getScheduler().cancelTasks(getPlugin());
        LogManager.log("Disabled successfully.", LogLevel.INFO);
    }
}
