package io.github.tastac.dungeonsmod.common.item;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

/**
 * @author CoffeeCatRailway
 * Created: 28/05/2020
 */
public class DeathCapMushroomArtifact extends ArtifactItem {

    private final float duration = 10.4f;
    private final float cooldown = 30f;

    public DeathCapMushroomArtifact(Properties prop) {
        super(prop);
    }

    @Override
    public void curioTick(ItemStack stack, String identifier, int index, PlayerEntity player) {
        if (this.isActive(stack) && !player.getCooldownTracker().hasCooldown(this)) {
            player.addPotionEffect(new EffectInstance(Effects.STRENGTH, (int) (duration * 20f), 0, false, false, true));
            player.addPotionEffect(new EffectInstance(Effects.SPEED, (int) (duration * 20f), 0, false, false, true));
            player.getCooldownTracker().setCooldown(this, (int) ((cooldown + duration) * 20f));
        }
        super.curioTick(stack, identifier, index, player);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<ITextComponent> info, ITooltipFlag flag) {
        info.add(new StringTextComponent("Greatly increases attack ad movement speed"));
        info.add(new StringTextComponent(TextFormatting.GOLD + "Duration: " + TextFormatting.YELLOW + duration + "sec"));
        info.add(new StringTextComponent(TextFormatting.GOLD + "Cooldown: " + TextFormatting.YELLOW + cooldown + "sec"));
    }

    @Override
    public boolean hasRender(String identifier, LivingEntity entity) {
        return false;
    }

    @Override
    public void render(String identifier, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int light, LivingEntity entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageTicks, float headYaw, float headPitch) {
    }
}
