package studio.thevipershow.chalkboard.data;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import org.bukkit.Location;
import org.bukkit.util.BoundingBox;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ChalkboardManager {

    private static ChalkboardManager chalkboardManagerInstance;
    private final Map<String, Chalkboard> activeChalkboards = new HashMap<>();

    private ChalkboardManager() {
    }

    public static ChalkboardManager getInstance() {
        if (chalkboardManagerInstance == null) {
            chalkboardManagerInstance = new ChalkboardManager();
        }
        return chalkboardManagerInstance;
    }

    @NotNull
    public Map<String, Chalkboard> getActiveChalkboards() {
        return activeChalkboards;
    }

    @Nullable
    public Chalkboard searchByBlock(@NotNull Location location) {
        for (final Chalkboard chalkboard : activeChalkboards.values()) {

            ChalkboardSurface surface = chalkboard.getChalkboardSurface();
            Location firstCorner = surface.getFirstCorner();
            Location secondCorner = surface.getSecondCorner();

            BoundingBox boundingBox = BoundingBox.of(firstCorner, secondCorner);

            if (boundingBox.contains(location.getX(), location.getY(), location.getZ())) {
                return chalkboard;
            }
        }

        return null;
    }
}
