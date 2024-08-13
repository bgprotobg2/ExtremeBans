package bgprotobg.net.extremebans.punishments;

import java.util.Date;

public class Punishment {

    private PunishmentType type;
    private String reason;
    private long expireTime;

    public Punishment(PunishmentType type, String reason, long expireTime) {
        this.type = type;
        this.reason = reason;
        this.expireTime = expireTime;
    }

    public PunishmentType getType() {
        return type;
    }

    public String getReason() {
        return reason;
    }

    public long getExpireTime() {
        return expireTime;
    }
    @Override
    public String toString() {
        return "Type: " + type + ", Reason: " + reason + ", Expires: " +
                (expireTime > 0 ? new Date(expireTime).toString() : "Never");
    }

}
