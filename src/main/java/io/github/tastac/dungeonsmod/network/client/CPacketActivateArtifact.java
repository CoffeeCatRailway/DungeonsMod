package io.github.tastac.dungeonsmod.network.client;

import io.github.tastac.dungeonsmod.common.item.ArtifactItem;
import io.github.tastac.dungeonsmod.integration.CuriosIntegration;
import io.github.tastac.dungeonsmod.network.IPacket;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * @author CoffeeCatRailway
 * Created: 29/05/2020
 */
public class CPacketActivateArtifact implements IPacket<CPacketActivateArtifact> {

    private int slot;

    public CPacketActivateArtifact(int slot) {
        this.slot = slot;
    }

    @Override
    public void encode(CPacketActivateArtifact packet, PacketBuffer buffer) {
        buffer.writeInt(packet.slot);
    }

    @Override
    public CPacketActivateArtifact decode(PacketBuffer buffer) {
        return new CPacketActivateArtifact(buffer.readInt());
    }

    @Override
    public void handle(CPacketActivateArtifact packet, Supplier<NetworkEvent.Context> ctxSuppler) {
        NetworkEvent.Context ctx = ctxSuppler.get();
        ctx.enqueueWork(() -> {
            ServerPlayerEntity player = ctx.getSender();
            if (player != null) {
                ItemStack artifact = CuriosIntegration.getArtifactStack(player, packet.slot);
                if (!artifact.isEmpty()) ((ArtifactItem) artifact.getItem()).activate(artifact, true);
            }
        });
        ctx.setPacketHandled(true);
    }
}
