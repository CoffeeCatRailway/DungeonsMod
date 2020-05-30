package io.github.tastac.dungeonsmod.common.item;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
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
    public void curioTick(ItemStack stack, String identifier, int index, PlayerEntity player) {
        CompoundNBT nbt = stack.getOrCreateTag();
        float duration = nbt.getFloat(TAG_DURATION);
        float cooldown = nbt.getFloat(TAG_COOLDOWN);
        if (this.isActive(stack) && !player.getCooldownTracker().hasCooldown(this)) {
            player.addPotionEffect(new EffectInstance(Effects.STRENGTH, (int) duration, 1, false, false, true));
            player.addPotionEffect(new EffectInstance(Effects.SPEED, (int) duration, 1, false, false, true));
            player.getCooldownTracker().setCooldown(this, (int) (cooldown + duration));
        }
        super.curioTick(stack, identifier, index, player);
    }

    @Override
    public boolean hasRender(String identifier, LivingEntity entity) {
        return false;
    }

    @Override
    public void render(String identifier, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int light, LivingEntity entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageTicks, float headYaw, float headPitch) {
    }
}
