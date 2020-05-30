package io.github.tastac.dungeonsmod;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.providers.ProviderType;
import io.github.tastac.dungeonsmod.integration.CuriosIntegration;
import io.github.tastac.dungeonsmod.integration.registrate.DungeonsLang;
import io.github.tastac.dungeonsmod.integration.registrate.DungeonsTags;
import io.github.tastac.dungeonsmod.network.PacketHandler;
import io.github.tastac.dungeonsmod.registry.DungeonsItems;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.commons.lang3.tuple.Pair;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static io.github.tastac.dungeonsmod.DungeonsMod.MOD_ID;

@SuppressWarnings("unused")
@Mod(MOD_ID)
public class DungeonsMod {

    public static final String MOD_ID = "dungeonsmod";

    public static final Logger LOGGER = Logger.getLogger(MOD_ID);
    public static Registrate REGISTRATE;

    public static final List<KeyBinding> KEY_REGISTRY = new ArrayList<>();
    public static final String KEY_CATEGORY = "key." + MOD_ID + ".category";

    public static final KeyBinding USE_ARTIFACT_1 = registerKeyBinding("use_artifact_1", GLFW.GLFW_KEY_Z);
    public static final KeyBinding USE_ARTIFACT_2 = registerKeyBinding("use_artifact_2", GLFW.GLFW_KEY_X);
    public static final KeyBinding USE_ARTIFACT_3 = registerKeyBinding("use_artifact_3", GLFW.GLFW_KEY_C);

    public static DungeonsConfig.Server SERVER_CONFIG;

    public static final ItemGroup GROUP = new ItemGroup(MOD_ID) {

        private final ResourceLocation BACKGROUND_IMAGE = DungeonsMod.getLocation("textures/gui/container/creative_inventory/tab_items.png");
        private final ResourceLocation TABS_IMAGE = DungeonsMod.getLocation("textures/gui/container/creative_inventory/tabs.png");

        @Override
        public ItemStack createIcon() {
            return new ItemStack(DungeonsItems.DEATH_CAP_MUSHROOM.get());
        }

        @Override
        @OnlyIn(Dist.CLIENT)
        public ResourceLocation getBackgroundImage() {
            return BACKGROUND_IMAGE;
        }

        @Override
        @OnlyIn(Dist.CLIENT)
        public ResourceLocation getTabsImage() {
            return TABS_IMAGE;
        }
    };

    public DungeonsMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::setupClient);
        modEventBus.addListener(this::setupCommon);

        final Pair<DungeonsConfig.Server, ForgeConfigSpec> serverConfig = new ForgeConfigSpec.Builder().configure(DungeonsConfig.Server::new);
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, serverConfig.getRight());
        SERVER_CONFIG = serverConfig.getLeft();

        FMLJavaModLoadingContext.get().getModEventBus().addListener(CuriosIntegration::sendImc);
        MinecraftForge.EVENT_BUS.register(this);

        REGISTRATE = Registrate.create(MOD_ID);
        REGISTRATE.itemGroup(() -> GROUP);
        REGISTRATE.addDataGenerator(ProviderType.BLOCK_TAGS, new DungeonsTags.Blocks());
        REGISTRATE.addDataGenerator(ProviderType.ITEM_TAGS, new DungeonsTags.Items());
        REGISTRATE.addDataGenerator(ProviderType.LANG, new DungeonsLang());

        DungeonsItems.load();
    }

    public void setupClient(final FMLClientSetupEvent event) {
        ClientEvents.setupClient(event);

        KEY_REGISTRY.forEach(ClientRegistry::registerKeyBinding);
        DungeonsMod.LOGGER.info("Client Event: Register key binds");
    }

    public void setupCommon(FMLCommonSetupEvent event) {
        PacketHandler.init();
    }

    private static KeyBinding registerKeyBinding(String id, final int key) {
        KeyBinding bind = new KeyBinding("key." + MOD_ID + "." + id, key, KEY_CATEGORY);
        KEY_REGISTRY.add(bind);
        return bind;
    }

    public static ResourceLocation getLocation(String path) {
        return new ResourceLocation(DungeonsMod.MOD_ID, path);
    }
}