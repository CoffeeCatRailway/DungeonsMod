package io.github.tastac.dungeonsmod.registry;

import com.tterrag.registrate.util.entry.RegistryEntry;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import io.github.tastac.dungeonsmod.DungeonsMod;
import io.github.tastac.dungeonsmod.common.entity.TotemOfRegenerationEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;

import static io.github.tastac.dungeonsmod.DungeonsMod.REGISTRATE;

/**
 * @author CoffeeCatRailway
 * Created: 4/06/2020
 */
public class DungeonsEntities {

    public static final RegistryEntry<EntityType<TotemOfRegenerationEntity>> TOTEM_OF_REGENERATION = REGISTRATE.<TotemOfRegenerationEntity>entity("totem_of_regeneration", TotemOfRegenerationEntity::new, EntityClassification.MISC)
            .properties(prop -> prop.size(.5f, .5f).setShouldReceiveVelocityUpdates(false)).defaultLang().loot(NonNullBiConsumer.noop()).register();

    public static void load() {
        DungeonsMod.LOGGER.info("Register entities");
    }
}
