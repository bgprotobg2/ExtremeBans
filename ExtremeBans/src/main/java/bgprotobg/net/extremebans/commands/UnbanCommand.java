package bgprotobg.net.extremebans.commands;

import bgprotobg.net.extremebans.ExtremeBans;
import bgprotobg.net.extremebans.punishments.PunishmentManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class UnbanCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1) {
            sender.sendMessage("Usage: /unban <player>");
            return false;
        }
        String playerName = args[0];
        PunishmentManager.getInstance().unbanPlayer(playerName);
        String message = ExtremeBans.getInstance().getMessage("unban");
        message = message.replace("{player}", playerName);
        sender.sendMessage(message);
        return true;
    }
}
