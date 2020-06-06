package io.github.tastac.dungeonsmod.client.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import io.github.tastac.dungeonsmod.DungeonsMod;
import io.github.tastac.dungeonsmod.common.entity.TotemOfRegenerationEntity;
import io.github.tastac.dungeonsmod.registry.DungeonsItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.culling.ClippingHelperImpl;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * @author CoffeeCatRailway
 * Created: 4/06/2020
 */
@OnlyIn(Dist.CLIENT)
public class TotemOfRegenerationRenderer extends EntityRenderer<TotemOfRegenerationEntity> {

    public TotemOfRegenerationRenderer(EntityRendererManager renderManager) {
        super(renderManager);
    }

    @Override
    public void render(TotemOfRegenerationEntity entity, float entityYaw, float partialTicks, MatrixStack matrix, IRenderTypeBuffer buffer, int packedLight) {
        super.render(entity, entityYaw, partialTicks, matrix, buffer, packedLight);
        matrix.push();
        Minecraft.getInstance().getItemRenderer().renderItem(entity.getItem(), ItemCameraTransforms.TransformType.FIXED, packedLight, OverlayTexture.NO_OVERLAY, matrix, buffer);
        matrix.pop();
        System.out.println("TEST-render");
    }

    @Override
    public ResourceLocation getEntityTexture(TotemOfRegenerationEntity entity) {
        return DungeonsMod.getLocation("textures/item/totem_of_regeneration.png");
    }
}
