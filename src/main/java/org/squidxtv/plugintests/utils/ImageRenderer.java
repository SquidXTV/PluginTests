package org.squidxtv.plugintests.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.jetbrains.annotations.NotNull;

import java.awt.image.BufferedImage;
import java.time.LocalTime;

public class ImageRenderer extends MapRenderer {

    private BufferedImage image;
    private boolean done = true;

    @Override
    public void render(@NotNull MapView mapView, @NotNull MapCanvas mapCanvas,
                       @NotNull Player player) {
        System.out.println("called: " + LocalTime.now());
        if (done)
            return;
        mapCanvas.drawImage(0, 0, image);
        mapView.setTrackingPosition(false);
        this.done = true;
        System.out.println("done");
    }

    public void update(BufferedImage image) {
        this.image = image;
        this.done = false;
    }

    public static ItemStack createMap(World world) {
        MapView view = Bukkit.createMap(world);
        // remove all renderer
        for (MapRenderer renderer : view.getRenderers()) {
            view.removeRenderer(renderer);
        }
        // add own renderer
        ImageRenderer renderer = new ImageRenderer();
        view.addRenderer(renderer);
        // set map data
        ItemStack map = new ItemStack(Material.FILLED_MAP);
        MapMeta meta = (MapMeta) map.getItemMeta();
        meta.setMapView(view);
        map.setItemMeta(meta);
        return map;
    }

}