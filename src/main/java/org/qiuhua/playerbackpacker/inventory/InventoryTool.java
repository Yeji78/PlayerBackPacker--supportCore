package org.qiuhua.playerbackpacker.inventory;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class InventoryTool {
    //判断是否是背包界面
    public static boolean isBackpackInventoryHolder (InventoryHolder holder)
    {
        return holder instanceof BackpackInventoryHolder;
    }
    public static boolean isBackpackInventoryHolder (Inventory inventory)
    {
        return inventory.getHolder() instanceof BackpackInventoryHolder;
    }

    /**
     * 如果返回true就代表这个是玩家物品栏
     * 不是上半部分
     * @param slot
     * @return
     */
    public static boolean isPlayerInventory(int slot){
        return slot < 0 || slot >= 54;
    }

    /**
     * 如果返回true就代表这里是空格子
     * @param itemStack
     * @return
     */
    public static boolean isAirOrNull (ItemStack itemStack)
    {
        return itemStack == null || itemStack.getType() == (Material.AIR);
    }






}
