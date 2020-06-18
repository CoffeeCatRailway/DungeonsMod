package io.github.tastac.dungeonsmod.client.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import io.github.tastac.dungeonsmod.DungeonsMod;
import io.github.tastac.dungeonsmod.common.entity.RegenerationTotemEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

/**
 * @author CoffeeCatRailway
 * Created: 4/06/2020
 */
@OnlyIn(Dist.CLIENT)
public class RegenerationTotemRenderer extends TotemRenderer<RegenerationTotemEntity> {

    private static final ResourceLocation TEXTURE = DungeonsMod.getLocation("textures/item/totem_of_regeneration.png");

    public RegenerationTotemRenderer(EntityRendererManager renderManager) {
        super(renderManager);
    }

    @Override
    public void addPosUvs(List<PosUv> posUvs, float range) {
        float y = 0.01f;
        posUvs.add(new PosUv(range, y, range, 0f, 0f));
        posUvs.add(new PosUv(range, y, -range, 1f, 0f));
        posUvs.add(new PosUv(-range, y, -range, 1f, 1f));
        posUvs.add(new PosUv(-range, y, range, 0f, 1f));
    }

    @Override
    public int getBaseColor() {
        return DungeonsMod.CLIENT_CONFIG.regenerationTotemColor.get();
    }

    @Override
    public IVertexBuilder getVertexBuilder(RegenerationTotemEntity entity, IRenderTypeBuffer typeBuffer) {
        return super.getVertexBuilder(entity, typeBuffer);
    }

    @Override
    public ResourceLocation getEntityTexture(RegenerationTotemEntity entity) {
        return TEXTURE;
    }
}
