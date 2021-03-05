package com.hakan.pickup;

import com.hakan.icreator.ItemCreator;
import com.hakan.invapi.InventoryAPI;
import com.hakan.pickup.cmd.Commands;
import com.hakan.pickup.listeners.bukkitlisteners.ConnectionListener;
import com.hakan.pickup.listeners.bukkitlisteners.PluginDisableListener;
import com.hakan.pickup.listeners.pickuplisteners.BlockPickupListener;
import com.hakan.pickup.listeners.pickuplisteners.MobDropListener;
import com.hakan.pickup.utils.DataManager;
import com.hakan.pickup.utils.Metrics;
import com.hakan.pickup.utils.Variables;
import com.hakan.pickup.utils.yaml.Yaml;
import com.hakan.sqliteapi.SQLite;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;

public class PickupPlugin extends JavaPlugin {

    public static Yaml config;

    private static Plugin instance;

    public static Plugin getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        new Metrics(this, 10561);
        InventoryAPI.setup(this);
        Variables.sqLite = new SQLite(new File(getDataFolder() + "/data/playerData.db"));
        ItemCreator.setup(this, "type", "name", "lore", "amount", "datavalue", "glow", "nbt", "slot");
        Variables.serverVersion = Bukkit.getServer().getClass().getName().split("\\.")[3];

        config = new Yaml(getDataFolder() + "/config.yml", "config.yml");

        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new BlockPickupListener(), this);
        pm.registerEvents(new MobDropListener(), this);
        pm.registerEvents(new ConnectionListener(), this);
        pm.registerEvents(new PluginDisableListener(), this);
        getCommand("hpickup").setExecutor(new Commands());

        for (String material : config.getConfigurationSection("settings.auto-block-items").getKeys(false)) {

            Material fromBlock = Material.valueOf(material);
            int needAmount = config.getInt("settings.auto-block-items." + material + ".amount");
            Material toBlock = Material.valueOf(config.getString("settings.auto-block-items." + material + ".type"));
            TranslatableBlock translatableBlock = new TranslatableBlock(fromBlock, toBlock, needAmount);

            Variables.itemStackList.put(fromBlock, translatableBlock);
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.getLogger().info("Pickup system load started.");
                DataManager.loadData(state -> {
                    if (state) {
                        Bukkit.getLogger().info("Pickup system load completed.");
                    } else {
                        Bukkit.getLogger().warning("Pickup system load failed.");
                    }
                    Variables.canJoin = true;
                });
            }
        }.runTaskLater(this, 20);

        new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.getLogger().info("Pickup system save started.");
                DataManager.saveData(true, state -> {
                    if (state) {
                        Bukkit.getLogger().info("Pickup system save completed.");
                    } else {
                        Bukkit.getLogger().warning("Pickup system save failed.");
                    }
                });
            }
        }.runTaskTimer(this, 20 * 1800, 20 * 1800);
    }
}