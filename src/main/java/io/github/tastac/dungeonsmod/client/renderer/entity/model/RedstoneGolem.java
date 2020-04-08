////Paste this code into your mod.
//
//import org.lwjgl.opengl.GL11;
//import net.minecraft.client.model.ModelBase;
//import net.minecraft.client.model.ModelBox;
//import net.minecraft.client.model.RendererModel;
//import net.minecraft.client.renderer.GlStateManager;
//import net.minecraft.entity.Entity;
//
//public class custom_model extends ModelBase {
//	private final RendererModel lower_body;
//	private final RendererModel upper_body;
//	private final RendererModel head;
//	private final RendererModel arm_left;
//	private final RendererModel lower_arm_left;
//	private final RendererModel thumb_left;
//	private final RendererModel finger_1_left;
//	private final RendererModel finger_2_left;
//	private final RendererModel arm_right;
//	private final RendererModel lower_arm_right;
//	private final RendererModel thumb_right;
//	private final RendererModel finger_1_right;
//	private final RendererModel finger_2_right;
//	private final RendererModel leg_right;
//	private final RendererModel leg_left;
//
//	public custom_model() {
//		textureWidth = 256;
//		textureHeight = 256;
//
//		lower_body = new RendererModel(this);
//		lower_body.setRotationPoint(0.0F, -4.0F, 0.0F);
//		lower_body.cubeList.add(new ModelBox(lower_body, 120, 36, -11.0F, 0.0F, -7.0F, 22, 8, 14, 0.0F, false));
//
//		upper_body = new RendererModel(this);
//		upper_body.setRotationPoint(0.0F, 0.0F, 0.0F);
//		lower_body.addChild(upper_body);
//		upper_body.cubeList.add(new ModelBox(upper_body, 0, 0, -20.0F, -32.0F, -10.0F, 40, 32, 20, 0.0F, false));
//		upper_body.cubeList.add(new ModelBox(upper_body, 49, 90, -8.0F, -20.0F, -2.0F, 16, 16, 16, 0.0F, false));
//
//		head = new RendererModel(this);
//		head.setRotationPoint(0.0F, -24.0F, -10.0F);
//		upper_body.addChild(head);
//		head.cubeList.add(new ModelBox(head, 124, 8, -8.0F, -8.0F, -12.0F, 16, 16, 12, 0.0F, false));
//
//		arm_left = new RendererModel(this);
//		arm_left.setRotationPoint(20.0F, -26.0F, 0.0F);
//		upper_body.addChild(arm_left);
//		arm_left.cubeList.add(new ModelBox(arm_left, 3, 122, 0.0F, -4.0F, -6.0F, 14, 24, 12, 0.0F, false));
//
//		lower_arm_left = new RendererModel(this);
//		lower_arm_left.setRotationPoint(9.0F, 20.0F, 0.0F);
//		arm_left.addChild(lower_arm_left);
//		lower_arm_left.cubeList.add(new ModelBox(lower_arm_left, 55, 124, -5.0F, 0.0F, -6.0F, 11, 22, 12, 0.0F, true));
//
//		thumb_left = new RendererModel(this);
//		thumb_left.setRotationPoint(-2.5F, 22.0F, -3.0F);
//		lower_arm_left.addChild(thumb_left);
//		thumb_left.cubeList.add(new ModelBox(thumb_left, 81, 57, -1.5F, 0.0F, -2.0F, 3, 8, 5, 0.0F, false));
//
//		finger_1_left = new RendererModel(this);
//		finger_1_left.setRotationPoint(2.5F, 22.0F, -3.0F);
//		lower_arm_left.addChild(finger_1_left);
//		finger_1_left.cubeList.add(new ModelBox(finger_1_left, 97, 55, -1.5F, 0.0F, -2.5F, 3, 10, 5, 0.0F, false));
//
//		finger_2_left = new RendererModel(this);
//		finger_2_left.setRotationPoint(2.5F, 22.0F, 3.0F);
//		lower_arm_left.addChild(finger_2_left);
//		finger_2_left.cubeList.add(new ModelBox(finger_2_left, 97, 55, -1.5F, 0.0F, -2.5F, 3, 10, 5, 0.0F, false));
//
//		arm_right = new RendererModel(this);
//		arm_right.setRotationPoint(-20.0F, -26.0F, 0.0F);
//		upper_body.addChild(arm_right);
//		arm_right.cubeList.add(new ModelBox(arm_right, 0, 52, -14.0F, -4.0F, -6.0F, 14, 24, 12, 0.0F, false));
//
//		lower_arm_right = new RendererModel(this);
//		lower_arm_right.setRotationPoint(-9.0F, 20.0F, 0.0F);
//		arm_right.addChild(lower_arm_right);
//		lower_arm_right.cubeList.add(new ModelBox(lower_arm_right, 3, 88, -6.0F, 0.0F, -6.0F, 11, 22, 12, 0.0F, false));
//
//		thumb_right = new RendererModel(this);
//		thumb_right.setRotationPoint(2.5F, 22.0F, -3.0F);
//		lower_arm_right.addChild(thumb_right);
//		thumb_right.cubeList.add(new ModelBox(thumb_right, 81, 57, -1.5F, 0.0F, -2.0F, 3, 8, 5, 0.0F, true));
//
//		finger_1_right = new RendererModel(this);
//		finger_1_right.setRotationPoint(-2.5F, 22.0F, -3.0F);
//		lower_arm_right.addChild(finger_1_right);
//		finger_1_right.cubeList.add(new ModelBox(finger_1_right, 97, 55, -1.5F, 0.0F, -2.5F, 3, 10, 5, 0.0F, true));
//
//		finger_2_right = new RendererModel(this);
//		finger_2_right.setRotationPoint(-2.5F, 22.0F, 3.0F);
//		lower_arm_right.addChild(finger_2_right);
//		finger_2_right.cubeList.add(new ModelBox(finger_2_right, 97, 55, -1.5F, 0.0F, -2.5F, 3, 10, 5, 0.0F, true));
//
//		leg_right = new RendererModel(this);
//		leg_right.setRotationPoint(-11.0F, 8.0F, 0.0F);
//		lower_body.addChild(leg_right);
//		leg_right.cubeList.add(new ModelBox(leg_right, 113, 58, -9.0F, 0.0F, -6.0F, 12, 20, 12, 0.0F, false));
//
//		leg_left = new RendererModel(this);
//		leg_left.setRotationPoint(11.0F, 8.0F, 0.0F);
//		lower_body.addChild(leg_left);
//		leg_left.cubeList.add(new ModelBox(leg_left, 161, 58, -3.0F, 0.0F, -6.0F, 12, 20, 12, 0.0F, false));
//	}
//
//	@Override
//	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
//		lower_body.render(f5);
//	}
//	public void setRotationAngle(RendererModel modelRenderer, float x, float y, float z) {
//		modelRenderer.rotateAngleX = x;
//		modelRenderer.rotateAngleY = y;
//		modelRenderer.rotateAngleZ = z;
//	}
//}