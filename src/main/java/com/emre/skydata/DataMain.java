package com.emre.skydata;

import com.emre.skydata.utils.Yaml;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class DataMain extends JavaPlugin {

    public static Yaml config;
    private static DataMain instance;
    private static DataManager dataManager;

    public void onEnable() {
        instance = this;
        dataManager = new DataManager();
        config = new Yaml(getDataFolder() + "/config.yml", "config.yml");
        Bukkit.getPluginManager().registerEvents((Listener) new DataListener(),(Plugin) this);
        this.getCommand("skydata").setExecutor((CommandExecutor) new DataCmds());
    }

    public void onDisable() {
        dataManager.unloadAllData();
    }

    public static DataManager getDataManager() {
        return dataManager;
    }
    public static DataMain getInstance() {
        return instance;
    }
}
