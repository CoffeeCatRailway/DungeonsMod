package io.github.tastac.dungeonsmod.client.renderer.entity.model;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RedstoneGolemModel<T extends LivingEntity> extends BipedModel<T> {

    private final ModelRenderer upperBody;

    private final ModelRenderer lowerArmRight;
    private final ModelRenderer thumbRight;
    private final ModelRenderer finger1Right;
    private final ModelRenderer finger2Right;

    private final ModelRenderer lowerArmLeft;
    private final ModelRenderer thumbLeft;
    private final ModelRenderer finger1Left;
    private final ModelRenderer finger2Left;

    public RedstoneGolemModel(float scale) {
        super(0.0f, 0.0f, 256, 256);

        this.bipedBody = new ModelRenderer(this, 120, 36);
        this.bipedBody.setRotationPoint(0.0F, -4.0F, 0.0F);
        this.bipedBody.addBox(-11.0F, 0.0F, -7.0F, 22, 8, 14, scale);

        this.upperBody = new ModelRenderer(this, 0, 0);
        this.upperBody.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.bipedBody.addChild(this.upperBody);
        this.upperBody.addBox(-20.0F, -32.0F, -10.0F, 40, 32, 20, scale);
        this.upperBody.setTextureOffset(49, 90);
        this.upperBody.addBox(-8.0F, -20.0F, -2.0F, 16, 16, 16, scale);

        this.bipedHead = new ModelRenderer(this, 124, 8);
        this.bipedHead.setRotationPoint(0.0F, -24.0F, -10.0F);
        this.upperBody.addChild(this.bipedHead);
        this.bipedHead.addBox(-8.0F, -8.0F, -12.0F, 16, 16, 12, scale);

        this.bipedRightLeg = new ModelRenderer(this, 113, 58);
        this.bipedRightLeg.setRotationPoint(-11.0F, 8.0F, 0.0F);
        this.bipedBody.addChild(this.bipedRightLeg);
        this.bipedRightLeg.addBox(-9.0F, 0.0F, -6.0F, 12, 20, 12, scale);

        this.bipedLeftLeg = new ModelRenderer(this, 161, 58);
        this.bipedLeftLeg.setRotationPoint(11.0F, 8.0F, 0.0F);
        this.bipedBody.addChild(this.bipedLeftLeg);
        this.bipedLeftLeg.addBox(-3.0F, 0.0F, -6.0F, 12, 20, 12, scale);

        this.bipedRightArm = new ModelRenderer(this, 0, 52);
        this.bipedRightArm.setRotationPoint(-20.0F, -26.0F, 0.0F);
        this.upperBody.addChild(this.bipedRightArm);
        this.bipedRightArm.addBox(-14.0F, -4.0F, -6.0F, 14, 24, 12, scale);

        this.lowerArmRight = new ModelRenderer(this, 3, 88);
        this.lowerArmRight.setRotationPoint(-9.0F, 20.0F, 0.0F);
        this.bipedRightArm.addChild(this.lowerArmRight);
        this.lowerArmRight.addBox(-6.0F, 0.0F, -6.0F, 11, 22, 12, scale);

        this.thumbRight = new ModelRenderer(this, 81, 57);
        this.thumbRight.setRotationPoint(2.5F, 22.0F, -3.0F);
        this.lowerArmRight.addChild(this.thumbRight);
        this.thumbRight.addBox(-1.5F, 0.0F, -2.0F, 3, 8, 5, scale, true);

        this.finger1Right = new ModelRenderer(this, 97, 55);
        this.finger1Right.setRotationPoint(-2.5F, 22.0F, -3.0F);
        this.lowerArmRight.addChild(this.finger1Right);
        this.finger1Right.addBox(-1.5F, 0.0F, -2.5F, 3, 10, 5, scale, true);

        this.finger2Right = new ModelRenderer(this, 97, 55);
        this.finger2Right.setRotationPoint(-2.5F, 22.0F, 3.0F);
        this.lowerArmRight.addChild(this.finger2Right);
        this.finger2Right.addBox(-1.5F, 0.0F, -2.5F, 3, 10, 5, scale, true);

        this.bipedLeftArm = new ModelRenderer(this, 3, 122);
        this.bipedLeftArm.setRotationPoint(20.0F, -26.0F, 0.0F);
        this.upperBody.addChild(this.bipedLeftArm);
        this.bipedLeftArm.addBox(0.0F, -4.0F, -6.0F, 14, 24, 12, scale);

        this.lowerArmLeft = new ModelRenderer(this, 55, 124);
        this.lowerArmLeft.setRotationPoint(9.0F, 20.0F, 0.0F);
        this.bipedLeftArm.addChild(this.lowerArmLeft);
        this.lowerArmLeft.addBox(-5.0F, 0.0F, -6.0F, 11, 22, 12, scale, true);

        this.thumbLeft = new ModelRenderer(this, 81, 57);
        this.thumbLeft.setRotationPoint(-2.5F, 22.0F, -3.0F);
        this.lowerArmLeft.addChild(this.thumbLeft);
        this.thumbLeft.addBox(-1.5F, 0.0F, -2.0F, 3, 8, 5, scale);

        this.finger1Left = new ModelRenderer(this, 97, 55);
        this.finger1Left.setRotationPoint(2.5F, 22.0F, -3.0F);
        this.lowerArmLeft.addChild(this.finger1Left);
        this.finger1Left.addBox(-1.5F, 0.0F, -2.5F, 3, 10, 5, scale);

        this.finger2Left = new ModelRenderer(this, 97, 55);
        this.finger2Left.setRotationPoint(2.5F, 22.0F, 3.0F);
        this.lowerArmLeft.addChild(this.finger2Left);
        this.finger2Left.addBox(-1.5F, 0.0F, -2.5F, 3, 10, 5, scale);
    }
}