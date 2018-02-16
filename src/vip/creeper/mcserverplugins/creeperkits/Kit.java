package vip.creeper.mcserverplugins.creeperkits;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import vip.creeper.mcserverplugins.creeperkits.untils.PlayerUtil;

import java.util.List;

/**
 * Created by July on 2018/2/14.
 */
public class Kit {
    private String name;
    private int size;
    private List<ItemStack> items;

    public Kit(String name, List<ItemStack> items) {
        this.name = name;
        this.items = items;
        this.size = items.size();
    }

    public String getName() {
        return name;
    }

    public List<ItemStack> getItems() {
        return items;
    }

    public int getSize() {
        return size;
    }

    public void give(Player player) {
        PlayerInventory playerInv = player.getInventory();

        for (ItemStack item : items) {
            playerInv.addItem(item);
        }
    }

    // 检测是否能装下物品
    public boolean canHold(Player player) {
        return PlayerUtil.getPlayerInvFreeSize(player) >= size;
    }
}
