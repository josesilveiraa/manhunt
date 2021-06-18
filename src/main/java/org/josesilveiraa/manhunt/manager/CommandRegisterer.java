package org.josesilveiraa.manhunt.manager;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.BukkitCommandCompletionContext;
import co.aikar.commands.CommandCompletions;
import co.aikar.commands.PaperCommandManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class CommandRegisterer {

    private final PaperCommandManager commandManager;
    private final CommandCompletions<BukkitCommandCompletionContext> commandCompletions;

    public CommandRegisterer(PaperCommandManager commandManager) {
        this.commandManager = commandManager;
        this.commandCompletions = this.commandManager.getCommandCompletions();
    }

    public void register(BaseCommand baseCommand) {
        this.commandManager.registerCommand(baseCommand);
    }

    public void registerCompletion(String completionId, String... completions) {
        this.commandCompletions.registerAsyncCompletion(completionId, (c) -> {
            CommandSender sender = c.getSender();
            if(sender instanceof Player) {
                return Arrays.asList(completions);
            }
            return null;
        });
    }
}
