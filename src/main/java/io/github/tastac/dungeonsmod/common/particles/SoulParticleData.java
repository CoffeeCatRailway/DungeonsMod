package io.github.tastac.dungeonsmod.common.particles;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.github.tastac.dungeonsmod.registry.DungeonsParticles;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * @author CoffeeCatRailway
 * Created: 25/06/2020
 */
public class SoulParticleData implements IParticleData {

    public static final IParticleData.IDeserializer<SoulParticleData> DESERIALIZER = new IParticleData.IDeserializer<SoulParticleData>() {
        public SoulParticleData deserialize(ParticleType<SoulParticleData> particleType, StringReader reader) throws CommandSyntaxException {
            reader.expect(' ');
            return new SoulParticleData(particleType, Minecraft.getInstance().getIntegratedServer().getPlayerList().getPlayerByUsername(reader.readString()));
        }

        public SoulParticleData read(ParticleType<SoulParticleData> particleType, PacketBuffer buffer) {
            return new SoulParticleData(particleType, (PlayerEntity) Minecraft.getInstance().world.getEntityByID(buffer.readVarInt()));
        }
    };

    public static SoulParticleData create(PlayerEntity player) {
        return new SoulParticleData(DungeonsParticles.SOUL.get(), player);
    }

    private final ParticleType<SoulParticleData> particleType;
    private final PlayerEntity player;

    public SoulParticleData(ParticleType<SoulParticleData> particleType, PlayerEntity player) {
        this.particleType = particleType;
        this.player = player;
    }

    @OnlyIn(Dist.CLIENT)
    public PlayerEntity getPlayer() {
        return player;
    }

    @Override
    public ParticleType<?> getType() {
        return this.particleType;
    }

    @Override
    public void write(PacketBuffer buffer) {
        buffer.writeVarInt(this.player.getEntityId());
    }

    @Override
    public String getParameters() {
        return Registry.PARTICLE_TYPE.getKey(this.getType()) + " " + this.player.getName();
    }
}
