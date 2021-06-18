package org.josesilveiraa.manhunt.manager;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.BukkitCommandCompletionContext;
import co.aikar.commands.CommandCompletions;
import co.aikar.commands.PaperCommandManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class CommandRegisterer {

    private final PaperCommandManager commandManager;

    public CommandRegisterer(PaperCommandManager commandManager) {
        this.commandManager = commandManager;
    }

    public void register(BaseCommand baseCommand) {
        this.commandManager.registerCommand(baseCommand);
    }

    public void registerCompletion(String completionId, String... completions) {
        CommandCompletions<BukkitCommandCompletionContext> commandCompletions = this.commandManager.getCommandCompletions();
        commandCompletions.registerAsyncCompletion(completionId, (c) -> {
            CommandSender sender = c.getSender();
            if(sender instanceof Player) {
                return Arrays.asList(completions);
            }
            return null;
        });
    }

    public void registerCompletion(String completionId, List<String> completions) {
        CommandCompletions<BukkitCommandCompletionContext> commandCompletions = this.commandManager.getCommandCompletions();
        commandCompletions.registerAsyncCompletion(completionId, (c) -> {
            CommandSender sender = c.getSender();
            if(sender instanceof Player) {
                return completions;
            }
            return null;
        });
    }

}
