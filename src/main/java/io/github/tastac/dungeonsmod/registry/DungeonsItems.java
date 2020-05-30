package io.github.tastac.dungeonsmod.registry;

import com.tterrag.registrate.util.RegistryEntry;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import io.github.tastac.dungeonsmod.DungeonsMod;
import io.github.tastac.dungeonsmod.common.item.DeathCapMushroomArtifact;
import io.github.tastac.dungeonsmod.integration.registrate.DungeonsTags;

import static io.github.tastac.dungeonsmod.DungeonsMod.REGISTRATE;

/**
 * @author CoffeeCatRailway
 * Created: 28/05/2020
 */
public class DungeonsItems {

    public static final RegistryEntry<DeathCapMushroomArtifact> DEATH_CAP_MUSHROOM = REGISTRATE.item("death_cap_mushroom", DeathCapMushroomArtifact::new)
            .defaultLang().tag(DungeonsTags.Items.CURIOS_CHARM).model(NonNullBiConsumer.noop()).register();

    public static void load() {
        DungeonsMod.LOGGER.info("Register items");
    }
}
