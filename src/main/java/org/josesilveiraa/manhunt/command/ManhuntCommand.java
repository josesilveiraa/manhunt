package org.josesilveiraa.manhunt.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.*;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.josesilveiraa.manhunt.Main;
import org.josesilveiraa.manhunt.config.ConventionalConfig;
import org.josesilveiraa.manhunt.config.Messages;

import java.util.Collection;

@CommandAlias("manhunt")
@CommandPermission("manhunt.admin")
public class ManhuntCommand extends BaseCommand {

    @Subcommand("start")
    @Description("Starts a game.")
    public static void onStart(CommandSender sender) {

        if (Main.getGame().isOccurring()) {
            sender.sendMessage(Messages.GAME_ALREADY_OCCURRING);
            return;
        }

        int playerAmount = Bukkit.getOnlinePlayers().size();

        if (playerAmount < ConventionalConfig.MIN_PLAYERS) {
            sender.sendMessage(Messages.MIN_PLAYERS);
            return;
        }

        Player random = random(Bukkit.getOnlinePlayers());
        Main.getGameManager().setupGame(Bukkit.getOnlinePlayers(), random);
    }

    @Subcommand("stop")
    @Description("Stops a game")
    public static void onStop(CommandSender sender) {
        if (!Main.getGame().isOccurring()) {
            sender.sendMessage(Messages.NO_GAME_OCCURRING);
            return;
        }

        Main.getGameManager().stopGame(Main.getGame());
    }

    @Subcommand("reload")
    @Syntax("[config]")
    @CommandCompletion("@configs")
    @Description("Reloads a specific config")
    public static void onReload(CommandSender sender, @Optional @Default("DEFAULT") ConfigType configType) {
        switch (configType) {
            case DEFAULT: {
                Main.getPlugin().reloadConfig();
                break;
            }
            case MESSAGES: {
                Main.getMessages().reloadConfig();
                break;
            }
            case SCOREBOARD: {
                Main.getScoreboardConfig().reloadConfig();
                break;
            }
        }
        sender.sendMessage("§f" + configType.getName() + " §aconfig reloaded successfully.");
    }

    @HelpCommand
    public static void onHelp(CommandHelp help) {
        help.showHelp();
    }

    @CatchUnknown
    public static void onUnknown(CommandSender sender) {
        sender.sendMessage(Messages.UNKNOWN_COMMAND);
    }


    private static <T> T random(Collection<T> collection) {
        int num = (int) (Math.random() * collection.size());
        for(T t : collection) if (--num < 0) return t;
        throw new AssertionError();
    }

    private enum ConfigType {
        DEFAULT("Default"),
        MESSAGES("Messages"),
        SCOREBOARD("Scoreboard");

        private final String name;

        ConfigType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

}
