package org.qiuhua.playerbackpacker.data;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.qiuhua.playerbackpacker.Main;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BackpackDataController {
    //全部背包的合集
    private static final Map<String, BackpackData> allBackpackData = new ConcurrentHashMap<>();

    public static Map<String, BackpackData>  getAllBackpackDataMap(){
        return allBackpackData;
    }


    //获取这个背包的数据
    public static BackpackData getData(String uuid){
        if(!allBackpackData.containsKey(uuid)){
            allBackpackData.put(uuid, new BackpackData());
        }
        return allBackpackData.get(uuid);
    }


    //获取一个物品界面
    public static Inventory getBackpack(String uuid){
        return getData(uuid).getInventory();
    }

    //序列化
    public static String itemStackSave(ItemStack itemStack) {
        YamlConfiguration yml = new YamlConfiguration();
        yml.set("item", itemStack);
        return yml.saveToString();
    }
    //反序列化
    public static ItemStack itemStackLoad(String str) {
        YamlConfiguration yml = new YamlConfiguration();
        try {
            if(str != null){
                yml.loadFromString(str);
            }
        } catch (InvalidConfigurationException var3) {
            Main.getMainPlugin().getLogger().severe("无法加载物品");
            throw new RuntimeException(var3);
        }

        return yml.getItemStack("item");
    }


}
