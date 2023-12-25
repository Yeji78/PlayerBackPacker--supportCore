package org.qiuhua.playerbackpacker;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.qiuhua.playerbackpacker.listener.InventoryListener;
import org.qiuhua.playerbackpacker.sql.SqliteControl;
import org.qiuhua.playerbackpacker.sql.SqliteDataControl;

public class Main extends JavaPlugin {
    private static Main mainPlugin;

    public static Main getMainPlugin(){
        return  mainPlugin;
    }
    //启动时运行
    @Override
    public void onEnable(){
        //设置主插件
        mainPlugin = this;
        //注册指令
        new Command().register();
        Bukkit.getPluginManager().registerEvents(new InventoryListener(), this);
        Config.saveAllConfig();
        Config.reload();
        Compatible.setCompatible();
        SqliteControl.loadSQLiteJDBC();
        SqliteDataControl.loadAllData();
    }

    //关闭时运行
    @Override
    public void onDisable(){
        SqliteDataControl.saveAllData();
    }


    //执行重载命令时运行
    @Override
    public void reloadConfig(){
        Config.reload();
        Compatible.setCompatible();
        ;
    }
}