package io.github.tastac.dungeonsmod.common.entity;

import io.github.tastac.dungeonsmod.DungeonsMod;
import io.github.tastac.dungeonsmod.common.item.ArtifactItem;
import io.github.tastac.dungeonsmod.common.item.TotemArtifact;
import io.github.tastac.dungeonsmod.registry.DungeonsEntities;
import io.github.tastac.dungeonsmod.registry.DungeonsItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.function.Supplier;

/**
 * @author CoffeeCatRailway
 * Created: 12/06/2020
 */
public class ShieldingTotemEntity extends TotemEntity {

    private static final Vec3d VEC_TWO = new Vec3d(2d, 2d, 2d);

    public ShieldingTotemEntity(EntityType<? extends TotemEntity> entityType, World world) {
        super(entityType, world);
    }

    public ShieldingTotemEntity(World world, PlayerEntity player, ItemStack stack, float durationInTicks) {
        super(DungeonsEntities.TOTEM_OF_SHIELDING.get(), world, player, stack, durationInTicks);
    }

    @Override
    public void tick() {
        super.tick();
        ItemStack totemStack = this.getTotem();
        if (!totemStack.isEmpty()) {
            double shieldBounceOffset = DungeonsMod.SERVER_CONFIG.shieldBounceOffset.get();
            double range = totemStack.getOrCreateTag().getFloat(ArtifactItem.TAG_RANGE) + shieldBounceOffset;

            this.world.getEntitiesWithinAABBExcludingEntity(this, this.getBoundingBox().grow(range - shieldBounceOffset))
                    .stream().filter(entity -> entity instanceof IProjectile).forEach(projectile -> {
                if (projectile.getPositionVec().squareDistanceTo(this.getPositionVec()) <= range * range) {
                    Vec3d n = projectile.getPositionVec().subtract(this.getPositionVec()).normalize();
                    Vec3d d = projectile.getMotion().normalize();
                    double dot = d.dotProduct(n);
                    projectile.setMotion(d.subtract(VEC_TWO.mul(dot, dot, dot).mul(n)).mul(d.length(), d.length(), d.length()));
                }
            });
        }
    }

    @Override
    public Supplier<? extends TotemArtifact> getTotemArtifactSupplier() {
        return DungeonsItems.TOTEM_OF_SHIELDING;
    }
}
