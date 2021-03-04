package com.hakan.pickup.api;

import com.hakan.pickup.PlayerData;
import com.hakan.pickup.utils.Variables;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PickupAPI {

    public static List<PlayerData> getPlayerDataList() {
        return new ArrayList<>(Variables.playerData.values());
    }

    public static PlayerData getPlayerData(String playerName) {
        if (!hasPlayerData(playerName)) {
            createPlayerData(playerName);
        }
        return Variables.playerData.get(playerName);
    }

    public static PlayerData getPlayerData(Player player) {
        return getPlayerData(player.getName());
    }

    public static PlayerData getPlayerData(OfflinePlayer offlinePlayer) {
        return getPlayerData(offlinePlayer.getName());
    }

    public static void createPlayerData(String playerName) {
        PlayerData playerData = new PlayerData(playerName);
        playerData.set(PlayerData.PickupType.BLOCK_DROPS, true);
        playerData.set(PlayerData.PickupType.BLOCK_TRANSLATOR, true);
        playerData.set(PlayerData.PickupType.MINE_SMELT, true);
        playerData.set(PlayerData.PickupType.MOB_DROPS, false);

        Variables.playerData.put(playerName, playerData);
    }

    public static void createPlayerData(Player player) {
        createPlayerData(player.getName());
    }

    public static void createPlayerData(OfflinePlayer offlinePlayer) {
        createPlayerData(offlinePlayer.getName());
    }

    public static boolean hasPlayerData(String playerName) {
        return Variables.playerData.containsKey(playerName);
    }

    public static boolean hasPlayerData(Player player) {
        return hasPlayerData(player.getName());
    }

    public static boolean hasPlayerData(OfflinePlayer offlinePlayer) {
        return hasPlayerData(offlinePlayer.getName());
    }
}