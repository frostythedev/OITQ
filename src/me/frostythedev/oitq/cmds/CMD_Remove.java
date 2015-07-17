package me.frostythedev.oitq.cmds;

import me.frostythedev.oitq.OITQ;
import me.frostythedev.oitq.utils.Lang;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Programmed by Tevin on 7/12/2015.
 */
public class CMD_Remove implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args.length == 0) {
                Lang.sendMessage(p, "&cCorrect Usage: /removearena <name> .");
            } else if (args.length == 1) {
                if (OITQ.getInstance().getArenaManager().deleteArena(args[0])) {
                    Lang.sendMessage(p, "&aSucess! Arena has been deleted sucessfully.");
                } else {
                    Lang.sendMessage(p, "&cError while trying to delete arena " + args[0] + ".");
                }
            }
        }
        return false;
    }

}
