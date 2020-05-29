package io.github.tastac.dungeonsmod.integration.registrate;

import com.tterrag.registrate.providers.RegistrateTagsProvider;
import com.tterrag.registrate.util.nullness.NonNullConsumer;
import io.github.tastac.dungeonsmod.DungeonsMod;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;

/**
 * @author CoffeeCatRailway
 * Created: 28/05/2020
 */
public class DungeonsTags {

    public static class Blocks implements NonNullConsumer<RegistrateTagsProvider<Block>> {

        @Override
        public void accept(RegistrateTagsProvider<Block> provider) {
            DungeonsMod.LOGGER.info("DataGen: Block tags");
        }

        static Tag<Block> tag(String name) {
            return new BlockTags.Wrapper(DungeonsMod.getLocation(name));
        }

        static Tag<Block> tag(String id, String name) {
            return new BlockTags.Wrapper(new ResourceLocation(id, name));
        }
    }

    public static class Items implements NonNullConsumer<RegistrateTagsProvider<Item>> {

        @Override
        public void accept(RegistrateTagsProvider<Item> provider) {
            provider.getBuilder(Items.CURIOS_CHARM).build(Items.CURIOS_CHARM.getId());

            DungeonsMod.LOGGER.info("DataGen: Item tags");
        }

        public static final Tag<Item> CURIOS_CHARM = tag("curios", "charm");

        static Tag<Item> tag(String name) {
            return new ItemTags.Wrapper(DungeonsMod.getLocation(name));
        }

        static Tag<Item> tag(String id, String name) {
            return new ItemTags.Wrapper(new ResourceLocation(id, name));
        }
    }
}
