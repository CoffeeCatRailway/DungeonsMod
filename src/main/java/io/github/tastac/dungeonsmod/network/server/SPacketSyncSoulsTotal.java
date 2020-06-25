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
 * Created: 24/06/2020
 */
public class SPacketSyncSoulsTotal implements IPacket<SPacketSyncSoulsTotal> {

    private int entityId;
    private int souls;

    public SPacketSyncSoulsTotal(int entityId, int souls) {
        this.entityId = entityId;
        this.souls = souls;
    }

    @Override
    public void encode(SPacketSyncSoulsTotal packet, PacketBuffer buffer) {
        buffer.writeInt(packet.entityId);
        buffer.writeInt(packet.souls);
    }

    @Override
    public SPacketSyncSoulsTotal decode(PacketBuffer buffer) {
        return new SPacketSyncSoulsTotal(buffer.readInt(), buffer.readInt());
    }

    @Override
    public void handle(SPacketSyncSoulsTotal packet, Supplier<NetworkEvent.Context> ctxSuppler) {
        ctxSuppler.get().enqueueWork(() -> {
            Entity entity = Minecraft.getInstance().world.getEntityByID(packet.entityId);
            if (entity instanceof LivingEntity) {
                entity.getCapability(SoulsCapibility.SOULS_CAP).ifPresent(handler -> handler.setSouls(packet.souls));
            }
        });
        ctxSuppler.get().setPacketHandled(true);
    }
}
