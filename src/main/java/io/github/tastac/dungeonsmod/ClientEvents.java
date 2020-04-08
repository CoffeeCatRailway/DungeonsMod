package io.github.tastac.dungeonsmod;

import io.github.tastac.dungeonsmod.client.renderer.entity.RedstoneGolemRenderer;
import io.github.tastac.dungeonsmod.common.block.TableBlock;
import io.github.tastac.dungeonsmod.common.entity.RedstoneGolemEntity;
import io.github.tastac.dungeonsmod.common.init.DungeonsBlocks;
import io.github.tastac.dungeonsmod.common.init.DungeonsEntities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

/**
 * @author CoffeeCatRailway
 * Created: 6/04/2020
 */
@OnlyIn(Dist.CLIENT)
public class ClientEvents {

    public static void registerBlockColors() {
        BlockColors blocks = Minecraft.getInstance().getBlockColors();
        blocks.register((state, world, pos, tintIndex) -> world != null && pos != null ? TableBlock.getColthColor(world, pos).getColorValue() : 0xFFFFFF, DungeonsBlocks.TABLE.get());
    }

    public static void registerBlockRenderLayers() {
        RenderTypeLookup.setRenderLayer(DungeonsBlocks.SIDE_TABLE.get(), RenderType.getSolid());
        RenderTypeLookup.setRenderLayer(DungeonsBlocks.TABLE.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(DungeonsBlocks.URN.get(), RenderType.getCutout());
    }

    public static void registerEntityRenderers() {
        RenderingRegistry.registerEntityRenderingHandler(DungeonsEntities.REDSTONE_GOLEM.get(), RedstoneGolemRenderer::new);
    }
}
