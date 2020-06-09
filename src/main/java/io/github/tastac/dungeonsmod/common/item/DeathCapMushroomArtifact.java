package io.github.tastac.dungeonsmod.common.item;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

/**
 * @author CoffeeCatRailway
 * Created: 28/05/2020
 */
public class DeathCapMushroomArtifact extends ArtifactItem {

    public DeathCapMushroomArtifact(Properties prop) {
        super(prop, 10.4f, 30f);
    }

    @Override
    public void onArtifactActivate(float durationInTicks, float cooldownInTicks, ItemStack stack, String identifier, int index, PlayerEntity player) {
        player.addPotionEffect(new EffectInstance(Effects.STRENGTH, (int) durationInTicks, 1, false, false, true));
        player.addPotionEffect(new EffectInstance(Effects.SPEED, (int) durationInTicks, 1, false, false, true));
    }

    @Override
    public boolean hasRender(ItemStack stack, String identifier, LivingEntity entity) {
        return false;
    }

    @Override
    public void render(ItemStack stack, String identifier, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int light, LivingEntity entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageTicks, float headYaw, float headPitch) {
    }
}
