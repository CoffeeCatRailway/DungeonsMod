package io.github.tastac.dungeonsmod;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.providers.ProviderType;
import io.github.tastac.dungeonsmod.client.SoulsHUDOverlayHandler;
import io.github.tastac.dungeonsmod.common.capability.SoulsCapibility;
import io.github.tastac.dungeonsmod.common.command.SoulsCommand;
import io.github.tastac.dungeonsmod.common.item.SoulArtifactItem;
import io.github.tastac.dungeonsmod.common.particles.SoulParticleData;
import io.github.tastac.dungeonsmod.integration.CuriosIntegration;
import io.github.tastac.dungeonsmod.integration.registrate.DungeonsLang;
import io.github.tastac.dungeonsmod.integration.registrate.DungeonsTags;
import io.github.tastac.dungeonsmod.network.NetworkHandler;
import io.github.tastac.dungeonsmod.network.server.SPacketSyncSoulsTotal;
import io.github.tastac.dungeonsmod.registry.DungeonsEntities;
import io.github.tastac.dungeonsmod.registry.DungeonsItems;
import io.github.tastac.dungeonsmod.registry.DungeonsParticles;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.PacketDistributor;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;
import top.theillusivec4.curios.api.CuriosAPI;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static io.github.tastac.dungeonsmod.DungeonsMod.MOD_ID;

@SuppressWarnings("unused")
@Mod(MOD_ID)
public class DungeonsMod {

    public static final String MOD_ID = "dungeonsmod";

    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
    public static Registrate REGISTRATE;

    private static final List<KeyBinding> KEY_REGISTRY = new ArrayList<>();
    public static final String KEY_CATEGORY = "key." + MOD_ID + ".category";

    public static final KeyBinding USE_ARTIFACT_1 = registerKeyBinding("use_artifact_1", GLFW.GLFW_KEY_Z);
    public static final KeyBinding USE_ARTIFACT_2 = registerKeyBinding("use_artifact_2", GLFW.GLFW_KEY_X);
    public static final KeyBinding USE_ARTIFACT_3 = registerKeyBinding("use_artifact_3", GLFW.GLFW_KEY_C);

    public static DungeonsConfig.Client CLIENT_CONFIG;
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

        final Pair<DungeonsConfig.Client, ForgeConfigSpec> clientConfig = new ForgeConfigSpec.Builder().configure(DungeonsConfig.Client::new);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, clientConfig.getRight());
        CLIENT_CONFIG = clientConfig.getLeft();

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
        DungeonsEntities.load();
        DungeonsParticles.PARTICLE_TYPES.register(modEventBus);
    }

    public void setupClient(final FMLClientSetupEvent event) {
        SoulsHUDOverlayHandler.init();
        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> ClientEvents::entityRenderers);
        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> ClientEvents::particleFactories);

        KEY_REGISTRY.forEach(ClientRegistry::registerKeyBinding);
        DungeonsMod.LOGGER.info("Client Event: Register key binds");
    }

    public void setupCommon(final FMLCommonSetupEvent event) {
        SoulsCapibility.register();
        NetworkHandler.init();
    }

    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        SoulsCommand.register(event.getCommandDispatcher());
    }

    @SubscribeEvent
    public void onCapabilitiesEntity(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof PlayerEntity) {
            event.addCapability(SoulsCapibility.ID, new SoulsCapibility.Provider((PlayerEntity) event.getObject()));
        }
    }

    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof LivingEntity) {
            entity.getCapability(SoulsCapibility.SOULS_CAP).ifPresent(handler -> {
                if (entity instanceof ServerPlayerEntity) {
                    ServerPlayerEntity serverPlayer = (ServerPlayerEntity) entity;
                    NetworkHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> serverPlayer), new SPacketSyncSoulsTotal(serverPlayer.getEntityId(), handler.getSouls()));
                }
            });
        }
    }

    @SubscribeEvent
    public void onPlayerStartTracking(PlayerEvent.StartTracking event) {
        Entity target = event.getTarget();
        PlayerEntity player = event.getPlayer();

        if (player instanceof ServerPlayerEntity && target instanceof LivingEntity) {
            target.getCapability(SoulsCapibility.SOULS_CAP).ifPresent(handler -> NetworkHandler.INSTANCE
                    .send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new SPacketSyncSoulsTotal(target.getEntityId(), handler.getSouls())));
        }
    }

    @SubscribeEvent
    public void onEntityDie(LivingDeathEvent event) { // TODO: Add entity/particle for souls
        if (event.getSource().getTrueSource() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getSource().getTrueSource();
            World world = player.world;

            if (CuriosIntegration.hasSoulArtifact(player).isEmpty())
                return;

            player.getCapability(SoulsCapibility.SOULS_CAP).ifPresent(playerHandler -> {
                LivingEntity entity = event.getEntityLiving();
                AtomicInteger soulCount = new AtomicInteger(1);
                if (entity instanceof PlayerEntity) {
                    entity.getCapability(SoulsCapibility.SOULS_CAP).ifPresent(handler -> {
                        int souls = player.world.rand.nextInt(handler.getSouls() / 2) + 1;
                        soulCount.set(souls);
                    });
                }

                for (int slot = 0; slot < CuriosAPI.getType("charm").get().getSize(); slot++) {
                    ItemStack charm = CuriosIntegration.getArtifactStack(player, slot);
                    if (charm.getItem() instanceof SoulArtifactItem) {
                        soulCount.set(soulCount.get() * charm.getOrCreateTag().getInt(SoulArtifactItem.TAG_SOUL_GATHERING));
                    }
                }

                playerHandler.addSouls(soulCount.get());
                if (!world.isRemote) {
                    float speed = SERVER_CONFIG.soulsParticleSpeed.get().floatValue();
                    ((ServerWorld) world).spawnParticle(SoulParticleData.create(player), entity.getPosX(), entity.getPosY(), entity.getPosZ(), soulCount.get(), 0d, 0d, 0d, speed);
                }
            });
        }
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