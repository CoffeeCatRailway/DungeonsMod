package io.github.tastac.dungeonsmod.network;

import io.github.tastac.dungeonsmod.DungeonsMod;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

/**
 * @author CoffeeCatRailway
 * Created: 28/05/2020
 */
public class PacketHandler {

    private static final String PROTOCOL_VERSION = "1";
    private static int nextId = 0;
    public static SimpleChannel INSTANCE;

    public static void init() {
        INSTANCE = NetworkRegistry.ChannelBuilder.named(DungeonsMod.getLocation("network"))
                .networkProtocolVersion(() -> PROTOCOL_VERSION)
                .clientAcceptedVersions(PROTOCOL_VERSION::equals)
                .serverAcceptedVersions(PROTOCOL_VERSION::equals)
                .simpleChannel();
        register(ActivateArtifactMessage.class, new ActivateArtifactMessage(0));

        DungeonsMod.LOGGER.info("Common Event: Register packets");
    }

    private static <T> void register(Class<T> clazz, IMessage<T> message) {
        INSTANCE.registerMessage(nextId++, clazz, message::encode, message::decode, message::handle);
    }
}
