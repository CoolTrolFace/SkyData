package com.emre.skydata;

import net.brcdev.shopgui.event.ShopPostTransactionEvent;
import net.brcdev.shopgui.event.ShopPreTransactionEvent;
import net.brcdev.shopgui.shop.ShopTransactionResult;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class DataListener implements Listener {

    @EventHandler
    public void onTrans(ShopPostTransactionEvent e){
        ShopTransactionResult result = e.getResult();
        String transType = result.getShopAction().toString();
        int purcAmount = result.getAmount();
        String name = result.getShopItem().getItem().getType().toString();
        double money = result.getPrice();

        if(transType.equalsIgnoreCase("BUY")){
            DataMain.getDataManager().addBuyData(name,money,purcAmount);
        }else{
            DataMain.getDataManager().addSellData(name,money,purcAmount);
        }
    }
}
