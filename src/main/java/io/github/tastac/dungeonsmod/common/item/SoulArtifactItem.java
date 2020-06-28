package io.github.tastac.dungeonsmod.common.item;

import io.github.tastac.dungeonsmod.common.capability.SoulsCapibility;
import io.github.tastac.dungeonsmod.integration.registrate.DungeonsLang;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
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
 * Created: 24/06/2020
 */
public abstract class SoulArtifactItem extends ArtifactItem {

    public static final String TAG_SOUL_GATHERING = "SoulGathering";

    private int soulGathering;

    public SoulArtifactItem(Properties prop, float durationInSeconds, float cooldownInSeconds, int soulGathering) {
        super(prop, durationInSeconds, cooldownInSeconds);
        this.hasManualShiftInfoPlacement(true);
        this.soulGathering = soulGathering;
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        CompoundNBT stackNbt = stack.getOrCreateTag();
        if (this.soulGathering > 0)
            if (!stackNbt.contains(TAG_SOUL_GATHERING, Constants.NBT.TAG_ANY_NUMERIC))
                stackNbt.putInt(TAG_SOUL_GATHERING, this.soulGathering);
        return super.initCapabilities(stack, nbt);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<ITextComponent> info, ITooltipFlag flag) {
        super.addInformation(stack, world, info, flag);

        CompoundNBT nbt = stack.getOrCreateTag();
        int soulGathering = nbt.getInt(TAG_SOUL_GATHERING);
        info.add(new StringTextComponent(TextFormatting.GOLD + I18n.format(DungeonsLang.ARTIFACT_DESC_SOULS_GATHERED) + ": " + TextFormatting.YELLOW + "+" + soulGathering));
        info.add(new TranslationTextComponent(DungeonsLang.ARTIFACT_DESC_REQUIRES_SOULS));

        super.addShiftInfo(info);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onArtifactActivate(float durationInTicks, float cooldownInTicks, ItemStack stack, String identifier, int index, PlayerEntity player) {
        player.getCapability(SoulsCapibility.SOULS_CAP).ifPresent(handler -> {
            int playerSouls = handler.getSouls();

            if (playerSouls > 0 || player.isCreative()) {
                if (player.isCreative())
                    playerSouls = 20;
                int minSouls = playerSouls / 4;
                int usedSouls = Math.max(1, player.world.rand.nextInt(playerSouls - minSouls + 1) + minSouls);

                this.onSoulActivate(durationInTicks, cooldownInTicks, stack, identifier, index, player, playerSouls, usedSouls);
            }
        });
    }

    @SuppressWarnings("ConstantConditions")
    protected void removeSouls(PlayerEntity player, int usedSouls) {
        if (!player.isCreative())
            player.getCapability(SoulsCapibility.SOULS_CAP).ifPresent(handler -> handler.removeSouls(usedSouls));
    }

    public abstract void onSoulActivate(float durationInTicks, float cooldownInTicks, ItemStack stack, String identifier, int index, PlayerEntity player, int playerSouls, int usedSouls);
}
