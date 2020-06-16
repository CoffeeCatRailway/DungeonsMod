package io.github.tastac.dungeonsmod.common.entity;

import io.github.tastac.dungeonsmod.common.item.ArtifactItem;
import io.github.tastac.dungeonsmod.common.item.RegenerationTotem;
import io.github.tastac.dungeonsmod.common.item.TotemArtifact;
import io.github.tastac.dungeonsmod.registry.DungeonsEntities;
import io.github.tastac.dungeonsmod.registry.DungeonsItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.World;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author CoffeeCatRailway
 * Created: 4/06/2020
 */
public class RegenerationTotemEntity extends TotemEntity {

    public RegenerationTotemEntity(EntityType<? extends TotemEntity> entityType, World world) {
        super(entityType, world);
    }

    public RegenerationTotemEntity(World world, PlayerEntity player, ItemStack stack, float durationInTicks) {
        super(DungeonsEntities.TOTEM_OF_REGENERATION.get(), world, player, stack, durationInTicks);
    }

    @Override
    public Supplier<? extends TotemArtifact> getTotemArtifactSupplier() {
        return DungeonsItems.TOTEM_OF_REGENERATION;
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.world.isRemote) {
            CompoundNBT nbt = this.getTotem().getOrCreateTag();
            float range = nbt.getFloat(ArtifactItem.TAG_RANGE);
            float healAmount = nbt.getFloat(RegenerationTotem.TAG_HEAL_AMOUNT);
            List<PlayerEntity> players = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getBoundingBox().grow(range)).stream()
                    .filter(entity -> entity instanceof PlayerEntity).map(entity -> (PlayerEntity) entity).collect(Collectors.toList());
            players.forEach(player -> {
                if (this.ticksExisted % nbt.getInt(RegenerationTotem.TAG_HEAL_SPEED) == 0)
                    player.heal(healAmount);
            });
        }
    }
}
