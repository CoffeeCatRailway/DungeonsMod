package io.github.tastac.dungeonsmod.common.capability;

import io.github.tastac.dungeonsmod.DungeonsMod;
import io.github.tastac.dungeonsmod.network.NetworkHandler;
import io.github.tastac.dungeonsmod.network.server.SPacketSyncSouls;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.NetworkDirection;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author CoffeeCatRailway
 * Created: 22/06/2020
 */
public class SoulsCapibility {

    public static final ResourceLocation ID = DungeonsMod.getLocation("souls");
    public static final String TAG_SOULS = "Souls";

    @CapabilityInject(ISoulsHandler.class)
    public static final Capability<ISoulsHandler> SOULS_CAP = null;

    public static void register() {
        CapabilityManager.INSTANCE.register(ISoulsHandler.class, new Capability.IStorage<ISoulsHandler>() {
            @Nullable
            @Override
            public INBT writeNBT(Capability<ISoulsHandler> capability, ISoulsHandler instance, Direction side) {
                CompoundNBT compound = new CompoundNBT();
                compound.putInt(TAG_SOULS, instance.getSouls());
                return compound;
            }

            @Override
            public void readNBT(Capability<ISoulsHandler> capability, ISoulsHandler instance, Direction side, INBT nbt) {
                instance.addSouls(((CompoundNBT) nbt).getInt(TAG_SOULS));
            }
        }, SoulsWrapper::new);
    }

    public static class SoulsWrapper implements ISoulsHandler {

        int souls;
        LivingEntity owner;

        public SoulsWrapper() {
            this(null);
        }

        public SoulsWrapper(final LivingEntity owner) {
            this.souls = 0;
            this.owner = owner;
        }

        @Override
        public int getSouls() {
            return this.souls;
        }

        @Override
        public void setSouls(int souls) {
            this.souls = souls;
        }

        @Override
        public void addSouls(int amount) {
            if (amount > 0) {
                this.souls += amount;
                if (this.owner instanceof ServerPlayerEntity && ((ServerPlayerEntity) this.owner).connection != null)
                    NetworkHandler.INSTANCE.sendTo(new SPacketSyncSouls(this.owner.getEntityId(), amount, false),
                            ((ServerPlayerEntity) this.owner).connection.getNetworkManager(), NetworkDirection.PLAY_TO_CLIENT);
            }
        }

        @Override
        public void removeSouls(int amount) {
            if (amount > 0) {
                amount = Math.min(this.getSouls(), amount);
                if (this.owner instanceof ServerPlayerEntity && ((ServerPlayerEntity) this.owner).connection != null)
                    NetworkHandler.INSTANCE.sendTo(new SPacketSyncSouls(this.owner.getEntityId(), amount, true),
                            ((ServerPlayerEntity) this.owner).connection.getNetworkManager(), NetworkDirection.PLAY_TO_CLIENT);
                this.souls -= amount;
            }
        }
    }

    public static class Provider implements ICapabilitySerializable<INBT> {

        final LazyOptional<ISoulsHandler> optional;
        final ISoulsHandler handler;

        public Provider(final LivingEntity owner) {
            this.handler = new SoulsWrapper(owner);
            this.optional = LazyOptional.of(() -> this.handler);
        }

        @SuppressWarnings("ConstantConditions")
        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
            return SoulsCapibility.SOULS_CAP.orEmpty(cap, this.optional);
        }

        @SuppressWarnings("ConstantConditions")
        @Override
        public INBT serializeNBT() {
            return SoulsCapibility.SOULS_CAP.writeNBT(this.handler, null);
        }

        @SuppressWarnings("ConstantConditions")
        @Override
        public void deserializeNBT(INBT nbt) {
            SoulsCapibility.SOULS_CAP.readNBT(this.handler, null, nbt);
        }
    }
}
