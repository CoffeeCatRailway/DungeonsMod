package io.github.tastac.dungeonsmod.client.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import io.github.tastac.dungeonsmod.common.entity.TotemEntity;
import io.github.tastac.dungeonsmod.common.item.ArtifactItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TagsUpdatedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author CoffeeCatRailway
 * Created: 12/06/2020
 */
@OnlyIn(Dist.CLIENT)
public abstract class TotemRenderer<T extends TotemEntity> extends EntityRenderer<T> {

    private final Set<T> toRender = new HashSet<>();

    protected List<PosUv> posUvs = new ArrayList<>();

    public TotemRenderer(EntityRendererManager renderManager) {
        super(renderManager);
        MinecraftForge.EVENT_BUS.register(this);
    }

    public abstract void addPosUvs(List<PosUv> posUvs, float range);

    public abstract int getBaseColor();

    public IVertexBuilder getVertexBuilder(T entity, IRenderTypeBuffer typeBuffer) {
        return typeBuffer.getBuffer(RenderType.getEntityTranslucent(this.getEntityTexture(entity)));
    }

    protected List<PosUv> getPosUvs(float range) {
        if (this.posUvs.isEmpty())
            this.addPosUvs(this.posUvs, range);
        return this.posUvs;
    }

    @SubscribeEvent
    public void onReload(TagsUpdatedEvent event) {
        if (!this.posUvs.isEmpty())
            this.posUvs = new ArrayList<>();
    }

    @SubscribeEvent
    public void renderWorldLast(RenderWorldLastEvent event) {
        World world = Minecraft.getInstance().world;
        MatrixStack matrix = event.getMatrixStack();
        IRenderTypeBuffer.Impl typeBuffer = Minecraft.getInstance().getRenderTypeBuffers().getBufferSource();
        if (world == null)
            return;

        Vec3d view = Minecraft.getInstance().getRenderManager().info.getProjectedView();

        matrix.push();
        matrix.translate(-view.x, -view.y, -view.z);
        this.toRender.forEach(entity -> {
            ItemStack stack = entity.getTotem();
            int packedLight = WorldRenderer.getCombinedLight(world, entity.getPosition());
            if (!stack.isEmpty()) {
                IVertexBuilder buffer = this.getVertexBuilder(entity, typeBuffer);

                matrix.push();
                matrix.translate(entity.lastTickPosX + (entity.getPosX() - entity.lastTickPosX) * event.getPartialTicks(), entity.lastTickPosY + (entity.getPosY() - entity.lastTickPosY) * event.getPartialTicks(), entity.lastTickPosZ + (entity.getPosZ() - entity.lastTickPosZ) * event.getPartialTicks());

                this.render(entity, stack, event.getPartialTicks(), packedLight, typeBuffer, buffer, matrix);

                matrix.pop();
            }
        });
        matrix.pop();
        RenderSystem.disableCull();
        typeBuffer.finish();
        RenderSystem.enableCull();
        this.toRender.clear();
    }

    public void render(T entity, ItemStack stack, float partialTicks, int packedLight, IRenderTypeBuffer typeBuffer, IVertexBuilder buffer, MatrixStack matrix) {
        Matrix4f matrixLast = matrix.getLast().getMatrix();
        float range = stack.getOrCreateTag().getFloat(ArtifactItem.TAG_RANGE);

        List<PosUv> posUvs = this.getPosUvs(range);
        if (!posUvs.isEmpty()) {
            for (int i = 0; i < posUvs.size(); i += 4) {
                Color color = new Color(this.getBaseColor());
                int r = color.getRed();
                int g = color.getGreen();
                int b = color.getBlue();
                int a = color.getAlpha();

                buffer.pos(matrixLast, posUvs.get(i).x, posUvs.get(i).y, posUvs.get(i).z).color(r, g, b, a).tex(posUvs.get(i).u, posUvs.get(i).v)
                        .overlay(OverlayTexture.NO_OVERLAY).lightmap(packedLight).normal(0f, 1f, 0f).endVertex();
                buffer.pos(matrixLast, posUvs.get(i + 1).x, posUvs.get(i + 1).y, posUvs.get(i + 1).z).color(r, g, b, a).tex(posUvs.get(i + 1).u, posUvs.get(i + 1).v)
                        .overlay(OverlayTexture.NO_OVERLAY).lightmap(packedLight).normal(0f, 1f, 0f).endVertex();
                buffer.pos(matrixLast, posUvs.get(i + 2).x, posUvs.get(i + 2).y, posUvs.get(i + 2).z).color(r, g, b, a).tex(posUvs.get(i + 2).u, posUvs.get(i + 2).v)
                        .overlay(OverlayTexture.NO_OVERLAY).lightmap(packedLight).normal(0f, 1f, 0f).endVertex();
                buffer.pos(matrixLast, posUvs.get(i + 3).x, posUvs.get(i + 3).y, posUvs.get(i + 3).z).color(r, g, b, a).tex(posUvs.get(i + 3).u, posUvs.get(i + 3).v)
                        .overlay(OverlayTexture.NO_OVERLAY).lightmap(packedLight).normal(0f, 1f, 0f).endVertex();
            }
        }
    }

    @Override
    public void render(T entity, float entityYaw, float partialTicks, MatrixStack matrix, IRenderTypeBuffer typeBuffer, int packedLight) {
        super.render(entity, entityYaw, partialTicks, matrix, typeBuffer, packedLight);
        ItemStack stack = entity.getTotem();
        if (!stack.isEmpty()) {
            matrix.push();

            matrix.scale(.75f, .75f, .75f);
            matrix.rotate(Vector3f.YP.rotationDegrees(180f - entity.rotationYaw));

            boolean hasEnded = this.isEnding(entity);
            if (hasEnded)
                entity.deadAngle = MathHelper.lerp(0.05f, entity.deadAngle, -90f);
            matrix.rotate(Vector3f.XP.rotationDegrees(entity.deadAngle));

            if (!hasEnded)
                entity.yOffset = (float) Math.sin((entity.ticksExisted + partialTicks) / 4.5f) / 48f;
            else
                entity.yOffset = MathHelper.lerp(0.025f, entity.yOffset, -.25f);
            matrix.translate(0f, .75f + entity.yOffset, 0f);

            Minecraft.getInstance().getItemRenderer().renderItem(stack, ItemCameraTransforms.TransformType.FIXED, packedLight, OverlayTexture.NO_OVERLAY, matrix, typeBuffer);

            matrix.pop();
        }
        this.toRender.add(entity);
    }

    protected boolean isEnding(T entity) {
        return entity.ticksExisted >= entity.getDuration() - DungeonsMod.CLIENT_CONFIG.totemEndDuration.get().floatValue();
    }

    protected Vec2f rotatePoint(Vec2f point, Vec2f origin, float angle) {
        double radians = Math.toRadians(angle);
        float sin = (float) Math.sin(radians);
        float cos = (float) Math.cos(radians);

        Vec2f newPoint = new Vec2f(point.x - origin.x, point.y - origin.y);
        return new Vec2f(newPoint.x * cos + newPoint.y * sin + origin.x, -newPoint.x * sin + newPoint.y * cos + origin.y);
    }

    protected static class PosUv {

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
