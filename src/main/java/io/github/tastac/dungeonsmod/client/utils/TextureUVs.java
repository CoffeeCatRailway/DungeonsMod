package io.github.tastac.dungeonsmod.client.utils;

import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec2f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * @author CoffeeCatRailway
 * Created: 30/06/2020
 */
public class TextureUVs {

    public Vec2f topLeft;
    public Vec2f topRight;
    public Vec2f bottomRight;
    public Vec2f bottomLeft;

    public TextureUVs(float u1, float v1, float u2, float v2, float u3, float v3, float u4, float v4) {
        this(new Vec2f(u1, v1), new Vec2f(u2, v2), new Vec2f(u3, v3), new Vec2f(u4, v4));
    }

    public TextureUVs(Vec2f topLeft, Vec2f topRight, Vec2f bottomRight, Vec2f bottomLeft) {
        this.topLeft = topLeft;
        this.topRight = topRight;
        this.bottomRight = bottomRight;
        this.bottomLeft = bottomLeft;
    }
}
