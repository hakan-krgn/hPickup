package com.hakan.pickup.cmd;

import com.hakan.pickup.PickupPlugin;
import com.hakan.pickup.gui.MainGUI;
import com.hakan.pickup.utils.Variables;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Commands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (command.getName().equals("hpickup")) {
            if (args.length == 0) {
                if (commandSender instanceof Player) {
                    MainGUI.open(((Player) commandSender));
                }
            } else if (args.length == 1) {
                if (args[0].equals("reload") || args[0].equals("yenile")) {
                    PickupPlugin.config.reload();
                    /*for (String material : PickupPlugin.config.getConfigurationSection("settings.auto-block").getKeys(false)) {

                        String[] x2 = material.split(":");

                        String[] typeString = PickupPlugin.config.getString("settings.auto-block." + material + ".type").split(":");
                        Material materialType = Material.valueOf(typeString[0]);
                        int needAmount = PickupPlugin.config.getInt("settings.auto-block." + material + ".amount");

                        Variables.itemStackList.put(new ItemStack(materialType, needAmount, Byte.parseByte(typeString[1])), new ItemStack(Material.valueOf(x2[0]), 1, Byte.parseByte(x2[1])));
                    }*/

                    commandSender.sendMessage("hPickup has been reloaded.");
                }
            }
        }
        return false;
    }
}