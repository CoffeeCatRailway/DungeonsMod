package io.github.tastac.dungeonsmod;

import io.github.tastac.dungeonsmod.client.entity.RegenerationTotemRenderer;
import io.github.tastac.dungeonsmod.client.entity.ShieldingTotemRenderer;
import io.github.tastac.dungeonsmod.client.particle.SoulParticle;
import io.github.tastac.dungeonsmod.integration.CuriosIntegration;
import io.github.tastac.dungeonsmod.network.NetworkHandler;
import io.github.tastac.dungeonsmod.network.client.CPacketActivateArtifact;
import io.github.tastac.dungeonsmod.registry.DungeonsEntities;
import io.github.tastac.dungeonsmod.registry.DungeonsParticles;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;

/**
 * @author CoffeeCatRailway
 * Created: 6/04/2020
 */
@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = DungeonsMod.MOD_ID)
public class ClientEvents {

    public static void entityRenderers() {
        RenderingRegistry.registerEntityRenderingHandler(DungeonsEntities.TOTEM_OF_REGENERATION.get(), RegenerationTotemRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(DungeonsEntities.TOTEM_OF_SHIELDING.get(), ShieldingTotemRenderer::new);
    }

    public static void particleFactories() {
        Minecraft.getInstance().particles.registerFactory(DungeonsParticles.SOUL.get(), SoulParticle.Factory::new);
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
    public static void keyPressed(InputEvent.KeyInputEvent event) {
        Minecraft minecraft = Minecraft.getInstance();
        int slot = getSlotKey();
        if (minecraft.player != null && slot != -1) {
            ClientPlayerEntity player = minecraft.player;
            ItemStack artifact = CuriosIntegration.getArtifactStack(player, slot);
            if (!artifact.isEmpty())
                NetworkHandler.INSTANCE.sendToServer(new CPacketActivateArtifact(slot));
        }
    }

    private static int getSlotKey() {
        if (DungeonsMod.USE_ARTIFACT_1.isPressed())
            return 0;
        if (DungeonsMod.USE_ARTIFACT_2.isPressed())
            return 1;
        if (DungeonsMod.USE_ARTIFACT_3.isPressed())
            return 2;
        return -1;
    }
}
