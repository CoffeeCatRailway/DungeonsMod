package io.github.tastac.dungeonsmod.common.item;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @author CoffeeCatRailway
 * Created: 31/05/2020
 */
public class LoveMedallionArtifact extends ArtifactItem {

    public LoveMedallionArtifact(Properties prop) {
        super(prop, 10f, 30f);
        this.setRange(5f, false);
    }

    @Override
    public void onArtifactActivate(float durationInTicks, float cooldownInTicks, ItemStack stack, String identifier, int index, PlayerEntity player) {
        float range = stack.getOrCreateTag().getFloat(TAG_RANGE);
        List<LivingEntity> entities = player.world.getEntitiesWithinAABBExcludingEntity(player, player.getBoundingBox().grow(range)).stream()
                .filter(entity -> entity instanceof LivingEntity).map(entity -> (LivingEntity) entity).collect(Collectors.toList());

        if (entities.size() > 3) {
            for (int i = 0; i < 3; i++)
                this.setTargets(entities, i, range);
        } else {
            if (entities.size() > 2)
                this.setTargets(entities, 0, range);
        }
    }

    private void setTargets(List<LivingEntity> entities, int startIndex, float range) {
        LivingEntity entity = entities.get(startIndex);
        World world = entity.world;
        if (entity.getClassification(false) == EntityClassification.MONSTER) {
            LivingEntity target;
            do {
                target = world.getClosestEntity(entities, new EntityPredicate().setDistance(range).allowFriendlyFire(), entity, entity.getPosX(), entity.getPosYEye(), entity.getPosZ());
            } while (!entities.contains(target));

            if (target != null) {
                entity.setRevengeTarget(target);
                Random rand = world.rand;
                float xs = rand.nextFloat() * 0.5f - 0.25f;
                float ys = rand.nextFloat() * 0.5f - 0.15f;
                float zs = rand.nextFloat() * 0.5f - 0.25f;
                ((ServerWorld) world).spawnParticle(ParticleTypes.HEART, entity.getPosX(), entity.getPosY() + 1.5f, entity.getPosZ(), 3, xs, ys, zs, 1);
            }
        }
    }

    @Override
    public boolean hasRender(ItemStack stack, String identifier, LivingEntity entity) {
        return false;
    }

    @Override
    public void render(ItemStack stack, String identifier, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int light, LivingEntity entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageTicks, float headYaw, float headPitch) {
    }
}
