package io.github.tastac.dungeonsmod.client.utils;

import net.minecraft.client.renderer.Vector3f;
import net.minecraft.util.math.Vec2f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * @author CoffeeCatRailway
 * Created: 30/06/2020
 */
@OnlyIn(Dist.CLIENT)
public class PosUv {

    public Vector3f pos;

    public float u;
    public float v;

    public PosUv(float x, float y, float z, Vec2f uv) {
        this(new Vector3f(x, y, z), uv.x, uv.y);
    }

    public PosUv(float x, float y, float z, float u, float v) {
        this(new Vector3f(x, y, z), u, v);
    }

    public PosUv(Vector3f pos, float u, float v) {
        this.pos = pos;
        this.u = u;
        this.v = v;
    }
}
