package studio.thevipershow.chalkboard.data;

import org.bukkit.Particle;
import org.jetbrains.annotations.NotNull;

public class StandardChalkboard implements Chalkboard {

    private ChalkboardSurface chalkboardSurface;
    private ChalkboardRenderer chalkboardRenderer;
    public static Particle DEFAULT = Particle.SOUL_FIRE_FLAME;

    @Override
    public @NotNull ChalkboardSurface getChalkboardSurface() {
        return chalkboardSurface;
    }

    @Override
    public @NotNull ChalkboardRenderer getChalkboardRenderer() {
        return chalkboardRenderer;
    }

    @Override
    public @NotNull Particle getParticle() {
        return DEFAULT;
    }

    @Override
    public void setChalkboardSurface(@NotNull ChalkboardSurface chalkboardSurface) {
        this.chalkboardSurface = chalkboardSurface;
    }

    @Override
    public void setChalkboardRenderer(@NotNull ChalkboardRenderer chalkboardRenderer) {
        this.chalkboardRenderer = chalkboardRenderer;
    }
}
