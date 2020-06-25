package io.github.tastac.dungeonsmod.registry;

import io.github.tastac.dungeonsmod.DungeonsMod;
import io.github.tastac.dungeonsmod.common.particles.SoulParticleData;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * @author CoffeeCatRailway
 * Created: 25/06/2020
 */
public class DungeonsParticles {

    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, DungeonsMod.MOD_ID);

    public static final RegistryObject<ParticleType<SoulParticleData>> SOUL = PARTICLE_TYPES.register("soul", () -> new ParticleType<>(false, SoulParticleData.DESERIALIZER));
}
