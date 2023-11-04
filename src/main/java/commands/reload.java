package commands;

import cl.staff.epicblocker.EpicBlocker;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import utils.ChatUtils;

import java.io.File;

public class reload implements CommandExecutor {
    private final EpicBlocker plugin;

    public reload(EpicBlocker plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender.hasPermission("epicblocker.reload") || sender instanceof ConsoleCommandSender || sender.isOp()) {
            reloadConfig();
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cEpicBlocker &7» &aThe configuration file was reloaded."));
            sender.sendMessage(ChatUtils.getColoredMessage("&7(Some options only apply after the server has been restarted.)"));
        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cEpicBlocker &7» &c&lUPS &cSorry, but you do not have permission to execute this command."));
        }
        return true;
    }

    private void reloadConfig() {
        File configFile = new File(plugin.getDataFolder(), "config.yml");

        if (!configFile.exists()) {
            // Si el archivo no existe, carga la configuración por defecto
            plugin.saveDefaultConfig();
        }

        plugin.reloadConfig();
    }
}