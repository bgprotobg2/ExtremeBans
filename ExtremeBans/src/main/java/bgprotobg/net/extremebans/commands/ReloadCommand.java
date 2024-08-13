package bgprotobg.net.extremebans.commands;

import bgprotobg.net.extremebans.ExtremeBans;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

public class ReloadCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            Plugin plugin = ExtremeBans.getInstance();
            plugin.reloadConfig();
            sender.sendMessage(ChatColor.YELLOW + "ExtremeBans configuration reloaded successfully.");
            return true;
        }
        sender.sendMessage("Usage: /extremebans reload");
        return false;
    }
}
