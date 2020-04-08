package io.github.tastac.dungeonsmod.client.renderer.entity.model;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RedstoneGolemModel<T extends LivingEntity> extends BipedModel<T> {

    private ModelRenderer upperBody;

    private ModelRenderer lowerArmRight;
    private ModelRenderer thumbRight;
    private ModelRenderer finger1Right;
    private ModelRenderer finger2Right;

    private ModelRenderer lowerArmLeft;
    private ModelRenderer thumbLeft;
    private ModelRenderer finger1Left;
    private ModelRenderer finger2Left;

    public RedstoneGolemModel(float scale) {
        super(0.0f, 10.0f, 256, 256);

        this.bipedBody = new ModelRenderer(this);
        this.bipedBody.setTextureOffset(120, 36).addBox(-11.0f, 0.0f, -7.0f, 22, 8, 14, scale);
        this.bipedBody.setRotationPoint(0.0f, -4.0f, 0.0f);

        this.upperBody = new ModelRenderer(this, 0, 0);
        this.upperBody.setRotationPoint(0.0f, 0.0f, 0.0f);
        this.bipedBody.addChild(this.upperBody);
        this.upperBody.addBox(-20.0f, -32.0f, -10.0f, 40, 32, 20, scale);
        this.upperBody.setTextureOffset(49, 90);
        this.upperBody.addBox(-8.0f, -20.0f, -2.0f, 16, 16, 16, scale);

        this.bipedHead = new ModelRenderer(this);
        this.bipedHead.setTextureOffset(124, 8).addBox(-8.0f, -8.0f, -12.0f, 16, 16, 12, scale);
        this.bipedHead.setRotationPoint(0.0f, -27.0f, -10.0f);
        this.bipedHeadwear = new ModelRenderer(this);

        this.bipedRightLeg = new ModelRenderer(this);
        this.bipedRightLeg.setTextureOffset(113, 58).addBox(-9.0f, -4.0f, -6.0f, 12, 20, 12, scale);
        this.bipedRightLeg.setRotationPoint(-11.0f, 8.0f, 0.0f);

        this.bipedLeftLeg = new ModelRenderer(this);
        this.bipedLeftLeg.setTextureOffset(161, 58).addBox(-3.0f, -4.0f, -6.0f, 12, 20, 12, scale);
        this.bipedLeftLeg.setRotationPoint(11.0f, 8.0f, 0.0f);

        this.bipedRightArm = new ModelRenderer(this, 0, 52);
        this.bipedRightArm.setRotationPoint(-20.0f, -30.0f, 0.0f);
        this.bipedRightArm.addBox(-14.0f, -4.0f, -6.0f, 14, 24, 12, scale);

        this.lowerArmRight = new ModelRenderer(this, 3, 88);
        this.lowerArmRight.setRotationPoint(-9.0f, 20.0f, 0.0f);
        this.bipedRightArm.addChild(this.lowerArmRight);
        this.lowerArmRight.addBox(-6.0f, 0.0f, -6.0f, 11, 22, 12, scale);

        this.thumbRight = new ModelRenderer(this, 81, 57);
        this.thumbRight.setRotationPoint(2.5f, 22.0f, -3.0f);
        this.lowerArmRight.addChild(this.thumbRight);
        this.thumbRight.addBox(-1.5f, 0.0f, -2.0f, 3, 8, 5, scale, true);

        this.finger1Right = new ModelRenderer(this, 97, 55);
        this.finger1Right.setRotationPoint(-2.5f, 22.0f, -3.0f);
        this.lowerArmRight.addChild(this.finger1Right);
        this.finger1Right.addBox(-1.5f, 0.0f, -2.5f, 3, 10, 5, scale, true);

        this.finger2Right = new ModelRenderer(this, 97, 55);
        this.finger2Right.setRotationPoint(-2.5f, 22.0f, 3.0f);
        this.lowerArmRight.addChild(this.finger2Right);
        this.finger2Right.addBox(-1.5f, 0.0f, -2.5f, 3, 10, 5, scale, true);

        this.bipedLeftArm = new ModelRenderer(this, 3, 122);
        this.bipedLeftArm.setRotationPoint(20.0f, -30.0f, 0.0f);
        this.bipedLeftArm.addBox(0.0f, -4.0f, -6.0f, 14, 24, 12, scale);

        this.lowerArmLeft = new ModelRenderer(this, 55, 124);
        this.lowerArmLeft.setRotationPoint(9.0f, 20.0f, 0.0f);
        this.bipedLeftArm.addChild(this.lowerArmLeft);
        this.lowerArmLeft.addBox(-5.0f, 0.0f, -6.0f, 11, 22, 12, scale, true);

        this.thumbLeft = new ModelRenderer(this, 81, 57);
        this.thumbLeft.setRotationPoint(-2.5f, 22.0f, -3.0f);
        this.lowerArmLeft.addChild(this.thumbLeft);
        this.thumbLeft.addBox(-1.5f, 0.0f, -2.0f, 3, 8, 5, scale);

        this.finger1Left = new ModelRenderer(this, 97, 55);
        this.finger1Left.setRotationPoint(2.5f, 22.0f, -3.0f);
        this.lowerArmLeft.addChild(this.finger1Left);
        this.finger1Left.addBox(-1.5f, 0.0f, -2.5f, 3, 10, 5, scale);

        this.finger2Left = new ModelRenderer(this, 97, 55);
        this.finger2Left.setRotationPoint(2.5f, 22.0f, 3.0f);
        this.lowerArmLeft.addChild(this.finger2Left);
        this.finger2Left.addBox(-1.5f, 0.0f, -2.5f, 3, 10, 5, scale);
    }

    @Override
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        boolean flag = entityIn.getTicksElytraFlying() > 4;
        boolean flag1 = entityIn.isActualySwimming();
        this.bipedHead.rotateAngleY = netHeadYaw * ((float)Math.PI / 180f);
        if (flag)
            this.bipedHead.rotateAngleX = (-(float)Math.PI / 4f);
        else if (this.swimAnimation > 0.0f) {
            if (flag1)
                this.bipedHead.rotateAngleX = this.rotLerpRad(this.bipedHead.rotateAngleX, (-(float)Math.PI / 4f), this.swimAnimation);
            else
                this.bipedHead.rotateAngleX = this.rotLerpRad(this.bipedHead.rotateAngleX, headPitch * ((float)Math.PI / 180f), this.swimAnimation);
        } else
            this.bipedHead.rotateAngleX = headPitch * ((float)Math.PI / 180f);

        float f = 1.0f;
        if (flag) {
            f = (float)entityIn.getMotion().lengthSquared();
            f = f / 0.2f;
            f = f * f * f;
        }

        if (f < 1.0f)
            f = 1.0f;

        this.bipedRightArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662f + (float)Math.PI) * 2.0f * limbSwingAmount * 0.5f / f;
        this.bipedRightArm.rotateAngleZ = 0.0f;
        this.lowerArmRight.rotateAngleX = MathHelper.cos(limbSwing * 0.6662f + (float)Math.PI) * 2.5f * limbSwingAmount * 0.5f / f;
        this.lowerArmRight.rotateAngleZ = 0.0f;

        this.bipedLeftArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662f) * 2.0f * limbSwingAmount * 0.5f / f;
        this.bipedLeftArm.rotateAngleZ = 0.0f;
        this.bipedLeftArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662f) * 2.5f * limbSwingAmount * 0.5f / f;
        this.bipedLeftArm.rotateAngleZ = 0.0f;

        this.bipedRightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662f) * 1.4f * limbSwingAmount / f;
        this.bipedRightLeg.rotateAngleY = 0.0f;
        this.bipedRightLeg.rotateAngleZ = 0.0f;

        this.bipedLeftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662f + (float)Math.PI) * 1.4f * limbSwingAmount / f;
        this.bipedLeftLeg.rotateAngleY = 0.0f;
        this.bipedLeftLeg.rotateAngleZ = 0.0f;

        if (this.isSitting) {
            this.bipedRightArm.rotateAngleX += (-(float)Math.PI / 5f);
            this.bipedLeftArm.rotateAngleX += (-(float)Math.PI / 5f);
            this.bipedRightLeg.rotateAngleX = -1.4137167f;
            this.bipedRightLeg.rotateAngleY = ((float)Math.PI / 10f);
            this.bipedRightLeg.rotateAngleZ = 0.07853982f;
            this.bipedLeftLeg.rotateAngleX = -1.4137167f;
            this.bipedLeftLeg.rotateAngleY = (-(float)Math.PI / 10f);
            this.bipedLeftLeg.rotateAngleZ = -0.07853982f;
        }

        this.bipedRightArm.rotateAngleY = 0.0f;
        this.bipedRightArm.rotateAngleZ = 0.0f;
        switch(this.leftArmPose) {
            case EMPTY:
                this.bipedLeftArm.rotateAngleY = 0.0f;
                break;
            case BLOCK:
                this.bipedLeftArm.rotateAngleX = this.bipedLeftArm.rotateAngleX * 0.5f - 0.9424779f;
                this.bipedLeftArm.rotateAngleY = ((float)Math.PI / 6f);
                break;
            case ITEM:
                this.bipedLeftArm.rotateAngleX = this.bipedLeftArm.rotateAngleX * 0.5f - ((float)Math.PI / 10f);
                this.bipedLeftArm.rotateAngleY = 0.0f;
        }

        switch(this.rightArmPose) {
            case EMPTY:
                this.bipedRightArm.rotateAngleY = 0.0f;
                break;
            case BLOCK:
                this.bipedRightArm.rotateAngleX = this.bipedRightArm.rotateAngleX * 0.5f - 0.9424779f;
                this.bipedRightArm.rotateAngleY = (-(float)Math.PI / 6f);
                break;
            case ITEM:
                this.bipedRightArm.rotateAngleX = this.bipedRightArm.rotateAngleX * 0.5f - ((float)Math.PI / 10f);
                this.bipedRightArm.rotateAngleY = 0.0f;
                break;
            case THROW_SPEAR:
                this.bipedRightArm.rotateAngleX = this.bipedRightArm.rotateAngleX * 0.5f - (float)Math.PI;
                this.bipedRightArm.rotateAngleY = 0.0f;
        }

        if (this.leftArmPose == BipedModel.ArmPose.THROW_SPEAR && this.rightArmPose != BipedModel.ArmPose.BLOCK && this.rightArmPose != BipedModel.ArmPose.THROW_SPEAR && this.rightArmPose != BipedModel.ArmPose.BOW_AND_ARROW) {
            this.bipedLeftArm.rotateAngleX = this.bipedLeftArm.rotateAngleX * 0.5f - (float)Math.PI;
            this.bipedLeftArm.rotateAngleY = 0.0f;
        }

        if (this.swingProgress > 0.0f) {
            HandSide handside = this.getMainHand(entityIn);
            float f1 = this.swingProgress;
            this.bipedBody.rotateAngleY = MathHelper.sin(MathHelper.sqrt(f1) * ((float) Math.PI * 2f)) * 0.2f;
            if (handside == HandSide.LEFT)
                this.bipedBody.rotateAngleY *= -1.0f;
        }
    }
}