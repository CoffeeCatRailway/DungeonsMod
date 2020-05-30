package io.github.tastac.dungeonsmod.common.item;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

/**
 * @author CoffeeCatRailway
 * Created: 28/05/2020
 */
public class DeathCapMushroomArtifact extends ArtifactItem {

    public DeathCapMushroomArtifact(Properties prop) {
        super(prop);
    }

    @Override
    public void curioTick(ItemStack stack, String identifier, int index, PlayerEntity player) {
        super.curioTick(stack, identifier, index, player);
    }

    @Override
    public boolean hasRender(String identifier, LivingEntity entity) {
        return false;
    }

    @Override
    public void render(String identifier, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int light, LivingEntity entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageTicks, float headYaw, float headPitch) {
    }
}
