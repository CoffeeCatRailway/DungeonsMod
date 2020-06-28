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
import net.minecraft.util.text.TextFormatting;

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
            "Eaton by daring warriors before battle, the Death Cap Mushroom drives fighters into a frenzy", DeathCapMushroomArtifact::new).model(NonNullBiConsumer.noop()).register();

    public static final RegistryEntry<WindHornArtifact> WIND_HORN = registerArtifact("wind_horn", "Pushes enemies away from you and slows them briefly",
            "When the Wind Horn echoes throughout the forests of the Overworld the creatures of the night tremble with fear", WindHornArtifact::new).model(NonNullBiConsumer.noop()).register();

    public static final RegistryEntry<LoveMedallionArtifact> LOVE_MEDALLION = registerArtifact("love_medallion", "Turn up to thee hostile mobs into allies for ten seconds before they disappear",
            "A spell radiates from this trinket, enchanting those nearby into a trance where they must protect the holder of the medallion at all costs", LoveMedallionArtifact::new).model(NonNullBiConsumer.noop()).register();

    public static final RegistryEntry<IronHideAmuletArtifact> IRON_HIDE_AMULET = registerArtifact("iron_hide_amulet", "It provides a major boost to defense for a short time",
            "The Iron Hide Amulet is both ancient and timeless. Sand mysteriously and endlessly slips through the cracks in the iron", IronHideAmuletArtifact::new).model(NonNullBiConsumer.noop()).register();

    public static final RegistryEntry<BootsOfSwistnessArtifact> BOOTS_OF_SWISTNESS = registerArtifact("boots_of_swiftness", "Gives a short boost to movement speed",
            "Boots blessed with enchantments to allow for swift movements. Useful in uncertain times such as these", BootsOfSwistnessArtifact::new).model(NonNullBiConsumer.noop()).register();

    public static final RegistryEntry<RegenerationTotem> TOTEM_OF_REGENERATION = registerArtifact("totem_of_regeneration", "A totem that creates a circular aura, healing you and your allies",
            "This hand-crafted wooden figurine radiates a warmth like that of a crackling campfire, healing those who gather around it", RegenerationTotem::new, () -> {
                Map<String, String> langs = new HashMap<>();
                langs.put("heal_amount", "Hearts Healed");
                return langs;
            }).model(NonNullBiConsumer.noop()).register();

    public static final RegistryEntry<ShieldingTotem> TOTEM_OF_SHIELDING = registerArtifact("totem_of_shielding", "This totem has mystical powers that shield those around it from projectiles",
            "This totem radiates powerful energy that bursts forth as a protective shield around those near it", ShieldingTotem::new).model(NonNullBiConsumer.noop()).register();

    public static final RegistryEntry<SoulHealerArtifact> SOUL_HEALER = registerArtifact("soul_healer", "Heals the most injured ally nearby, including yourself",
            "The Soul Healer amulet is cold to the touch and trembles with the power of souls. It is a common among the Illagers of the Woodland Mansions", SoulHealerArtifact::new).model(NonNullBiConsumer.noop()).register();

    public static final RegistryEntry<WIPItem> LIGHTNING_ROD = REGISTRATE.item("lightning_rod", WIPItem::new).model((ctx, provider) -> provider.handheld(ctx::getEntry)).register();

    public static final RegistryEntry<WIPItem> LIGHT_FEATHER = REGISTRATE.item("light_feather", WIPItem::new).model((ctx, provider) -> provider.handheld(ctx::getEntry)).register();

    private static <T extends ArtifactItem> ItemBuilder<T, Registrate> registerArtifact(String id, String description, String flavourText, NonNullFunction<Item.Properties, T> factory) {
        return registerArtifact(id, description, flavourText, factory, HashMap::new);
    }

    private static <T extends ArtifactItem> ItemBuilder<T, Registrate> registerArtifact(String id, String description, String flavourText, NonNullFunction<Item.Properties, T> factory, Supplier<Map<String, String>> langs) {
        String langPrefix = "item." + DungeonsMod.MOD_ID + "." + id + ".";
        DungeonsLang.ARTIFACT_LANGS.put(langPrefix + "description", description);
        DungeonsLang.ARTIFACT_LANGS.put(langPrefix + "flavourText", TextFormatting.GRAY + flavourText);

        langs.get().forEach((key, value) -> DungeonsLang.ARTIFACT_LANGS.put(langPrefix + key, value));
        return REGISTRATE.item(id, factory).defaultLang().tag(DungeonsTags.Items.CURIOS_CHARM);
    }

    public static void load() {
        DungeonsMod.LOGGER.info("Register items");
    }
}
