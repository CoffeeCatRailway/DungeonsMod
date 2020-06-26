package io.github.tastac.dungeonsmod.common.particles;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.github.tastac.dungeonsmod.registry.DungeonsParticles;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
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
            ServerPlayerEntity player = Minecraft.getInstance().getIntegratedServer().getPlayerList().getPlayerByUsername(reader.readString());
            reader.expect(' ');
            boolean goAway = reader.readBoolean();
            return new SoulParticleData(particleType, player, goAway);
        }

        public SoulParticleData read(ParticleType<SoulParticleData> particleType, PacketBuffer buffer) {
            return new SoulParticleData(particleType, (PlayerEntity) Minecraft.getInstance().world.getEntityByID(buffer.readVarInt()), buffer.readBoolean());
        }
    };

    public static SoulParticleData create(PlayerEntity player, boolean goAway) {
        return new SoulParticleData(DungeonsParticles.SOUL.get(), player, goAway);
    }

    private final ParticleType<SoulParticleData> particleType;
    private final PlayerEntity player;
    private final boolean goAway;

    public SoulParticleData(ParticleType<SoulParticleData> particleType, PlayerEntity player, boolean goAway) {
        this.particleType = particleType;
        this.player = player;
        this.goAway = goAway;
    }

    @OnlyIn(Dist.CLIENT)
    public PlayerEntity getPlayer() {
        return player;
    }

    @OnlyIn(Dist.CLIENT)
    public boolean isGoAway() {
        return goAway;
    }

    @Override
    public ParticleType<?> getType() {
        return this.particleType;
    }

    @Override
    public void write(PacketBuffer buffer) {
        buffer.writeVarInt(this.player.getEntityId());
        buffer.writeBoolean(this.goAway);
    }

    @Override
    public String getParameters() {
        return Registry.PARTICLE_TYPE.getKey(this.getType()) + " " + this.player.getName() + " " + this.goAway;
    }
}
