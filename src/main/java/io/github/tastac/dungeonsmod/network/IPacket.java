package io.github.tastac.dungeonsmod.network;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * @author CoffeeCatRailway
 * Created: 28/05/2020
 */
public interface IPacket<T> {

    void encode(T packet, PacketBuffer buffer);

    T decode(PacketBuffer buffer);

    void handle(T packet, Supplier<NetworkEvent.Context> ctxSuppler);
}
