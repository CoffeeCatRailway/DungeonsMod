package io.github.tastac.dungeonsmod.common.item;

import com.mojang.blaze3d.matrix.MatrixStack;
import io.github.tastac.dungeonsmod.common.entity.TotemOfRegenerationEntity;
import io.github.tastac.dungeonsmod.integration.registrate.DungeonsLang;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

/**
 * @author CoffeeCatRailway
 * Created: 4/06/2020
 */
public class TotemOfRegenerationArtifact extends ArtifactItem {

    public static final String TAG_HEAL_AMOUNT = "HealAmount";

    public TotemOfRegenerationArtifact(Properties prop) {
        super(prop, 6.5f, 25f);
        this.hasManualSideCheck(true);
        this.showDuration(false);
        this.setRange(5f, false);
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        CompoundNBT stackNbt = stack.getOrCreateTag();
        if (!stackNbt.contains(TAG_HEAL_AMOUNT, Constants.NBT.TAG_ANY_NUMERIC))
            stackNbt.putFloat(TAG_HEAL_AMOUNT, .5f);
        return super.initCapabilities(stack, nbt);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<ITextComponent> info, ITooltipFlag flag) {
        super.addInformation(stack, world, info, flag);
        CompoundNBT nbt = stack.getOrCreateTag();
        info.add(new StringTextComponent(TextFormatting.GOLD + I18n.format(this.getTranslationKey() + ".heal_amount") + " " +
                TextFormatting.YELLOW + nbt.getFloat(TAG_HEAL_AMOUNT) + I18n.format(DungeonsLang.ARTIFACT_DESC_SECOND)));
    }

    @Override
    public void onArtifactActivate(float durationInTicks, float cooldownInTicks, ItemStack stack, String identifier, int index, PlayerEntity player) {
        World world = player.world;
        TotemOfRegenerationEntity totem = new TotemOfRegenerationEntity(world, player);
        totem.setTotem(stack.copy());
        totem.setDuration(durationInTicks);
        if (!world.isRemote) {
            ServerWorld serverWorld = (ServerWorld) world;
            if (serverWorld.addEntity(totem)) {
                Random rand = serverWorld.rand;
                float xs = rand.nextFloat() * 0.5f - 0.25f;
                float ys = rand.nextFloat() * 0.5f - 0.15f;
                float zs = rand.nextFloat() * 0.5f - 0.25f;
                serverWorld.spawnParticle(new RedstoneParticleData(1f, 0f, 0f, 1f), totem.getPosX(), totem.getPosY() + .5f, totem.getPosZ(), 10, xs, ys, zs, 1);
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
}
