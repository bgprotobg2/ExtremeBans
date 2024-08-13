package bgprotobg.net.extremebans.commands;

import bgprotobg.net.extremebans.punishments.PunishmentManager;
import bgprotobg.net.extremebans.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class TempMuteCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 3) {
            sender.sendMessage("Usage: /tempmute <player> <time> <reason>");
            return false;
        }
        String playerName = args[0];
        long duration = Utils.parseDuration(args[1]);
        String reason = String.join(" ", java.util.Arrays.copyOfRange(args, 2, args.length));
        PunishmentManager.getInstance().tempMutePlayer(playerName, duration, reason);
        sender.sendMessage("Player " + playerName + " has been temporarily muted for: " + reason);
        return true;
    }
}
