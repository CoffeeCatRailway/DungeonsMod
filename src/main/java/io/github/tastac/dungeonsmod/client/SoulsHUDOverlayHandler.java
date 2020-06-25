package io.github.tastac.dungeonsmod.client;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.tastac.dungeonsmod.DungeonsMod;
import io.github.tastac.dungeonsmod.common.capability.SoulsCapibility;
import io.github.tastac.dungeonsmod.integration.CuriosIntegration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

/**
 * @author CoffeeCatRailway
 * Created: 23/06/2020
 */
@OnlyIn(Dist.CLIENT)
public class SoulsHUDOverlayHandler {

    private int soulsIconsOffset;
    private int playerSouls;

    private static final ResourceLocation ICONS_TEX = DungeonsMod.getLocation("textures/gui/icons.png");

    public static void init() {
        MinecraftForge.EVENT_BUS.register(new SoulsHUDOverlayHandler());
    }

    @SuppressWarnings("ConstantConditions")
    @SubscribeEvent(priority = EventPriority.LOW)
    public void onPreOverlayRender(RenderGameOverlayEvent.Post event) {
        if (event.getType() != RenderGameOverlayEvent.ElementType.FOOD)
            return;

        this.soulsIconsOffset = ForgeIngameGui.right_height;

        if (event.isCanceled())
            return;

        Minecraft mc = Minecraft.getInstance();
        PlayerEntity player = mc.player;

        if (CuriosIntegration.hasSoulArtifact(player).isEmpty())
            return;

        player.getCapability(SoulsCapibility.SOULS_CAP).ifPresent(handler -> {
            this.playerSouls = handler.getSouls();

            int left = mc.getMainWindow().getScaledWidth() / 2 + 91;
            int top = mc.getMainWindow().getScaledHeight() - this.soulsIconsOffset;

            mc.getTextureManager().bindTexture(ICONS_TEX);
            RenderSystem.enableBlend();

            for (int i = 0; i < 10; i++) {
                int x = left - i * 8 - 9;
                int y = top;
                if (player.getAir() < player.getMaxAir())
                    y -= 9;

                AbstractGui.blit(x, y, mc.ingameGUI.getBlitOffset(), 18, 0, 9, 9, 32, 32);

                if (this.playerSouls > 0) {
                    int idx = i * 2 + 1;

                    if (idx < this.playerSouls)
                        AbstractGui.blit(x, y, mc.ingameGUI.getBlitOffset(), 0, 0, 9, 9, 32, 32);
                    else if (idx == this.playerSouls)
                        AbstractGui.blit(x, y, mc.ingameGUI.getBlitOffset(), 9, 0, 9, 9, 32, 32);
                }
            }
            RenderSystem.disableBlend();
            mc.getTextureManager().bindTexture(AbstractGui.GUI_ICONS_LOCATION);
        });
    }
}
