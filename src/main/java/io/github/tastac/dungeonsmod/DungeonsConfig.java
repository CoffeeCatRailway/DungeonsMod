package io.github.tastac.dungeonsmod;

import net.minecraftforge.common.ForgeConfigSpec;

/**
 * @author CoffeeCatRailway
 * Created: 28/05/2020
 */
public class DungeonsConfig {

    private static final String CONFIG_ARTIFACT = DungeonsMod.MOD_ID + ".artifact.";

    public static class Server {

        public ForgeConfigSpec.BooleanValue canRightClickEquip;

        public Server(ForgeConfigSpec.Builder builder) {
            this.canRightClickEquip = builder.comment("If true, you can press the right-mouse button to equip an artifact").define(CONFIG_ARTIFACT + "canRightClickEquip", true);
        }
    }
}
