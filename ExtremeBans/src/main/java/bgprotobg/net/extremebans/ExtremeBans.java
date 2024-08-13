package bgprotobg.net.extremebans;

import bgprotobg.net.extremebans.database.SQLiteDatabase;
import bgprotobg.net.extremebans.listeners.PlayerListener;
import bgprotobg.net.extremebans.punishments.PunishmentManager;
import org.bukkit.plugin.java.JavaPlugin;
import bgprotobg.net.extremebans.commands.CommandManager;

public final class ExtremeBans extends JavaPlugin {

    private static ExtremeBans instance;
    private PunishmentManager punishmentManager;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        SQLiteDatabase db = SQLiteDatabase.getInstance();
        punishmentManager = PunishmentManager.getInstance();
        new CommandManager(this);
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        punishmentManager.initialize();
    }

    @Override
    public void onDisable() {
        if (punishmentManager != null) {
            punishmentManager.saveAllPunishments();
        }
        SQLiteDatabase.getInstance().closeConnection();
    }

    public static ExtremeBans getInstance() {
        return instance;
    }
    public PunishmentManager getPunishmentManager() {
        return punishmentManager;
    }
}
