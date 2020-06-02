package io.github.tastac.dungeonsmod.common.item;

import io.github.tastac.dungeonsmod.integration.CuriosIntegration;
import io.github.tastac.dungeonsmod.integration.registrate.DungeonsLang;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;
import java.util.List;

/**
 * @author CoffeeCatRailway
 * Created: 28/05/2020
 */
public abstract class ArtifactItem extends Item implements IDungeonsCurio {

    /*
     * Main tags
     */
    public static final String TAG_ACTIVE = "Activate";
    public static final String TAG_DURATION = "Duration";
    public static final String TAG_COOLDOWN = "Cooldown";
    /*
     * Additional tags
     */
    public static final String TAG_RANGE = "Range";

    private float duration;
    private float cooldown;

    public ArtifactItem(Properties prop, float duration, float cooldown) {
        super(prop.maxStackSize(1));
        this.duration = duration * 20f;
        this.cooldown = cooldown * 20f;
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        CompoundNBT stackNbt = stack.getOrCreateTag();
        if (!stackNbt.contains(TAG_ACTIVE, Constants.NBT.TAG_BYTE))
            stackNbt.putBoolean(TAG_ACTIVE, false);

        if (this.duration > 0f)
            if (!stackNbt.contains(TAG_DURATION, Constants.NBT.TAG_ANY_NUMERIC))
                stackNbt.putFloat(TAG_DURATION, this.duration);

        if (this.cooldown > 0f)
            if (!stackNbt.contains(TAG_COOLDOWN, Constants.NBT.TAG_ANY_NUMERIC))
                stackNbt.putFloat(TAG_COOLDOWN, this.cooldown);
        return CuriosIntegration.getCapability(stack);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<ITextComponent> info, ITooltipFlag flag) {
        CompoundNBT nbt = stack.getOrCreateTag();
        float duration = nbt.getFloat(TAG_DURATION) / 20f;
        float cooldown = nbt.getFloat(TAG_COOLDOWN) / 20f;
        info.add(new StringTextComponent(I18n.format(this.getTranslationKey() + ".description")));
        if (duration > 0)
            info.add(new StringTextComponent(TextFormatting.GOLD + I18n.format(DungeonsLang.ARTIFACT_DESC_PREFIX + "duration") + ": " + TextFormatting.YELLOW + duration + I18n.format(DungeonsLang.ARTIFACT_DESC_SECOND)));
        if (cooldown > 0)
            info.add(new StringTextComponent(TextFormatting.GOLD + I18n.format(DungeonsLang.ARTIFACT_DESC_PREFIX + "cooldown") + ": " + TextFormatting.YELLOW + cooldown + I18n.format(DungeonsLang.ARTIFACT_DESC_SECOND)));
    }

    @Override
    public void curioTick(ItemStack stack, String identifier, int index, PlayerEntity player) {
        CompoundNBT nbt = stack.getOrCreateTag();
        float duration = nbt.getFloat(TAG_DURATION);
        float cooldown = nbt.getFloat(TAG_COOLDOWN);
        if (this.isActive(stack) && !player.getCooldownTracker().hasCooldown(this)) {
            this.onArtifactActivate(duration, cooldown, stack, identifier, index, player);
            player.getCooldownTracker().setCooldown(this, (int) (cooldown + duration));
        }
        if (this.isActive(stack)) this.activate(stack, false);
    }

    public abstract void onArtifactActivate(float duration, float cooldown, ItemStack stack, String identifier, int index, PlayerEntity player);

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
