package io.github.tastac.dungeonsmod.integration.registrate;

import com.tterrag.registrate.providers.RegistrateLangProvider;
import com.tterrag.registrate.util.nullness.NonNullConsumer;
import io.github.tastac.dungeonsmod.DungeonsMod;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

import java.util.HashMap;
import java.util.Map;

/**
 * @author CoffeeCatRailway
 * Created: 28/05/2020
 */
public class DungeonsLang implements NonNullConsumer<RegistrateLangProvider> {

    public static final Map<String, String> ARTIFACT_LANGS = new HashMap<>();
    public static final String ARTIFACT_DESC_PREFIX = "item." + DungeonsMod.MOD_ID + ".artifact.";
    public static final String ARTIFACT_DESC_SECOND = ARTIFACT_DESC_PREFIX + "second";
    public static final String ARTIFACT_DESC_BLOCKS = ARTIFACT_DESC_PREFIX + "blocks";
    public static final String ARTIFACT_DESC_HOLD_SHIFT = ARTIFACT_DESC_PREFIX + "hold_shift";
    public static final String ARTIFACT_DESC_REQUIRES_SOULS = ARTIFACT_DESC_PREFIX + "requires_souls";
    public static final String ARTIFACT_DESC_SOULS_GATHERED = ARTIFACT_DESC_PREFIX + "souls_gathered";

    @Override
    public void accept(RegistrateLangProvider provider) {
        provider.add(DungeonsMod.GROUP, new StringTextComponent(TextFormatting.GRAY + "Dungeons Mod").getText());

        provider.add(DungeonsMod.KEY_CATEGORY, "Dungeons Mod");
        provider.add(DungeonsMod.USE_ARTIFACT_1.getKeyDescription(), "Activate First Artifact");
        provider.add(DungeonsMod.USE_ARTIFACT_2.getKeyDescription(), "Activate Second Artifact");
        provider.add(DungeonsMod.USE_ARTIFACT_3.getKeyDescription(), "Activate Third Artifact");

        provider.add(ARTIFACT_DESC_PREFIX + "duration", "Duration");
        provider.add(ARTIFACT_DESC_PREFIX + "cooldown", "Cooldown");
        provider.add(ARTIFACT_DESC_SECOND, "sec");
        provider.add(ARTIFACT_DESC_BLOCKS, "blocks");
        provider.add(ARTIFACT_DESC_HOLD_SHIFT, new StringTextComponent(TextFormatting.GRAY + "Hold [Shift] for %s").getText());
        provider.add(ARTIFACT_DESC_REQUIRES_SOULS, "Requires Souls");
        provider.add(ARTIFACT_DESC_SOULS_GATHERED, "Soul Gathering");
        ARTIFACT_LANGS.forEach(provider::add);

        DungeonsMod.LOGGER.info("DataGen: Lang");
    }
}
