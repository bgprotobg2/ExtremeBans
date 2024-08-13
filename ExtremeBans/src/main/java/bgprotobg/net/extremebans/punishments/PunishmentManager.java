package bgprotobg.net.extremebans.punishments;

import bgprotobg.net.extremebans.database.PunishmentDAO;
import bgprotobg.net.extremebans.database.SQLiteDatabase;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PunishmentManager {

    private static PunishmentManager instance;

    private Map<UUID, Punishment> punishments = new HashMap<>();
    private final PunishmentDAO punishmentDAO;

    private PunishmentManager() {
        SQLiteDatabase database = SQLiteDatabase.getInstance();
        this.punishmentDAO = new PunishmentDAO(database);
    }

    public static synchronized PunishmentManager getInstance() {
        if (instance == null) {
            instance = new PunishmentManager();
        }
        return instance;
    }

    public void banPlayer(String playerName, String reason) {
        UUID playerUUID = Bukkit.getOfflinePlayer(playerName).getUniqueId();
        Punishment punishment = new Punishment(PunishmentType.BAN, reason, -1);
        punishments.put(playerUUID, punishment);
        punishmentDAO.savePunishment(playerUUID, punishment);
        Player player = Bukkit.getPlayer(playerName);
        if (player != null) {
            player.kickPlayer("You have been banned: " + reason);
        }
    }

    public void tempBanPlayer(String playerName, long duration, String reason) {
        UUID playerUUID = Bukkit.getOfflinePlayer(playerName).getUniqueId();
        Punishment punishment = new Punishment(PunishmentType.TEMPBAN, reason, System.currentTimeMillis() + duration);

        punishments.put(playerUUID, punishment);
        punishmentDAO.savePunishment(playerUUID, punishment);

        Player player = Bukkit.getPlayer(playerName);
        if (player != null) {
            player.kickPlayer("You have been temporarily banned for: " + reason);
        }
    }

    public void mutePlayer(String playerName, String reason) {
        UUID playerUUID = Bukkit.getOfflinePlayer(playerName).getUniqueId();
        Punishment punishment = new Punishment(PunishmentType.MUTE, reason, -1);

        punishments.put(playerUUID, punishment);
        punishmentDAO.savePunishment(playerUUID, punishment);
    }

    public void tempMutePlayer(String playerName, long duration, String reason) {
        UUID playerUUID = Bukkit.getOfflinePlayer(playerName).getUniqueId();
        Punishment punishment = new Punishment(PunishmentType.TEMPMUTE, reason, System.currentTimeMillis() + duration);

        punishments.put(playerUUID, punishment);
        punishmentDAO.savePunishment(playerUUID, punishment);
    }

    public void kickPlayer(String playerName, String reason) {
        Player player = Bukkit.getPlayer(playerName);
        if (player != null) {
            player.kickPlayer("You have been kicked: " + reason);
        }
    }

    public void blacklistPlayer(String playerName, String reason) {
        UUID playerUUID = Bukkit.getOfflinePlayer(playerName).getUniqueId();
        Punishment punishment = new Punishment(PunishmentType.BLACKLIST, reason, -1);

        punishments.put(playerUUID, punishment);
        punishmentDAO.savePunishment(playerUUID, punishment);

        Player player = Bukkit.getPlayer(playerName);
        if (player != null) {
            player.kickPlayer("You have been blacklisted: " + reason);
        }
    }

    public void unbanPlayer(String playerName) {
        UUID playerUUID = Bukkit.getOfflinePlayer(playerName).getUniqueId();
        punishments.remove(playerUUID);
        punishmentDAO.removePunishment(playerUUID);
    }

    public void unmutePlayer(String playerName) {
        UUID playerUUID = Bukkit.getOfflinePlayer(playerName).getUniqueId();
        punishments.remove(playerUUID);
        punishmentDAO.removePunishment(playerUUID);
    }

    public boolean isBanned(UUID playerUUID) {
        Punishment punishment = punishments.get(playerUUID);
        if (punishment != null && (punishment.getType() == PunishmentType.BAN || punishment.getType() == PunishmentType.TEMPBAN)) {
            if (punishment.getType() == PunishmentType.TEMPBAN && punishment.getExpireTime() > 0 && System.currentTimeMillis() > punishment.getExpireTime()) {
                punishments.remove(playerUUID);
                punishmentDAO.removePunishment(playerUUID);
                return false;
            }
            return true;
        }
        return false;
    }

    public boolean isMuted(UUID playerUUID) {
        Punishment punishment = punishments.get(playerUUID);
        if (punishment != null && (punishment.getType() == PunishmentType.MUTE || punishment.getType() == PunishmentType.TEMPMUTE)) {
            if (punishment.getType() == PunishmentType.TEMPMUTE && punishment.getExpireTime() > 0 && System.currentTimeMillis() > punishment.getExpireTime()) {
                punishments.remove(playerUUID);
                punishmentDAO.removePunishment(playerUUID);
                return false;
            }
            return true;
        }
        return false;
    }

    public List<Punishment> getPunishmentHistory(String playerName) {
        UUID playerUUID = Bukkit.getOfflinePlayer(playerName).getUniqueId();
        return punishmentDAO.getPunishmentHistory(playerUUID);
    }

    public void saveAllPunishments() {
        punishmentDAO.saveAllPunishments(punishments);
    }
    public void initialize() {
        punishments = punishmentDAO.loadPunishments();
    }
}
