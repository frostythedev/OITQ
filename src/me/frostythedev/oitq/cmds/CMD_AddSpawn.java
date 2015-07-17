package me.frostythedev.oitq.cmds;

import me.frostythedev.oitq.arena.Arena;
import me.frostythedev.oitq.utils.Lang;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Programmed by Tevin on 7/12/2015.
 */
public class CMD_AddSpawn implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args.length == 0) {
                if (CMD_Create.editMap.containsKey(p.getUniqueId())) {
                    Arena arena = CMD_Create.editMap.get(p.getUniqueId());
                    arena.addSpawn(p.getLocation());
                    Lang.sendMessage(p, "&a Success! Spawnpoint added successfully.");
                    Lang.sendMessage(p, "&a If you are finished you may not setup the lobby for the arena via /setlobby.");
                } else {
                    Lang.sendMessage(p, "&cYou are currently not editing a map.");
                }
            }
        }
        return false;
    }
}