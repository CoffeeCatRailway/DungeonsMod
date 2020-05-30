package io.github.tastac.dungeonsmod.registry;

import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateItemModelProvider;
import com.tterrag.registrate.util.RegistryEntry;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import io.github.tastac.dungeonsmod.DungeonsMod;
import io.github.tastac.dungeonsmod.common.item.ArtifactItem;
import io.github.tastac.dungeonsmod.common.item.DeathCapMushroomArtifact;
import io.github.tastac.dungeonsmod.integration.registrate.DungeonsLang;
import io.github.tastac.dungeonsmod.integration.registrate.DungeonsTags;
import net.minecraft.item.Item;

import static io.github.tastac.dungeonsmod.DungeonsMod.REGISTRATE;

/**
 * @author CoffeeCatRailway
 * Created: 28/05/2020
 */
public class DungeonsItems {

    public static final RegistryEntry<DeathCapMushroomArtifact> DEATH_CAP_MUSHROOM = reisterArtifact("death_cap_mushroom", "Greatly increases attack and movement speed",
            DeathCapMushroomArtifact::new, NonNullBiConsumer.noop());

    private static <T extends ArtifactItem> RegistryEntry<T> reisterArtifact(String id, String description, NonNullFunction<Item.Properties, T> factory, NonNullBiConsumer<DataGenContext<Item, T>, RegistrateItemModelProvider> model) {
        DungeonsLang.ARTIFACT_LANGS.put("item." + DungeonsMod.MOD_ID + "." + id + ".description", description);
        return REGISTRATE.item(id, factory).defaultLang().tag(DungeonsTags.Items.CURIOS_CHARM).model(model).register();
    }

    public static void load() {
        DungeonsMod.LOGGER.info("Register items");
    }
}
