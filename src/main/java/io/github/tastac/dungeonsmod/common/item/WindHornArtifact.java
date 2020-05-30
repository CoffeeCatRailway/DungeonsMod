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

    public static final String TAG_RANGE = "Range";

    public WindHornArtifact(Properties prop) {
        super(prop, 0f, 10f);
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        CompoundNBT stackNbt = stack.getOrCreateTag();
        if (!stackNbt.contains(TAG_RANGE, Constants.NBT.TAG_ANY_NUMERIC))
            stackNbt.putFloat(TAG_RANGE, 5f);
        return super.initCapabilities(stack, nbt);
    }

    @Override
    public void artifactTick(float duration, float cooldown, ItemStack stack, String identifier, int index, PlayerEntity player) {
        List<Entity> entities = player.world.getEntitiesWithinAABBExcludingEntity(player, player.getBoundingBox().grow(stack.getOrCreateTag().getFloat(TAG_RANGE)));
        Vec3d pPos = player.getPositionVec();

        entities.forEach(entity -> {
            if (entity instanceof LivingEntity) {
                Vec3d ePos = entity.getPositionVec();
                Vec3d vel = new Vec3d(ePos.x - pPos.x, ePos.y - pPos.y, ePos.z - pPos.z).normalize().mul(2.25, 2.25, 2.25);

                entity.addVelocity(vel.x, vel.y, vel.z);
                ((LivingEntity) entity).addPotionEffect(new EffectInstance(Effects.SLOWNESS, (int) cooldown, 0, false, false, true));
            }
        });
    }

    @Override
    public boolean hasRender(String identifier, LivingEntity entity) {
        return false;
    }

    @Override
    public void render(String identifier, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int light, LivingEntity entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageTicks, float headYaw, float headPitch) {
    }
}
