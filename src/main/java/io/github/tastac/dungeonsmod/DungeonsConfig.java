package io.github.tastac.dungeonsmod;

import net.minecraftforge.common.ForgeConfigSpec;

/**
 * @author CoffeeCatRailway
 * Created: 28/05/2020
 */
public class DungeonsConfig {

    private static final String CONFIG_ARTIFACT = DungeonsMod.MOD_ID + ".artifact.";
    private static final String CONFIG_ARTIFACT_PROPERTIES = DungeonsMod.MOD_ID + ".artifact.properties.";
    private static final String CONFIG_ARTIFACT_SHIELD_TOTEM_COLOR = DungeonsMod.MOD_ID + ".artifact.shield_totem_color.";

    public static class Client {

        public ForgeConfigSpec.IntValue shieldTotemRed;
        public ForgeConfigSpec.IntValue shieldTotemGreen;
        public ForgeConfigSpec.IntValue shieldTotemBlue;
        public ForgeConfigSpec.IntValue shieldTotemAlpha;

        public Client(ForgeConfigSpec.Builder builder) {
            this.shieldTotemRed = builder.defineInRange(CONFIG_ARTIFACT_SHIELD_TOTEM_COLOR + "red", 204, 0, 255);
            this.shieldTotemGreen = builder.defineInRange(CONFIG_ARTIFACT_SHIELD_TOTEM_COLOR + "green", 143, 0, 255);
            this.shieldTotemBlue = builder.defineInRange(CONFIG_ARTIFACT_SHIELD_TOTEM_COLOR + "blue", 0, 0, 255);
            this.shieldTotemAlpha = builder.defineInRange(CONFIG_ARTIFACT_SHIELD_TOTEM_COLOR + "alpha", 255, 0, 255);
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
