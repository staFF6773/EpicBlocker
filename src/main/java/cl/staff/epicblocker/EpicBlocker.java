package cl.staff.epicblocker;

import block.CommandBlock;
import block.playerblock;
import commands.reload;
import manager.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import utils.ChatUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.logging.Level;

public final class EpicBlocker extends JavaPlugin implements Listener {

    ConsoleCommandSender mycmd = Bukkit.getConsoleSender();
    public static String prefix;
    public static EpicBlocker plugin;
    private String versionURL = "https://api.spigotmc.org/legacy/update.php?resource=113391";
    private BukkitTask updateCheckTask;

    @Override
    public void onEnable() {

        // updateCheckTask

        this.updateCheckTask = new BukkitRunnable() {
            @Override
            public void run() {
                checkForUpdate();
            }
        }.runTaskTimerAsynchronously(this, 0L, 86400L);

        // prefix
        prefix = ChatColor.translateAlternateColorCodes('&', getConfig().getString("prefix", "&cEpicBlocker &7»"));

        // Config
        ConfigManager.setupConfig(this);
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        //Register
        registerCommands();
        plugin = this;
        registerEvents();


        mycmd.sendMessage(ChatUtils.getColoredMessage("   &c ___   &cEpicBloker &7v1.0.0        "));
        mycmd.sendMessage(ChatUtils.getColoredMessage("   &c|___   &7Running on Bukkit - Paper  "));
        mycmd.sendMessage(ChatUtils.getColoredMessage("   &c|___ "));
        mycmd.sendMessage(ChatUtils.getColoredMessage("&7Commands successfully loaded"));

    }

    @Override
    public void onDisable() {

        // Guardar configuración al desactivar el plugin
        saveConfig();

        mycmd.sendMessage(ChatUtils.getColoredMessage("&cEpicBlocker &7is disabling, if this is a reload and you experience issues consider rebooting."));
        mycmd.sendMessage(ChatUtils.getColoredMessage("&7Commands Saved Successfully"));
        mycmd.sendMessage(ChatUtils.getColoredMessage("&7Goodbye!"));
    }

    private void checkForUpdate() {
        try {
            int resourceId = getResourceId(); // Obtener el ID del recurso del plugin

            URL url = new URL(versionURL.replace("113391", String.valueOf(resourceId)));
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String latestVersion = reader.readLine();

            if (latestVersion != null && !latestVersion.equals(getDescription().getVersion())) {
                getLogger().info("A new version is available! Latest version: " + latestVersion);
                getLogger().info("Download the update at: https://www.spigotmc.org/resources/epicblocker.113391/");

                // Notificar al usuario en el servidor
                getServer().getOnlinePlayers().forEach(player ->
                        player.sendMessage("A new version of Epicblocker is available! " +
                                "Most recent version: " + latestVersion +
                                ". Download the update at: https://www.spigotmc.org/resources/epicblocker.113391/")
                );

            } else {
                getLogger().info("Epicblocker is updated. Current version: " + getDescription().getVersion());
            }

        } catch (IOException e) {
            getLogger().log(Level.WARNING, "Update could not be verified: " + e.getMessage());
        }
    }

    private int getResourceId() {
        // Obtener el ID del recurso del plugin desde SpigotMC
        int resourceId = -1;

        try {
            URL url = new URL("https://api.spigotmc.org/legacy/update.php?resource=113391");
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String resourceIdString = reader.readLine();

            if (resourceIdString != null) {
                resourceId = Integer.parseInt(resourceIdString);
            }

        } catch (IOException | NumberFormatException e) {
            getLogger().log(Level.WARNING, "The resource ID could not be obtained: " + e.getMessage());
        }

        return resourceId;
    }

    public void registerCommands() {
        this.getCommand("reload").setExecutor(new reload(this));
    }

    public void registerEvents() {
        getServer().getPluginManager().registerEvents(new playerblock(this), this);
        getServer().getPluginManager().registerEvents(new CommandBlock(), this);
    }

    public static EpicBlocker getplugin() {
        return plugin;
    }
}
