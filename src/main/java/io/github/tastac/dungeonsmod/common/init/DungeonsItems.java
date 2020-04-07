package io.github.tastac.dungeonsmod.common.init;

import io.github.tastac.dungeonsmod.DungeonsMod;
import io.github.tastac.dungeonsmod.common.item.WindhornItem;
import io.github.tastac.dungeonsmod.common.item.TastyBoneItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class DungeonsItems {

    public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, DungeonsMod.MOD_ID);

    public static final RegistryObject<WindhornItem> WINDHORN = ITEMS.register("windhorn", () -> new WindhornItem(new Item.Properties().group(DungeonsMod.GROUP).maxStackSize(1)));
    public static final RegistryObject<TastyBoneItem> TASTY_BONE = ITEMS.register("tasty_bone", () -> new TastyBoneItem(new Item.Properties().group(DungeonsMod.GROUP).maxStackSize(1)));
}
