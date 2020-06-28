package io.github.tastac.dungeonsmod.common.item;

import io.github.tastac.dungeonsmod.integration.registrate.DungeonsLang;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

/**
 * @author CoffeeCatRailway
 * Created: 28/06/2020
 *
 * This is to <em>only</em> be used for items that are "work in progess"!
 */
@Deprecated
public class WIPItem extends Item {

    public WIPItem(Properties properties) {
        super(properties);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new TranslationTextComponent(DungeonsLang.WIP_ITEM, stack.getItem().getName()));
    }
}
