package org.qiuhua.playerbackpacker.sql;

import org.bukkit.inventory.ItemStack;
import org.qiuhua.playerbackpacker.Main;
import org.qiuhua.playerbackpacker.data.BackpackData;
import org.qiuhua.playerbackpacker.data.BackpackDataController;

import java.util.List;
import java.util.Map;

public class SqliteDataControl {
    //将指定uuid的数据写进数据库
    public static void addBackpackData(String uuid) {
        SqliteControl.dropTable(uuid);
        SqliteControl.createTable(uuid);
        BackpackData data = BackpackDataController.getData(uuid);
//        Main.getMainPlugin().getLogger().info("本次写入 " + uuid);
        Map<Integer, ItemStack> map = data.getItemMap();
        for(Integer slot : map.keySet()){
            String item = BackpackDataController.itemStackSave(map.get(slot));
            SqliteControl.addItemSlot(uuid, slot, item);
        }
//        Main.getMainPlugin().getLogger().info("标题 " + data.getTitle());
//        Main.getMainPlugin().getLogger().info("实际容量 " + data.getRealSize());
//        Main.getMainPlugin().getLogger().info("箱子大小 " + data.getSize());
        SqliteControl.addTitleSizeRealSize(uuid, data.getTitle(), data.getSize(), data.getRealSize());
    }

    //将指定uuid的数据读取
    public static void loadBackpackData(String uuid){
        SqliteControl.connect();
        BackpackData data = BackpackDataController.getData(uuid);
        Map<Integer, String> map = SqliteControl.getItemMap(uuid);
//        Main.getMainPlugin().getLogger().info("本次加载 " + uuid);
        if(!map.isEmpty()){
            for(Integer slot : map.keySet()){
                if(map.get(slot) == null || map.get(slot).equals("")){
                    continue;
                }
                data.putItemMap(slot, BackpackDataController.itemStackLoad(map.get(slot)));
            }
        }
        data.setTitle(SqliteControl.getTitle(uuid));
        data.setRealSize(SqliteControl.getRealSize(uuid));
        data.setSize(SqliteControl.getSize(uuid));
//        Main.getMainPlugin().getLogger().info("标题 " + SqliteControl.getTitle(uuid));
//        Main.getMainPlugin().getLogger().info("实际容量 " + SqliteControl.getRealSize(uuid));
//        Main.getMainPlugin().getLogger().info("箱子大小" + SqliteControl.getSize(uuid));

    }

    //读取全部
    public static void loadAllData(){
        SqliteControl.connect();
        try {
            List<String> list = SqliteControl.allTableName();
            if(list.isEmpty()){
                return;
            }
            for(String uuid : list){
                loadBackpackData(uuid);
            }
        } finally {
            SqliteControl.close();
        }

    }

    //写入全部
    public static void saveAllData(){
        SqliteControl.connect();
        try{
            Map<String, BackpackData> map = BackpackDataController.getAllBackpackDataMap();
            if(map.isEmpty()){
                return;
            }
            for(String uuid : map.keySet()){
                addBackpackData(uuid);
            }
        } finally {
            SqliteControl.close();
        }

    }
}
