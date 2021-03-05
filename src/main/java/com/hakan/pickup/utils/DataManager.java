package com.hakan.pickup.utils;

import com.hakan.pickup.PickupPlugin;
import com.hakan.pickup.PlayerData;
import com.hakan.pickup.api.PickupAPI;
import org.bukkit.Bukkit;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class DataManager {

    private static HashMap<String, PlayerData> getSqlData(Callback callback) {
        HashMap<String, PlayerData> playerDataHashMap = new HashMap<>();
        try {
            Statement statement = Variables.sqLite.getStatement();

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS hPickup (playerName TEXT, blockDrop BYTE, blockTranslator BYTE, mineSmelt BYTE, mobDrop BYTE)");
            ResultSet resultSet = statement.executeQuery("SELECT * FROM hPickup");

            while (resultSet.next()) {
                String playerName = resultSet.getString("playerName");
                boolean blockDrop = resultSet.getByte("blockDrop") == 1;
                boolean blockTranslator = resultSet.getByte("blockTranslator") == 1;
                boolean mineSmelt = resultSet.getByte("mineSmelt") == 1;
                boolean mobDrop = resultSet.getByte("mobDrop") == 1;

                PlayerData playerData = new PlayerData(playerName);
                playerData.set(PlayerData.PickupType.BLOCK_DROPS, blockDrop);
                playerData.set(PlayerData.PickupType.BLOCK_TRANSLATOR, blockTranslator);
                playerData.set(PlayerData.PickupType.MINE_SMELT, mineSmelt);
                playerData.set(PlayerData.PickupType.MOB_DROPS, mobDrop);

                playerDataHashMap.put(playerName, playerData);
            }

            statement.close();

            if (callback != null) {
                callback.complete(true);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            if (callback != null) {
                callback.complete(false);
            }
        }

        return playerDataHashMap;
    }

    public static void loadData(Callback callback) {
        Bukkit.getScheduler().runTaskAsynchronously(PickupPlugin.getInstance(), () -> Variables.playerData = getSqlData(callback));
    }

    private static void saveData(Callback callback) {
        try {
            HashMap<String, PlayerData> playerDataHashMap = getSqlData(null);
            Statement statement = Variables.sqLite.getStatement();

            for (PlayerData playerData : PickupAPI.getPlayerDataList()) {
                String playerName = playerData.getPlayerName();
                int blockDrop = playerData.has(PlayerData.PickupType.BLOCK_DROPS) ? 1 : 0;
                int blockTranslator = playerData.has(PlayerData.PickupType.BLOCK_TRANSLATOR) ? 1 : 0;
                int mineSmelt = playerData.has(PlayerData.PickupType.MINE_SMELT) ? 1 : 0;
                int mobDrop = playerData.has(PlayerData.PickupType.MOB_DROPS) ? 1 : 0;

                if (!playerDataHashMap.containsKey(playerName)) {
                    statement.executeUpdate("INSERT INTO hPickup (playerName, blockDrop, blockTranslator, mineSmelt, mobDrop) VALUES('" + playerName + "', " + blockDrop + ", " + blockTranslator + ", " + mineSmelt + ", " + mobDrop + ")");
                } else if (!playerData.equals(playerDataHashMap.get(playerName))) {
                    statement.executeUpdate("UPDATE hPickup SET blockDrop = " + blockDrop + ", blockTranslator = " + blockTranslator + ", mineSmelt = " + mineSmelt + ", mobDrop = " + mobDrop + " WHERE playerName = '" + playerName + "'");
                }
            }

            statement.close();

            callback.complete(true);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            callback.complete(false);
        }
    }

    public static void saveData(boolean isAsync, Callback callback) {
        if (isAsync) {
            Bukkit.getScheduler().runTaskAsynchronously(PickupPlugin.getInstance(), () -> saveData(callback));
        } else {
            saveData(callback);
        }
    }

    public interface Callback {
        void complete(boolean state);
    }
}