package io.github.tastac.dungeonsmod.client.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import io.github.tastac.dungeonsmod.DungeonsMod;
import io.github.tastac.dungeonsmod.client.utils.PosUv;
import io.github.tastac.dungeonsmod.common.entity.TotemEntity;
import io.github.tastac.dungeonsmod.common.item.ArtifactItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.culling.ClippingHelperImpl;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
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
    private boolean doesExpandUp;

    public TotemRenderer(EntityRendererManager renderManager, boolean doesExpandUp) {
        super(renderManager);
        this.doesExpandUp = doesExpandUp;
        MinecraftForge.EVENT_BUS.register(this);
    }

    public abstract void addPosUvs(List<PosUv> posUvs, float range);

    public abstract int getBaseColor(T entity);

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

        IVertexBuilder buffer = null;
        Vec3d view = Minecraft.getInstance().getRenderManager().info.getProjectedView();

        matrix.push();
        matrix.translate(-view.x, -view.y, -view.z);
        for (T entity : this.toRender) {
            ItemStack stack = entity.getTotem();
            int packedLight = WorldRenderer.getCombinedLight(world, entity.getPosition());
            if (!stack.isEmpty()) {
                if (buffer == null)
                    buffer = this.getVertexBuilder(entity, typeBuffer);

                matrix.push();
                matrix.translate(entity.lastTickPosX + (entity.getPosX() - entity.lastTickPosX) * event.getPartialTicks(), entity.lastTickPosY + (entity.getPosY() - entity.lastTickPosY) * event.getPartialTicks(), entity.lastTickPosZ + (entity.getPosZ() - entity.lastTickPosZ) * event.getPartialTicks());

                this.render(entity, stack, event.getPartialTicks(), packedLight, typeBuffer, buffer, matrix);

                matrix.pop();
            }
        }
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
                Color color = new Color(this.getBaseColor(entity));
                int r = color.getRed();
                int g = color.getGreen();
                int b = color.getBlue();
                int a = color.getAlpha();

                buffer.pos(matrixLast, posUvs.get(i).pos.getX(), posUvs.get(i).pos.getY(), posUvs.get(i).pos.getZ()).color(r, g, b, a).tex(posUvs.get(i).u, posUvs.get(i).v)
                        .overlay(OverlayTexture.NO_OVERLAY).lightmap(packedLight).normal(0f, 1f, 0f).endVertex();
                buffer.pos(matrixLast, posUvs.get(i + 1).pos.getX(), posUvs.get(i + 1).pos.getY(), posUvs.get(i + 1).pos.getZ()).color(r, g, b, a).tex(posUvs.get(i + 1).u, posUvs.get(i + 1).v)
                        .overlay(OverlayTexture.NO_OVERLAY).lightmap(packedLight).normal(0f, 1f, 0f).endVertex();
                buffer.pos(matrixLast, posUvs.get(i + 2).pos.getX(), posUvs.get(i + 2).pos.getY(), posUvs.get(i + 2).pos.getZ()).color(r, g, b, a).tex(posUvs.get(i + 2).u, posUvs.get(i + 2).v)
                        .overlay(OverlayTexture.NO_OVERLAY).lightmap(packedLight).normal(0f, 1f, 0f).endVertex();
                buffer.pos(matrixLast, posUvs.get(i + 3).pos.getX(), posUvs.get(i + 3).pos.getY(), posUvs.get(i + 3).pos.getZ()).color(r, g, b, a).tex(posUvs.get(i + 3).u, posUvs.get(i + 3).v)
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

    @Override
    public boolean shouldRender(T entity, ClippingHelperImpl camera, double camX, double camY, double camZ) {
        if (super.shouldRender(entity, camera, camX, camY, camZ))
            return true;
        AxisAlignedBB box = entity.getRenderBoundingBox();
        float range = entity.getTotem().getOrCreateTag().getFloat(ArtifactItem.TAG_RANGE);
        return camera.isBoundingBoxInFrustum(box.grow(range, this.doesExpandUp ? range : 0f, range));
    }

    protected boolean isEnding(T entity) {
        return entity.ticksExisted >= entity.getDuration() - DungeonsMod.CLIENT_CONFIG.totemEndDuration.get().floatValue();
    }

    public static Vec2f rotatePoint(Vec2f point, Vec2f origin, float angle) {
        double radians = Math.toRadians(angle);
        float sin = (float) Math.sin(radians);
        float cos = (float) Math.cos(radians);

        Vec2f newPoint = new Vec2f(point.x - origin.x, point.y - origin.y);
        return new Vec2f(newPoint.x * cos + newPoint.y * sin + origin.x, -newPoint.x * sin + newPoint.y * cos + origin.y);
    }
}
