package io.github.tastac.dungeonsmod.common.entity;

import io.github.tastac.dungeonsmod.common.init.DungeonsEntities;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

/**
 * @author CoffeeCatRailway
 * Created: 8/04/2020
 */
public class RedstoneMonstrosityEntity extends MonsterEntity {

    public RedstoneMonstrosityEntity(World world) {
        this(DungeonsEntities.REDSTONE_MONSTROSITY.get(), world);
    }

    public RedstoneMonstrosityEntity(EntityType<? extends MonsterEntity> type, World world) {
        super(type, world);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(1, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        this.goalSelector.addGoal(2, new RandomWalkingGoal(this, 1.0d));
    }
}
