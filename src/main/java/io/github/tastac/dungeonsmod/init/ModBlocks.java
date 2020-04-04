package io.github.tastac.dungeonsmod.init;

import io.github.tastac.dungeonsmod.DungeonsMod;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Function;
import java.util.function.Supplier;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, DungeonsMod.MOD_ID);

    public static final RegistryObject<Block> CRATE = register("crate", () -> new Block(Block.Properties.from(Blocks.BARREL)), new Item.Properties());
    public static final RegistryObject<Block> DIRT_PATH = register("crate", () -> new Block(Block.Properties.from(Blocks.DIRT)), new Item.Properties());
    public static final RegistryObject<Block> ROCKY_DIRT_PATH = register("crate", () -> new Block(Block.Properties.from(Blocks.COBBLESTONE)), new Item.Properties());
    public static final RegistryObject<Block> STONE_BRICKS_TILE = register("crate", () -> new Block(Block.Properties.from(Blocks.STONE_BRICKS)), new Item.Properties());
    public static final RegistryObject<Block> DIRT_GRASSY_LESS = register("crate", () -> new Block(Block.Properties.from(Blocks.DIRT)), new Item.Properties());


    //Registry

    private static RegistryObject<Block> register(String name, Supplier<Block> block, Item.Properties itemProperties)
    {
        return register(name, block, object -> new BlockItem(object.get(), itemProperties.group(DungeonsMod.GROUP)));
    }

    private static RegistryObject<Block> register(String name, Supplier<Block> block, Function<RegistryObject<Block>, Item> item)
    {
        RegistryObject<Block> object = BLOCKS.register(name, block);
        ModItems.ITEMS.register(name, () -> item.apply(object));
        return object;
    }

}
