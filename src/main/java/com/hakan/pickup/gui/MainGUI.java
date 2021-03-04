package com.hakan.pickup.gui;

import com.hakan.icreator.utils.fyaml.YamlItem;
import com.hakan.invapi.InventoryAPI;
import com.hakan.invapi.inventory.invs.HInventory;
import com.hakan.invapi.inventory.item.ClickableItem;
import com.hakan.pickup.PickupPlugin;
import com.hakan.pickup.PlayerData;
import com.hakan.pickup.api.PickupAPI;
import com.hakan.pickup.utils.PlaySound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;

public class MainGUI {

    public static void open(Player player) {
        FileConfiguration config = PickupPlugin.config.getFileConfiguration();
        PlayerData playerData = PickupAPI.getPlayerData(player);

        HInventory hInventory = InventoryAPI.getInventoryManager().setTitle(config.getString("gui-main.title")).setSize(config.getInt("gui-main.size")).setInventoryType(InventoryType.CHEST).setClickable(false).setCloseable(true).setId("hpickup_maingui_" + player.getName()).create();
        hInventory.guiAir();

        YamlItem autoBlock = new YamlItem(PickupPlugin.getInstance(), config, "gui-main.items.block-drops");
        autoBlock.setGlow(playerData.has(PlayerData.PickupType.BLOCK_DROPS));
        hInventory.setItem(autoBlock.getSlot(), ClickableItem.of(autoBlock.complete(), (event) -> {
            PlaySound.playButtonClick(player);
            playerData.set(PlayerData.PickupType.BLOCK_DROPS, !playerData.has(PlayerData.PickupType.BLOCK_DROPS));
            open(player);
        }));

        YamlItem mobDrops = new YamlItem(PickupPlugin.getInstance(), config, "gui-main.items.mob-drops");
        mobDrops.setGlow(playerData.has(PlayerData.PickupType.MOB_DROPS));
        hInventory.setItem(mobDrops.getSlot(), ClickableItem.of(mobDrops.complete(), (event) -> {
            PlaySound.playButtonClick(player);
            playerData.set(PlayerData.PickupType.MOB_DROPS, !playerData.has(PlayerData.PickupType.MOB_DROPS));
            open(player);
        }));

        YamlItem blockTranslator = new YamlItem(PickupPlugin.getInstance(), config, "gui-main.items.block-translator");
        blockTranslator.setGlow(playerData.has(PlayerData.PickupType.BLOCK_TRANSLATOR));
        hInventory.setItem(blockTranslator.getSlot(), ClickableItem.of(blockTranslator.complete(), (event) -> {
            PlaySound.playButtonClick(player);
            playerData.set(PlayerData.PickupType.BLOCK_TRANSLATOR, !playerData.has(PlayerData.PickupType.BLOCK_TRANSLATOR));
            open(player);
        }));

        YamlItem mineSmelter = new YamlItem(PickupPlugin.getInstance(), config, "gui-main.items.mine-smelt");
        mineSmelter.setGlow(playerData.has(PlayerData.PickupType.MINE_SMELT));
        hInventory.setItem(mineSmelter.getSlot(), ClickableItem.of(mineSmelter.complete(), (event) -> {
            PlaySound.playButtonClick(player);
            playerData.set(PlayerData.PickupType.MINE_SMELT, !playerData.has(PlayerData.PickupType.MINE_SMELT));
            open(player);
        }));

        hInventory.open(player);
    }
}