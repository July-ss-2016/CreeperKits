package vip.creeper.mcserverplugins.creeperkits.commands;

import org.bukkit.command.CommandSender;

/**
 * Created by July on 2018/02/16.
 */
public interface KitCommand {
    boolean onlyPlayerCanExecute();

    boolean onCommand(CommandSender cs, String[] args);

    String getUsage();
}
