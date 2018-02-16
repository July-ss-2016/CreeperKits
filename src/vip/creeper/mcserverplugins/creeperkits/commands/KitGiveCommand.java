package vip.creeper.mcserverplugins.creeperkits.commands;

import org.bukkit.Bukkit;
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
public class KitGiveCommand implements KitCommand {
    private KitManager kitManager;
    private CacheKitManager cacheKitManager;

    public KitGiveCommand(CreeperKits plugin) {
        this.kitManager = plugin.getKitManager();
        this.cacheKitManager = plugin.getCacheKitManager();
    }

    @Override
    public boolean onlyPlayerCanExecute() {
        return false;
    }

    @Override
    public boolean onCommand(CommandSender cs, String[] args) {
        if (args.length == 3) {
            String kitName = args[1];
            String targetPlayerName = args[2];

            if (!kitManager.isExistsKit(kitName)) {
                MsgUtil.sendMsg(cs, "&cKit &e" + kitName + " &c不存在.");
                return true;
            }

            Kit kit = cacheKitManager.getOrLoadCacheKit(kitName);
            Player targetPlayer = Bukkit.getPlayer(targetPlayerName);

            if (targetPlayer == null || !targetPlayer.isOnline()) {
                MsgUtil.sendMsg(targetPlayer, "&c目标玩家不在线.");
                return true;
            }

            if (!kit.canHold(targetPlayer)) {
                MsgUtil.sendMsg(targetPlayer, "&c背包空间不足.");
                return true;
            }

            kit.give(targetPlayer);
            MsgUtil.sendMsg(targetPlayer, "&dKit &e" + kitName + " &d已添加到您的背包!");
            MsgUtil.sendMsg(cs, "&d玩家 &e" + targetPlayerName + " &d已收到 &e" + kitName + " &dKit.");
            return true;
        }

        return false;
    }

    @Override
    public String getUsage() {
        return "give <Kit名> <玩家名>";
    }
}
