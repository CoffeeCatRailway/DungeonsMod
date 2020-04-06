package io.github.tastac.dungeonsmod.common.init;

import io.github.tastac.dungeonsmod.DungeonsMod;
import io.github.tastac.dungeonsmod.common.dimension.DungeonDimension;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.ModDimension;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class DungeonsDimensions
{
    public static final DeferredRegister<ModDimension> DIMENSIONS = new DeferredRegister<>(ForgeRegistries.MOD_DIMENSIONS, DungeonsMod.MOD_ID);

    public static final RegistryObject<ModDimension> DUNGEON = DIMENSIONS.register("dungeon", () -> ModDimension.withFactory(DungeonDimension::new));
}
