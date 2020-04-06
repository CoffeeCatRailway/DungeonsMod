package io.github.tastac.dungeonsmod;

import io.github.tastac.dungeonsmod.common.block.TableBlock;
import io.github.tastac.dungeonsmod.common.init.DungeonsBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * @author CoffeeCatRailway
 * Created: 6/04/2020
 */
@OnlyIn(Dist.CLIENT)
public class ClientEvents
{
    public static void registerBlockColors()
    {
        BlockColors blocks = Minecraft.getInstance().getBlockColors();
        blocks.register((state, world, pos, tintIndex) -> world != null && pos != null ? TableBlock.getColthColor(world, pos).getColorValue() : 0xFFFFFF, DungeonsBlocks.TABLE.get());
    }

    public static void registerBlockRenderLayers()
    {
        RenderTypeLookup.setRenderLayer(DungeonsBlocks.TABLE.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(DungeonsBlocks.URN.get(), RenderType.getCutout());
    }
}
