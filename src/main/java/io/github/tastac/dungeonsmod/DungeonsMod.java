package io.github.tastac.dungeonsmod;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.providers.ProviderType;
import io.github.tastac.dungeonsmod.integration.CuriosIntegration;
import io.github.tastac.dungeonsmod.integration.registrate.DungeonsLang;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.logging.Logger;

import static io.github.tastac.dungeonsmod.DungeonsMod.MOD_ID;

@SuppressWarnings("unused")
@Mod(MOD_ID)
public class DungeonsMod {

    public static final String MOD_ID = "dungeonsmod";

    public static final Logger LOGGER = Logger.getLogger(MOD_ID);
    public static Registrate REGISTRATE;

    public static final ItemGroup GROUP = new ItemGroup(MOD_ID) {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(Items.STICK);
        }
    };

    public DungeonsMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::setupClient);
        modEventBus.addListener(this::setupCommon);

        // CONFIG

        FMLJavaModLoadingContext.get().getModEventBus().addListener(CuriosIntegration::sendImc);
        MinecraftForge.EVENT_BUS.register(this);

        REGISTRATE = Registrate.create(MOD_ID);
        REGISTRATE.itemGroup(() -> GROUP);
        REGISTRATE.addDataGenerator(ProviderType.LANG, DungeonsLang.INSTANCE);
    }

    public void setupClient(final FMLClientSetupEvent event) {
        ClientEvents.setupClient(event);
    }

    public void setupCommon(FMLCommonSetupEvent event) {

    }

    public static ResourceLocation getLocation(String path) {
        return new ResourceLocation(DungeonsMod.MOD_ID, path);
    }
}