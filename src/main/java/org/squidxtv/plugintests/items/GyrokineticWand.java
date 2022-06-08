package org.squidxtv.plugintests.items;

import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.squidxtv.plugintests.PluginTests;

public class GyrokineticWand implements CustomItem {

    @Override
    public void create(@NotNull Player player) {
        ItemStack item = new ItemStack(Material.BLAZE_ROD);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(Component.text("Gyrokinetic Wand"));
        NamespacedKey key = new NamespacedKey(PluginTests.getInstance(), "custom-item");
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "gyrokinetic-wand");
        item.setItemMeta(meta);
        player.getInventory().addItem(item);
    }

    @Override
    public void onLeftClick(@NotNull PlayerInteractEvent event) {

    }

    @Override
    public void onRightClick(@NotNull PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Location loc = player.getTargetBlock(20).getLocation().add(0, 1, 0);
        Vector destination = loc.toVector();

        loc.getNearbyLivingEntities(10, 5).forEach(entity -> {
            if(entity.getUniqueId().equals(player.getUniqueId())) return;
            new BukkitRunnable() {

                private int counter = 0;

                @Override
                public void run() {
                    if(counter >= 8) cancel();
                    Vector e = entity.getLocation().toVector();
                    double dx = destination.getX();
                    double dy = destination.getY();
                    double dz = destination.getZ();
                    double ex = e.getX();
                    double ey = e.getY();
                    double ez = e.getZ();
                    Vector velocity = new Vector((dx-ex)/2.0, (dy-ey)/2.0, (dz-ez)/2.0);
                    entity.setVelocity(velocity);
                    counter++;
                }
            }.runTaskTimer(PluginTests.getInstance(), 0, 10);
        });
    }

}
