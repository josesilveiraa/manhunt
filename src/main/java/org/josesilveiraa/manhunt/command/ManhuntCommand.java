package org.josesilveiraa.manhunt.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.*;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.josesilveiraa.manhunt.Main;
import org.josesilveiraa.manhunt.config.*;
import org.josesilveiraa.manhunt.config.api.Configuration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@CommandAlias("manhunt|mh")
@CommandPermission("manhunt.admin")
public final class ManhuntCommand extends BaseCommand {

    @Subcommand("start")
    @Syntax("[player]")
    @Description("Starts a game.")
    @CommandCompletion("@players")
    public static void onStart(CommandSender sender, @Optional OnlinePlayer onlinePlayer) {

        if (Main.getGame().isOccurring()) {
            sender.sendMessage(Messages.GAME_ALREADY_OCCURRING);
            return;
        }

        int playerAmount = Bukkit.getOnlinePlayers().size();

        if (playerAmount < Config.MIN_PLAYERS) {
            sender.sendMessage(Messages.MIN_PLAYERS);
            return;
        }

        Player target = onlinePlayer != null ? onlinePlayer.getPlayer() : random(Bukkit.getOnlinePlayers());

        Main.getGameManager().setupGame(Bukkit.getOnlinePlayers(), target);
    }

    @Subcommand("stop")
    @Description("Stops a game.")
    public static void onStop(CommandSender sender) {
        if (!Main.getGame().isOccurring()) {
            sender.sendMessage(Messages.NO_GAME_OCCURRING);
            return;
        }

        sender.sendMessage("§aGame stopped successfully.");
        Main.getGameManager().stopGame(Main.getGame());
    }

    @Subcommand("info|status")
    @Description("Shows information about the occurring game.")
    public static void onInfo(CommandSender sender) {
        if(!Main.getGame().isOccurring()) {
            sender.sendMessage(Messages.NO_GAME_OCCURRING);
            return;
        }

        List<String> messages = new ArrayList<>();

        for(String s : Messages.GAME_INFO) {
            if (Main.getGame().getRunner() != null) {
                messages.add(s.replace("{runner}", Main.getGame().getRunner().getName()).replace("{hunter_list}", playerList(Main.getGame().getHunters())).replace("{seconds}", String.valueOf(Main.getGame().getTotalSeconds())));
            }
        }

        sender.sendMessage(arrayListToArray(messages));
    }

    @Subcommand("reload|rl")
    @Syntax("[config]")
    @CommandCompletion("@configs")
    @Description("Reloads a specific config.")
    public static void onReload(CommandSender sender, @Optional @Default("default") ConfigType configType) {
        configType.reloadCorrespondingConfig();
        sender.sendMessage(Messages.CONFIG_RELOADED.replace("{type}", configType.getName()));
    }

    @HelpCommand
    @Default
    public static void onHelp(CommandHelp help) {
        help.showHelp();
    }

    @CatchUnknown
    public static void onUnknown(CommandSender sender) {
        sender.sendMessage(Messages.UNKNOWN_COMMAND);
    }


    private static String playerList(List<Player> players) {
        StringBuilder sb = new StringBuilder();

        for(Player p : players) {
            if(players.indexOf(p) == players.size() - 1) {
                sb.append(p.getName());
                continue;
            }
            sb.append(p.getName()).append(",").append(" ");
        }

        return sb.toString();
    }

    private static <T> T random(Collection<T> collection) {
        int num = (int) (Math.random() * collection.size());
        for(T t : collection) if (--num < 0) return t;
        throw new AssertionError();
    }

    private static String[] arrayListToArray(@NotNull List<String> arrayList) {
        return arrayList.toArray(new String[0]);
    }

    private enum ConfigType {
        DEFAULT("Default", Main.getGeneralConfig()),
        MESSAGES("Messages", Main.getMessages()),
        SCOREBOARD("Scoreboard", Main.getScoreboardConfig());

        private final String name;
        private final Configuration correspondingFile;

        ConfigType(String name, Configuration correspondingFile) {
            this.name = name;
            this.correspondingFile = correspondingFile;
        }

        public String getName() {
            return name;
        }

        public void reloadCorrespondingConfig() {
            this.correspondingFile.reloadConfig();
        }
    }

}
