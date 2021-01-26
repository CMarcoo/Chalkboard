package studio.thevipershow.chalkboard.data;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.jetbrains.annotations.NotNull;

public class StandardParticleData implements ParticleData {

    private final Particle particle;
    private final Location location;

    public StandardParticleData(@NotNull Particle particle, @NotNull Location location) {
        this.particle = particle;
        this.location = location;
    }

    @Override
    public @NotNull Location getLocation() {
        return location;
    }

    @Override
    public @NotNull Particle getParticle() {
        return particle;
    }
}
