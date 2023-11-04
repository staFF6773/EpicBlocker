package block;

import cl.staff.epicblocker.EpicBlocker;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;
import utils.ChatUtils;

public class playerblock implements Listener {

    private final EpicBlocker plugin;

    public playerblock(EpicBlocker plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();

        // Verifica si el jugador tiene el permiso de bypass
        if (player.hasPermission("epicblocker.bypass")) {
            return;  // No se aplica el bloqueo
        }

        for (String blockedWord : plugin.getConfig().getStringList("blocked-words")) {
            if (message.toLowerCase().contains(blockedWord.toLowerCase())) {
                String errorMessage = plugin.getConfig().getString("error-message");
                player.sendMessage(ChatUtils.getColoredMessage(EpicBlocker.prefix + " " + errorMessage));
                event.setCancelled(true);
                break;
            }
        }
    }
}
