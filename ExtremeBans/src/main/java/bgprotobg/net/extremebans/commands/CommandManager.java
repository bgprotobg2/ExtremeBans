package bgprotobg.net.extremebans.commands;

import bgprotobg.net.extremebans.ExtremeBans;

public class CommandManager {

    public CommandManager(ExtremeBans plugin) {
        plugin.getCommand("ban").setExecutor(new BanCommand());
        plugin.getCommand("tempban").setExecutor(new TempBanCommand());
        plugin.getCommand("mute").setExecutor(new MuteCommand());
        plugin.getCommand("tempmute").setExecutor(new TempMuteCommand());
        plugin.getCommand("kick").setExecutor(new KickCommand());
        plugin.getCommand("blacklist").setExecutor(new BlacklistCommand());
        plugin.getCommand("unban").setExecutor(new UnbanCommand());
        plugin.getCommand("unmute").setExecutor(new UnmuteCommand());
        plugin.getCommand("history").setExecutor(new HistoryCommand());
    }
}
