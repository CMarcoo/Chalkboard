package studio.thevipershow.chalkboard.data;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.jetbrains.annotations.NotNull;

public interface ParticleData {

    @NotNull
    Location getLocation();

    @NotNull
    Particle getParticle();
}
