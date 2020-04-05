package io.github.tastac.dungeonsmod;

import io.github.tastac.dungeonsmod.common.init.ModBlocks;
import io.github.tastac.dungeonsmod.common.init.ModItems;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.logging.Logger;

import static io.github.tastac.dungeonsmod.DungeonsMod.MOD_ID;

@Mod(MOD_ID)
public class DungeonsMod {

    public static final String MOD_ID = "dungeonsmod", NAME = "Dungeons Mod", VERSION = "0.1.0";
    public static final Logger LOGGER = Logger.getLogger(MOD_ID);
    public static final ItemGroup GROUP = new ItemGroup(MOD_ID){
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModBlocks.CRATE.get());
        }
    };


    public DungeonsMod(){
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModItems.ITEMS.register(modBus);
        ModBlocks.BLOCKS.register(modBus);
    }

}
