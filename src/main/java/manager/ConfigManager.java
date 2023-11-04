package manager;

import cl.staff.epicblocker.EpicBlocker;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {

    private static FileConfiguration config;

    public static void setupConfig(EpicBlocker epicblocker){
        ConfigManager.config = epicblocker.getConfig();
        epicblocker.saveDefaultConfig();
    }
}