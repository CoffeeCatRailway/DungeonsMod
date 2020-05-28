package io.github.tastac.dungeonsmod.client.renderer.entity.model;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.MathHelper;

public class RedstoneMonstrosityModel<T extends LivingEntity> extends BipedModel<T> {
    
	private ModelRenderer upperBody;

	private ModelRenderer hornLeft;
	private ModelRenderer hornRight;
	private ModelRenderer jawBottom;

	private ModelRenderer shoulderLeft;
	private ModelRenderer armLowerLeft;
	private ModelRenderer wristLeft;
    private ModelRenderer handLeft;
    private ModelRenderer finger1Left;
    private ModelRenderer finger2Left;
    private ModelRenderer finger3Left;

    private ModelRenderer shoulderRright;
    private ModelRenderer armLowerRight;
    private ModelRenderer wristRight;
	private ModelRenderer handRight;
	private ModelRenderer finger1Right;
	private ModelRenderer finger2Right;
	private ModelRenderer finger3Right;

	private void set(float scale) {
        bipedBody = new ModelRenderer(this);
        bipedBody.setTextureOffset(0, 2).addBox(-14.0f, -5.0f, -8.0f, 28, 11, 19, scale);
        bipedBody.setRotationPoint(0.0f, -6.0f, 0.0f);
        this.bipedHeadwear = new ModelRenderer(this);

        upperBody = new ModelRenderer(this);
        upperBody.setTextureOffset(121, 137).addBox(-36.5f, -56.75f, -15.0f, 74, 57, 31, scale);
        upperBody.setTextureOffset(113, 94).addBox(-14.5f, -47.75f, 16.0f, 28, 16, 11, scale);
        upperBody.setRotationPoint(-0.5f, -5.25f, 2.0f);
        bipedBody.addChild(upperBody);

        bipedHead = new ModelRenderer(this);
        bipedHead.setTextureOffset(73, 11).addBox(-14.0f, -18.0f, -21.0f, 28, 31, 21, scale);
        bipedHead.setRotationPoint(0.5f, -44.75f, -15.0f);

        hornLeft = new ModelRenderer(this);
        hornLeft.setTextureOffset(144, 32).addBox(0.5f, -12.5f, -6.5f, 11, 13, 13, scale);
        hornLeft.setTextureOffset(192, 16).addBox(11.5f, -28.5f, -6.5f, 9, 29, 13, scale);
        hornLeft.setRotationPoint(13.5f, -3.5f, -9.5f);
        bipedHead.addChild(hornLeft);

        hornRight = new ModelRenderer(this);
        hornRight.setTextureOffset(236, 32).addBox(0.5f, -12.5f, -6.5f, 11, 13, 13, scale);
        hornRight.setTextureOffset(284, 16).addBox(11.5f, -28.5f, -6.5f, 9, 29, 13, scale);
        hornRight.setRotationPoint(-13.5f, -3.5f, -9.5f);
        hornRight.rotateAngleY = 3.1416f;
        bipedHead.addChild(hornRight);

        jawBottom = new ModelRenderer(this);
        jawBottom.setTextureOffset(147, 63).addBox(-14.0f, -11.0f, -21.0f, 28, 10, 21, scale);
        jawBottom.setTextureOffset(98, 73).addBox(-14.0f, -2.1f, -21.0f, 28, 0, 21, scale);
        jawBottom.setRotationPoint(0.0f, 18.0f, 0.0f);
        bipedHead.addChild(jawBottom);

        bipedLeftArm = new ModelRenderer(this);
        bipedLeftArm.setRotationPoint(-36.4f, -53.25f, -1.5f);

        shoulderLeft = new ModelRenderer(this);
        shoulderLeft.setTextureOffset(0, 105).addBox(-36.625f, -5.5f, -13.5f, 37, 23, 27, scale);
        shoulderLeft.setTextureOffset(0, 156).addBox(-19.625f, -28.5f, -13.5f, 20, 23, 27, scale);
        shoulderLeft.setRotationPoint(-0.475f, 0.0f, 0.0f);
        bipedLeftArm.addChild(shoulderLeft);

        armLowerLeft = new ModelRenderer(this);
        armLowerLeft.setTextureOffset(118, 262).addBox(-11.0f, 0.25f, -8.25f, 22, 25, 17, scale);
        armLowerLeft.setRotationPoint(-19.1f, 17.25f, -0.25f);
        bipedLeftArm.addChild(armLowerLeft);

        wristLeft = new ModelRenderer(this);
        wristLeft.setTextureOffset(222, 324).addBox(-14.0f, 0.0f, -14.25f, 30, 20, 29, scale);
        wristLeft.setRotationPoint(-1.0f, 25.25f, 0.0f);
        armLowerLeft.addChild(wristLeft);

        handRight = new ModelRenderer(this);
        handRight.setRotationPoint(-54.0f, 36.0f, -0.25f);
        wristLeft.addChild(handRight);

        finger1Right = new ModelRenderer(this);
        finger1Right.setTextureOffset(3, 33).addBox(-1.5f, -2.0f, -2.5f, 3, 10, 5, scale);
        finger1Right.setRotationPoint(47.5f, -15.0f, 5.5f);
        handRight.addChild(finger1Right);

        finger2Right = new ModelRenderer(this);
        finger2Right.setTextureOffset(3, 33).addBox(-1.5f, -2.0f, -2.5f, 3, 10, 5, scale);
        finger2Right.setRotationPoint(47.5f, -15.0f, -5.5f);
        handRight.addChild(finger2Right);

        finger3Right = new ModelRenderer(this);
        finger3Right.setTextureOffset(3, 33).addBox(-1.5f, -2.0f, -2.5f, 3, 10, 5, scale);
        finger3Right.setRotationPoint(63.5f, -15.0f, 0.5f);
        handRight.addChild(finger3Right);

        bipedRightArm = new ModelRenderer(this);
        bipedRightArm.setRotationPoint(37.875f, -53.25f, -1.5f);

        shoulderRright = new ModelRenderer(this);
        shoulderRright.setTextureOffset(38, 378).addBox(-0.375f, -5.5f, -13.5f, 37, 23, 27, scale, true);
        shoulderRright.setTextureOffset(4, 292).addBox(-0.375f, -28.5f, -13.5f, 20, 23, 27, scale);
        shoulderRright.setRotationPoint(0.0f, 0.0f, 0.0f);
        bipedRightArm.addChild(shoulderRright);

        armLowerRight = new ModelRenderer(this);
        armLowerRight.setTextureOffset(118, 262).addBox(-10.375f, 0.5f, -8.5f, 22, 25, 17, scale);
        armLowerRight.setRotationPoint(17.0f, 17.0f, 0.0f);
        bipedRightArm.addChild(armLowerRight);

        wristRight = new ModelRenderer(this);
        wristRight.setTextureOffset(102, 324).addBox(-14.5f, 0.0f, -15.25f, 30, 20, 29, scale);
        wristRight.setRotationPoint(0.125f, 25.5f, 0.75f);
        armLowerRight.addChild(wristRight);

        handLeft = new ModelRenderer(this);
        handLeft.setRotationPoint(0.5f, 20.0f, -1.25f);
        wristRight.addChild(handLeft);

        finger1Left = new ModelRenderer(this);
        finger1Left.setTextureOffset(3, 33).addBox(-1.5f, -2.0f, -2.5f, 3, 10, 5, scale);
        finger1Left.setRotationPoint(8.5f, 1.0f, 5.5f);
        handLeft.addChild(finger1Left);

        finger2Left = new ModelRenderer(this);
        finger2Left.setTextureOffset(3, 33).addBox(-1.5f, -2.0f, -2.5f, 3, 10, 5, scale);
        finger2Left.setRotationPoint(8.5f, 1.0f, -5.5f);
        handLeft.addChild(finger2Left);

        finger3Left = new ModelRenderer(this);
        finger3Left.setTextureOffset(3, 33).addBox(-1.5f, -2.0f, -2.5f, 3, 10, 5, scale);
        finger3Left.setRotationPoint(-7.5f, 1.0f, 0.5f);
        handLeft.addChild(finger3Left);

        bipedRightLeg = new ModelRenderer(this);
        bipedRightLeg.setTextureOffset(0, 54).addBox(-13.0f, -9.0f, -8.0f, 24, 29, 19, scale);
        bipedRightLeg.setRotationPoint(-20.0f, 4.0f, 0.0f);

        bipedLeftLeg = new ModelRenderer(this);
        bipedLeftLeg.setTextureOffset(426, 2).addBox(-11.0f, -9.0f, -8.0f, 24, 29, 19, scale, true);
        bipedLeftLeg.setRotationPoint(20.0f, 4.0f, 0.0f);
    }

