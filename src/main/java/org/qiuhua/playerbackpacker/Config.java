package org.qiuhua.playerbackpacker;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Config {
    private static FileConfiguration file;
    //重新加载
    public static void reload ()
    {
        file = load(new File(Main.getMainPlugin().getDataFolder (),"\\Config.yml"));
        Main.getMainPlugin().getLogger().info("文件加载完成");
    }

    public static YamlConfiguration load (File file)
    {
        return YamlConfiguration.loadConfiguration(file);
    }

    public static Boolean getBoolean( String value){
        return file.getBoolean(value);
    }

    public static String getString(String value){
        return file.getString(value);
    }

    public static Integer getInteger(String value){
        return file.getInt(value);
    }

    public static byte getByte(String value){
        return (byte) file.getInt(value);
    }

    public static List<String> getList(String value){
        return file.getStringList(value);
    }

    public static Double getDouble(String value){
        return file.getDouble(value);
    }

    //创建配置文件
    public static void saveAllConfig(){
        //创建一个插件文件夹路径为基础的 并追加下一层。所以此时的文件应该是Config.yml
        //exists 代表是否存在
        if (!(new File (Main.getMainPlugin().getDataFolder () ,"\\Config.yml").exists()))
            Main.getMainPlugin().saveResource("Config.yml", false);
    }


    public static List<String> getAllKeys() {
        ConfigurationSection section = file.getConfigurationSection("Items");
        if (section == null) {
            return null; // 返回空列表或抛出异常，取决于你的需求
        }
        Set<String> keys = section.getKeys(false);
        return new ArrayList<>(keys);
    }


}
