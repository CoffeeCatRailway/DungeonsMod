package io.github.tastac.dungeonsmod.common.item;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.mojang.blaze3d.matrix.MatrixStack;
import io.github.tastac.dungeonsmod.DungeonsMod;
import io.github.tastac.dungeonsmod.integration.CuriosIntegration;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;

/**
 * @author CoffeeCatRailway
 * Created: 28/05/2020
 */
public interface IDungeonsCurio {

    void curioTick(String identifier, int index, LivingEntity entity);

    default boolean canEquip(String identifier, LivingEntity entity) {
        if (entity instanceof PlayerEntity)
            return CuriosIntegration.getArtifactStacks((PlayerEntity) entity).stream().map(ItemStack::isEmpty).anyMatch(bool -> true);
        return false;
    }

    default boolean canRightClickEquip() {
        return DungeonsMod.SERVER_CONFIG.canRightClickEquip.get();
    }

    default void playEquipSound(LivingEntity entity) {
        entity.world.playSound(null, entity.getPosition(), SoundEvents.ITEM_ARMOR_EQUIP_GOLD, SoundCategory.NEUTRAL, 1.0F, 1.0F);
    }

    default Multimap<String, AttributeModifier> getAttributeModifiers(String identifier) {
        return HashMultimap.create();
    }

    boolean hasRender(String identifier, LivingEntity entity);

    void render(String identifier, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int light, LivingEntity entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageTicks, float headYaw, float headPitch);
}
