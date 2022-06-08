package org.squidxtv.plugintests;

import org.bukkit.plugin.java.JavaPlugin;
import org.squidxtv.plugintests.commands.GiveCommand;
import org.squidxtv.plugintests.listener.GyrokineticWandListener;

public final class PluginTests extends JavaPlugin {

    private static PluginTests instance;

    @Override
    public void onEnable() {
        getCommand("give_item").setExecutor(new GiveCommand());
        getServer().getPluginManager().registerEvents(new GyrokineticWandListener(), this);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        instance =  this;
    }

    @Override
    public void onDisable() {

    }

    public static PluginTests getInstance() {
        return instance;
    }
}
