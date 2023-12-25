package org.qiuhua.playerbackpacker.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.qiuhua.playerbackpacker.inventory.Gui;
import org.qiuhua.playerbackpacker.inventory.InventoryTool;

public class InventoryListener implements Listener {
    //当玩家关闭物品栏时
    @EventHandler
    public void onInventoryCloseEvent(InventoryCloseEvent event){
        Inventory inv = event.getInventory();
        Player player = (Player) event.getPlayer();
        if(InventoryTool.isBackpackInventoryHolder(inv)){
            //关闭gui后的事情
            Gui.closeGui(player, inv);
            return;
        }
    }

    //当玩家点击物品栏中的格子时触发事件事件
    @EventHandler
    public void onInventoryClickEvent (InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        Inventory inv = event.getInventory();
        //界面判断
        if(InventoryTool.isBackpackInventoryHolder(inv)){
            Gui.click(event);
            return;
        }

    }


}
