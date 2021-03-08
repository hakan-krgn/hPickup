package com.hakan.pickup.listeners.pickuplisteners;

import com.hakan.pickup.PickupPlugin;
import com.hakan.pickup.PlayerData;
import com.hakan.pickup.api.PickupAPI;
import com.hakan.pickup.utils.Utils;
import com.hakan.pickup.utils.Variables;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BlockPickupListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (player.getGameMode().equals(GameMode.SURVIVAL)) {
            PlayerData playerData = PickupAPI.getPlayerData(player);

            Inventory inventory = player.getInventory();

            if (inventory.firstEmpty() == -1) {
                return;
            }
            if (playerData.has(PlayerData.PickupType.MINE_SMELT)) {
                switch (event.getBlock().getType()) {
                    case IRON_ORE:
                        event.setCancelled(true);
                        event.getBlock().setType(Material.AIR);
                        inventory.addItem(new ItemStack(Material.IRON_INGOT));
                        return;
                    case GOLD_ORE:
                        event.setCancelled(true);
                        event.getBlock().setType(Material.AIR);
                        inventory.addItem(new ItemStack(Material.GOLD_INGOT));
                        return;
                }
            }
            List<Material> materials = new ArrayList<>();
            if (playerData.has(PlayerData.PickupType.BLOCK_DROPS)) {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        Location location = event.getBlock().getLocation();
                        Collection<Entity> entities = location.getWorld().getNearbyEntities(location, 0.8, 0.8, 0.8);
                        for (Entity entity : entities) {
                            if (entity instanceof Item) {
                                Item item = ((Item) entity);
                                ItemStack itemStack = item.getItemStack();
                                inventory.addItem(itemStack);
                                materials.add(itemStack.getType());
                                item.remove();
                            }
                        }
                    }
                }.runTaskLater(PickupPlugin.getInstance(), 1);
            }
            if (playerData.has(PlayerData.PickupType.BLOCK_TRANSLATOR)) {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        for (Material material : materials) {
                            if (Variables.itemStackList.containsKey(material)) {
                                Utils.updateInventory(player, Variables.itemStackList.get(material));
                            }
                        }
                    }
                }.runTaskLater(PickupPlugin.getInstance(), 2);
            }
        }
    }
}