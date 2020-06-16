package io.github.tastac.dungeonsmod.common.item;

import io.github.tastac.dungeonsmod.DungeonsMod;
import io.github.tastac.dungeonsmod.common.entity.RegenerationTotemEntity;
import io.github.tastac.dungeonsmod.integration.registrate.DungeonsLang;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.List;

/**
 * @author CoffeeCatRailway
 * Created: 4/06/2020
 */
public class RegenerationTotem extends TotemArtifact {

    public static final String TAG_HEAL_AMOUNT = "HealAmount";
    public static final String TAG_HEAL_SPEED = "HealSpeed";

    public RegenerationTotem(Properties prop) {
        super(prop, 7.5f, 25f, 5f, new Color(0xFF0000), RegenerationTotemEntity::new);
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        CompoundNBT stackNbt = stack.getOrCreateTag();
        if (!stackNbt.contains(TAG_HEAL_AMOUNT, Constants.NBT.TAG_ANY_NUMERIC))
            stackNbt.putFloat(TAG_HEAL_AMOUNT, 1f);

        if (!stackNbt.contains(TAG_HEAL_SPEED, Constants.NBT.TAG_ANY_NUMERIC))
            stackNbt.putInt(TAG_HEAL_SPEED, (int) (DungeonsMod.SERVER_CONFIG.totemOfRegenerationSpeed.get() * 20));
        return super.initCapabilities(stack, nbt);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<ITextComponent> info, ITooltipFlag flag) {
        super.addInformation(stack, world, info, flag);
        CompoundNBT nbt = stack.getOrCreateTag();
        info.add(new StringTextComponent(TextFormatting.GOLD + I18n.format(this.getTranslationKey() + ".heal_amount") + " " +
                TextFormatting.YELLOW + nbt.getFloat(TAG_HEAL_AMOUNT) + "/" + (nbt.getInt(TAG_HEAL_SPEED) / 20f) + I18n.format(DungeonsLang.ARTIFACT_DESC_SECOND)));
    }
}
