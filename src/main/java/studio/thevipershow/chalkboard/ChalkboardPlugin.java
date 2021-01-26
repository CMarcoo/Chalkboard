package studio.thevipershow.chalkboard;

import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import studio.thevipershow.chalkboard.commands.ChalkboardCommand;
import studio.thevipershow.chalkboard.data.ChalkboardManager;
import studio.thevipershow.chalkboard.listeners.InteractListeners;

public final class ChalkboardPlugin extends JavaPlugin {

    private final ChalkboardManager chalkboardManager = ChalkboardManager.getInstance();

    private ChalkboardCommand chalkboardCommand;
    private InteractListeners interactListeners;

    private void setupCommands() {
        chalkboardCommand = ChalkboardCommand.getInstance(this);
    }

    private void setupListeners() {
        if (interactListeners == null) {
            interactListeners = InteractListeners.getInstance(this);
        }
    }

    private void registerListeners() {
        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(interactListeners, this);
    }

    private void registerCommands() {
        PluginCommand chalkboardPluginCommand = getCommand("chalkboard");
        chalkboardPluginCommand.setExecutor(chalkboardCommand);
        chalkboardPluginCommand.setTabCompleter(chalkboardCommand);
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        setupListeners();
        registerListeners();
        setupCommands();
        registerCommands();
        GlobalChalkboardRenderer.getInstance(this).startRendering();
    }

    @NotNull
    public ChalkboardCommand getChalkboardCommand() {
        return chalkboardCommand;
    }

    @NotNull
    public ChalkboardManager getChalkboardManager() {
        return chalkboardManager;
    }

    @NotNull
    public InteractListeners getInteractListeners() {
        return interactListeners;
    }
}
