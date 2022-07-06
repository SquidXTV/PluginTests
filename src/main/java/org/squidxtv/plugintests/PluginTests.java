package org.squidxtv.plugintests;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.squidxtv.plugintests.commands.GiveCommand;
import org.squidxtv.plugintests.commands.PacketCommand;
import org.squidxtv.plugintests.listener.GyrokineticWandListener;

public final class PluginTests extends JavaPlugin {

    private static PluginTests instance;
    private ProtocolManager protocolManager;

    @Override
    public void onLoad() {
        super.onLoad();
        instance = this;
        protocolManager = ProtocolLibrary.getProtocolManager();
    }

    @Override
    public void onEnable() {
        getCommand("give_item").setExecutor(new GiveCommand());
        getCommand("packet").setExecutor(new PacketCommand());
        getServer().getPluginManager().registerEvents(new GyrokineticWandListener(), this);
    }

    @Override
    public void onDisable() {

    }

    public static PluginTests getInstance() {
        return instance;
    }

    public ProtocolManager getProtocolManager() {
        return protocolManager;
    }
}
