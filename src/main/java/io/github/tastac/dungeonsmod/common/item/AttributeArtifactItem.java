package io.github.tastac.dungeonsmod.common.item;

import com.google.common.collect.Multimap;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

/**
 * @author CoffeeCatRailway
 * Created: 3/06/2020
 */
public abstract class AttributeArtifactItem extends ArtifactItem {

    private final Map<String, Multimap<String, AttributeModifier>> CREATED_ATTRIBUTES = new HashMap<>();

    public AttributeArtifactItem(Properties prop, float durationInSeconds, float cooldownInSeconds) {
        super(prop, durationInSeconds, cooldownInSeconds);
    }

    @Override
    public void curioTick(ItemStack stack, String identifier, int index, PlayerEntity player) {
        if (!player.getCooldownTracker().hasCooldown(this)) {
            Multimap<String, AttributeModifier> modifiers = this.getOrCreateAttributeModifiers(stack, identifier);
            modifiers.forEach((name, modifier) -> player.getAttributes().getAllAttributes().forEach(instance -> instance.removeModifier(modifier.getID())));
        }
        super.curioTick(stack, identifier, index, player);
    }

    @Override
    public void onArtifactActivate(float durationInTicks, float cooldownInTicks, ItemStack stack, String identifier, int index, PlayerEntity player) {
        player.getAttributes().applyAttributeModifiers(this.getOrCreateAttributeModifiers(stack, identifier));
        if (!player.isCreative())
            player.getCooldownTracker().setCooldown(this, (int) durationInTicks);
    }

    public abstract Multimap<String, AttributeModifier> getAttributeModifiers(ItemStack stack, String identifier);

    private Multimap<String, AttributeModifier> getOrCreateAttributeModifiers(ItemStack stack, String identifier) {
        if (!CREATED_ATTRIBUTES.containsKey(identifier))
            CREATED_ATTRIBUTES.put(identifier, this.getAttributeModifiers(stack, identifier));
        return CREATED_ATTRIBUTES.get(identifier);
    }
}
