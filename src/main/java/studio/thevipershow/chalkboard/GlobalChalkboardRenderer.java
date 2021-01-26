package studio.thevipershow.chalkboard;

import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;
import studio.thevipershow.chalkboard.data.Chalkboard;

public final class GlobalChalkboardRenderer {

    private final ChalkboardPlugin chalkboardPlugin;
    private static GlobalChalkboardRenderer instance;

    private BukkitTask renderTask;

    private GlobalChalkboardRenderer(ChalkboardPlugin chalkboardPlugin) {
        this.chalkboardPlugin = chalkboardPlugin;
    }

    public static GlobalChalkboardRenderer getInstance(@NotNull ChalkboardPlugin chalkboardPlugin) {
        if (instance == null) {
            instance = new GlobalChalkboardRenderer(chalkboardPlugin);
        }
        return instance;
    }

    public void startRendering() {
        if (renderTask == null) {
            renderTask = chalkboardPlugin.getServer().getScheduler()
                    .runTaskTimerAsynchronously(chalkboardPlugin, () -> {
                        for (Chalkboard activeChalkboard : chalkboardPlugin.getChalkboardManager().getActiveChalkboards().values()) {
                            activeChalkboard.getChalkboardRenderer().renderParticles(activeChalkboard);
                        }
                    }, 1L, 10L);
        }
    }

    public void stopRendering() {
        if (renderTask != null) {
            renderTask.cancel();
        }
    }
}
