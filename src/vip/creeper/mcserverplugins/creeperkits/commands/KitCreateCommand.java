package vip.creeper.mcserverplugins.creeperkits.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import vip.creeper.mcserverplugins.creeperkits.CreeperKits;
import vip.creeper.mcserverplugins.creeperkits.managers.CacheKitManager;
import vip.creeper.mcserverplugins.creeperkits.managers.KitManager;
import vip.creeper.mcserverplugins.creeperkits.untils.ItemUtil;
import vip.creeper.mcserverplugins.creeperkits.untils.MsgUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by July on 2018/02/16.
 */
public class KitCreateCommand implements KitCommand {
    private KitManager kitManager;
    private CacheKitManager cacheKitManager;

    public KitCreateCommand(CreeperKits plugin) {
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
        int argsLen = args.length;

        if (argsLen >= 2 && args[0].equalsIgnoreCase("create")) {
            String kitName = args[1];

            if (kitManager.isExistsKit(kitName)) {
                MsgUtil.sendMsg(cs, "&cKit &e" + kitName + " &c已存在.");
                return true;
            }

            if (args.length == 2) {
                List<ItemStack> items = new ArrayList<>();

                for (ItemStack item : player.getInventory().getContents()) {
                    if (ItemUtil.isValidItem(item)) {
                        items.add(item);
                    }
                }

                if (items.size() == 0) {
                    MsgUtil.sendMsg(cs, "&c背包至少有一个物品才能创建Kit.");
                    return true;
                }

                boolean result = kitManager.createKit(kitName, items);

                if (result) {
                    // 载入到cache
                    cacheKitManager.getOrLoadCacheKit(kitName);
                    MsgUtil.sendMsg(cs, "&b创建 &e" + kitName + " &b成功.");
                    return true;
                }

                MsgUtil.sendMsg(cs, "&c创建 &e" + kitName + " &c失败.");
                return true;
            }


            if (argsLen == 3 && args[2].equalsIgnoreCase("hand")) {
                List<ItemStack> items = new ArrayList<>();
                ItemStack handItem = player.getItemInHand();

                if (!ItemUtil.isValidItem(handItem)) {
                    MsgUtil.sendMsg(cs, "&c手上的物品不能为空气!");
                    return true;
                }

                items.add(handItem);

                boolean result = kitManager.createKit(kitName, items);

                if (result) {
                    // 载入Cache
                    cacheKitManager.getOrLoadCacheKit(kitName);
                    MsgUtil.sendMsg(cs, "&b创建 &e" + kitName + " &b成功(单手持物品)!");
                    return true;
                }

                MsgUtil.sendMsg(cs, "&c创建 &e" + kitName + " &c失败.");
                return true;
            }
        }

        return false;
    }

    @Override
    public String getUsage() {
        return "create <Kit名> [hand]";
    }
}
