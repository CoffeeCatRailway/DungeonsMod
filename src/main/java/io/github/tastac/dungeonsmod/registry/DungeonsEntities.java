package io.github.tastac.dungeonsmod.registry;

import com.tterrag.registrate.util.entry.RegistryEntry;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import io.github.tastac.dungeonsmod.DungeonsMod;
import io.github.tastac.dungeonsmod.common.entity.RegenerationTotemEntity;
import io.github.tastac.dungeonsmod.common.entity.TotemEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;

import static io.github.tastac.dungeonsmod.DungeonsMod.REGISTRATE;

/**
 * @author CoffeeCatRailway
 * Created: 4/06/2020
 */
public class DungeonsEntities {

    public static final RegistryEntry<EntityType<RegenerationTotemEntity>> TOTEM_OF_REGENERATION = registerTotem("totem_of_regeneration", RegenerationTotemEntity::new);

    private static <T extends TotemEntity> RegistryEntry<EntityType<T>> registerTotem(String id, EntityType.IFactory<T> factory) {
        return REGISTRATE.<T>entity(id, factory, EntityClassification.MISC)
                .properties(prop -> prop.size(1f, 1f).setShouldReceiveVelocityUpdates(false)).defaultLang().loot(NonNullBiConsumer.noop()).register();
    }

    public static void load() {
        DungeonsMod.LOGGER.info("Register entities");
    }
}
