package vip.creeper.mcserverplugins.creeperkits.commands;

import org.bukkit.command.CommandSender;
import vip.creeper.mcserverplugins.creeperkits.CreeperKits;
import vip.creeper.mcserverplugins.creeperkits.Kit;
import vip.creeper.mcserverplugins.creeperkits.managers.CacheKitManager;
import vip.creeper.mcserverplugins.creeperkits.managers.KitManager;
import vip.creeper.mcserverplugins.creeperkits.utils.MsgUtil;

/**
 * Created by July on 2018/02/16.
 */
public class KitListCommand implements KitCommand {
    private KitManager kitManager;

    public KitListCommand(CreeperKits plugin) {
        this.kitManager = plugin.getKitManager();
    }

    @Override
    public boolean onlyPlayerCanExecute() {
        return false;
    }

    @Override
    public boolean onCommand(CommandSender cs, String[] args) {
        MsgUtil.sendMsgWithoutPrefix(cs, "Kits: ");

        for (String kitName : kitManager.getKitNames()) {
            MsgUtil.sendMsgWithoutPrefix(cs, "- " + kitName);
        }

        return true;
    }

    @Override
    public String getUsage() {
        return "list - 列出Kit";
    }
}
