package vip.creeper.mcserverplugins.creeperkits.commands;

import org.bukkit.command.CommandSender;
import vip.creeper.mcserverplugins.creeperkits.CreeperKits;
import vip.creeper.mcserverplugins.creeperkits.Kit;
import vip.creeper.mcserverplugins.creeperkits.managers.CacheKitManager;
import vip.creeper.mcserverplugins.creeperkits.untils.MsgUtil;

/**
 * Created by July on 2018/02/16.
 */
public class KitListCommand implements KitCommand {
    private CacheKitManager cacheKitManager;

    public KitListCommand(CreeperKits plugin) {
        this.cacheKitManager = plugin.getCacheKitManager();
    }

    @Override
    public boolean onlyPlayerCanExecute() {
        return false;
    }

    @Override
    public boolean onCommand(CommandSender cs, String[] args) {
        MsgUtil.sendMsgWithoutPrefix(cs, "Kits: ");

        for (Kit kit : cacheKitManager.getCacheKits()) {
            MsgUtil.sendMsgWithoutPrefix(cs, "- " + kit.getName());
        }

        return true;
    }

    @Override
    public String getUsage() {
        return null;
    }
}
