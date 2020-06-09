package io.github.tastac.dungeonsmod.registry;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.util.entry.RegistryEntry;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import io.github.tastac.dungeonsmod.DungeonsMod;
import io.github.tastac.dungeonsmod.common.item.*;
import io.github.tastac.dungeonsmod.integration.registrate.DungeonsLang;
import io.github.tastac.dungeonsmod.integration.registrate.DungeonsTags;
import net.minecraft.item.Item;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import static io.github.tastac.dungeonsmod.DungeonsMod.REGISTRATE;

/**
 * @author CoffeeCatRailway
 * Created: 28/05/2020
 */
public class DungeonsItems {

    public static final RegistryEntry<DeathCapMushroomArtifact> DEATH_CAP_MUSHROOM = registerArtifact("death_cap_mushroom", "Greatly increases attack and movement speed",
            DeathCapMushroomArtifact::new).model(NonNullBiConsumer.noop()).register();

    public static final RegistryEntry<WindHornArtifact> WIND_HORN = registerArtifact("wind_horn", "Pushes enemies away from you and slows them briefly",
            WindHornArtifact::new).model(NonNullBiConsumer.noop()).register();

    public static final RegistryEntry<LoveMedallionArtifact> LOVE_MEDALLION = registerArtifact("love_medallion", "Turn up to thee hostile mobs into allies for ten seconds before they disappear",
            LoveMedallionArtifact::new).model(NonNullBiConsumer.noop()).register();

    public static final RegistryEntry<IronHideAmuletArtifact> IRON_HIDE_AMULET = registerArtifact("iron_hide_amulet", "It provides a major boost to defense for a short time",
            IronHideAmuletArtifact::new).model(NonNullBiConsumer.noop()).register();

    public static final RegistryEntry<BootsOfSwistnessArtifact> BOOTS_OF_SWISTNESS = registerArtifact("boots_of_swiftness", "Gives a short boost to movement speed",
            BootsOfSwistnessArtifact::new).model(NonNullBiConsumer.noop()).register();

    public static final RegistryEntry<TotemOfRegenerationArtifact> TOTEM_OF_REGENERATION = registerArtifact("totem_of_regeneration", "A totem that creates a circular aura, healing you and your allies",
            TotemOfRegenerationArtifact::new, () -> {
                Map<String, String> langs = new HashMap<>();
                langs.put("heal_amount", "Hearts Healed");
                return langs;
            }).model(NonNullBiConsumer.noop()).register();

    private static <T extends ArtifactItem> ItemBuilder<T, Registrate> registerArtifact(String id, String description, NonNullFunction<Item.Properties, T> factory) {
        return registerArtifact(id, description, factory, HashMap::new);
    }

    private static <T extends ArtifactItem> ItemBuilder<T, Registrate> registerArtifact(String id, String description, NonNullFunction<Item.Properties, T> factory, Supplier<Map<String, String>> langs) {
        String langPrefix = "item." + DungeonsMod.MOD_ID + "." + id + ".";
        DungeonsLang.ARTIFACT_LANGS.put(langPrefix + "description", description);
        langs.get().forEach((key, value) -> DungeonsLang.ARTIFACT_LANGS.put(langPrefix + key, value));
        return REGISTRATE.item(id, factory).defaultLang().tag(DungeonsTags.Items.CURIOS_CHARM);
    }

    public static void load() {
        DungeonsMod.LOGGER.info("Register items");
    }
}
