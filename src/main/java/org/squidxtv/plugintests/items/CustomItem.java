package org.squidxtv.plugintests.items;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.jetbrains.annotations.NotNull;

public interface CustomItem {

    void create(@NotNull Player player);
    void onLeftClick(@NotNull PlayerInteractEvent event);
    void onRightClick(@NotNull PlayerInteractEvent event);
}
