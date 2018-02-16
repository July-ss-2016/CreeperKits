package vip.creeper.mcserverplugins.creeperkits.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import vip.creeper.mcserverplugins.creeperkits.CreeperKits;
import vip.creeper.mcserverplugins.creeperkits.Kit;
import vip.creeper.mcserverplugins.creeperkits.managers.CacheKitManager;
import vip.creeper.mcserverplugins.creeperkits.managers.KitManager;
import vip.creeper.mcserverplugins.creeperkits.untils.MsgUtil;

/**
 * Created by July on 2018/02/16.
 */
public class KitGetCommand implements KitCommand {
    private KitManager kitManager;
    private CacheKitManager cacheKitManager;

    public KitGetCommand(CreeperKits plugin) {
        this.kitManager = plugin.getKitManager();
        this.cacheKitManager = plugin.getCacheKitManager();
    }

    @Override
    public boolean onlyPlayerCanExecute() {
        return true;
    }

    @Override
    public boolean onCommand(CommandSender cs, String[] args) {
        Player player = (Player) cs;

        if (args.length == 2) {
            String kitName = args[1];

            if (!kitManager.isExistsKit(kitName)) {
                MsgUtil.sendMsg(cs, "&cKit &e" + kitName + " &c不存在.");
                return true;
            }

            Kit kit = cacheKitManager.getOrLoadCacheKit(kitName);

            if (!kit.canHold(player)) {
                MsgUtil.sendMsg(cs, "&c背包空间不足.");
                return true;
            }

            kit.give(player);
            MsgUtil.sendMsg(cs, "&bKit &e" + kitName + " &b已添加到你的背包!");
            return true;
        }

        return false;
    }

    @Override
    public String getUsage() {
        return "get <Kit名>";
    }
}
