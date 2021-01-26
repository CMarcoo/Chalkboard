package studio.thevipershow.chalkboard.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.RayTraceResult;
import org.jetbrains.annotations.NotNull;
import studio.thevipershow.chalkboard.ChalkboardPlugin;
import studio.thevipershow.chalkboard.data.Chalkboard;
import studio.thevipershow.chalkboard.data.ChalkboardManager;
import studio.thevipershow.chalkboard.data.PlayersData;
import studio.thevipershow.chalkboard.data.StandardParticleData;

public class InteractListeners implements Listener {

    private final ChalkboardPlugin chalkboardPlugin;
    private final PlayersData playersData = PlayersData.getInstance();
    private static InteractListeners instance;

    private InteractListeners(ChalkboardPlugin chalkboardPlugin) {
        this.chalkboardPlugin = chalkboardPlugin;
    }

    public static InteractListeners getInstance(@NotNull ChalkboardPlugin chalkboardPlugin) {
        if (instance == null) {
            instance = new InteractListeners(chalkboardPlugin);
        }
        return instance;
    }

    private void interactWithChalkboard(@NotNull Player player, @NotNull Block clickedBlock) {
        ChalkboardManager manager = chalkboardPlugin.getChalkboardManager();
        Chalkboard search = manager.searchByBlock(clickedBlock.getLocation());
        if (search == null) {
            player.sendMessage(ChatColor.RED + "You are not writing on a registered chalkboard!");
        } else {
            final RayTraceResult rayTraceResult = player.rayTraceBlocks(3.50);
            if (rayTraceResult != null) {
                final Location test = rayTraceResult.getHitPosition().toLocation(player.getWorld()).subtract(.02, .02, .02);
                final Particle chosenParticle = playersData.getSelectedParticle().getOrDefault(player.getUniqueId(), search.getParticle());
                search.getChalkboardSurface().getChalkParticlesData().add(new StandardParticleData(chosenParticle, test));
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerInteract(final PlayerInteractEvent event) {
        Action action = event.getAction();

        if (action == Action.RIGHT_CLICK_BLOCK && event.hasItem()) {

            Block clickedBlock = event.getClickedBlock();

            if (clickedBlock == null) {
                return;
            }

            if (clickedBlock.getType() != Material.BLACK_CONCRETE) {
                return;
            }

            ItemStack handItem = event.getItem();
            if (handItem == null) {
                return;
            }

            Material handMaterial = handItem.getType();
            if (handMaterial == Material.BONE_MEAL) {
                interactWithChalkboard(event.getPlayer(), clickedBlock);
                event.setCancelled(true);
            }
        }
    }
}
