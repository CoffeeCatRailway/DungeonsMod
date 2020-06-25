package io.github.tastac.dungeonsmod.network.server;

import io.github.tastac.dungeonsmod.common.capability.SoulsCapibility;
import io.github.tastac.dungeonsmod.network.IPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * @author CoffeeCatRailway
 * Created: 22/06/2020
 */
public class SPacketSyncSouls implements IPacket<SPacketSyncSouls> {

    private int entityId;
    private int amount;
    private boolean remove;

    public SPacketSyncSouls(int entityId, int amount, boolean remove) {
        this.entityId = entityId;
        this.amount = amount;
        this.remove = remove;
    }

    @Override
    public void encode(SPacketSyncSouls packet, PacketBuffer buffer) {
        buffer.writeInt(packet.entityId);
        buffer.writeInt(packet.amount);
        buffer.writeBoolean(packet.remove);
    }

    @Override
    public SPacketSyncSouls decode(PacketBuffer buffer) {
        return new SPacketSyncSouls(buffer.readInt(), buffer.readInt(), buffer.readBoolean());
    }

    @Override
    public void handle(SPacketSyncSouls packet, Supplier<NetworkEvent.Context> ctxSuppler) {
        ctxSuppler.get().enqueueWork(() -> {
            Entity entity = Minecraft.getInstance().world.getEntityByID(packet.entityId);
            if (entity instanceof LivingEntity) {
                entity.getCapability(SoulsCapibility.SOULS_CAP).ifPresent(handler -> {
                    if (packet.remove)
                        handler.removeSouls(packet.amount);
                    else
                        handler.addSouls(packet.amount);
                });
            }
        });
        ctxSuppler.get().setPacketHandled(true);
    }
}
