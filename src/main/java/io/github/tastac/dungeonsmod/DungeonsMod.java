package io.github.tastac.dungeonsmod;

import io.github.tastac.dungeonsmod.common.init.*;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.logging.Logger;

import static io.github.tastac.dungeonsmod.DungeonsMod.MOD_ID;

@SuppressWarnings("unused")
@Mod(MOD_ID)
public class DungeonsMod
{
    public static final String MOD_ID = "dungeonsmod";
    public static final Logger LOGGER = Logger.getLogger(MOD_ID);
    public static final ItemGroup GROUP = new ItemGroup(MOD_ID)
    {
        @Override
        public ItemStack createIcon()
        {
            return new ItemStack(DungeonsBlocks.CRATE.get());
        }
    };

    public DungeonsMod()
    {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        DungeonsItems.ITEMS.register(modBus);
        DungeonsBlocks.BLOCKS.register(modBus);
        DungeonsFeatures.FEATURES.register(modBus);
        DungeonsBiomes.BIOMES.register(modBus);
        DungeonsDimensions.DIMENSIONS.register(modBus);
        MinecraftForge.EVENT_BUS.register(this);
    }
}