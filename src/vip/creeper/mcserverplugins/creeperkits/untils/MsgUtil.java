package vip.creeper.mcserverplugins.creeperkits.untils;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 * Created by July on 2018/02/16.
 */
public class MsgUtil {
    public static void sendMsg(CommandSender cs, String msg) {
        cs.sendMessage("§a[CreeperKits] §f" + ChatColor.translateAlternateColorCodes('&', msg));
    }

    public static void sendMsgWithoutPrefix(CommandSender cs, String msg) {
        cs.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
    }
}
