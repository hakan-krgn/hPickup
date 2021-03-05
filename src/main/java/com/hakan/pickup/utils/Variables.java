package com.hakan.pickup.utils;

import com.hakan.pickup.PlayerData;
import com.hakan.pickup.TranslatableBlock;
import com.hakan.sqliteapi.SQLite;
import org.bukkit.Material;

import java.util.HashMap;

public class Variables {

    public static HashMap<String, PlayerData> playerData = new HashMap<>();

    public static HashMap<Material, TranslatableBlock> itemStackList = new HashMap<>();
    public static SQLite sqLite;
    public static boolean canJoin = false;
    public static String serverVersion;

}