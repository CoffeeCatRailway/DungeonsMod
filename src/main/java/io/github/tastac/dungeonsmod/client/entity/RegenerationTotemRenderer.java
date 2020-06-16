package io.github.tastac.dungeonsmod.client.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import io.github.tastac.dungeonsmod.DungeonsMod;
import io.github.tastac.dungeonsmod.common.entity.RegenerationTotemEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * @author CoffeeCatRailway
 * Created: 4/06/2020
 */
@OnlyIn(Dist.CLIENT)
public class RegenerationTotemRenderer extends TotemRenderer<RegenerationTotemEntity> {

    private static final ResourceLocation TEXTURE = DungeonsMod.getLocation("textures/item/totem_of_regeneration.png");

    public RegenerationTotemRenderer(EntityRendererManager renderManager) {
        super(renderManager);
    }

    @Override
    public void render(RegenerationTotemEntity entity, float entityYaw, float partialTicks, MatrixStack matrix, IRenderTypeBuffer typeBuffer, int packedLight) {
        super.render(entity, entityYaw, partialTicks, matrix, typeBuffer, packedLight);
    }

    @Override
    public ResourceLocation getEntityTexture(RegenerationTotemEntity entity) {
        return TEXTURE;
    }
}
