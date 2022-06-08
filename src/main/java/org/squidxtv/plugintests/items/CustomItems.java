package org.squidxtv.plugintests.items;

public enum CustomItems {
    GYROKINETIC_WAND(new GyrokineticWand());

    final CustomItem item;
    CustomItems(CustomItem item) {
        this.item = item;
    }

    public CustomItem getItem() {
        return this.item;
    }
}
