package vip.creeper.mcserverplugins.creeperkits.commands;

import org.bukkit.command.CommandSender;
import vip.creeper.mcserverplugins.creeperkits.CreeperKits;
import vip.creeper.mcserverplugins.creeperkits.managers.CacheKitManager;
import vip.creeper.mcserverplugins.creeperkits.managers.KitManager;
import vip.creeper.mcserverplugins.creeperkits.untils.MsgUtil;

/**
 * Created by July on 2018/02/16.
 */
public class KitRemoveCommand implements KitCommand {
    private KitManager kitManager;
    private CacheKitManager cacheKitManager;

    public KitRemoveCommand(CreeperKits plugin) {
        this.kitManager = plugin.getKitManager();
        this.cacheKitManager = plugin.getCacheKitManager();
    }

    @Override
    public boolean onlyPlayerCanExecute() {
        return false;
    }

    @Override
    public boolean onCommand(CommandSender cs, String[] args) {
        if (args.length == 2) {
            String kitName = args[1];

            if (!kitManager.isExistsKit(kitName)) {
                MsgUtil.sendMsg(cs, "&cKit &e" + kitName + " &c不存在!");
                return true;
            }

            boolean result = kitManager.removeKit(kitName);

            if (result) {
                // 删除Cache
                cacheKitManager.removeKitFromCache(kitName);
                MsgUtil.sendMsg(cs, "&b删除 &e" + kitName + " &b成功!");
                return true;
            }

            MsgUtil.sendMsg(cs, "&c删除 &e" + kitName + " &c失败!");
            return true;
        }

        return false;
    }

    @Override
    public String getUsage() {
        return "remove <Kit名>";
    }
}
