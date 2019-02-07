package vip.creeper.mcserverplugins.creeperkits;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import vip.creeper.mcserverplugins.creeperkits.utils.PlayerUtil;
import vip.creeper.mcserverplugins.creeperkits.utils.StrUtil;

import java.util.ArrayList;
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
        if (!canHold(player)) {
            throw new InventorySizeNotEnoughException("背包空间至少要大于或等于: " + size + ".");
        }

        PlayerInventory playerInv = player.getInventory();

        for (ItemStack oldItem : items) {
            ItemStack newItem = oldItem.clone();

            replacedVariablesForItem(newItem, player);
            playerInv.addItem(newItem);
        }
    }

    // 不替换LORE变量给予ITEM
    public void giveWithoutReplaceLoreVariables(Player player) {
        if (!canHold(player)) {
            throw new InventorySizeNotEnoughException("背包空间至少要大于或等于: " + size + ".");
        }

        PlayerInventory playerInv = player.getInventory();

        for (ItemStack item : items) {
            playerInv.addItem(item);
        }
    }

    // 检测是否能装下物品
    public boolean canHold(Player player) {
        return PlayerUtil.getPlayerInvFreeSize(player) >= size;
    }

    private boolean replacedVariablesForItem(ItemStack item, Player player) {
        if (item == null || item.getType() == Material.AIR) {
            return false;
        }

        ItemMeta meta = item.getItemMeta();
        List<String> oldLores = meta.getLore();
        List<String> newLores = new ArrayList<>();

        if (oldLores == null) {
            return false;
        }

        for (String oldLore : oldLores) {
            newLores.add(replaceVariables(oldLore, player));
        }

        meta.setLore(newLores);
        item.setItemMeta(meta);
        return true;
    }

    private String replaceVariables(String s, Player player) {
        return s
                .replace("{received_time}", StrUtil.getCurrentTimeStr())
                .replace("{player_name}", player.getName());
    }
}
