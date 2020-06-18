package io.github.tastac.dungeonsmod.client.entity;

import io.github.tastac.dungeonsmod.DungeonsMod;
import io.github.tastac.dungeonsmod.common.entity.ShieldingTotemEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec2f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

/**
 * @author CoffeeCatRailway
 * Created: 4/06/2020
 */
@OnlyIn(Dist.CLIENT)
public class ShieldingTotemRenderer extends TotemRenderer<ShieldingTotemEntity> {

    private static final ResourceLocation SHIELD_TEXTURE = DungeonsMod.getLocation("textures/misc/shield.png");

    public ShieldingTotemRenderer(EntityRendererManager renderManager) {
        super(renderManager);
    }

    @Override
    public void addPosUvs(List<PosUv> posUvs, float range) {
        float oneDivThree = 1f / 3f;
        float oneDivThreeT2 = oneDivThree * 2f;
        float oneDivThreeT3 = oneDivThree * 3f;
        float angle = 360f / 6f;

        for (int i = 0; i < 2; i++) {
            float halfMul = i != 0 ? -1f : 1f;
            float halfAdd = i != 0 ? 1.3f : 0f;

            Vec2f topPos = new Vec2f(0f, range - 3.975f);
            Vec2f topPosNext = topPos;

            Vec2f topMidPos = new Vec2f(0f, range - 1.55f);
            Vec2f topMidPosNext = topMidPos;

            Vec2f bottomMidPos = new Vec2f(0f, range - .5f);
            Vec2f bottomMidPosNext = bottomMidPos;

            Vec2f bottomPos = new Vec2f(0f, range);
            Vec2f bottomPosNext = bottomPos;

            for (int j = 0; j < 6; j++) {
                topPosNext = this.rotatePoint(topPosNext, Vec2f.ZERO, angle);
                topMidPosNext = this.rotatePoint(topMidPosNext, Vec2f.ZERO, angle);
                bottomMidPosNext = this.rotatePoint(bottomMidPosNext, Vec2f.ZERO, angle);
                bottomPosNext = this.rotatePoint(bottomPosNext, Vec2f.ZERO, angle);

                posUvs.add(new PosUv(topPosNext.x, range * halfMul + halfAdd, topPosNext.y, 0f, 0f));
                posUvs.add(new PosUv(topPos.x, range * halfMul + halfAdd, topPos.y, oneDivThree, 0f));
                posUvs.add(new PosUv(topMidPos.x, (range - .75f) * halfMul + halfAdd, topMidPos.y, oneDivThree, oneDivThree));
                posUvs.add(new PosUv(topMidPosNext.x, (range - .75f) * halfMul + halfAdd, topMidPosNext.y, 0f, oneDivThree));

                posUvs.add(new PosUv(topMidPosNext.x, (range - .75f) * halfMul + halfAdd, topMidPosNext.y, oneDivThree, oneDivThree));
                posUvs.add(new PosUv(topMidPos.x, (range - .75f) * halfMul + halfAdd, topMidPos.y, oneDivThreeT2, oneDivThree));
                posUvs.add(new PosUv(bottomMidPos.x, (range - 1.85f) * halfMul + halfAdd, bottomMidPos.y, oneDivThreeT2, oneDivThreeT2));
                posUvs.add(new PosUv(bottomMidPosNext.x, (range - 1.85f) * halfMul + halfAdd, bottomMidPosNext.y, oneDivThree, oneDivThreeT2));

                posUvs.add(new PosUv(bottomMidPosNext.x, (range - 1.85f) * halfMul + halfAdd, bottomMidPosNext.y, oneDivThreeT2, oneDivThreeT2));
                posUvs.add(new PosUv(bottomMidPos.x, (range - 1.85f) * halfMul + halfAdd, bottomMidPos.y, oneDivThreeT3, oneDivThreeT2));
                posUvs.add(new PosUv(bottomPos.x, (range - 3.35f) * halfMul + halfAdd, bottomPos.y, oneDivThreeT3, oneDivThreeT3));
                posUvs.add(new PosUv(bottomPosNext.x, (range - 3.35f) * halfMul + halfAdd, bottomPosNext.y, oneDivThreeT2, oneDivThreeT3));

                topPos = this.rotatePoint(topPos, Vec2f.ZERO, angle);
                topMidPos = this.rotatePoint(topMidPos, Vec2f.ZERO, angle);
                bottomMidPos = this.rotatePoint(bottomMidPos, Vec2f.ZERO, angle);
                bottomPos = this.rotatePoint(bottomPos, Vec2f.ZERO, angle);
            }
        }
    }

    @Override
    public int getBaseColor() {
        return DungeonsMod.CLIENT_CONFIG.shieldTotemColor.get();
    }

    @Override
    public ResourceLocation getEntityTexture(ShieldingTotemEntity entity) {
        return SHIELD_TEXTURE;
    }
}
