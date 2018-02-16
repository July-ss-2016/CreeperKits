package vip.creeper.mcserverplugins.creeperkits.untils;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Created by July on 2018/02/16.
 */
public class PlayerUtil {
    public static int getPlayerInvFreeSize(Player player) {
        int counter = 0;

        for (ItemStack item : player.getInventory().getContents()) {
            if (!ItemUtil.isValidItem(item)) {
                counter ++;
            }
        }

        return counter;
    }
}
