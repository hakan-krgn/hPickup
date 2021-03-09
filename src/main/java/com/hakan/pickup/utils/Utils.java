package com.hakan.pickup.utils;

import com.hakan.pickup.TranslatableBlock;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static void updateInventory(Player player, TranslatableBlock translatableBlock) {
        ItemStack[] contents = player.getInventory().getContents();
        int amount = 0;
        List<ItemStack> itemStackList = new ArrayList<>();
        for (ItemStack itemStack : contents) {
            if (itemStack != null) {
                if (itemStack.getType().equals(translatableBlock.getFrom())) {
                    amount = amount + itemStack.getAmount();
                    itemStackList.add(itemStack);
                }
            }
        }
        for (ItemStack itemStack : itemStackList) {
            itemStack.setType(Material.AIR);
        }
        int added = amount / translatableBlock.getNeed();
        int extra = amount % translatableBlock.getNeed();
        player.getInventory().addItem(new ItemStack(translatableBlock.getTo(), added));
        player.getInventory().addItem(new ItemStack(translatableBlock.getFrom(), extra));
    }

    public static List<String> replaceList(List<String> strings, String replace, String with) {
        List<String> newList = new ArrayList<>();
        for (String value : strings) {
            newList.add(value.replace(replace, with));
        }
        return newList;
    }

    public static void setXp(Player player, int xp) {
        player.setTotalExperience(0);
        player.setLevel(0);
        player.setExp(0);
        player.giveExp(xp);
    }

    public static void addXp(Player player, int xp) {
        setXp(player, player.getTotalExperience() + xp);
    }
}