package io.github.tastac.dungeonsmod.common.init;

import io.github.tastac.dungeonsmod.DungeonsMod;
import io.github.tastac.dungeonsmod.common.block.PathBlock;
import io.github.tastac.dungeonsmod.common.block.TableBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Function;
import java.util.function.Supplier;

public class DungeonsBlocks
{

    public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, DungeonsMod.MOD_ID);

    public static final RegistryObject<Block> CRATE = register("crate", () -> new Block(Block.Properties.from(Blocks.BARREL)), new Item.Properties());
    public static final RegistryObject<Block> DIRT_PATH = register("dirt_path", () -> new PathBlock(Block.Properties.from(Blocks.DIRT)), new Item.Properties());
    public static final RegistryObject<Block> ROCKY_DIRT_PATH = register("rocky_dirt_path", () -> new Block(Block.Properties.from(Blocks.COBBLESTONE)), new Item.Properties());
    public static final RegistryObject<Block> STONE_BRICKS_TILE = register("stone_bricks_tile", () -> new Block(Block.Properties.from(Blocks.STONE_BRICKS)), new Item.Properties());
    public static final RegistryObject<Block> DIRT_GRASSY_LESS = register("dirt_grassy_less", () -> new Block(Block.Properties.from(Blocks.DIRT)), new Item.Properties());

    public static final RegistryObject<TableBlock> TABLE = register("table", () -> new TableBlock(Block.Properties.from(Blocks.OAK_PLANKS)), new Item.Properties());

    //Registry

    private static RegistryObject<Block> register(String name, Supplier<Block> block, Item.Properties itemProperties)
    {
        return register(name, block, object -> new BlockItem(object.get(), itemProperties.group(DungeonsMod.GROUP)));
    }

    private static RegistryObject<Block> register(String name, Supplier<Block> block, Function<RegistryObject<Block>, Item> item)
    {
        RegistryObject<Block> object = BLOCKS.register(name, block);
        DungeonsItems.ITEMS.register(name, () -> item.apply(object));
        return object;
    }

}
