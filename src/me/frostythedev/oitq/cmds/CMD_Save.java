package me.frostythedev.oitq.cmds;

import me.frostythedev.oitq.arena.Arena;
import me.frostythedev.oitq.utils.ItemBuilder;
import me.frostythedev.oitq.utils.Lang;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Programmed by Tevin on 7/12/2015.
 */
public class CMD_Save implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args.length == 0) {
                if (CMD_Create.editMap.containsKey(p.getUniqueId())) {
                    Arena arena = CMD_Create.editMap.get(p.getUniqueId());
                    arena.saveArena();
                    Lang.sendMessage(p, "&a Success! Your arena is now setup.");
                    p.getWorld().dropItemNaturally(p.getEyeLocation(), new ItemBuilder(Material.SIGN).name("&b&lPlace me!").build());
                    Lang.sendMessage(p, "&aPlace this sign on a wall to activate the wall sign.");
                } else {
                    Lang.sendMessage(p, "&cYou are currently not editing a map.");
                }
            }
        }
        return false;
    }
}
