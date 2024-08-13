package bgprotobg.net.extremebans.commands;

import bgprotobg.net.extremebans.punishments.Punishment;
import bgprotobg.net.extremebans.punishments.PunishmentManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class HistoryCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1) {
            sender.sendMessage("Usage: /history <player>");
            return false;
        }
        String playerName = args[0];
        List<Punishment> history = PunishmentManager.getInstance().getPunishmentHistory(playerName);
        if (history.isEmpty()) {
            sender.sendMessage("No history found for player: " + playerName);
        } else {
            sender.sendMessage("Punishment history for player: " + playerName);
            for (Punishment punishment : history) {
                sender.sendMessage(punishment.toString());
            }
        }
        return true;
    }
}
