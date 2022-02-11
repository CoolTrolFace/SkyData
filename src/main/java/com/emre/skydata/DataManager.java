package com.emre.skydata;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class DataManager {
    public static HashMap<String, TransactionData> transDataMap = new HashMap<>();

    public DataManager(){
        File dataFile = new File(DataMain.getInstance().getDataFolder(), "/data/transactions.yml");
        FileConfiguration transDataConf = YamlConfiguration.loadConfiguration(dataFile);
        new BukkitRunnable(){
            @Override
            public void run() {
                transDataConf.getKeys(false).forEach(name -> {
                    loadTransDatas(name);
                });
            }
        }.runTaskAsynchronously(DataMain.getInstance());
    }

    public void loadTransDatas(String name){
        if (isExists(name)) return;
        File dataFile = new File(DataMain.getInstance().getDataFolder(), "/data/transactions.yml");

        FileConfiguration transDataConf = YamlConfiguration.loadConfiguration(dataFile);
        double Smoney = transDataConf.getDouble(name+".sale.money");
        int Samount = transDataConf.getInt(name+".sale.amount");
        double Pmoney = transDataConf.getDouble(name+".purc.money");
        int Pamount = transDataConf.getInt(name+".purc.amount");
        TransactionData data = new TransactionData(Smoney,Samount,Pmoney,Pamount);
        transDataMap.put(name,data);
    }

    public void addSellData(String name, double money, int amount){
        TransactionData data;
        if(isExists(name)){
            data = transDataMap.get(name);
        }else{
            data = new TransactionData();
        }
        data.setSellAmount(data.getSellAmount()+amount);
        data.setTotalSellMoney(data.getTotalSellMoney()+money);
        transDataMap.put(name,data);
    }
    public void addBuyData(String name, double money, int amount){
        TransactionData data;
        if(isExists(name)){
            data = transDataMap.get(name);
        }else{
            data = new TransactionData();
        }
        data.setBuyAmount(data.getBuyAmount()+amount);
        data.setTotalBuyMoney(data.getTotalBuyMoney()+money);
        transDataMap.put(name,data);
    }

    public boolean isExists(String playername){
        return transDataMap.containsKey(playername);
    }

    public void saveTransData(String name) {
        if (!isExists(name)) return;
        TransactionData data = transDataMap.get(name);
        File transFile = new File(DataMain.getInstance().getDataFolder(), "/data/transactions.yml");
        FileConfiguration transDataConf = (FileConfiguration) YamlConfiguration.loadConfiguration(transFile);
        transDataConf.set(name,data.getTotalSellMoney());
        transDataConf.set(name+".sale.money",data.getTotalSellMoney());
        transDataConf.set(name+".sale.amount",data.getSellAmount());
        transDataConf.set(name+".purc.money",data.getTotalBuyMoney());
        transDataConf.set(name+".purc.amount",data.getBuyAmount());

        try {
            transDataConf.save(transFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void unloadTransData(String name) {
        if (!isExists(name)) return;
        saveTransData(name);
        transDataMap.remove(name);
    }

    public static HashMap<String, TransactionData> getTransDataMap() {
        return transDataMap;
    }

    public void unloadAllData(){
        transDataMap.forEach((s, data) -> {
            saveTransData(s);
        });
    }
}
