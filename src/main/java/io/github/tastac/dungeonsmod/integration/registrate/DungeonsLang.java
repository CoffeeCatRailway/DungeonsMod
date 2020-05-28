package io.github.tastac.dungeonsmod.integration.registrate;

import com.tterrag.registrate.providers.RegistrateLangProvider;
import com.tterrag.registrate.util.nullness.NonNullConsumer;
import io.github.tastac.dungeonsmod.DungeonsMod;

/**
 * @author CoffeeCatRailway
 * Created: 28/05/2020
 */
public class DungeonsLang implements NonNullConsumer<RegistrateLangProvider> {

    public static final DungeonsLang INSTANCE = new DungeonsLang();

    @Override
    public void accept(RegistrateLangProvider provider) {
        provider.add(DungeonsMod.GROUP, "Dungeons Mod");

        DungeonsMod.LOGGER.info("DataGen: Lang");
    }
}
