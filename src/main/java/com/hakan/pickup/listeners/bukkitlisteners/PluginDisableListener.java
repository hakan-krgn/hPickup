package com.hakan.pickup.listeners.bukkitlisteners;

import com.hakan.pickup.PickupPlugin;
import com.hakan.pickup.utils.DataManager;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;

public class PluginDisableListener implements Listener {

    @EventHandler
    public void onDisable(PluginDisableEvent event) {
        if (event.getPlugin().equals(PickupPlugin.getInstance())) {
            Bukkit.getLogger().info("Pickup system save started.");
            DataManager.saveData(false, state -> {
                if (state) {
                    Bukkit.getLogger().info("Pickup system save completed.");
                } else {
                    Bukkit.getLogger().warning("Pickup system save failed.");
                }
            });
        }
    }
}