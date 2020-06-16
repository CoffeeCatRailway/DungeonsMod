package io.github.tastac.dungeonsmod.client.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import io.github.tastac.dungeonsmod.DungeonsMod;
import io.github.tastac.dungeonsmod.common.entity.ShieldingTotemEntity;
import io.github.tastac.dungeonsmod.common.item.ArtifactItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Matrix4f;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.TagsUpdatedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author CoffeeCatRailway
 * Created: 4/06/2020
 */
@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = DungeonsMod.MOD_ID)
public class ShieldingTotemRenderer extends TotemRenderer<ShieldingTotemEntity> {

    private static final ResourceLocation SHIELD_TEXTURE = DungeonsMod.getLocation("textures/misc/shield.png");
    private static final ResourceLocation TEXTURE = DungeonsMod.getLocation("textures/item/totem_of_shielding.png");

    private static final Set<ShieldingTotemEntity> TO_RENDER = new HashSet<>();
    private static List<PosUv> POS_UVS;

    public ShieldingTotemRenderer(EntityRendererManager renderManager) {
        super(renderManager);
    }

    @Override
    public void render(ShieldingTotemEntity entity, float entityYaw, float partialTicks, MatrixStack matrix, IRenderTypeBuffer typeBuffer, int packedLight) {
        super.render(entity, entityYaw, partialTicks, matrix, typeBuffer, packedLight);
        TO_RENDER.add(entity);
    }

    @SubscribeEvent
    public static void onReload(TagsUpdatedEvent event) {
        if (POS_UVS != null && !POS_UVS.isEmpty())
            POS_UVS = new ArrayList<>();
    }

    @SubscribeEvent
    public static void renderWorldLast(RenderWorldLastEvent event) {
        World world = Minecraft.getInstance().world;
        MatrixStack matrix = event.getMatrixStack();
        IRenderTypeBuffer.Impl typeBuffer = Minecraft.getInstance().getRenderTypeBuffers().getBufferSource();
        if (world == null)
            return;

        IVertexBuilder buffer = typeBuffer.getBuffer(RenderType.getEntityTranslucent(SHIELD_TEXTURE));
        Vec3d view = Minecraft.getInstance().getRenderManager().info.getProjectedView();

        int r = DungeonsMod.CLIENT_CONFIG.shieldTotemRed.get();
        int g = DungeonsMod.CLIENT_CONFIG.shieldTotemGreen.get();
        int b = DungeonsMod.CLIENT_CONFIG.shieldTotemBlue.get();
        int a = DungeonsMod.CLIENT_CONFIG.shieldTotemAlpha.get();

        matrix.push();
        matrix.translate(-view.x, -view.y, -view.z);
        TO_RENDER.forEach(entity -> {
            ItemStack stack = entity.getTotem();
            int packedLight = WorldRenderer.getCombinedLight(world, entity.getPosition());
            if (!stack.isEmpty()) {
                matrix.push();
                matrix.translate(entity.lastTickPosX + (entity.getPosX() - entity.lastTickPosX) * event.getPartialTicks(), entity.lastTickPosY + (entity.getPosY() - entity.lastTickPosY) * event.getPartialTicks(), entity.lastTickPosZ + (entity.getPosZ() - entity.lastTickPosZ) * event.getPartialTicks());

                Matrix4f matrixLast = matrix.getLast().getMatrix();
                float range = stack.getOrCreateTag().getFloat(ArtifactItem.TAG_RANGE);

                List<PosUv> posUvs = getPosUvs(range);
                for (int i = 0; i < posUvs.size(); i += 4) {
                    buffer.pos(matrixLast, posUvs.get(i).x, posUvs.get(i).y, posUvs.get(i).z).color(r, g, b, a).tex(posUvs.get(i).u, posUvs.get(i).v)
                            .overlay(OverlayTexture.NO_OVERLAY).lightmap(packedLight).normal(0f, 1f, 0f).endVertex();
                    buffer.pos(matrixLast, posUvs.get(i + 1).x, posUvs.get(i + 1).y, posUvs.get(i + 1).z).color(r, g, b, a).tex(posUvs.get(i + 1).u, posUvs.get(i + 1).v)
                            .overlay(OverlayTexture.NO_OVERLAY).lightmap(packedLight).normal(0f, 1f, 0f).endVertex();
                    buffer.pos(matrixLast, posUvs.get(i + 2).x, posUvs.get(i + 2).y, posUvs.get(i + 2).z).color(r, g, b, a).tex(posUvs.get(i + 2).u, posUvs.get(i + 2).v)
                            .overlay(OverlayTexture.NO_OVERLAY).lightmap(packedLight).normal(0f, 1f, 0f).endVertex();
                    buffer.pos(matrixLast, posUvs.get(i + 3).x, posUvs.get(i + 3).y, posUvs.get(i + 3).z).color(r, g, b, a).tex(posUvs.get(i + 3).u, posUvs.get(i + 3).v)
                            .overlay(OverlayTexture.NO_OVERLAY).lightmap(packedLight).normal(0f, 1f, 0f).endVertex();
                }

                matrix.pop();
            }
        });
        matrix.pop();
        RenderSystem.disableCull();
        typeBuffer.finish();
        RenderSystem.enableCull();
        TO_RENDER.clear();
    }

