package io.github.tastac.dungeonsmod;

import net.minecraftforge.common.ForgeConfigSpec;

/**
 * @author CoffeeCatRailway
 * Created: 28/05/2020
 */
public class DungeonsConfig {

    private static final String CONFIG_ARTIFACT = DungeonsMod.MOD_ID + ".artifact.";
    private static final String CONFIG_ARTIFACT_PROPERTIES = CONFIG_ARTIFACT + "properties.";
    private static final String CONFIG_ARTIFACT_PROPERTIES_TOTEM = CONFIG_ARTIFACT + "properties.totem.";

    public static class Client {

        public ForgeConfigSpec.DoubleValue totemEndDuration;

        public ForgeConfigSpec.IntValue regenerationTotemColor;
        public ForgeConfigSpec.IntValue shieldTotemColor;

        public Client(ForgeConfigSpec.Builder builder) {
            this.totemEndDuration = builder.comment("The duration of which a totem start to end (fall)").comment("Stored in ticks: 20 ticks = 1 second")
                    .defineInRange(CONFIG_ARTIFACT_PROPERTIES_TOTEM + "endDuration", 40f, 5f, 100f);

            int colorMin = Integer.MIN_VALUE;
            int colorMax = Integer.MAX_VALUE;
            this.regenerationTotemColor = builder.defineInRange(CONFIG_ARTIFACT_PROPERTIES_TOTEM + "regenerationColor", 0xff1600, colorMin, colorMax);
            this.shieldTotemColor = builder.defineInRange(CONFIG_ARTIFACT_PROPERTIES_TOTEM + "shieldColor", 0xcc8f00, colorMin, colorMax);
        }
    }

    public static class Server {

        public ForgeConfigSpec.BooleanValue canRightClickEquip;

        public ForgeConfigSpec.DoubleValue totemOfRegenerationSpeed;
        public ForgeConfigSpec.DoubleValue shieldBounceOffset;

        public Server(ForgeConfigSpec.Builder builder) {
            this.canRightClickEquip = builder.comment("If true, you can press the right-mouse button to equip an artifact").define(CONFIG_ARTIFACT + "canRightClickEquip", true);

            this.totemOfRegenerationSpeed = builder.comment("How long it takes the Totem Of Regeneration it heal nearby players per second")
                    .defineInRange(CONFIG_ARTIFACT_PROPERTIES + "totemOfRegenerationSpeed", .75d, .5d, 60d);
            this.shieldBounceOffset = builder.comment("How close a projectile appears to bounce of the Totem Of Shielding")
                    .defineInRange(CONFIG_ARTIFACT_PROPERTIES + "shieldBounceOffset", .5d, 0d, 1d);
        }
    }
}
