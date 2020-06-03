package io.github.tastac.dungeonsmod.integration;

import com.mojang.blaze3d.matrix.MatrixStack;
import io.github.tastac.dungeonsmod.DungeonsMod;
import io.github.tastac.dungeonsmod.common.item.IDungeonsCurio;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import top.theillusivec4.curios.api.CuriosAPI;
import top.theillusivec4.curios.api.capability.CuriosCapability;
import top.theillusivec4.curios.api.capability.ICurio;
import top.theillusivec4.curios.api.capability.ICurioItemHandler;
import top.theillusivec4.curios.api.imc.CurioIMCMessage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author CoffeeCatRailway
 * Created: 28/05/2020
 */
public class CuriosIntegration {

    public static void sendImc(InterModEnqueueEvent event) {
        InterModComms.sendTo(CuriosAPI.MODID, CuriosAPI.IMC.REGISTER_TYPE, () -> new CurioIMCMessage("charm").setSize(3));
        DungeonsMod.LOGGER.info("Common Event: Register curios types");
    }

    public static ItemStack getArtifactStack(PlayerEntity player) {
        return getArtifactStack(player, -1);
    }

    public static ItemStack getArtifactStack(PlayerEntity player, int slot) {
        AtomicReference<ItemStack> artifact = new AtomicReference<>(ItemStack.EMPTY);

        if (CuriosAPI.getType("charm").isPresent()) {
            LazyOptional<ICurioItemHandler> optional = CuriosAPI.getCuriosHandler(player);
            optional.ifPresent(handler -> {
                if (handler.getStackInSlot("charm", slot).getItem() instanceof IDungeonsCurio)
                    artifact.set(handler.getStackInSlot("charm", slot));
            });
        }

        return artifact.get();
    }

    public static ICapabilityProvider getCapability(ItemStack stack) {
        return new ICapabilityProvider() {

            private final LazyOptional<ICurio> opt = LazyOptional.of(() -> new Wrapper(stack));

            @Nonnull
            @Override
            public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
                return CuriosCapability.ITEM.orEmpty(cap, opt);
            }
        };
    }

    private static class Wrapper implements ICurio {

        private final ItemStack stack;

        public Wrapper(ItemStack stack) {
            this.stack = stack;
        }

        @Override
        public void onCurioTick(String identifier, int index, LivingEntity entity) {
            if (!entity.world.isRemote && entity instanceof PlayerEntity)
                ((IDungeonsCurio) stack.getItem()).curioTick(stack, identifier, index, (PlayerEntity) entity);
        }

        @Override
        public boolean canEquip(String identifier, LivingEntity entity) {
            return ((IDungeonsCurio) stack.getItem()).canEquip(stack, identifier, entity);
        }

        @Override
        public boolean canUnequip(String identifier, LivingEntity entity) {
            return ((IDungeonsCurio) stack.getItem()).canUnequip(stack, identifier, entity);
        }

        @Override
        public boolean canRightClickEquip() {
            return ((IDungeonsCurio) stack.getItem()).canRightClickEquip(stack);
        }

        @Override
        public void playEquipSound(LivingEntity entity) {
            ((IDungeonsCurio) stack.getItem()).playEquipSound(stack, entity);
        }

        @Override
        public boolean hasRender(String identifier, LivingEntity entity) {
            return ((IDungeonsCurio) stack.getItem()).hasRender(stack, identifier, entity);
        }

        @Override
        public void render(String identifier, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int light, LivingEntity entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageTicks, float headYaw, float headPitch) {
            if (this.hasRender(identifier, entity))
                ((IDungeonsCurio) stack.getItem()).render(stack, identifier, matrixStack, renderTypeBuffer, light, entity, limbSwing, limbSwingAmount, partialTicks, ageTicks, headYaw, headPitch);
        }
    }
}
