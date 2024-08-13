package bgprotobg.net.extremebans.commands;

import bgprotobg.net.extremebans.ExtremeBans;
import bgprotobg.net.extremebans.punishments.PunishmentManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class MuteCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 2) {
            sender.sendMessage("Usage: /mute <player> <reason>");
            return false;
        }
        String playerName = args[0];
        String reason = String.join(" ", java.util.Arrays.copyOfRange(args, 1, args.length));
        PunishmentManager.getInstance().mutePlayer(playerName, reason);
        String message = ExtremeBans.getInstance().getMessage("mute");
        message = message.replace("{player}", playerName).replace("{reason}", reason);
        sender.sendMessage(message);
        return true;
    }
}
