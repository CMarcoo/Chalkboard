package studio.thevipershow.chalkboard.data;

import java.util.Collection;
import java.util.LinkedList;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

public class StandardChalkboardSurface implements ChalkboardSurface {

    private Location firstCorner;
    private Location secondCorner;

    private final LinkedList<ParticleData> particlesPositions = new LinkedList<>();

    @Override
    public @NotNull Location getFirstCorner() {
        return firstCorner;
    }

    @Override
    public @NotNull Location getSecondCorner() {
        return secondCorner;
    }

    @Override
    public void setFirstCorner(@NotNull Location location) {
        firstCorner = location;
    }

    @Override
    public void setSecondCorner(@NotNull Location location) {
        secondCorner = location;
    }

    @Override
    public @NotNull Collection<ParticleData> getChalkParticlesData() {
        return particlesPositions;
    }


}
