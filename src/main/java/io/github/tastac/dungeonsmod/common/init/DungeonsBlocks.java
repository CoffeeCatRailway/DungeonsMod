package io.github.tastac.dungeonsmod.common.init;

import io.github.tastac.dungeonsmod.DungeonsMod;
import io.github.tastac.dungeonsmod.common.block.PathBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Function;
import java.util.function.Supplier;

public class DungeonsBlocks
{

    public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, DungeonsMod.MOD_ID);

    public static final RegistryObject<Block> CRATE = register("crate", () -> new Block(Block.Properties.from(Blocks.BARREL)));
    public static final RegistryObject<Block> DIRT_PATH = register("dirt_path", () -> new PathBlock(Block.Properties.from(Blocks.DIRT)));
    public static final RegistryObject<Block> ROCKY_DIRT_PATH = register("rocky_dirt_path", () -> new Block(Block.Properties.from(Blocks.COBBLESTONE)));
    public static final RegistryObject<Block> STONE_BRICKS_TILE = register("stone_bricks_tile", () -> new Block(Block.Properties.from(Blocks.STONE_BRICKS)));
    public static final RegistryObject<Block> DIRT_GRASSY_LESS = register("dirt_grassy_less", () -> new Block(Block.Properties.from(Blocks.DIRT)));

    //Registry

    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> block)
    {
        return register(name, block, object -> new BlockItem(object.get(), new Item.Properties().group(DungeonsMod.GROUP)));
    }

    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> block, Item.Properties itemProperties)
    {
        return register(name, block, object -> new BlockItem(object.get(), itemProperties));
    }

    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> block, Function<RegistryObject<T>, Item> item)
    {
        RegistryObject<T> object = BLOCKS.register(name, block);
        DungeonsItems.ITEMS.register(name, () -> item.apply(object));
        return object;
    }
}
