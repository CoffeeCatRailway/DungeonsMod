package io.github.tastac.dungeonsmod.common.item;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Quaternion;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.ItemStack;

/**
 * @author CoffeeCatRailway
 * Created: 2/06/2020
 */
public class BootsOfSwistnessArtifact extends AttributeArtifactItem {

    public BootsOfSwistnessArtifact(Properties prop) {
        super(prop, 1.6f, 5f);
    }

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(ItemStack stack, String identifier) {
        Multimap<String, AttributeModifier> attributes = HashMultimap.create();
        attributes.put(SharedMonsterAttributes.MOVEMENT_SPEED.getName(), new AttributeModifier(SharedMonsterAttributes.MOVEMENT_SPEED.getName(), 1.75d, AttributeModifier.Operation.MULTIPLY_TOTAL));
        return attributes;
    }

    @Override
    public boolean hasRender(ItemStack stack, String identifier, LivingEntity entity) {
        return true;
    }

    @Override
    public void render(ItemStack stack, String identifier, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int light, LivingEntity entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageTicks, float headYaw, float headPitch) {
        EntityRenderer<? super LivingEntity> render = Minecraft.getInstance().getRenderManager().getRenderer(entity);
        if (render instanceof LivingRenderer) {
            EntityModel<LivingEntity> model = ((LivingRenderer) render).getEntityModel();
            if (model instanceof BipedModel) {
                BipedModel<LivingEntity> biped = (BipedModel<LivingEntity>) model;
                this.renderBoot(stack, matrixStack, renderTypeBuffer, light, biped.bipedLeftLeg);
                this.renderBoot(stack, matrixStack, renderTypeBuffer, light, biped.bipedRightLeg);
            }
        }
    }

    private void renderBoot(ItemStack stack, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int light, ModelRenderer leg) {
        matrixStack.push();
        float scale = 1.05f;
        matrixStack.scale(scale, scale, scale);
        matrixStack.rotate(Vector3f.XN.rotationDegrees(180.0f));
        matrixStack.rotate(Vector3f.YN.rotationDegrees(180.0f));

        float x = leg.rotationPointX;
        float y = leg.rotationPointY;
        float z = leg.rotationPointZ;
        matrixStack.translate(-x / 16f, -y / 16f, (z - .56f) / 16f);

        float xr = leg.rotateAngleX;
        float yr = leg.rotateAngleY;
        float zr = leg.rotateAngleZ;
        matrixStack.rotate(new Quaternion(-xr, yr, zr, false));
        matrixStack.translate(0f, -3.315f / 16f, 0f); // TODO: Fix clipping when walking

        Minecraft.getInstance().getItemRenderer().renderItem(stack, ItemCameraTransforms.TransformType.NONE, light, OverlayTexture.NO_OVERLAY, matrixStack, renderTypeBuffer);
        matrixStack.pop();
    }
}
