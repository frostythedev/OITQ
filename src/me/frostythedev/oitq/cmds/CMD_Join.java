package me.frostythedev.oitq.cmds;

import me.frostythedev.oitq.OITQ;
import me.frostythedev.oitq.utils.Lang;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Programmed by Tevin on 7/15/2015.
 */
public class CMD_Join implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args.length == 0) {
                Lang.sendMessage(p, "&cCorrect Usage: /join <name> .");
            } else if (args.length == 1) {
                if (OITQ.getInstance().getArenaManager().getArena(args[0]) != null) {
                    if (!OITQ.getInstance().getArenaManager().isInArena(p)) {
                        OITQ.getInstance().getArenaManager().getArena(args[0]).addPlayer(p);
                    } else {
                        Lang.sendMessage(p, "&c&lYou are currently in a game. Do /leave first");
                    }
                } else {
                    Lang.sendMessage(p, "&cNo arena with that name could be found.");
                }
            } else {
                Lang.sendMessage(p, "&cCorrect Usage: /join <name> .");
            }
        }
        return false;
    }
}
