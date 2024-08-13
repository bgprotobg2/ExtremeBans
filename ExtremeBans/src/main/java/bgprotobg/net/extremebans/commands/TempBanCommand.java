package bgprotobg.net.extremebans.commands;

import bgprotobg.net.extremebans.ExtremeBans;
import bgprotobg.net.extremebans.punishments.PunishmentManager;
import bgprotobg.net.extremebans.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class TempBanCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 3) {
            sender.sendMessage("Usage: /tempban <player> <time> <reason>");
            return false;
        }
        String playerName = args[0];
        long duration = Utils.parseDuration(args[1]);
        String reason = String.join(" ", java.util.Arrays.copyOfRange(args, 2, args.length));
        PunishmentManager.getInstance().tempBanPlayer(playerName, duration, reason);
        String message = ExtremeBans.getInstance().getMessage("tempban");
        message = message.replace("{player}", playerName).replace("{reason}", reason);
        sender.sendMessage(message);
        return true;
    }
}
