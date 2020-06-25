package io.github.tastac.dungeonsmod.network;

import io.github.tastac.dungeonsmod.DungeonsMod;
import io.github.tastac.dungeonsmod.network.client.CPacketActivateArtifact;
import io.github.tastac.dungeonsmod.network.server.SPacketSyncSouls;
import io.github.tastac.dungeonsmod.network.server.SPacketSyncSoulsTotal;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

/**
 * @author CoffeeCatRailway
 * Created: 28/05/2020
 */
public class NetworkHandler {

    private static final String PROTOCOL_VERSION = "1";
    private static int nextId = 0;
    public static SimpleChannel INSTANCE;

    public static void init() {
        INSTANCE = NetworkRegistry.ChannelBuilder.named(DungeonsMod.getLocation("network"))
                .networkProtocolVersion(() -> PROTOCOL_VERSION)
                .clientAcceptedVersions(PROTOCOL_VERSION::equals)
                .serverAcceptedVersions(PROTOCOL_VERSION::equals)
                .simpleChannel();

        register(CPacketActivateArtifact.class, new CPacketActivateArtifact(0));
        register(SPacketSyncSouls.class, new SPacketSyncSouls(0, 0, false));
        register(SPacketSyncSoulsTotal.class, new SPacketSyncSoulsTotal(0, 0));

        DungeonsMod.LOGGER.info("Common Event: Register packets");
    }

    private static <T> void register(Class<T> clazz, IPacket<T> message) {
        INSTANCE.registerMessage(nextId++, clazz, message::encode, message::decode, message::handle);
    }
}
