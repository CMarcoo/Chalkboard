package studio.thevipershow.chalkboard.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.util.BoundingBox;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import studio.thevipershow.chalkboard.ChalkboardPlugin;
import studio.thevipershow.chalkboard.data.Chalkboard;
import studio.thevipershow.chalkboard.data.ChalkboardManager;
import studio.thevipershow.chalkboard.data.ChalkboardSurface;
import studio.thevipershow.chalkboard.data.PlayersData;
import studio.thevipershow.chalkboard.data.StandardChalkboard;
import studio.thevipershow.chalkboard.data.StandardChalkboardRenderer;
import studio.thevipershow.chalkboard.data.StandardChalkboardSurface;

public final class ChalkboardCommand implements TabExecutor {

    private final ChalkboardPlugin chalkboardPlugin;
    private static ChalkboardCommand chalkboardCommand;

    private ChalkboardCommand(@NotNull ChalkboardPlugin chalkboardPlugin) {
        this.chalkboardPlugin = chalkboardPlugin;
    }

    public static ChalkboardCommand getInstance(@NotNull ChalkboardPlugin chalkboardPlugin) {
        if (chalkboardCommand == null) {
            chalkboardCommand = new ChalkboardCommand(chalkboardPlugin);
        }
        return chalkboardCommand;
    }

    public static String color(@NotNull final String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static void sendHelp(@NotNull CommandSender sender) {

    }

    public void createChalkboard(@NotNull Player player, @NotNull String name) {
        ChalkboardManager chalkboardManager = chalkboardPlugin.getChalkboardManager();
        Map<String, Chalkboard> activeChalkboards = chalkboardManager.getActiveChalkboards();

        if (activeChalkboards.containsKey(name)) {
            player.sendMessage(color("&cA chalkboard with this name already exists!"));
            return;
        }

        Chalkboard search = chalkboardManager.searchByBlock(player.getLocation());
        if (search != null) {
            player.sendMessage(color("&cYou already created a chalkboard here!"));
        } else {
            player.sendMessage(color(String.format("&aCreated new chalkboard %s", player.getLocation().toVector().toString())));
            Chalkboard chalkboard = new StandardChalkboard();
            chalkboard.setChalkboardRenderer(new StandardChalkboardRenderer());
            ChalkboardSurface surface = new StandardChalkboardSurface();
            Location playerLocation = player.getLocation();
            World world = playerLocation.getWorld();
            surface.setFirstCorner(playerLocation.clone().add(0d, 1d, 0d));
            surface.setSecondCorner(playerLocation.clone().add(1d, 6d, 12d));
            chalkboard.setChalkboardSurface(surface);
            BoundingBox chalkboardBox = BoundingBox.of(surface.getFirstCorner(), surface.getSecondCorner());

            for (int x = (int) chalkboardBox.getMinX(); x <= chalkboardBox.getMaxX(); x++) {
                for (int y = (int) chalkboardBox.getMinY(); y <= chalkboardBox.getMaxY(); y++) {
                    for (int z = (int) chalkboardBox.getMinZ(); z <= chalkboardBox.getMaxZ(); z++) {
                        world.getBlockAt(x, y, z).setType(Material.BLACK_CONCRETE);
                    }
                }
            }

            activeChalkboards.put(name, chalkboard);
        }
    }

    private void changeGlobalParticle(@NotNull CommandSender sender, final @NotNull String particle) {
        for (Particle value : Particle.values()) {
            String particleName = value.name();
            if (particle.equalsIgnoreCase(particleName)) {
                sender.sendMessage(color("&aYou changed default writing particle to &f" + particleName));
                StandardChalkboard.DEFAULT = value;
                return;
            }
        }
    }

    private void changePersonalParticle(@NotNull Player player, final @NotNull String particle) {
        for (Particle value : Particle.values()) {
            String particleName = value.name();
            if (particle.equalsIgnoreCase(particleName)) {
                player.sendMessage(color("&aYou changed your writing particle to &f" + particleName));
                PlayersData.getInstance().getSelectedParticle().put(player.getUniqueId(), value);
                return;
            }
        }
    }

    private void removeChalkboard(@NotNull CommandSender sender, @NotNull String name) {
        ChalkboardManager chalkboardManager = chalkboardPlugin.getChalkboardManager();
        Map<String, Chalkboard> chalkboardMap = chalkboardManager.getActiveChalkboards();
        if (chalkboardMap.containsKey(name)) {
            sender.sendMessage(color("&aSuccessfully removed chalkboard."));
            chalkboardMap.remove(name);
        } else {
            sender.sendMessage(color("&cA chalkboard with that name doesn't exist!"));
        }
    }

    private void cleanChalkboard(@NotNull CommandSender sender, @NotNull String name) {
        ChalkboardManager manager = ChalkboardManager.getInstance();
        Map<String, Chalkboard> chalkboardMap = manager.getActiveChalkboards();
        if (chalkboardMap.containsKey(name)) {
            chalkboardMap.get(name).getChalkboardSurface().getChalkParticlesData().clear();
            sender.sendMessage(color("&aCompletely cleaned chalkboard."));
        } else {
            sender.sendMessage(color("&cThat chalkboard does not exist!"));
        }
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        final int length = args.length;
        if (length == 0) {
            sendHelp(sender);
        } else if (length == 2) {
            if (args[0].equalsIgnoreCase("create")) {
                if (!(sender instanceof Player)) {
                    sender.sendMessage(color("&cOnly players can create chalkboards!"));
                } else {
                    createChalkboard((Player) sender, args[1]);
                }
            } else if (args[0].equalsIgnoreCase("gparticle")) {
                changeGlobalParticle(sender, args[1]);
            } else if (args[0].equalsIgnoreCase("particle")) {
                if (!(sender instanceof Player)) {
                    sender.sendMessage(color("&cOnly players can modify personal particles settings!"));
                } else {
                    changePersonalParticle((Player) sender, args[1]);
                }
            } else if (args[0].equalsIgnoreCase("remove")) {
                removeChalkboard(sender, args[1]);
            } else if (args[0].equalsIgnoreCase("clean")) {
                cleanChalkboard(sender, args[1]);
            } else {
                sender.sendMessage(color("&cUnknown first argument."));
            }
        }
        return true;
    }

    private final static List<String> BASE_ARGS = Arrays.asList("create", "clean", "particle", "gparticle");
    private final static List<String> PARTICLE_COMPLETION = Arrays.stream(Particle.values()).map(Enum::name).sorted().collect(Collectors.toList());

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length == 1) {
            return BASE_ARGS;
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("particle") || args[0].equalsIgnoreCase("gparticle")) {
                return PARTICLE_COMPLETION;
            } else if (args[0].equalsIgnoreCase("clean")) {
                return new ArrayList<>(ChalkboardManager.getInstance().getActiveChalkboards().keySet());
            }
        }
        return null;
    }
}
