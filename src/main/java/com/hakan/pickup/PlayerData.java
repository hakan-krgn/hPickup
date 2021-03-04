package com.hakan.pickup;

import java.util.HashMap;
import java.util.Objects;

public class PlayerData {

    private final String playerName;
    private final HashMap<PickupType, Boolean> openingModes = new HashMap<>();

    public PlayerData(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerName() {
        return this.playerName;
    }

    public void set(PickupType pickupType, boolean state) {
        this.openingModes.put(pickupType, state);
    }

    public boolean has(PickupType pickupType) {
        return this.openingModes.get(pickupType);
    }

    public boolean equals(PlayerData playerData) {
        return getPlayerName().equals(playerData.getPlayerName()) && openingModes.equals(playerData.openingModes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPlayerName(), openingModes);
    }

    public enum PickupType {

        MOB_DROPS,
        BLOCK_DROPS,
        MINE_SMELT,
        BLOCK_TRANSLATOR;

        PickupType() {
        }
    }
}