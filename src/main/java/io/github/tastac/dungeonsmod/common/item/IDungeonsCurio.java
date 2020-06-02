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
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import top.theillusivec4.curios.api.CurioType;
import top.theillusivec4.curios.api.CuriosAPI;

import java.util.Optional;

/**
 * @author CoffeeCatRailway
 * Created: 28/05/2020
 */
public interface IDungeonsCurio {

    void curioTick(ItemStack stack, String identifier, int index, PlayerEntity player);

    default boolean canEquip(ItemStack stack, String identifier, LivingEntity entity) {
        if (entity instanceof PlayerEntity) {
            Optional<CurioType> charm = CuriosAPI.getType("charm");
            if (charm.isPresent())
                for (int i = 0; i < charm.get().getSize(); i++)
                    if (CuriosIntegration.getArtifactStack((PlayerEntity) entity, i).isEmpty())
                        return true;
        }
        return false;
    }

    default boolean canUnequip(ItemStack stack, String identifier, LivingEntity entity) {
        if (entity instanceof PlayerEntity)
            return this instanceof Item && !((PlayerEntity) entity).getCooldownTracker().hasCooldown((Item) this);
        return false;
    }

    default boolean canRightClickEquip(ItemStack stack) {
        return DungeonsMod.SERVER_CONFIG.canRightClickEquip.get();
    }

    default void playEquipSound(ItemStack stack, LivingEntity entity) {
        entity.world.playSound(null, entity.getPosition(), SoundEvents.ITEM_ARMOR_EQUIP_GOLD, SoundCategory.NEUTRAL, 1.0F, 1.0F);
    }

    default Multimap<String, AttributeModifier> getAttributeModifiers(ItemStack stack, String identifier) {
        return HashMultimap.create();
    }

    boolean hasRender(ItemStack stack, String identifier, LivingEntity entity);

    void render(ItemStack stack, String identifier, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int light, LivingEntity entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageTicks, float headYaw, float headPitch);
}
