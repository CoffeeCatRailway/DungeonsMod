package io.github.tastac.dungeonsmod.network;

import io.github.tastac.dungeonsmod.common.item.ArtifactItem;
import io.github.tastac.dungeonsmod.integration.CuriosIntegration;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * @author CoffeeCatRailway
 * Created: 29/05/2020
 */
public class ActivateArtifactMessage implements IMessage<ActivateArtifactMessage> {

    private int slot;

    public ActivateArtifactMessage(int slot) {
        this.slot = slot;
    }

    @Override
    public void encode(ActivateArtifactMessage message, PacketBuffer buffer) {
        buffer.writeInt(message.slot);
    }

    @Override
    public ActivateArtifactMessage decode(PacketBuffer buffer) {
        return new ActivateArtifactMessage(buffer.readInt());
    }

    @Override
    public void handle(ActivateArtifactMessage message, Supplier<NetworkEvent.Context> ctxSuppler) {
        NetworkEvent.Context ctx = ctxSuppler.get();
        ctx.enqueueWork(() -> {
            ServerPlayerEntity player = ctx.getSender();
            if (player != null) {
                ItemStack artifact = CuriosIntegration.getArtifactStack(player, message.slot);
                if (!artifact.isEmpty()) ((ArtifactItem) artifact.getItem()).activate(artifact, true);
            }
        });
        ctx.setPacketHandled(true);
    }
}
