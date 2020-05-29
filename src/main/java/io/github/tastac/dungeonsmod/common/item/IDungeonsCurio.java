package io.github.tastac.dungeonsmod.common.item;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.mojang.blaze3d.matrix.MatrixStack;
import io.github.tastac.dungeonsmod.DungeonsMod;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;

/**
 * @author CoffeeCatRailway
 * Created: 28/05/2020
 */
public interface IDungeonsCurio {

    void curioTick(ItemStack stack, String identifier, int index, PlayerEntity player);

    default boolean canEquip(String identifier, LivingEntity entity) {
//        if (entity instanceof PlayerEntity)
//            return CuriosIntegration.getArtifactStack((PlayerEntity) entity).stream().map(ItemStack::isEmpty).anyMatch(bool -> true);
        return true;
    }

    default boolean canUnequip(String identifier, LivingEntity entity) {
        if (entity instanceof PlayerEntity)
            return this instanceof Item && !((PlayerEntity) entity).getCooldownTracker().hasCooldown((Item) this);
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
