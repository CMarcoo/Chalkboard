package studio.thevipershow.chalkboard.data;

import org.bukkit.Particle;
import org.jetbrains.annotations.NotNull;

public interface Chalkboard {

    @NotNull
    ChalkboardSurface getChalkboardSurface();

    @NotNull
    ChalkboardRenderer getChalkboardRenderer();

    void setChalkboardSurface(@NotNull ChalkboardSurface chalkboardSurface);

    void setChalkboardRenderer(@NotNull ChalkboardRenderer chalkboardRenderer);

    @NotNull
    Particle getParticle();
}
