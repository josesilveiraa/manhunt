package org.josesilveiraa.huntsman.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.josesilveiraa.huntsman.Main;

import java.util.Random;

@CommandAlias("manhunt")
@CommandPermission("manhunt.admin")
public class ManhuntCommand extends BaseCommand {

    @Subcommand("stop") @Syntax("<+tag> stop") @Description("Stops a game")
    public static void onStop(Player p, String[] args) {
        if(!Main.getGame().isOccurring()) {
            p.sendMessage("§cThere isn't any game occurring right now.");
            return;
        }

        Main.getGameManager().stopGame(Main.getGame());
    }

    @Subcommand("start") @Syntax("<+tag> start") @Description("Starts a game.")
    public static void onStart(Player p, String[] args) {

        if(Main.getGame().isOccurring()) {
            p.sendMessage("§cThere's already a game occurring.");
            return;
        }

        int playerAmount = Bukkit.getOnlinePlayers().size();

        if(playerAmount < 3) {
            p.sendMessage("§cThe plugin needs at least three players to work properly.");
            return;
        }

        if(args.length != 0) {
            Player target = Bukkit.getPlayer(args[0]);

            if(target == null || !target.isOnline()) {
                p.sendMessage("§cWhoops! It looks like this player is invalid or offline.");
                return;
            }

            Main.getGameManager().setupGame(Bukkit.getOnlinePlayers(), target);
        }


        Player random = getRandomPlayer();

        Main.getGameManager().setupGame(Bukkit.getOnlinePlayers(), random);
    }

    @HelpCommand
    public static void onHelp(Player p, CommandHelp help) {
        help.showHelp();
    }

    @CatchUnknown
    public static void onUnknown(Player p) {
        p.sendMessage("§cUnknown command. Try /manhunt help for help.");
    }


    private static Player getRandomPlayer() {
        Random rnd = new Random();
        int i = rnd.nextInt(Bukkit.getOnlinePlayers().size());
        return (Player) Bukkit.getOnlinePlayers().toArray()[i];
    }

}
