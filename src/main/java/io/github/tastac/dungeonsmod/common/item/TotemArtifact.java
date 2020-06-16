package io.github.tastac.dungeonsmod.common.item;

import com.mojang.blaze3d.matrix.MatrixStack;
import io.github.tastac.dungeonsmod.common.entity.TotemEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.awt.*;
import java.util.Random;

/**
 * @author CoffeeCatRailway
 * Created: 11/06/2020
 */
public class TotemArtifact extends ArtifactItem {

    private TotemSupplier supplier;
    private Color dustColor;

    public TotemArtifact(Properties prop, float durationInSeconds, float cooldownInSeconds, float range, Color dustColor, TotemSupplier supplier) {
        super(prop, durationInSeconds, cooldownInSeconds);
        this.hasManualSideCheck(true);
        this.showDuration(false);
        this.setRange(range, false);
        this.dustColor = dustColor;
        this.supplier = supplier;
    }

    @Override
    public void onArtifactActivate(float durationInTicks, float cooldownInTicks, ItemStack stack, String identifier, int index, PlayerEntity player) {
        World world = player.world;
        TotemEntity totem = this.supplier.get(world, player, stack, durationInTicks);
        if (!world.isRemote) {
            ServerWorld serverWorld = (ServerWorld) world;
            if (serverWorld.addEntity(totem)) {
                Random rand = serverWorld.rand;
                float xs = rand.nextFloat() * 0.5f - 0.25f;
                float ys = rand.nextFloat() * 0.5f - 0.15f;
                float zs = rand.nextFloat() * 0.5f - 0.25f;

                float r = this.dustColor.getRed() / 255f;
                float g = this.dustColor.getGreen() / 255f;
                float b = this.dustColor.getBlue() / 255f;
                float a = this.dustColor.getAlpha() / 255f;
                serverWorld.spawnParticle(new RedstoneParticleData(r, g, b, a), totem.getPosX(), totem.getPosY() + .5f, totem.getPosZ(), 10, xs, ys, zs, 1);
            }
        }
    }

    @Override
    public boolean hasRender(ItemStack stack, String identifier, LivingEntity entity) {
        return false;
    }

    @Override
    public void render(ItemStack stack, String identifier, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int light, LivingEntity entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageTicks, float headYaw, float headPitch) {
    }

    interface TotemSupplier {

        TotemEntity get(World world, PlayerEntity player, ItemStack stack, float durationInTicks);
    }
}
