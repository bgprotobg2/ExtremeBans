package bgprotobg.net.extremebans.database;

import bgprotobg.net.extremebans.punishments.Punishment;
import bgprotobg.net.extremebans.punishments.PunishmentType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class PunishmentDAO {

    private final SQLiteDatabase database;

    public PunishmentDAO(SQLiteDatabase database) {
        this.database = database;
    }

    public void savePunishment(UUID playerUUID, Punishment punishment) {
        String insertSQL = "REPLACE INTO punishments (uuid, type, reason, expire_time) VALUES (?, ?, ?, ?)";

        try (Connection conn = database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {

            if (conn == null || conn.isClosed()) {
                throw new SQLException("Database connection is closed.");
            }

            pstmt.setString(1, playerUUID.toString());
            pstmt.setString(2, punishment.getType().name());
            pstmt.setString(3, punishment.getReason());
            pstmt.setLong(4, punishment.getExpireTime());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void removePunishment(UUID playerUUID) {
        String deleteSQL = "DELETE FROM punishments WHERE uuid = ?";

        try (Connection conn = database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(deleteSQL)) {

            pstmt.setString(1, playerUUID.toString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Map<UUID, Punishment> loadPunishments() {
        Map<UUID, Punishment> punishments = new HashMap<>();
        String query = "SELECT * FROM punishments";

        try (Connection conn = database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                UUID uuid = UUID.fromString(rs.getString("uuid"));
                PunishmentType type = PunishmentType.valueOf(rs.getString("type"));
                String reason = rs.getString("reason");
                long expireTime = rs.getLong("expire_time");

                punishments.put(uuid, new Punishment(type, reason, expireTime));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return punishments;
    }

    public void saveAllPunishments(Map<UUID, Punishment> punishments) {
        for (Map.Entry<UUID, Punishment> entry : punishments.entrySet()) {
            savePunishment(entry.getKey(), entry.getValue());
        }
    }
    public List<Punishment> getPunishmentHistory(UUID playerUUID) {
        List<Punishment> punishments = new ArrayList<>();
        String query = "SELECT * FROM punishments WHERE uuid = ?";

        try (Connection conn = database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, playerUUID.toString());
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    PunishmentType type = PunishmentType.valueOf(rs.getString("type"));
                    String reason = rs.getString("reason");
                    long expireTime = rs.getLong("expire_time");

                    punishments.add(new Punishment(type, reason, expireTime));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return punishments;
    }

}
