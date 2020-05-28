package io.github.tastac.dungeonsmod.common.init;

import io.github.tastac.dungeonsmod.DungeonsMod;
import io.github.tastac.dungeonsmod.common.entity.RedstoneGolemEntity;
import io.github.tastac.dungeonsmod.common.entity.RedstoneMonstrosityEntity;
import io.github.tastac.dungeonsmod.common.item.TastyBoneItem;
import io.github.tastac.dungeonsmod.common.item.WindhornItem;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class DungeonsEntities {

    public static final DeferredRegister<EntityType<?>> ENTITIES = new DeferredRegister<>(ForgeRegistries.ENTITIES, DungeonsMod.MOD_ID);

    public static final RegistryObject<EntityType<RedstoneGolemEntity>> REDSTONE_GOLEM = ENTITIES.register("redstone_golem", () -> EntityType.Builder.<RedstoneGolemEntity>create(RedstoneGolemEntity::new, EntityClassification.MONSTER)
            .size(2.0f, 3.5f).setCustomClientFactory((spawnEntity, world) -> new RedstoneGolemEntity(world)).build("redstone_golem"));
    public static final RegistryObject<EntityType<RedstoneMonstrosityEntity>> REDSTONE_MONSTROSITY = ENTITIES.register("redstone_monstrosity", () -> EntityType.Builder.<RedstoneMonstrosityEntity>create(RedstoneMonstrosityEntity::new, EntityClassification.MONSTER)
            .size(4.0f, 5.5f).setCustomClientFactory((spawnEntity, world) -> new RedstoneMonstrosityEntity(world)).build("redstone_monstrosity"));
}
