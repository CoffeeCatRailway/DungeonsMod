package io.github.tastac.dungeonsmod.common.item;

import io.github.tastac.dungeonsmod.integration.CuriosIntegration;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;

/**
 * @author CoffeeCatRailway
 * Created: 28/05/2020
 */
public abstract class ArtifactItem extends Item implements IDungeonsCurio {

    public static final String TAG_ACTIVE = "Activate";

    public ArtifactItem(Properties prop) {
        super(prop.maxStackSize(1));
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        CompoundNBT stackNbt = stack.getOrCreateTag();
        if (!stackNbt.contains(TAG_ACTIVE, Constants.NBT.TAG_BYTE))
            stackNbt.putBoolean(TAG_ACTIVE, false);
        return CuriosIntegration.getCapability(stack);
    }

    @Override
    public void curioTick(ItemStack stack, String identifier, int index, PlayerEntity player) {
        if (this.isActive(stack)) this.activate(stack, false);
    }

    public void activate(ItemStack stack, boolean active) {
        if (!stack.isEmpty())
            stack.getOrCreateTag().putBoolean(TAG_ACTIVE, active);
    }

    public boolean isActive(ItemStack stack) {
        CompoundNBT nbt = stack.getOrCreateTag();
        if (nbt.contains(TAG_ACTIVE, Constants.NBT.TAG_BYTE))
            return nbt.getBoolean(TAG_ACTIVE);
        return false;
    }
}
