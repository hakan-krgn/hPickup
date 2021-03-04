package com.hakan.pickup.listeners.pickuplisteners;

import com.hakan.pickup.PickupPlugin;
import com.hakan.pickup.PlayerData;
import com.hakan.pickup.api.PickupAPI;
import com.hakan.pickup.utils.Variables;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collection;

public class BlockPickupListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (player.getGameMode().equals(GameMode.SURVIVAL)) {
            PlayerData playerData = PickupAPI.getPlayerData(player);
            if (player.getInventory().firstEmpty() == -1) {
                return;
            }
            if (playerData.has(PlayerData.PickupType.MINE_SMELT)) {
                switch (event.getBlock().getType()) {
                    case IRON_ORE:
                        event.setCancelled(true);
                        event.getBlock().setType(Material.AIR);
                        player.getInventory().addItem(new ItemStack(Material.IRON_INGOT));
                        return;
                    case GOLD_ORE:
                        event.setCancelled(true);
                        event.getBlock().setType(Material.AIR);
                        player.getInventory().addItem(new ItemStack(Material.GOLD_INGOT));
                        return;
                }
            }
            if (playerData.has(PlayerData.PickupType.BLOCK_DROPS)) {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        Collection<Entity> entities = event.getBlock().getLocation().getNearbyEntities(0.8, 0.8, 0.8);
                        for (Entity entity : entities) {
                            if (entity instanceof Item) {
                                Item item = ((Item) entity);
                                player.getInventory().addItem(item.getItemStack());
                                item.remove();
                            }
                        }
                    }
                }.runTaskLater(PickupPlugin.getInstance(), 1);
            }

        }
    }
}