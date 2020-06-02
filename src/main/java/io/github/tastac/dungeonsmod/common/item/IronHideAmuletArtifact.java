package io.github.tastac.dungeonsmod.common.item;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * @author CoffeeCatRailway
 * Created: 2/06/2020
 */
public class IronHideAmuletArtifact extends ArtifactItem {

    private static final AttributeModifier modifier = new AttributeModifier(SharedMonsterAttributes.ARMOR.getName(), 2.0d, AttributeModifier.Operation.MULTIPLY_TOTAL);

    public IronHideAmuletArtifact(Properties prop) {
        super(prop, 10.3f, 25f);
    }

    @Override
    public void curioTick(ItemStack stack, String identifier, int index, PlayerEntity player) {
        if (!player.getCooldownTracker().hasCooldown(this)) {
            IAttributeInstance attribute = player.getAttributes().getAttributeInstanceByName(SharedMonsterAttributes.ARMOR.getName());
            if (attribute != null) {
                List<AttributeModifier> modifiers = new ArrayList<>(this.getAttributeModifiers().get(SharedMonsterAttributes.ARMOR.getName()));
                modifiers.forEach(attributeModifier -> {
                    if (attributeModifier == modifier)
                        attribute.removeModifier(attributeModifier);
                });
            }
        }
        super.curioTick(stack, identifier, index, player);
    }

    @Override
    public void onArtifactActivate(float duration, float cooldown, ItemStack stack, String identifier, int index, PlayerEntity player) {
        player.getAttributes().applyAttributeModifiers(this.getAttributeModifiers());
        player.getCooldownTracker().setCooldown(this, (int) duration);
    }

    public Multimap<String, AttributeModifier> getAttributeModifiers() {
        Multimap<String, AttributeModifier> attributes = HashMultimap.create();
        attributes.put(SharedMonsterAttributes.ARMOR.getName(), modifier);
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
