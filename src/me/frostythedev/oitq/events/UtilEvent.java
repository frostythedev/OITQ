package me.frostythedev.oitq.events;

import me.frostythedev.oitq.OITQ;
import me.frostythedev.oitq.arena.Arena;
import me.frostythedev.oitq.cmds.CMD_Create;
import me.frostythedev.oitq.utils.Lang;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Programmed by Tevin on 7/12/2015.
 */
public class UtilEvent implements Listener {


    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if (e.getInventory().getName().equalsIgnoreCase("Available Games")) {
            e.setCancelled(true);
            if (e.getCurrentItem() != null) {
                String name = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
                if (OITQ.getInstance().getArenaManager().getArena(name) != null) {
                    OITQ.getInstance().getArenaManager().getArena(name).addPlayer(p);
                }
            }
        }
    }

    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Block b = e.getClickedBlock();
            if (e.getPlayer().getItemInHand().getType() == Material.SIGN) {
                if (CMD_Create.editMap.containsKey(p.getUniqueId())) {
                    Arena arena = CMD_Create.editMap.get(p.getUniqueId());
                    b.setType(Material.WALL_SIGN);
                    Sign sign = (Sign) b.getState();
                    sign.setLine(0, "§a[OITQ]");
                    sign.setLine(1, "§n" + arena.getName());
                    sign.setLine(2, "§b" + arena.getSize() + "/4");
                    if (!arena.isStarted()) {
                        sign.setLine(3, "§2§lLOBBY");
                    } else {
                        sign.setLine(3, "§c§lINGAME");
                    }
                    CMD_Create.editMap.remove(p.getUniqueId());
                    sign.update(true);
                    Lang.sendMessage(p, "&aArena configuration complete!");
                }
            }
        }
    }

    @EventHandler
    public void on(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Arrow) {
            Arrow a = (Arrow) e.getEntity();
            if (a.getShooter() instanceof Player) {
                e.setDamage(100);
            }
        }
    }
}
