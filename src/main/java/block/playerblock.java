package block;

import cl.staff.epicblocker.EpicBlocker;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import utils.ChatUtils;

public class playerblock implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event){
        Player player = event.getPlayer();
        String message = event.getMessage();

        if (message.toLowerCase().contains("aternos")){
            event.setCancelled(true);
            player.sendMessage(ChatUtils.getColoredMessage(EpicBlocker.prefix+" &cNo escribas palabras feas en el chat"));
        }
    }
}
