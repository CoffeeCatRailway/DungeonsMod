package io.github.tastac.dungeonsmod.common.item;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.ItemStack;

/**
 * @author CoffeeCatRailway
 * Created: 2/06/2020
 */
public class IronHideAmuletArtifact extends AttributeArtifactItem {

    public IronHideAmuletArtifact(Properties prop) {
        super(prop, 10.3f, 25f);
    }

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(ItemStack stack, String identifier) {
        Multimap<String, AttributeModifier> attributes = HashMultimap.create();
        attributes.put(SharedMonsterAttributes.ARMOR.getName(), new AttributeModifier(SharedMonsterAttributes.ARMOR.getName(), 2.0d, AttributeModifier.Operation.MULTIPLY_TOTAL));
        return attributes;
    }

    @Override
    public boolean hasRender(ItemStack stack, String identifier, LivingEntity entity) {
        return false;
    }

    @Override
    public void render(ItemStack stack, String identifier, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int light, LivingEntity entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageTicks, float headYaw, float headPitch) {
    }
}
