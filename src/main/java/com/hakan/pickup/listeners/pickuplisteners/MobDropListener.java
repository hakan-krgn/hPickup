package com.hakan.pickup.listeners.pickuplisteners;

import com.hakan.pickup.PlayerData;
import com.hakan.pickup.api.PickupAPI;
import com.hakan.pickup.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class MobDropListener implements Listener {

    @EventHandler
    public void onDeath(EntityDeathEvent event) {
        if (!(event.getEntity() instanceof Player) && event.getEntity().getKiller() instanceof Player) {
            Player player = event.getEntity().getKiller();
            PlayerData playerData = PickupAPI.getPlayerData(player);
            if (playerData.has(PlayerData.PickupType.MOB_DROPS)) {

                Utils.addXp(player, event.getDroppedExp());
                event.setDroppedExp(0);

                Inventory inventory = player.getInventory();
                for (ItemStack dropItem : new ArrayList<>(event.getDrops())) {
                    if (inventory.firstEmpty() == -1) {
                        return;
                    }
                    inventory.addItem(dropItem);
                    event.getDrops().remove(dropItem);
                }
            }
        }
    }
}