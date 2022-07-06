package org.squidxtv.plugintests.commands;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.squidxtv.plugintests.PluginTests;
import org.squidxtv.plugintests.packets.PacketManager;
import org.squidxtv.plugintests.utils.BufferedImageUtils;
import org.squidxtv.plugintests.utils.ImageRenderer;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

// works
public class PacketCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player player) {
            ItemStack map = ImageRenderer.createMap(player.getWorld());
            String path = args[0];
            BufferedImage image = null;
            try {
                image = BufferedImageUtils.loadImageFromPath(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            changeToImage(map, image, player);
            Location location = player.getLocation();
            try {
                PacketManager.sendData(player, location.getX(), location.getY(), location.getZ(), false, 3, map);
                final BufferedImage[] finalImage = {image};
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        finalImage[0] = rotateImage(finalImage[0]);
                        changeToImage(map, finalImage[0], player);
                    }
                }.runTaskTimer(PluginTests.getInstance(), 20, 20);
            } catch (InvocationTargetException e) {
                return false;
            }
        }
        return true;
    }

    private void changeToImage(ItemStack map, BufferedImage image, Player player) {
        MapMeta meta = (MapMeta) map.getItemMeta();
        MapView view = meta.getMapView();
        List<MapRenderer> renderers = view.getRenderers();
        ImageRenderer renderer = (ImageRenderer) renderers.get(0);
        renderer.update(image);
        player.sendMap(view);
        System.out.println("{PacketCommand} Renderers: " + renderers);
    }
    // Optional
    private BufferedImage rotateImage(BufferedImage image) {
        double angle = 90;
        int w = image.getWidth();
        int h = image.getHeight();

        BufferedImage rotated = new BufferedImage(w, h, image.getType());
        Graphics2D graphic = rotated.createGraphics();
        graphic.rotate(Math.toRadians(angle), w/2, h/2);
        graphic.drawImage(image, null, 0, 0);
        graphic.dispose();
        return rotated;
    }

}
