package studio.thevipershow.chalkboard.data;

import java.util.Collection;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

public interface ChalkboardSurface {

    @NotNull
    Location getFirstCorner();

    @NotNull
    Location getSecondCorner();

    void setFirstCorner(@NotNull Location location);

    void setSecondCorner(@NotNull Location location);

    @NotNull
    Collection<ParticleData> getChalkParticlesData();
}
