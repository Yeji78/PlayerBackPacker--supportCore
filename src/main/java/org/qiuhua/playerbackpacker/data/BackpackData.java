package org.qiuhua.playerbackpacker.data;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.qiuhua.playerbackpacker.Main;
import org.qiuhua.playerbackpacker.inventory.BackpackInventoryHolder;
import org.qiuhua.playerbackpacker.item.BuildItems;

import java.util.HashMap;
import java.util.Map;

public class BackpackData {
    //角色信息标题
    private String title = "默认标题";
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    //物品列表
    private final Map<Integer, ItemStack> itemMap = new HashMap<>();
    public void putItemMap (Integer slot, ItemStack item)
    {
        this.itemMap.put(slot, item);
    }
    public ItemStack getItem (Integer slot)
    {
        return this.itemMap.get(slot);
    }
    public Map<Integer, ItemStack> getItemMap()
    {
        return this.itemMap;
    }

    //规定大小的容量
    private Integer size = 54;
    public Integer getSize() {
        return this.size;
    }
    public void setSize(Integer size) {
        this.size = size;
    }

    //真实容量
    private Integer realSize = 54;
    public Integer getRealSize() {
        return this.realSize;
    }
    public void setRealSize(Integer realSize) {
        this.realSize = realSize;
    }

    //构建一个箱子界面
    public Inventory getInventory(){
        Inventory inv = Bukkit.createInventory(new BackpackInventoryHolder(), this.size, this.title);
        //先往剩余格子填充物品
        Integer a = realSize;
        Integer b = size;
        for(int i = b ; i > a; i--){
            inv.setItem(i - 1, BuildItems.getLimitItem());
        }
        //Main.getMainPlugin().getLogger().info(itemMap.keySet().toString());
        for(Integer slot : this.itemMap.keySet()){
            inv.setItem(slot, this.itemMap.get(slot));
        }
        return inv;
    }

    public String toString(){
        return "标题: " + this.title + " 可以放物品的大小: " + this.realSize + " 界面大小: " + this.size;
    }




}
