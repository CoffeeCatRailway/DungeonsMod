package io.github.tastac.dungeonsmod.common.entity;

import io.github.tastac.dungeonsmod.DungeonsMod;
import io.github.tastac.dungeonsmod.registry.DungeonsItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Pose;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SSpawnObjectPacket;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

/**
 * @author CoffeeCatRailway
 * Created: 4/06/2020
 */
public class TotemOfRegenerationEntity extends Entity {

    private static final DataParameter<ItemStack> TOTEM = EntityDataManager.createKey(TotemOfRegenerationEntity.class, DataSerializers.ITEMSTACK);

    public TotemOfRegenerationEntity(EntityType<? extends TotemOfRegenerationEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected float getEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return 0.0F;
    }

    @Override
    protected void registerData() {
        this.getDataManager().register(TOTEM, ItemStack.EMPTY);
    }

    public void tick() {
        if (!this.world.isRemote) {
//            System.out.println("TEST-tick " + this.getPosition());
        }
    }

    public ItemStack getItem() {
        ItemStack itemstack = this.getDataManager().get(TOTEM);
        if (itemstack.getItem() != DungeonsItems.TOTEM_OF_REGENERATION.get()) {
            if (this.world != null)
                DungeonsMod.LOGGER.error("TotemOfRegenerationEntity {} has no item?!", this.getEntityId());
            return new ItemStack(DungeonsItems.TOTEM_OF_REGENERATION.get());
        } else {
            return itemstack;
        }
    }

    public void setItem(ItemStack stack) {
        this.getDataManager().set(TOTEM, stack.copy());
    }

    @Override
    protected void readAdditional(CompoundNBT compound) {
        ItemStack stack = ItemStack.read(compound.getCompound("Totem"));
        if (stack.isEmpty())
            this.remove();
        else
            this.setItem(stack);
    }

    @Override
    protected void writeAdditional(CompoundNBT compound) {
        ItemStack stack = this.getItem();
        compound.put("Totem", stack.write(new CompoundNBT()));
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
