package me.frostythedev.oitq.cmds;

import me.frostythedev.oitq.OITQ;
import me.frostythedev.oitq.arena.Arena;
import me.frostythedev.oitq.utils.Lang;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Programmed by Tevin on 7/16/2015.
 */
public class CMD_Leave implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args.length == 0) {
                if (OITQ.getInstance().getArenaManager().isInArena(p)) {
                    Arena arena = OITQ.getInstance().getArenaManager().getArenaFromPlayer(p);
                    arena.removePlayer(p);
                } else {
                    Lang.sendMessage(p, "&c&lYou are currently not in an arena.");
                }
            }
        }
        return false;
    }

}
