package org.qiuhua.playerbackpacker.item;

import de.tr7zw.changeme.nbtapi.NBT;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import org.qiuhua.playerbackpacker.Config;
import org.qiuhua.playerbackpacker.data.BackpackData;
import org.qiuhua.playerbackpacker.data.BackpackDataController;


import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class BuildItems {


    public static ItemStack getItem(String level){
        Material itemType = Material.getMaterial(Config.getString("Items." + level + ".type"));
        String name = Config.getString("Items." + level + ".name");
        List<String> lore = Config.getList("Items." + level + ".lore");
        ItemStack item = new ItemStack(itemType, 1);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(name);
        String uuid = getItemUUID();
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
        NBT.modify(item, nbt ->{
            nbt.setString("PlayerBackPack", uuid);
        });
        String title = Config.getString("Items." + level + ".Gui.title");
        int size = 27;
        Integer realSize = Config.getInteger("Items." + level + ".Gui.size");
        //如果真实容量大于27 那就是大箱子界面 否则就是小箱子界面
        if(realSize >= 28){
            size = 54;
        }
        BackpackData data = BackpackDataController.getData(uuid);
        data.setTitle(title);
        data.setSize(size);
        data.setRealSize(realSize);

        return item;
    }


    //创建一个这个物品的uuid
    public static String getItemUUID() {
        String dateStr = LocalDate.now().toString().replace("-", ""); // 获取当前日期的字符串形式，例如 "20230622"
        String uuid = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 8);
        return dateStr + "-" + uuid;
    }

    public static ItemStack getLimitItem(){

        byte data = Config.getByte("Limit.data");
        Material itemType = Material.getMaterial(Config.getString("Limit.type"));
        String name = Config.getString("Limit.name");
        List<String> lore = Config.getList("Limit.lore");
        MaterialData materialData = new MaterialData(itemType, data);
        ItemStack item = materialData.toItemStack(1);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(name);
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
        NBT.modify(item, nbt ->{
            nbt.setString("PlayerBackPack", "LimitItem");
        });
        return item;
    }



}
