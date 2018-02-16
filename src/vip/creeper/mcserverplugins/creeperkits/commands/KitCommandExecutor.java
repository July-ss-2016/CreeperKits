package vip.creeper.mcserverplugins.creeperkits.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import vip.creeper.mcserverplugins.creeperkits.untils.MsgUtil;

import java.util.HashMap;

/**
 * Created by July on 2018/02/16.
 */
public class KitCommandExecutor implements CommandExecutor {
    private HashMap<String, KitCommand> cmds;

    public KitCommandExecutor() {
        this.cmds = new HashMap<>();
    }

    public void registerCmd(String firstArg, KitCommand cmd) {
        cmds.put(firstArg.toLowerCase(), cmd);
    }

    public boolean onCommand(CommandSender cs, Command cmd, String lable, String[] args) {
        if (!cs.hasPermission("CreeperKits.admin")) {
            MsgUtil.sendMsg(cs, "&c无权限! ");
            return true;
        }

        if (args.length >=1) {
            String lowerFirstArg = args[0].toLowerCase();

            if (cmds.containsKey(lowerFirstArg)) {
                KitCommand kitCmd = cmds.get(lowerFirstArg);

                if (kitCmd.onlyPlayerCanExecute() && !(cs instanceof Player)) {
                    MsgUtil.sendMsg(cs, "&c该命令必须由玩家来执行!");
                    return true;
                }

                if (!kitCmd.onCommand(cs, args)) {
                    MsgUtil.sendMsg(cs, "&c正确格式: &e/ckit " + kitCmd.getUsage() + "&c.");
                }

                return true;
            }
        }

        MsgUtil.sendMsg(cs, "&c指令错误!");
        MsgUtil.sendMsg(cs, "/ckit <create/remove/get/give/list> ...");
        return true;
    }
}
