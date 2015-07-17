package me.frostythedev.oitq.cmds;

import me.frostythedev.oitq.arena.Arena;
import me.frostythedev.oitq.utils.Lang;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Programmed by Tevin on 7/12/2015.
 */
public class CMD_Create implements CommandExecutor {

    public static Map<UUID, Arena> editMap = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args.length == 0) {
                Lang.sendMessage(p, "&cCorrect Usage: /createarena <name> .");
            } else if (args.length == 1) {
                if (!editMap.containsKey(p.getUniqueId())) {
                    Arena arena = new Arena(args[0]);
                    editMap.put(p.getUniqueId(), arena);
                    Lang.sendMessage(p, "&aSucessfully created map! Now to add the spawnpoints.");
                    Lang.sendMessage(p, "&aUse /addspawn to add a spawn to the map.");
                } else {
                    Lang.sendMessage(p, "You are already creating a map right now!");
                }
            }

        }
        return false;
    }
}
