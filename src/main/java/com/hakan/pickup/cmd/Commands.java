package com.hakan.pickup.cmd;

import com.hakan.pickup.PickupPlugin;
import com.hakan.pickup.TranslatableBlock;
import com.hakan.pickup.gui.MainGUI;
import com.hakan.pickup.utils.Variables;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (command.getName().equals("hpickup")) {
            if (args.length == 0) {
                if (commandSender instanceof Player) {
                    MainGUI.open(((Player) commandSender));
                }
            } else if (args.length == 1) {
                if (commandSender.isOp() || commandSender instanceof ConsoleCommandSender) {
                    if (args[0].equals("reload") || args[0].equals("yenile")) {
                        PickupPlugin.config.reload();

                        for (String material : PickupPlugin.config.getConfigurationSection("settings.auto-block-items").getKeys(false)) {

                            Material fromBlock = Material.matchMaterial(material);
                            int needAmount = PickupPlugin.config.getInt("settings.auto-block-items." + material + ".amount");
                            Material toBlock = Material.matchMaterial(PickupPlugin.config.getString("settings.auto-block-items." + material + ".type"));
                            TranslatableBlock translatableBlock = new TranslatableBlock(fromBlock, toBlock, needAmount);

                            Variables.itemStackList.put(fromBlock, translatableBlock);
                        }

                        commandSender.sendMessage("hPickup has been reloaded.");
                    }
                }
            }
        }

        return false;
    }
}