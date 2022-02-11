package com.emre.skydata;

import com.emre.skydata.utils.EmreUtils;
import com.emre.skydata.utils.YamlItem;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DataMenu implements InventoryProvider {

    int slot = 10;
    int page = 0;
    int itemNum = 1;
    int SORT_ID;
    Map<String, Double> sortList = new LinkedHashMap<String, Double>();

    public DataMenu(int page, int SORT_ID){
        this.page=page;
        this.SORT_ID=SORT_ID;
        DataManager.transDataMap.forEach((name, data) -> {
            if(SORT_ID==0){
                sortList.put(name,(double)data.getSellAmount());
            }else{
                sortList.put(name,(double)data.getBuyAmount());
            }
        });
        sortList = EmreUtils.sortByValue(sortList,false);
    }

    public void init(Player player, InventoryContents contents) {

        if(SORT_ID==1){
            contents.set(1,0,ClickableItem.of(new YamlItem("menu-buttons.sell-sort").complete(), inventoryClickEvent -> {
                openDataMenu(0,player,0);
            }));
        }else{
            contents.set(2,0,ClickableItem.of(new YamlItem("menu-buttons.buy-sort").complete(), inventoryClickEvent -> {
                openDataMenu(0,player,1);
            }));
        }


        if(page!=0){
            contents.set(5,3,ClickableItem.of(new YamlItem("menu-buttons.prev-page").complete(), inventoryClickEvent -> {
                openDataMenu(page-1,player,SORT_ID);
            }));
        }
        if(DataManager.getTransDataMap().size()-page*28>0){
            contents.set(5,5,ClickableItem.of(new YamlItem("menu-buttons.next-page").complete(),inventoryClickEvent -> {
                openDataMenu(page+1,player,SORT_ID);
            }));
        }

        for(String name : sortList.keySet()){
            if(itemNum<page*28) {
                itemNum += 1;
                continue;
            }
            TransactionData data = DataManager.getTransDataMap().get(name);
            ItemStack dataItem = new ItemStack(Material.valueOf(name));
            ItemMeta meta = dataItem.getItemMeta();
            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.translateAlternateColorCodes('&',"&c• Satış Miktarı: "+ data.getSellAmount()));
            lore.add(ChatColor.translateAlternateColorCodes('&',"&c• Elde Edilen Para: "+ (int)data.getTotalSellMoney()+"$"));
            lore.add("");
            lore.add(ChatColor.translateAlternateColorCodes('&',"&a• Alış Miktarı: "+ data.getBuyAmount()));
            lore.add(ChatColor.translateAlternateColorCodes('&',"&a• Harcanan Para: "+ (int)data.getTotalBuyMoney()+"$"));
            meta.setLore(lore);
            dataItem.setItemMeta(meta);
            contents.set(slot/9,slot%9, ClickableItem.empty(dataItem));
            slot+=1;
            if(slot==44) break;
            if(slot%9==8) slot+=2;
        }
    }

    public static void openDataMenu(int page, Player player, int SORT_ID){
        SmartInventory INVENTORY = SmartInventory.builder() //  Builds the menu
                .id("shopdata")
                .provider(new DataMenu(page,SORT_ID))
                .size(6, 9)
                .title(ChatColor.DARK_PURPLE + "Market Data")
                .build();
        INVENTORY.open(player); //    Opens the menu
    }

    public void update(Player player, InventoryContents inventoryContents) {

    }
}