	public RedstoneMonstrosityModel(float scale) {
        super(0.0f, 0.0f, 512, 512);

		set(scale);
	}

    @Override
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        set(0.0f);

        boolean flag = entityIn.getTicksElytraFlying() > 4;
        boolean flag1 = entityIn.isActualySwimming();
        this.bipedHead.rotateAngleY = netHeadYaw * ((float) Math.PI / 180f);
        if (flag)
            this.bipedHead.rotateAngleX = (-(float) Math.PI / 4f);
        else if (this.swimAnimation > 0.0f) {
            if (flag1)
                this.bipedHead.rotateAngleX = this.rotLerpRad(this.bipedHead.rotateAngleX, (-(float) Math.PI / 4f), this.swimAnimation);
            else
                this.bipedHead.rotateAngleX = this.rotLerpRad(this.bipedHead.rotateAngleX, headPitch * ((float) Math.PI / 180f), this.swimAnimation);
        } else
            this.bipedHead.rotateAngleX = headPitch * ((float) Math.PI / 180f);

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
        this.armLowerRight.rotateAngleX = MathHelper.cos(limbSwing * 0.6662f + (float)Math.PI) * 1.5f * limbSwingAmount * 0.5f / f;
        this.armLowerRight.rotateAngleZ = 0.0f;
        this.handRight.rotateAngleX = MathHelper.cos(limbSwing * 0.6662f + (float)Math.PI) * 1.0f * limbSwingAmount * 0.5f / f;
        this.handRight.rotateAngleZ = 0.0f;

        this.bipedLeftArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662f) * 2.0f * limbSwingAmount * 0.5f / f;
        this.bipedLeftArm.rotateAngleZ = 0.0f;
        this.armLowerLeft.rotateAngleX = MathHelper.cos(limbSwing * 0.6662f) * 1.5f * limbSwingAmount * 0.5f / f;
        this.armLowerLeft.rotateAngleZ = 0.0f;
        this.handLeft.rotateAngleX = MathHelper.cos(limbSwing * 0.6662f + (float)Math.PI) * 1.0f * limbSwingAmount * 0.5f / f;
        this.handLeft.rotateAngleZ = 0.0f;

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