package org.josesilveiraa.huntsman.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.*;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.josesilveiraa.huntsman.Main;

import java.util.Collection;

@CommandAlias("manhunt")
@CommandPermission("manhunt.admin")
public class ManhuntCommand extends BaseCommand {

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

    @Subcommand("start")
    @Syntax("<+tag> start [runner]")
    @Description("Starts a game.")
    public static void onStart(Player p, @Optional OnlinePlayer target) {

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

        if (target != null) {
            if(!target.getPlayer().isOnline()) {
                return;
            }
            Main.getGameManager().setupGame(Bukkit.getOnlinePlayers(), target.getPlayer());
            return;
        }

        Player random = random(Bukkit.getOnlinePlayers());
        Main.getGameManager().setupGame(Bukkit.getOnlinePlayers(), random);
    }

    @Subcommand("info")
    @Syntax("<+tag> info")
    @Description("Shows detailed info about the game.")
    public static void onInfo(Player p) {
        boolean occurring = Main.getGame().isOccurring();

        if(!occurring) {
            p.sendMessage("§cThere isn't any game occurring right now.");
            return;
        }

        p.sendMessage(new String[] {
                "§7Is the game occurring? §ayes",
                "§7Target: §c" + Main.getGame().getRunner().getName()
        });
    }

    @HelpCommand
    public static void onHelp(Player p, CommandHelp help) {
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
