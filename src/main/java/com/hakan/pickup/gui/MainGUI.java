package com.hakan.pickup.gui;

import com.hakan.icreator.utils.fyaml.YamlItem;
import com.hakan.invapi.InventoryAPI;
import com.hakan.invapi.inventory.invs.HInventory;
import com.hakan.invapi.inventory.item.ClickableItem;
import com.hakan.pickup.PickupPlugin;
import com.hakan.pickup.PlayerData;
import com.hakan.pickup.api.PickupAPI;
import com.hakan.pickup.utils.PlaySound;
import com.hakan.pickup.utils.Utils;
import com.hakan.pickup.utils.Variables;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;

public class MainGUI {

    public static void open(Player player) {
        FileConfiguration config = PickupPlugin.config.getFileConfiguration();
        PlayerData playerData = PickupAPI.getPlayerData(player);

        HInventory hInventory = InventoryAPI.getInventoryManager().setTitle(config.getString("gui-main.title")).setSize(config.getInt("gui-main.size")).setInventoryType(InventoryType.CHEST).setClickable(false).setCloseable(true).setId("hpickup_maingui_" + player.getName()).create();
        hInventory.guiAir();

        boolean hasPickup = player.hasPermission(PickupPlugin.config.getString("settings.auto-pickup-perm"));
        YamlItem autoBlock = new YamlItem(PickupPlugin.getInstance(), config, "gui-main.items.block-drops");
        if (!hasPickup) {
            playerData.set(PlayerData.PickupType.BLOCK_DROPS, false);
            autoBlock.setType(Material.BARRIER);
        }
        autoBlock.setLore(Utils.replaceList(autoBlock.getLore(), "%mode%", playerData.has(PlayerData.PickupType.BLOCK_DROPS) ? Variables.active : Variables.passive));
        autoBlock.setGlow(playerData.has(PlayerData.PickupType.BLOCK_DROPS));
        hInventory.setItem(autoBlock.getSlot(), ClickableItem.of(autoBlock.complete(), (event) -> {
            if (hasPickup) {
                PlaySound.playButtonClick(player);
                playerData.set(PlayerData.PickupType.BLOCK_DROPS, !playerData.has(PlayerData.PickupType.BLOCK_DROPS));
                open(player);
            }
        }));

        boolean hasMob = player.hasPermission(PickupPlugin.config.getString("settings.auto-mob-perm"));
        YamlItem mobDrops = new YamlItem(PickupPlugin.getInstance(), config, "gui-main.items.mob-drops");
        if (!hasMob) {
            playerData.set(PlayerData.PickupType.MOB_DROPS, false);
            mobDrops.setType(Material.BARRIER);
        }
        mobDrops.setLore(Utils.replaceList(mobDrops.getLore(), "%mode%", playerData.has(PlayerData.PickupType.MOB_DROPS) ? Variables.active : Variables.passive));
        mobDrops.setGlow(playerData.has(PlayerData.PickupType.MOB_DROPS));
        hInventory.setItem(mobDrops.getSlot(), ClickableItem.of(mobDrops.complete(), (event) -> {
            if (hasMob) {
                PlaySound.playButtonClick(player);
                playerData.set(PlayerData.PickupType.MOB_DROPS, !playerData.has(PlayerData.PickupType.MOB_DROPS));
                open(player);
            }
        }));

        boolean hasAutoBlock = player.hasPermission(PickupPlugin.config.getString("settings.auto-block-perm"));
        YamlItem blockTranslator = new YamlItem(PickupPlugin.getInstance(), config, "gui-main.items.block-translator");
        if (!hasAutoBlock) {
            playerData.set(PlayerData.PickupType.BLOCK_TRANSLATOR, false);
            blockTranslator.setType(Material.BARRIER);
        }
        blockTranslator.setLore(Utils.replaceList(blockTranslator.getLore(), "%mode%", playerData.has(PlayerData.PickupType.BLOCK_TRANSLATOR) ? Variables.active : Variables.passive));
        blockTranslator.setGlow(playerData.has(PlayerData.PickupType.BLOCK_TRANSLATOR));
        hInventory.setItem(blockTranslator.getSlot(), ClickableItem.of(blockTranslator.complete(), (event) -> {
            if (hasAutoBlock) {
                PlaySound.playButtonClick(player);
                playerData.set(PlayerData.PickupType.BLOCK_TRANSLATOR, !playerData.has(PlayerData.PickupType.BLOCK_TRANSLATOR));
                open(player);
            }
        }));

        boolean hasMineSmelt = player.hasPermission(PickupPlugin.config.getString("settings.auto-mine-perm"));
        YamlItem mineSmelter = new YamlItem(PickupPlugin.getInstance(), config, "gui-main.items.mine-smelt");
        if (!hasMineSmelt) {
            playerData.set(PlayerData.PickupType.MINE_SMELT, false);
            mineSmelter.setType(Material.BARRIER);
        }
        mineSmelter.setLore(Utils.replaceList(mineSmelter.getLore(), "%mode%", playerData.has(PlayerData.PickupType.MINE_SMELT) ? Variables.active : Variables.passive));
        mineSmelter.setGlow(playerData.has(PlayerData.PickupType.MINE_SMELT));
        hInventory.setItem(mineSmelter.getSlot(), ClickableItem.of(mineSmelter.complete(), (event) -> {
            if (hasMineSmelt) {
                PlaySound.playButtonClick(player);
                playerData.set(PlayerData.PickupType.MINE_SMELT, !playerData.has(PlayerData.PickupType.MINE_SMELT));
                open(player);
            }
        }));

        hInventory.open(player);
    }
}