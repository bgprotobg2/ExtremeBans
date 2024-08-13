package bgprotobg.net.extremebans.commands;

import bgprotobg.net.extremebans.punishments.PunishmentManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class UnmuteCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1) {
            sender.sendMessage("Usage: /unmute <player>");
            return false;
        }
        String playerName = args[0];
        PunishmentManager.getInstance().unmutePlayer(playerName);
        sender.sendMessage("Player " + playerName + " has been unmuted.");
        return true;
    }
}
