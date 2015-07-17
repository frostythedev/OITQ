package me.frostythedev.oitq.cmds;

import me.frostythedev.oitq.OITQ;
import me.frostythedev.oitq.arena.Arena;
import me.frostythedev.oitq.utils.ItemBuilder;
import me.frostythedev.oitq.utils.Lang;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

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
                    p.getInventory().clear();
                    p.getInventory().setArmorContents(null);
                    p.teleport(arena.getLobby());
                    ItemStack games = new ItemBuilder(Material.ARROW).name("&a&lGames").build();
                    ItemStack shop = new ItemBuilder(Material.EMERALD).name("&b&lCosmetic Shop").build();
                    p.getInventory().setItem(0, games);
                    p.getInventory().setItem(1, shop);
                } else {
                    Lang.sendMessage(p, "&c&lYou are currently not in an arena.");
                }
            }
        }
        return false;
    }

}
