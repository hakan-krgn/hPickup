package com.hakan.pickup;

import org.bukkit.Material;

public class TranslatableBlock {

    private Material type;
    private Material to;
    private int need;

    public TranslatableBlock(Material type, Material to, int need) {
        this.type = type;
        this.to = to;
        this.need = need;
    }

    public Material getFrom() {
        return type;
    }

    public void setFrom(Material type) {
        this.type = type;
    }

    public Material getTo() {
        return to;
    }

    public void setTo(Material to) {
        this.to = to;
    }

    public int getNeed() {
        return need;
    }

    public void setNeed(int need) {
        this.need = need;
    }
}
