package cl.staff.epicblocker;

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
import utils.ChatUtils;

public final class EpicBlocker extends JavaPlugin implements Listener {

    ConsoleCommandSender mycmd = Bukkit.getConsoleSender();
    public static String prefix;
    public static EpicBlocker plugin;

    @Override
    public void onEnable() {

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
        mycmd.sendMessage(ChatUtils.getColoredMessage("   &c|---   &7Running on Bukkit - Paper  "));
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

    public void registerCommands() {
        this.getCommand("reload").setExecutor(new reload(this));
    }

    public void registerEvents() {
        getServer().getPluginManager().registerEvents(new playerblock(this), this);
    }

    public static EpicBlocker getplugin() {
        return plugin;
    }
}
