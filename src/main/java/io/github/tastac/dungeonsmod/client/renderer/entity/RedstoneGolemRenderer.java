package io.github.tastac.dungeonsmod.client.renderer.entity;

import io.github.tastac.dungeonsmod.DungeonsMod;
import io.github.tastac.dungeonsmod.client.renderer.entity.model.RedstoneGolemModel;
import io.github.tastac.dungeonsmod.common.entity.RedstoneGolemEntity;
import net.minecraft.client.renderer.entity.BipedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

/**
 * @author CoffeeCatRailway
 * Created: 8/04/2020
 */
public class RedstoneGolemRenderer<T extends RedstoneGolemEntity> extends BipedRenderer<T, RedstoneGolemModel<T>> {

    public RedstoneGolemRenderer(EntityRendererManager renderManager) {
        super(renderManager, new RedstoneGolemModel<T>(0.0f), 2.0f);
    }

    @Override
    public ResourceLocation getEntityTexture(T entity) {
        return new ResourceLocation(DungeonsMod.MOD_ID, "textures/entity/redstone_golem.png");
    }
}
