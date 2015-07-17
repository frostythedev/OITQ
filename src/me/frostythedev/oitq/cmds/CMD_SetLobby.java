package me.frostythedev.oitq.cmds;

import me.frostythedev.oitq.arena.Arena;
import me.frostythedev.oitq.utils.Lang;
import me.frostythedev.oitq.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Programmed by Tevin on 7/12/2015.
 */
public class CMD_SetLobby implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args.length == 0) {
                if (CMD_Create.editMap.containsKey(p.getUniqueId())) {
                    Arena arena = CMD_Create.editMap.get(p.getUniqueId());
                    arena.setLobby(p.getLocation());
                    Lang.sendMessage(p, "&a Success! You have sucessfully setup the lobby for this map.");
                    if (!Utils.isNull(arena.getLobby(), arena.getSpawns())) {
                        Lang.sendMessage(p, "&a Now that you're done execute /savearena to complete the tutorial!");
                    } else {
                        Lang.sendMessage(p, "&6It looks like you've missed something in the tutorial. Please ensure you setup everything correctly.");
                    }
                } else {
                    Lang.sendMessage(p, "&cYou are currently not editing a map.");
                }
            }
        }
        return false;
    }
}
