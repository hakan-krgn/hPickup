package com.hakan.pickup.listeners.bukkitlisteners;

import com.hakan.pickup.api.PickupAPI;
import com.hakan.pickup.utils.Variables;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class ConnectionListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!Variables.canJoin) {
            player.kickPlayer("§cLoading hPickup Data\n§cPlease wait few seconds");
            return;
        }
        if (!PickupAPI.hasPlayerData(player)) {
            PickupAPI.createPlayerData(player);
        }
    }
}
