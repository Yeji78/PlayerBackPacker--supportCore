package org.qiuhua.playerbackpacker;

import com.germ.germplugin.api.GermSlotAPI;
import eos.moe.dragoncore.api.SlotAPI;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Compatible {
    private static String type;

    private static String value;



    public static void setCompatible(){
        type = Config.getString("Compatible.type");
        value = Config.getString("Compatible.value");
    }


    //获取龙核槽位物品
    public static ItemStack getItem(Player player){
        if(type.equals("DragonCore")){
            return SlotAPI.getCacheSlotItem(player, value);
        }
        if(type.equals("GermEngine")){
            return GermSlotAPI.getItemStackOrDefault(player, value);
        }
        return new ItemStack(Material.AIR);
    }




}
