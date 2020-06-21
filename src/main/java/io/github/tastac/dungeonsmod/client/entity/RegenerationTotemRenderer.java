package io.github.tastac.dungeonsmod.client.entity;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import io.github.tastac.dungeonsmod.DungeonsMod;
import io.github.tastac.dungeonsmod.common.entity.RegenerationTotemEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
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
public class RegenerationTotemRenderer extends TotemRenderer<RegenerationTotemEntity> {

    private static final ResourceLocation TEXTURE = DungeonsMod.getLocation("textures/item/totem_of_regeneration.png");
    private static final ResourceLocation RUNES_TEXTURE = DungeonsMod.getLocation("textures/misc/runes.png");

    public RegenerationTotemRenderer(EntityRendererManager renderManager) {
        super(renderManager);
    }

    @Override
    public void addPosUvs(List<PosUv> posUvs, float range) {
        float y = 0.01f;

        TextureUVs rune1 = new TextureUVs(0f, 0f, .125f, 0f, .125f, .125f, 0f, .125f);
        TextureUVs rune2 = new TextureUVs(.875f, 0f, 1f, 0f, 1f, .125f, .875f, .125f);
        TextureUVs rune3 = new TextureUVs(0f, .125f, .125f, .125f, .125f, .25f, 0f, .25f);
        TextureUVs rune4 = new TextureUVs(.375f, .125f, .5f, .125f, .5f, .25f, .375f, .25f);
        TextureUVs rune5 = new TextureUVs(.625f, .125f, .75f, .125f, .75f, .25f, .625f, .25f);
        TextureUVs rune6 = new TextureUVs(.375f, .25f, .5f, .25f, .5f, .375f, .375f, .375f);
        TextureUVs rune7 = new TextureUVs(.625f, .25f, .75f, .25f, .75f, .375f, .625f, .375f);
        TextureUVs rune8 = new TextureUVs(.125f, .625f, .25f, .625f, .25f, .75f, .125f, .75f);
        TextureUVs rune9 = new TextureUVs(.25f, .625f, .375f, .625f, .375f, .75f, .25f, .75f);
        TextureUVs[] runes = new TextureUVs[]{
                rune6, rune2, rune9, rune4, rune9, rune6, rune2, rune9, rune1, rune4, rune7, rune3, rune5, rune9, rune8
        };

        float length = runes.length;
        float angle = 360f / length;
        float offset = 8f / 16f;

        Vec2f runePos = new Vec2f(0f, range);

        for (int i = 0; i < length; i++) {
            float newAngle = angle * i + 180f;
            Vec2f topRight = this.rotatePoint(new Vec2f(runePos.x + offset, runePos.y), runePos, newAngle);
            Vec2f bottomRight = this.rotatePoint(new Vec2f(runePos.x + offset, runePos.y + offset), runePos, newAngle);
            Vec2f bottomLeft = this.rotatePoint(new Vec2f(runePos.x, runePos.y + offset), runePos, newAngle);

            posUvs.add(new PosUv(runePos.x, y, runePos.y, runes[i].topLeft));
            posUvs.add(new PosUv(topRight.x, y, topRight.y, runes[i].topRight));
            posUvs.add(new PosUv(bottomRight.x, y, bottomRight.y, runes[i].bottomRight));
            posUvs.add(new PosUv(bottomLeft.x, y, bottomLeft.y, runes[i].bottomLeft));

            runePos = this.rotatePoint(runePos, Vec2f.ZERO, angle);
        }
    }

    @Override
    public int getBaseColor(RegenerationTotemEntity entity) {
        return this.isEnding(entity) ? DungeonsMod.CLIENT_CONFIG.regenerationTotemFadedColor.get() : DungeonsMod.CLIENT_CONFIG.regenerationTotemColor.get();
    }

    @Override
    public IVertexBuilder getVertexBuilder(RegenerationTotemEntity entity, IRenderTypeBuffer typeBuffer) {
        return ItemRenderer.getBuffer(typeBuffer, RenderType.getEntityTranslucent(this.getEntityTexture(entity)), false, true);
    }

    @Override
    public ResourceLocation getEntityTexture(RegenerationTotemEntity entity) {
        return RUNES_TEXTURE;
    }
}
