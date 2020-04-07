package io.github.tastac.dungeonsmod.common.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

/**
 * @author CoffeeCatRailway
 * Created: 7/04/2020
 */
public class TastyBoneItem extends Item {

    public TastyBoneItem(Properties properties) {
        super(properties);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (!world.isRemote && entity instanceof PlayerEntity) {
            ServerWorld serverWorld = (ServerWorld) world;
            PlayerEntity player = (PlayerEntity) entity;
            if (hasTag(stack) && !stack.getTag().contains("doggoUUID")) {
                if (!player.getCooldownTracker().hasCooldown(stack.getItem())) {
                    Entity wolf = serverWorld.getEntityByUuid(stack.getTag().getUniqueId("doggoUUID"));
                    if (wolf != null)
                        serverWorld.removeEntityComplete(wolf, false);
                }
            }
        }
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (hasTag(stack) && !stack.getTag().contains("doggoUUID")) {
            world.playSound(null, player.getPosX(), player.getPosY(), player.getPosZ(), SoundEvents.ENTITY_WOLF_GROWL, SoundCategory.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
            player.getCooldownTracker().setCooldown(this, 60 * 20);
            if (!world.isRemote) {
                WolfEntity wolf = new WolfEntity(EntityType.WOLF, world);
                wolf.setPosition(player.getPosX(), player.getPosY(), player.getPosZ());
                wolf.setTamed(true);
                wolf.setOwnerId(player.getGameProfile().getId());
                stack.getTag().putUniqueId("doggoUUID", wolf.getUniqueID());
                world.addEntity(wolf);
            }

            player.addStat(Stats.ITEM_USED.get(this));
            if (!player.abilities.isCreativeMode) {
                stack.shrink(1);
            }
        }

        return ActionResult.resultSuccess(stack);
    }

    private boolean hasTag(ItemStack stack) {
        stack.setTag(stack.hasTag() ? stack.getTag() : stack.serializeNBT());
        return stack.hasTag();
    }
}
