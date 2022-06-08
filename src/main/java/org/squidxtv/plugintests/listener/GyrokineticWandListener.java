package org.squidxtv.plugintests.listener;

import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.squidxtv.plugintests.PluginTests;
import org.squidxtv.plugintests.items.CustomItems;

public class GyrokineticWandListener implements Listener {

    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        if(event.getHand() != EquipmentSlot.HAND) {
            return;
        }
        if(!event.getAction().isRightClick()) {
            return;
        }
        if(event.getItem() == null) return;
        ItemStack item = event.getItem();
        ItemMeta meta = item.getItemMeta();
        NamespacedKey key = new NamespacedKey(PluginTests.getInstance(), "custom-item");
        PersistentDataContainer container = meta.getPersistentDataContainer();
        if(!container.has(key)) return;
        if(!container.get(key, PersistentDataType.STRING).equals("gyrokinetic-wand")) return;
        CustomItems.GYROKINETIC_WAND.getItem().onRightClick(event);

    }
}