    private static List<PosUv> getPosUvs(float range) {
        if (POS_UVS == null || POS_UVS.isEmpty()) {
            POS_UVS = new ArrayList<>();
            float oneDivThree = 1f / 3f;
            float oneDivThreeT2 = oneDivThree * 2f;
            float oneDivThreeT3 = oneDivThree * 3f;

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
                    topPosNext = rotatePoint(topPosNext);
                    topMidPosNext = rotatePoint(topMidPosNext);
                    bottomMidPosNext = rotatePoint(bottomMidPosNext);
                    bottomPosNext = rotatePoint(bottomPosNext);

                    POS_UVS.add(new PosUv(topPosNext.x, range * halfMul + halfAdd, topPosNext.y, 0f, 0f));
                    POS_UVS.add(new PosUv(topPos.x, range * halfMul + halfAdd, topPos.y, oneDivThree, 0f));
                    POS_UVS.add(new PosUv(topMidPos.x, (range - .75f) * halfMul + halfAdd, topMidPos.y, oneDivThree, oneDivThree));
                    POS_UVS.add(new PosUv(topMidPosNext.x, (range - .75f) * halfMul + halfAdd, topMidPosNext.y, 0f, oneDivThree));

                    POS_UVS.add(new PosUv(topMidPosNext.x, (range - .75f) * halfMul + halfAdd, topMidPosNext.y, oneDivThree, oneDivThree));
                    POS_UVS.add(new PosUv(topMidPos.x, (range - .75f) * halfMul + halfAdd, topMidPos.y, oneDivThreeT2, oneDivThree));
                    POS_UVS.add(new PosUv(bottomMidPos.x, (range - 1.85f) * halfMul + halfAdd, bottomMidPos.y, oneDivThreeT2, oneDivThreeT2));
                    POS_UVS.add(new PosUv(bottomMidPosNext.x, (range - 1.85f) * halfMul + halfAdd, bottomMidPosNext.y, oneDivThree, oneDivThreeT2));

                    POS_UVS.add(new PosUv(bottomMidPosNext.x, (range - 1.85f) * halfMul + halfAdd, bottomMidPosNext.y, oneDivThreeT2, oneDivThreeT2));
                    POS_UVS.add(new PosUv(bottomMidPos.x, (range - 1.85f) * halfMul + halfAdd, bottomMidPos.y, oneDivThreeT3, oneDivThreeT2));
                    POS_UVS.add(new PosUv(bottomPos.x, (range - 3.35f) * halfMul + halfAdd, bottomPos.y, oneDivThreeT3, oneDivThreeT3));
                    POS_UVS.add(new PosUv(bottomPosNext.x, (range - 3.35f) * halfMul + halfAdd, bottomPosNext.y, oneDivThreeT2, oneDivThreeT3));

                    topPos = rotatePoint(topPos);
                    topMidPos = rotatePoint(topMidPos);
                    bottomMidPos = rotatePoint(bottomMidPos);
                    bottomPos = rotatePoint(bottomPos);
                }
            }
        }
        return POS_UVS;
    }

    public static Vec2f rotatePoint(Vec2f point) {
        double hexAngle = Math.toRadians(360f / 6f);

        float sin = (float) Math.sin(hexAngle);
        float cos = (float) Math.cos(hexAngle);
        return new Vec2f(point.x * cos + point.y * sin, -point.x * sin + point.y * cos);
    }

    @Override
    public ResourceLocation getEntityTexture(ShieldingTotemEntity entity) {
        return TEXTURE;
    }

    static class PosUv {

        float x;
        float y;
        float z;

        float u;
        float v;

        public PosUv(float x, float y, float z, float u, float v) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.u = u;
            this.v = v;
        }
    }
}
