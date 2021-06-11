package org.josesilveiraa.manhunt.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.josesilveiraa.manhunt.Main;

import java.util.Collection;

@CommandAlias("manhunt")
@CommandPermission("manhunt.admin")
public class ManhuntCommand extends BaseCommand {

    @Subcommand("start")
    @Syntax("<+tag> start")
    @Description("Starts a game.")
    public static void onStart(Player p) {

        if (Main.getGame().isOccurring()) {
            p.sendMessage("§cThere's already a game occurring.");
            return;
        }

        int playerAmount = Bukkit.getOnlinePlayers().size();
        int minPlayers = Main.getPlugin().getConfig().getInt("general.min-players");

        if (playerAmount < minPlayers) {
            p.sendMessage("§cThe plugin needs at least " + minPlayers + " players to work properly.");
            return;
        }

        Player random = random(Bukkit.getOnlinePlayers());
        Main.getGameManager().setupGame(Bukkit.getOnlinePlayers(), random);
    }

    @Subcommand("stop")
    @Syntax("<+tag> stop")
    @Description("Stops a game")
    public static void onStop(Player p) {
        if (!Main.getGame().isOccurring()) {
            p.sendMessage("§cThere isn't any game occurring right now.");
            return;
        }

        Main.getGameManager().stopGame(Main.getGame());
    }

    @HelpCommand
    public static void onHelp(CommandHelp help) {
        help.showHelp();
    }

    @CatchUnknown
    public static void onUnknown(Player p) {
        p.sendMessage("§cUnknown command. Try /manhunt help for help.");
    }


    private static <T> T random(Collection<T> collection) {
        int num = (int) (Math.random() * collection.size());
        for(T t : collection) if (--num < 0) return t;
        throw new AssertionError();
    }

}