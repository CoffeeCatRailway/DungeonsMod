package io.github.tastac.dungeonsmod.common.item;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author CoffeeCatRailway
 * Created: 31/05/2020
 */
public class LoveMedallionArtifact extends ArtifactItem {

    public LoveMedallionArtifact(Properties prop) {
        super(prop, 10f, 30f);
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
        float range = stack.getOrCreateTag().getFloat(TAG_RANGE);
        List<LivingEntity> entities = player.world.getEntitiesWithinAABBExcludingEntity(player, player.getBoundingBox().grow(range)).stream()
                .filter(entity -> entity instanceof LivingEntity).map(entity -> (LivingEntity) entity).collect(Collectors.toList());

        for (int i = 3; i < entities.size(); i++) {
            LivingEntity entity = entities.get(i);
            if (entity.getClassification(false) == EntityClassification.MONSTER) {
                MonsterEntity monster = (MonsterEntity) entity;
                LivingEntity target = entity.world.getClosestEntity(entities, new EntityPredicate().setDistance(range).allowFriendlyFire(), monster, entity.getPosX(), entity.getPosYEye(), entity.getPosZ());
                if (target != null)
                    monster.setAttackTarget(target);
            }
        }
    }

    @Override
    public boolean hasRender(String identifier, LivingEntity entity) {
        return false;
    }

    @Override
    public void render(String identifier, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int light, LivingEntity entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageTicks, float headYaw, float headPitch) {
    }
}
