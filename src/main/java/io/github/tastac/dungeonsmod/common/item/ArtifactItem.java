package io.github.tastac.dungeonsmod.common.item;

import io.github.tastac.dungeonsmod.integration.CuriosIntegration;
import io.github.tastac.dungeonsmod.integration.registrate.DungeonsLang;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
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
    public static final String TAG_ACTIVE = "Active";
    public static final String TAG_DURATION = "DurationInTicks";
    public static final String TAG_COOLDOWN = "CooldownInTicks";
    /*
     * Additional tags
     */
    public static final String TAG_RANGE = "Range";

    private float durationInTicks;
    private float cooldownInTicks;
    private float range = 0f;

    private boolean manualSideCheck = false;
    private boolean manualShiftInfoPlacement = false;

    private boolean showDuration;
    private boolean showCooldown;
    private boolean showRange;

    public ArtifactItem(Properties prop, float durationInSeconds, float cooldownInSeconds) {
        super(prop.maxStackSize(1));
        this.durationInTicks = durationInSeconds * 20f;
        this.cooldownInTicks = cooldownInSeconds * 20f;
        this.showDuration = this.durationInTicks != 0f;
        this.showCooldown = this.cooldownInTicks != 0f;
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        CompoundNBT stackNbt = stack.getOrCreateTag();
        if (!stackNbt.contains(TAG_ACTIVE, Constants.NBT.TAG_BYTE))
            stackNbt.putBoolean(TAG_ACTIVE, false);

        if (this.durationInTicks > 0f)
            if (!stackNbt.contains(TAG_DURATION, Constants.NBT.TAG_ANY_NUMERIC))
                stackNbt.putFloat(TAG_DURATION, this.durationInTicks);

        if (this.cooldownInTicks > 0f)
            if (!stackNbt.contains(TAG_COOLDOWN, Constants.NBT.TAG_ANY_NUMERIC))
                stackNbt.putFloat(TAG_COOLDOWN, this.cooldownInTicks);

        if (this.hasRange())
            if (!stackNbt.contains(TAG_RANGE, Constants.NBT.TAG_ANY_NUMERIC))
                stackNbt.putFloat(TAG_RANGE, this.range);
        return CuriosIntegration.getCapability(stack);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<ITextComponent> info, ITooltipFlag flag) {
        CompoundNBT nbt = stack.getOrCreateTag();
        float duration = nbt.getFloat(TAG_DURATION) / 20f;
        float cooldown = nbt.getFloat(TAG_COOLDOWN) / 20f;
        info.add(new TranslationTextComponent(this.getTranslationKey() + ".description"));

        if (this.showDuration)
            info.add(new StringTextComponent(TextFormatting.GOLD + I18n.format(DungeonsLang.ARTIFACT_DESC_PREFIX + "duration") + ": " + TextFormatting.YELLOW + duration + I18n.format(DungeonsLang.ARTIFACT_DESC_SECOND)));

        if (this.showCooldown)
            info.add(new StringTextComponent(TextFormatting.GOLD + I18n.format(DungeonsLang.ARTIFACT_DESC_PREFIX + "cooldown") + ": " + TextFormatting.YELLOW + cooldown + I18n.format(DungeonsLang.ARTIFACT_DESC_SECOND)));

        if (this.showRange && this.hasRange())
            info.add(new StringTextComponent(TextFormatting.GOLD + I18n.format(DungeonsLang.ARTIFACT_DESC_PREFIX + "range") + ": " + TextFormatting.YELLOW + range + " " + I18n.format(DungeonsLang.ARTIFACT_DESC_BLOCKS)));

        if (!this.isManualShiftInfoPlacement())
            this.addShiftInfo(info);
    }

    protected void addShiftInfo(List<ITextComponent> info) {
        if (Screen.hasShiftDown())
            info.add(new StringTextComponent(I18n.format(this.getTranslationKey() + ".flavourText")));
        else
            info.add(new StringTextComponent(I18n.format(DungeonsLang.ARTIFACT_DESC_HOLD_SHIFT, "more info.")));
    }

    @Override
    public void curioTick(ItemStack stack, String identifier, int index, PlayerEntity player) {
        CompoundNBT nbt = stack.getOrCreateTag();
        float durationInTicks = nbt.getFloat(TAG_DURATION);
        float cooldownInTicks = nbt.getFloat(TAG_COOLDOWN);
        if (this.isActive(stack) && !player.getCooldownTracker().hasCooldown(this)) {
            if (this.isRemote(player.world))
                this.onArtifactActivate(durationInTicks, cooldownInTicks, stack, identifier, index, player);
            player.getCooldownTracker().setCooldown(this, (int) (cooldownInTicks + durationInTicks));
        }
        if (this.isActive(stack)) this.activate(stack, false);
    }

    public abstract void onArtifactActivate(float durationInTicks, float cooldownInTicks, ItemStack stack, String identifier, int index, PlayerEntity player);

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

    protected boolean isRemote(World world) {
        return !world.isRemote || this.isManualSideCheck();
    }

    public boolean isManualSideCheck() {
        return manualSideCheck;
    }

    protected void hasManualSideCheck(boolean manualSideCheck) {
        this.manualSideCheck = manualSideCheck;
    }

    public boolean isManualShiftInfoPlacement() {
        return manualShiftInfoPlacement;
    }

    protected void hasManualShiftInfoPlacement(boolean manualShiftInfoPlacement) {
        this.manualShiftInfoPlacement = manualShiftInfoPlacement;
    }

    public boolean isShowingDuration() {
        return showDuration;
    }

    protected void showDuration(boolean showDuration) {
        this.showDuration = showDuration;
    }

    public boolean isShowingCooldown() {
        return showCooldown;
    }

    protected void showCooldown(boolean showCooldown) {
        this.showCooldown = showCooldown;
    }

    public boolean hasRange() {
        return this.range != 0f;
    }

    protected void setRange(float range) {
        this.setRange(range, range != 0f);
    }

    protected void setRange(float range, boolean showRange) {
        this.range = range;
        this.showRange = showRange;
    }
}
