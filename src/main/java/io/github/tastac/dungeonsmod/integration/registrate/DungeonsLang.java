package io.github.tastac.dungeonsmod.integration.registrate;

import com.tterrag.registrate.providers.RegistrateLangProvider;
import com.tterrag.registrate.util.nullness.NonNullConsumer;
import io.github.tastac.dungeonsmod.DungeonsMod;

/**
 * @author CoffeeCatRailway
 * Created: 28/05/2020
 */
public class DungeonsLang implements NonNullConsumer<RegistrateLangProvider> {

    @Override
    public void accept(RegistrateLangProvider provider) {
        provider.add(DungeonsMod.GROUP, "Dungeons Mod");

        provider.add(DungeonsMod.KEY_CATEGORY, "Dungeons Mod");
        provider.add(DungeonsMod.USE_ARTIFACT_1.getKeyDescription(), "Activate First Artifact");
        provider.add(DungeonsMod.USE_ARTIFACT_2.getKeyDescription(), "Activate Second Artifact");
        provider.add(DungeonsMod.USE_ARTIFACT_3.getKeyDescription(), "Activate Third Artifact");

        DungeonsMod.LOGGER.info("DataGen: Lang");
    }
}
