package bgprotobg.net.extremebans.utils;

public class Utils {

    public static long parseDuration(String time) {
        char unit = time.charAt(time.length() - 1);
        long duration = Long.parseLong(time.substring(0, time.length() - 1));
        switch (unit) {
            case 'd':
                return duration * 86400000L;
            case 'h':
                return duration * 3600000L;
            case 'm':
                return duration * 60000L;
            case 's':
                return duration * 1000L;
            default:
                throw new IllegalArgumentException("Invalid time unit");
        }
    }
}
