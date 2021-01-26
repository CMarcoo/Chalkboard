package studio.thevipershow.chalkboard.data;

import java.util.Collection;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;

public class StandardChalkboardRenderer implements ChalkboardRenderer {

    @Override
    public void renderParticles(@NotNull Chalkboard chalkboard) {
        final ChalkboardSurface surface = chalkboard.getChalkboardSurface();
        final World firstWorld = surface.getFirstCorner().getWorld();
        final World secondWorld = surface.getSecondCorner().getWorld();

        if (firstWorld != secondWorld) {
            throw new RuntimeException("ChalkboardPlugin surface is split between two different worlds!");
        }

        if (firstWorld == null) {
            throw new RuntimeException("ChalkboardPlugin surface's corner's world resulted null.");
        }

        final Collection<ParticleData> data = chalkboard.getChalkboardSurface().getChalkParticlesData();

        for (final ParticleData particleData : data) {
            final Location location = particleData.getLocation();
            firstWorld.spawnParticle(particleData.getParticle(), location.getX(), location.getY(), location.getZ(), 0, 0, 0, 0);
        }
    }
}
