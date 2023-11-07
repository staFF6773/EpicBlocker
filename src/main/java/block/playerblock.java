package block;

import cl.staff.epicblocker.EpicBlocker;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;
import utils.ChatUtils;

import java.util.regex.Pattern;

public class playerblock implements Listener {

    private final EpicBlocker plugin;

    public playerblock(EpicBlocker plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();
        String blockedWord = null; // Variable para almacenar la palabra bloqueada

        // Verifica si el jugador tiene el permiso de bypass
        if (player.hasPermission("epicblocker.bypass")) {
            return;  // No se aplica el bloqueo
        }

        for (String word : plugin.getConfig().getStringList("blocked-words")) {
            if (message.toLowerCase().contains(word.toLowerCase())) {
                blockedWord = word;
                String errorMessage = plugin.getConfig().getString("error-message");
                errorMessage = errorMessage.replace("%eb-word%", blockedWord); // Reemplazar %word% con la palabra bloqueada
                errorMessage = errorMessage.replace("%eb-player%", player.getName()); // Reemplazar %eb-player% con el nombre del jugador
                player.sendMessage(ChatUtils.getColoredMessage(EpicBlocker.prefix + " " + errorMessage));
                event.setCancelled(true);
                break; // Sale del bucle tan pronto como se encuentra una palabra bloqueada
            }

        }

        if (blockedWord != null) {
            // Aquí puedes hacer lo que necesites con la palabra bloqueada y el nombre del jugador.
        }
    }


}
