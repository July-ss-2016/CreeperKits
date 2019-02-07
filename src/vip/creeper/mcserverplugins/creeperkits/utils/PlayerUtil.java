package vip.creeper.mcserverplugins.creeperkits.utils;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

/**
 * Created by July on 2018/02/16.
 */
public class PlayerUtil {
    public static int getPlayerInvFreeSize(Player player) {
        int counter = 0;
        PlayerInventory playerInv = player.getInventory();

        for (ItemStack item : playerInv.getContents()) {
            if (item != null && item.getType() != Material.AIR) {
                counter ++;
            }
        }

        return 36 - counter;
    }
}
