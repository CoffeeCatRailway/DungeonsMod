package io.github.tastac.dungeonsmod.common.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class WindhornItem extends ArtifactItem {

    public static final int COOLDOWN = 1 * 20;
    public static final int PUSHDISTANCE = 5;

    public WindhornItem(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {

        player.getCooldownTracker().setCooldown(this, COOLDOWN);
        List<Entity> entities = world.getEntitiesWithinAABBExcludingEntity(player, player.getBoundingBox().grow(PUSHDISTANCE));
        Vec3d pPos = player.getPositionVec();

        entities.forEach(entity -> {
            Vec3d ePos = entity.getPositionVec();
            Vec3d vel = new Vec3d(ePos.x - pPos.x, ePos.y - pPos.y, ePos.z - pPos.z).normalize().mul(2.25, 2.25, 2.25);

            entity.addVelocity(vel.x, vel.y, vel.z);
        });

        return super.onItemRightClick(world, player, hand);
    }

}
