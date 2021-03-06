package io.github.tastac.dungeonsmod.common.entity;

import io.github.tastac.dungeonsmod.DungeonsMod;
import io.github.tastac.dungeonsmod.common.item.ArtifactItem;
import io.github.tastac.dungeonsmod.common.item.TotemArtifact;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.function.Supplier;

/**
 * @author CoffeeCatRailway
 * Created: 11/06/2020
 */
public abstract class TotemEntity extends Entity {

    private static final String TAG_TOTEM = "Totem";
    private static final DataParameter<ItemStack> TOTEM = EntityDataManager.createKey(RegenerationTotemEntity.class, DataSerializers.ITEMSTACK);

    @OnlyIn(Dist.CLIENT)
    public float deadAngle = 0f;
    @OnlyIn(Dist.CLIENT)
    public float yOffset = 0f;

    private Supplier<? extends TotemArtifact> totemArtifactSupplier;

    public TotemEntity(EntityType<? extends TotemEntity> entityType, World world) {
        super(entityType, world);
        this.totemArtifactSupplier = this.getTotemArtifactSupplier();
    }

    public TotemEntity(EntityType<? extends TotemEntity> entityType, World world, PlayerEntity player, ItemStack stack, float durationInTicks) {
        super(entityType, world);
        this.setPositionAndRotation(player.getPositionVec().x, player.getPositionVec().y, player.getPositionVec().z, player.rotationYaw, 0f);
        this.setTotem(stack.copy());
        this.setDuration(durationInTicks);
        this.totemArtifactSupplier = this.getTotemArtifactSupplier();
    }

    public abstract Supplier<? extends TotemArtifact> getTotemArtifactSupplier();

    @Override
    public void tick() {
        super.tick();
        if (!this.world.isRemote) {
            if (this.ticksExisted >= this.getDuration())
                this.remove();
        }
    }

    @Override
    protected void registerData() {
        this.getDataManager().register(TOTEM, ItemStack.EMPTY);
    }

    @Override
    protected void readAdditional(CompoundNBT compound) {
        if (compound.contains(TAG_TOTEM, Constants.NBT.TAG_COMPOUND))
            this.setTotem(ItemStack.read(compound.getCompound(TAG_TOTEM)));

        ItemStack stack = this.getTotem();
        CompoundNBT totemNbt = stack.getOrCreateTag();
        if (totemNbt.contains(ArtifactItem.TAG_DURATION, Constants.NBT.TAG_ANY_NUMERIC))
            this.setDuration(totemNbt.getFloat(ArtifactItem.TAG_DURATION));
    }

    @Override
    protected void writeAdditional(CompoundNBT compound) {
        ItemStack stack = this.getTotem();
        CompoundNBT totemNbt = stack.getOrCreateTag();
        if (!totemNbt.contains(ArtifactItem.TAG_DURATION, Constants.NBT.TAG_ANY_NUMERIC))
            totemNbt.putFloat(ArtifactItem.TAG_DURATION, this.getDuration());

        if (!compound.contains(TAG_TOTEM, Constants.NBT.TAG_COMPOUND))
            compound.put(TAG_TOTEM, stack.write(new CompoundNBT()));
    }

    public ItemStack getTotem() {
        ItemStack totem = this.getDataManager().get(TOTEM);
        if (totem.getItem() != this.totemArtifactSupplier.get()) {
            if (this.world != null)
                DungeonsMod.LOGGER.warn("TotemEntity {} doesn't have a totem?!", this.getUniqueID());
            return setTotem(new ItemStack(this.totemArtifactSupplier.get()));
        } else {
            return totem;
        }
    }

    public ItemStack setTotem(ItemStack stack) {
        ItemStack newStack = stack.copy();
        this.getDataManager().set(TOTEM, newStack);
        return newStack;
    }

    public float getDuration() {
        ItemStack totem = this.getDataManager().get(TOTEM);
        CompoundNBT nbt = totem.getOrCreateTag();
        if (nbt.contains(ArtifactItem.TAG_DURATION, Constants.NBT.TAG_ANY_NUMERIC))
            return nbt.getFloat(ArtifactItem.TAG_DURATION);
        return 0f;
    }

    public void setDuration(float duration) {
        ItemStack totem = this.getDataManager().get(TOTEM);
        totem.getOrCreateTag().putFloat(ArtifactItem.TAG_DURATION, duration);
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
