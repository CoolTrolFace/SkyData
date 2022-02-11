package com.emre.skydata;

import fr.minuskube.inv.SmartInventory;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DataCmds implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(sender instanceof Player) {
            Player player = ((Player) sender).getPlayer();
            if (args.length == 0 && player.hasPermission("skydata.admin")) {
                // All data
                DataMenu.openDataMenu(0,player, 0);
            }
        }

        return true;
    }
}
