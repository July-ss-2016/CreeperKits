package vip.creeper.mcserverplugins.creeperkits.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import vip.creeper.mcserverplugins.creeperkits.CreeperKits;
import vip.creeper.mcserverplugins.creeperkits.Kit;
import vip.creeper.mcserverplugins.creeperkits.managers.CacheKitManager;
import vip.creeper.mcserverplugins.creeperkits.managers.KitManager;
import vip.creeper.mcserverplugins.creeperkits.utils.MsgUtil;

/**
 * Created by July on 2018/03/10.
 */
public class KitGetWithoutReplaceVariablesCommand implements KitCommand {
    private KitManager kitManager;
    private CacheKitManager cacheKitManager;

    public KitGetWithoutReplaceVariablesCommand(CreeperKits plugin) {
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

            if (!kitManager.existKit(kitName)) {
                MsgUtil.sendMsg(cs, "&cKit &e" + kitName + " &c不存在.");
                return true;
            }

            Kit kit = cacheKitManager.getOrLoadCacheKit(kitName);

            if (!kit.canHold(player)) {
                MsgUtil.sendMsg(cs, "&c背包空间不足.");
                return true;
            }

            kit.giveWithoutReplaceLoreVariables(player);
            MsgUtil.sendMsg(cs, "&bKit &e" + kitName + " &b已添加到您的背包(不替换Lore变量)!");
            return true;
        }

        return false;
    }

    @Override
    public String getUsage() {
        return "aget <Kit名> - 得到Kit(不替换Lore变量)";
    }
}
