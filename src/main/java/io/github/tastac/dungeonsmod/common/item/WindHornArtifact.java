package io.github.tastac.dungeonsmod.common.item;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;
import java.util.List;

/**
 * @author CoffeeCatRailway
 * Created: 30/05/2020
 */
public class WindHornArtifact extends ArtifactItem {

    public WindHornArtifact(Properties prop) {
        super(prop, 0f, 10f);
        this.setRange(5f, false);
    }

    @Override
    public void onArtifactActivate(float durationInTicks, float cooldownInTicks, ItemStack stack, String identifier, int index, PlayerEntity player) {
        float range = stack.getOrCreateTag().getFloat(TAG_RANGE);
        List<Entity> entities = player.world.getEntitiesWithinAABBExcludingEntity(player, player.getBoundingBox().grow(range));
        Vec3d playerPos = player.getPositionVec();

        entities.forEach(entity -> {
            if (entity instanceof LivingEntity) {
                Vec3d entityPos = entity.getPositionVec();
                Vec3d vel = new Vec3d(entityPos.x - playerPos.x, entityPos.y - playerPos.y, entityPos.z - playerPos.z).normalize().mul(range, range, range);

                entity.addVelocity(vel.x, vel.y, vel.z);
                ((LivingEntity) entity).addPotionEffect(new EffectInstance(Effects.SLOWNESS, (int) cooldownInTicks, 0, false, false, true));
            }
        });
    }

    @Override
    public boolean hasRender(ItemStack stack, String identifier, LivingEntity entity) {
        return false;
    }

    @Override
    public void render(ItemStack stack, String identifier, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int light, LivingEntity entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageTicks, float headYaw, float headPitch) {
    }
}
