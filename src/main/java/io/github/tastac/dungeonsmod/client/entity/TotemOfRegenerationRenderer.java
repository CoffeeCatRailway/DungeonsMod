package io.github.tastac.dungeonsmod.client.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import io.github.tastac.dungeonsmod.DungeonsMod;
import io.github.tastac.dungeonsmod.common.entity.TotemOfRegenerationEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * @author CoffeeCatRailway
 * Created: 4/06/2020
 */
@OnlyIn(Dist.CLIENT)
public class TotemOfRegenerationRenderer extends EntityRenderer<TotemOfRegenerationEntity> {

    private static final ResourceLocation TEXTURE = DungeonsMod.getLocation("textures/item/totem_of_regeneration.png");

    public TotemOfRegenerationRenderer(EntityRendererManager renderManager) {
        super(renderManager);
    }

    @Override
    public void render(TotemOfRegenerationEntity entity, float entityYaw, float partialTicks, MatrixStack matrix, IRenderTypeBuffer buffer, int packedLight) {
        super.render(entity, entityYaw, partialTicks, matrix, buffer, packedLight);
        matrix.push();
        ItemStack stack = entity.getTotem();
        if (!stack.isEmpty()) {
            matrix.scale(.75f, .75f, .75f);
            matrix.rotate(Vector3f.YP.rotationDegrees(180f - entity.rotationYaw));

            boolean hasEnded = entity.ticksExisted >= entity.getDuration() - 40f;
            if (hasEnded)
                entity.deadAngle = MathHelper.lerp(0.05f, entity.deadAngle, -90f);
            matrix.rotate(Vector3f.XP.rotationDegrees(entity.deadAngle));

            if (!hasEnded)
                entity.yOffset = (float) Math.sin((entity.ticksExisted + partialTicks) / 4.5f) / 48f;
            else
                entity.yOffset = MathHelper.lerp(0.025f, entity.yOffset, -.25f);
            matrix.translate(0f, .75f + entity.yOffset, 0f);

            Minecraft.getInstance().getItemRenderer().renderItem(stack, ItemCameraTransforms.TransformType.FIXED, packedLight, OverlayTexture.NO_OVERLAY, matrix, buffer);
        }
        matrix.pop();
    }

    @Override
    public ResourceLocation getEntityTexture(TotemOfRegenerationEntity entity) {
        return TEXTURE;
    }
}
