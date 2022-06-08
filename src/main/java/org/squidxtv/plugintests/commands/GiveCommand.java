package org.squidxtv.plugintests.commands;

import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.squidxtv.plugintests.items.CustomItems;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GiveCommand implements CommandExecutor, TabCompleter {

    private final List<String> ITEMS = List.of("GYROKINETIC_WAND");

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player player) {
            if(args.length != 1) return false;
            try {
                CustomItems item = CustomItems.valueOf(args[0].toUpperCase());
                item.getItem().create(player);
            } catch(IllegalArgumentException e) {
                player.sendMessage(Component.text("CustomItem \"" + args[0] + "\" does not exist!"));
                return true;
            }
            CustomItems.valueOf(args[0]);
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        final List<String> completions = new ArrayList<>();
        StringUtil.copyPartialMatches(args[0], ITEMS, completions);
        Collections.sort(completions);
        return completions;
    }
}

