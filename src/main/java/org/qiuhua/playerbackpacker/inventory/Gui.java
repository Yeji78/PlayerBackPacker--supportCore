package org.qiuhua.playerbackpacker.inventory;

import de.tr7zw.changeme.nbtapi.NBT;
import de.tr7zw.changeme.nbtapi.iface.ReadableItemNBT;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.qiuhua.playerbackpacker.Compatible;
import org.qiuhua.playerbackpacker.Config;
import org.qiuhua.playerbackpacker.data.BackpackData;
import org.qiuhua.playerbackpacker.data.BackpackDataController;

import java.util.Map;
import java.util.function.Function;


public class Gui {

    //打开背包
    public static void open(Player player){
        //获取玩家物品
        ItemStack item = Compatible.getItem(player);
        if(item.getType() == Material.AIR){
            player.sendMessage(Config.getString("msg.没有背包"));
            return;
        }
        String uuid = NBT.get(item, (Function<ReadableItemNBT, String>) nbt -> nbt.getString("PlayerBackPack"));
        if(uuid.equals("") || uuid.equals("LimitItem")){
            player.sendMessage(Config.getString("msg.没有背包"));
            return;
        }

        player.openInventory(BackpackDataController.getBackpack(uuid));


    }



    //关闭gui后
    public static void closeGui(Player player, Inventory inv){
        //获取玩家物品
        ItemStack item = Compatible.getItem(player);
        String uuid = NBT.get(item, (Function<ReadableItemNBT, String>) nbt -> nbt.getString("PlayerBackPack"));
        saveGuiItem(inv, uuid);
    }



    //保存界面上的物品
    public static void saveGuiItem(Inventory inventory, String uuid){
        //获取这个背包数据
        BackpackData data = BackpackDataController.getData(uuid);
        //获取物品列表
        Map<Integer, ItemStack> itemMap = data.getItemMap();
        //清空
        itemMap.clear();
        for(int i = 0; i <= (inventory.getSize() - 1); i++){
            ItemStack item = inventory.getItem(i);
            if(item != null && item.getType() != Material.AIR){
                itemMap.put(i, item);
            }
        }
    }

    public static void click(InventoryClickEvent event){
        //当前点击的物品
        ItemStack currentClickItem = event.getCurrentItem();
        if(currentClickItem == null || currentClickItem.getType() == Material.AIR){
            return;
        }
        String uuid = NBT.get(currentClickItem, (Function<ReadableItemNBT, String>) nbt -> nbt.getString("PlayerBackPack"));
        if(uuid.equals("LimitItem")){
            event.setCancelled(true);
            return;
        }
        if(!uuid.equals("")){
            event.getWhoClicked().sendMessage(Config.getString("msg.禁止套娃"));
            event.setCancelled(true);
            return;
        }

    }


}
