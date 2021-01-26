package studio.thevipershow.chalkboard.data;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Particle;

public class PlayersData {

    private static final PlayersData playersData = new PlayersData();
    private PlayersData() {}

    public static PlayersData getInstance() {
        return playersData;
    }

    private final Map<UUID, Particle> selectedParticle = new HashMap<>();

    public Map<UUID, Particle> getSelectedParticle() {
        return selectedParticle;
    }
}
