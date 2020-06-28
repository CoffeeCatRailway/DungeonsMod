package io.github.tastac.dungeonsmod.common.item;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

import java.util.List;

/**
 * @author CoffeeCatRailway
 * Created: 23/06/2020
 */
public class SoulHealerArtifact extends SoulArtifactItem {

    public SoulHealerArtifact(Properties prop) {
        super(prop, 0f, 1f, 1);
        this.setRange(8f, false);
    }

    @Override
    public void onSoulActivate(float durationInTicks, float cooldownInTicks, ItemStack stack, String identifier, int index, PlayerEntity player, int playerSouls, int usedSouls) {
        if (player.shouldHeal() || player.isCreative()) {
            List<PlayerEntity> players = player.world.getEntitiesWithinAABB(PlayerEntity.class, player.getBoundingBox().grow(stack.getOrCreateTag().getFloat(TAG_RANGE)));
            PlayerEntity selectedPlayer = player;

            if (players.size() > 1) {
                for (PlayerEntity nearPlayer : players) {
                    if (nearPlayer.getHealth() < selectedPlayer.getHealth())
                        selectedPlayer = nearPlayer;
                }
            }

            if (selectedPlayer.shouldHeal()) {
                selectedPlayer.heal(usedSouls);
                super.removeSouls(player, usedSouls);
            }
        }
    }
}
