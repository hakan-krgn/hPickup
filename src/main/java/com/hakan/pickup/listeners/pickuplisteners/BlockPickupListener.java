package com.hakan.pickup.listeners.pickuplisteners;

import com.hakan.pickup.PickupPlugin;
import com.hakan.pickup.PlayerData;
import com.hakan.pickup.api.PickupAPI;
import com.hakan.pickup.utils.Utils;
import com.hakan.pickup.utils.Variables;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
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

            Block block = event.getBlock();
            Inventory inventory = player.getInventory();

            if (event.getExpToDrop() != 0) {
                Utils.addXp(player, event.getExpToDrop());
                event.setExpToDrop(0);
            }

            if (inventory.firstEmpty() == -1) {
                return;
            }
            if (playerData.has(PlayerData.PickupType.MINE_SMELT)) {
                switch (block.getType()) {
                    case IRON_ORE:
                        event.setCancelled(true);
                        block.setType(Material.AIR);
                        inventory.addItem(new ItemStack(Material.IRON_INGOT));
                        return;
                    case GOLD_ORE:
                        event.setCancelled(true);
                        block.setType(Material.AIR);
                        inventory.addItem(new ItemStack(Material.GOLD_INGOT));
                        return;
                }
            }
            List<Material> materials = new ArrayList<>();
            if (playerData.has(PlayerData.PickupType.BLOCK_DROPS)) {
                String blockName = block.getType().name();
                Location location = block.getLocation();
                if (blockName.contains("SUGAR_CANE") || blockName.contains("CACTUS")) {
                    event.setCancelled(true);

                    Material blockType = block.getType();
                    String newBlockName = blockName;

                    int amount = 0;
                    while (blockName.equals(newBlockName)) {
                        amount++;

                        location.getBlock().setType(Material.AIR);

                        location = location.add(0, 1, 0);
                        newBlockName = location.getBlock().getType().name();
                    }

                    inventory.addItem(new ItemStack(blockType, amount));
                } else {
                    Location finalLocation = location;
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            Collection<Entity> entities = finalLocation.getWorld().getNearbyEntities(finalLocation, 0.8, 0.8, 0.8);
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