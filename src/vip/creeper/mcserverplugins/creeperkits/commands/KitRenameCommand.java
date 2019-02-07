package vip.creeper.mcserverplugins.creeperkits.commands;

import org.bukkit.command.CommandSender;
import vip.creeper.mcserverplugins.creeperkits.CreeperKits;
import vip.creeper.mcserverplugins.creeperkits.managers.KitManager;
import vip.creeper.mcserverplugins.creeperkits.utils.MsgUtil;

/**
 * Created by July on 2018/03/10.
 */
public class KitRenameCommand implements KitCommand {
    private KitManager kitManager;

    public KitRenameCommand(CreeperKits plugin) {
        this.kitManager = plugin.getKitManager();
    }

    @Override
    public boolean onlyPlayerCanExecute() {
        return false;
    }

    @Override
    public boolean onCommand(CommandSender cs, String[] args) {
        if (args.length == 3) {
            String oldKitName = args[1];
            String newKitName = args[2];

            if (!kitManager.existKit(oldKitName)) {
                MsgUtil.sendMsg(cs, "&cKit &e" + oldKitName + " &c不存在.");
                return true;
            }

            if (kitManager.renameKit(oldKitName, newKitName)) {
                MsgUtil.sendMsg(cs, "&b重名 &e" + oldKitName + " &b成功!");
                return true;
            } else {
                MsgUtil.sendMsg(cs, "&c重名 &e" + oldKitName + " &c失败.");
                return true;
            }
        }

        return false;
    }

    @Override
    public String getUsage() {
        return "rename <Kit名> - 重命名Kit";
    }
}
