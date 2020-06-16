package io.github.tastac.dungeonsmod;

import net.minecraftforge.common.ForgeConfigSpec;

/**
 * @author CoffeeCatRailway
 * Created: 28/05/2020
 */
public class DungeonsConfig {

    private static final String CONFIG_ARTIFACT = DungeonsMod.MOD_ID + ".artifact.";
    private static final String CONFIG_ARTIFACT_PROPERTIES = DungeonsMod.MOD_ID + ".artifact.properties.";

    public static class Client {

        public Client(ForgeConfigSpec.Builder builder) {
        }
    }

    public static class Server {

        public ForgeConfigSpec.BooleanValue canRightClickEquip;

        public ForgeConfigSpec.DoubleValue totemOfRegenerationSpeed;

        public Server(ForgeConfigSpec.Builder builder) {
            this.canRightClickEquip = builder.comment("If true, you can press the right-mouse button to equip an artifact").define(CONFIG_ARTIFACT + "canRightClickEquip", true);

            this.totemOfRegenerationSpeed = builder.comment("How long it takes the Totem Of Regeneration it heal nearby players per second")
                    .defineInRange(CONFIG_ARTIFACT_PROPERTIES + "totemOfRegenerationSpeed", .75d, .5d, 60d);
        }
    }
}
