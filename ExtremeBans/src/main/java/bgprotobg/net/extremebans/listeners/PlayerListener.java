package bgprotobg.net.extremebans.listeners;

import bgprotobg.net.extremebans.ExtremeBans;
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
            String bannedMessage = ExtremeBans.getInstance().getMessage("banned_message");
            event.disallow(PlayerLoginEvent.Result.KICK_BANNED, bannedMessage);
        }
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        UUID playerUUID = event.getPlayer().getUniqueId();
        if (PunishmentManager.getInstance().isMuted(playerUUID)) {
            String mutedMessage = ExtremeBans.getInstance().getMessage("muted_message");
            event.getPlayer().sendMessage(mutedMessage);
            event.setCancelled(true);
        }
    }
}
