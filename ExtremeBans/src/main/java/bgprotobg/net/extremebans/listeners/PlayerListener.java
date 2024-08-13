package bgprotobg.net.extremebans.listeners;

import bgprotobg.net.extremebans.punishments.PunishmentManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerLoginEvent;

import java.util.UUID;

public class PlayerListener implements Listener {

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
        UUID playerUUID = event.getPlayer().getUniqueId();
        if (PunishmentManager.getInstance().isBanned(playerUUID)) {
            event.disallow(PlayerLoginEvent.Result.KICK_BANNED, "You are banned from this server.");
        }
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        UUID playerUUID = event.getPlayer().getUniqueId();
        if (PunishmentManager.getInstance().isMuted(playerUUID)) {
            event.getPlayer().sendMessage("You are muted and cannot speak.");
            event.setCancelled(true);
        }
    }
}
