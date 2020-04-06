package io.github.tastac.dungeonsmod.common.structure;

import com.mojang.datafixers.Dynamic;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeManager;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.template.TemplateManager;

import java.util.Random;
import java.util.function.Function;

public class DungeonStructure extends Structure<NoFeatureConfig>
{
    public DungeonStructure(Function<Dynamic<?>, ? extends NoFeatureConfig> configFactory)
    {
        super(configFactory);
    }

    @Override
    public boolean func_225558_a_(BiomeManager biomeManager, ChunkGenerator chunkGenerator, Random rand, int chunkX, int chunkZ, Biome biome)
    {
        return true;
    }

    @Override
    public IStartFactory getStartFactory()
    {
        return Start::new;
    }

    @Override
    public String getStructureName()
    {
        return "Dungeonsmod_Dungeon";
    }

    @Override
    public int getSize()
    {
        return 1;
    }

    public static class Start extends StructureStart
    {

        public Start(Structure<?> structure, int chunkPosX, int chunkPosZ, MutableBoundingBox bounds, int references, long seed)
        {
            super(structure, chunkPosX, chunkPosZ, bounds, references, seed);
        }

        @Override
        public void init(ChunkGenerator<?> generator, TemplateManager templateManagerIn, int chunkX, int chunkZ, Biome biomeIn)
        {

        }
    }
}
