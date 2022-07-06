package org.squidxtv.plugintests.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.squidxtv.plugintests.PluginTests;

import java.lang.reflect.InvocationTargetException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class PacketManager {

    private static final ProtocolManager protocolManager =
            PluginTests.getInstance().getProtocolManager();
    private static final AtomicInteger currentID = new AtomicInteger(100000);
    private static final int[] yawData = {0, 0, 180, 0, 90, 270};
    private static final int[] pitchData = {90, -90, 0, 0, 0, 0};

    /**
     *
     * @param player
     * @param x
     * @param y
     * @param z
     * @param invisible
     * @param direction
     * @param stack
     * @throws InvocationTargetException
     */
    public static void sendData(Player player, double x, double y, double z, boolean invisible,
            int direction, ItemStack stack) throws InvocationTargetException {
        PacketContainer packet = protocolManager.createPacket(PacketType.Play.Server.SPAWN_ENTITY);

        WrapperPlayServerSpawnEntity entity = createItemFrame(packet, x, y, z, direction);
        PacketContainer metadata = createMeta(stack, invisible);

        protocolManager.sendServerPacket(player, entity.getHandle());
        protocolManager.sendServerPacket(player, metadata);
        currentID.getAndIncrement();
    }

    private static WrapperPlayServerSpawnEntity createItemFrame(PacketContainer packet, double x,
            double y, double z, int direction) {
        WrapperPlayServerSpawnEntity entity = new WrapperPlayServerSpawnEntity(packet);
        // set to ITEM_FRAME
        entity.setType(EntityType.ITEM_FRAME);
        entity.setUniqueId(UUID.randomUUID());
        entity.setEntityID(currentID.get());
        // set location and rotation
        entity.setX(x);
        entity.setY(y);
        entity.setZ(z);
        entity.setObjectData(direction);
        entity.setYaw(yawData[direction]);
        entity.setPitch(pitchData[direction]);
        return entity;
    }

    private static PacketContainer createMeta(ItemStack item, boolean invisible) {
        // create ENTITY_METADATA packet
        PacketContainer metadata =
                protocolManager.createPacket(PacketType.Play.Server.ENTITY_METADATA);
        metadata.getIntegers().write(0, currentID.get());

        WrappedDataWatcher dataWatcher = new WrappedDataWatcher();
        if (invisible) {
            dataWatcher.setObject(0, WrappedDataWatcher.Registry.get(Byte.class), (byte) 0x20);
        }
        dataWatcher.setObject(8, WrappedDataWatcher.Registry.getItemStackSerializer(false), item);
        metadata.getWatchableCollectionModifier().write(0, dataWatcher.getWatchableObjects());
        return metadata;
    }
}
