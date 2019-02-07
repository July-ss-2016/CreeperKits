package vip.creeper.mcserverplugins.creeperkits.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Created by July on 2018/02/16.
 */
public class ItemUtil {
    public static boolean isValidItem(ItemStack item) {
        return item != null && item.getType() != Material.AIR;
    }
}
