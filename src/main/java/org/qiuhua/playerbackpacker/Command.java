package org.qiuhua.playerbackpacker;

import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.qiuhua.playerbackpacker.data.BackpackDataController;
import org.qiuhua.playerbackpacker.inventory.Gui;
import org.qiuhua.playerbackpacker.item.BuildItems;


import java.util.ArrayList;
import java.util.List;

public class Command implements CommandExecutor, TabExecutor {
    public void register() {
        Bukkit.getPluginCommand("pbp").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player  player = (Player)sender;
            //重载配置文件
            if(args.length == 1 && args[0].equalsIgnoreCase("open")){
                if(player.hasPermission("playerbackpacker.open")){
                    Gui.open(player);
                }
                return true;
            }
            //重载配置文件
            if(args.length == 1 && args[0].equalsIgnoreCase("reload")){
                if(player.hasPermission("playerbackpacker.reload")){
                    Main.getMainPlugin().reloadConfig();
                    player.sendMessage("重载文件完成");
                }
                return true;
            }
            if(args.length == 3 && args[0].equalsIgnoreCase("give")){
                if(player.hasPermission("playerbackpacker.give")){
                    ItemStack item = BuildItems.getItem(args[2]);
                    Player p = Bukkit.getPlayer(args[1]);
                    if(p != null){
                        p.getInventory().addItem(item);
                        Main.getMainPlugin().getLogger().info("成功给予该玩家背包");
                        return true;
                    }
                    player.sendMessage("该玩家不在线");
                }
                return true;
            }
        }else if(sender instanceof ConsoleCommandSender){
            if(args[0].equals("reload")) {
                Main.getMainPlugin().reloadConfig();
                return true;
            }
            if(args.length == 3 && args[0].equalsIgnoreCase("give")){
                ItemStack item = BuildItems.getItem(args[2]);
                Player p = Bukkit.getPlayer(args[1]);
                if(p != null){
                    p.getInventory().addItem(item);
                    Main.getMainPlugin().getLogger().info("成功给予该玩家背包");
                    return true;
                }
                Main.getMainPlugin().getLogger().info("该玩家不在线");
                return true;
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player)sender;
            List<String> result = new ArrayList<>();
            //当参数长度是1时
            if(args.length == 1) {
                if (player.hasPermission("playerbackpacker.reload"))
                    result.add("reload");
                if (player.hasPermission("playerbackpacker.give"))
                    result.add("give");
                return result;
            }
            //当参数长度是3时
            if(args.length == 2 && args[0].equals("give")) {
                if (player.hasPermission("playerbackpacker.give")){
                    result.add("请输入玩家名字");
                }
                return  result;
            }
            //当参数长度是3时
            if(args.length == 3 && args[0].equals("give")) {
                if (player.hasPermission("playerbackpacker.give")){
                    result = Config.getAllKeys();
                    if(result == null){
                        player.sendMessage("未找到模板");
                    }
                }
                return  result;
            }
        }else if(sender instanceof ConsoleCommandSender){
            List<String> result = new ArrayList<>();
            //当参数长度是1时
            if(args.length == 1) {
                result.add("reload");
                result.add("give");
                return result;
            }
            //当参数长度是3时
            if(args.length == 2 && args[0].equals("give")) {
                result.add("请输入玩家名字");
                return  result;
            }
            //当参数长度是3时
            if(args.length == 3 && args[0].equals("give")) {
                result = Config.getAllKeys();
                return  result;
            }
        }
        return null;
    }


}
