package me.frostythedev.oitq.events;

import me.frostythedev.oitq.OITQ;
import me.frostythedev.oitq.api.StatsAPI;
import me.frostythedev.oitq.arena.Arena;
import me.frostythedev.oitq.sql.SQLite;
import me.frostythedev.oitq.utils.ItemBuilder;
import me.frostythedev.oitq.utils.Lang;
import me.frostythedev.oitq.utils.Utils;
import net.minecraft.server.v1_8_R2.PacketPlayInClientCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Programmed by Tevin on 7/12/2015.
 */
public class PlayerEvent implements Listener {

    Inventory inv = Bukkit.createInventory(null, 54, "Available Games");

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        e.setJoinMessage(null);
        e.getPlayer().getInventory().clear();
        e.getPlayer().getInventory().setArmorContents(null);

        ResultSet rs = SQLite.query("SELECT kills,deaths FROM `stats` WHERE uuid = '" + e.getPlayer().getUniqueId().toString() + "'");

        if (rs != null) {
            try {
                if (!rs.next()) {
                    SQLite.executeStatement("INSERT INTO `stats` (`uuid`,`kills`,`deaths`) VALUES ('"
                            + e.getPlayer().getUniqueId().toString() + "',0,0)");
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }

        Utils.buildStats(e.getPlayer());

        if (OITQ.getInstance().getArenaManager().getArenaFromPlayer(e.getPlayer()) == null) {
            if (!e.getPlayer().hasPlayedBefore()) {
                Lang.broadcastServer(e.getPlayer().getName() + " &ehas joined the server.");
            }
            ItemStack games = new ItemBuilder(Material.ARROW).name("&a&lGames").build();
            ItemStack shop = new ItemBuilder(Material.EMERALD).name("&b&lCosmetic Shop").build();
            e.getPlayer().getInventory().setItem(0, games);
            e.getPlayer().getInventory().setItem(1, shop);
        } else {
            OITQ.getInstance().getArenaManager().getArenaFromPlayer(e.getPlayer()).removePlayer(e.getPlayer());
            onJoin(e);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        e.setQuitMessage(null);
        if (OITQ.getInstance().getArenaManager().getArenaFromPlayer(e.getPlayer()) != null) {
            OITQ.getInstance().getArenaManager().getArenaFromPlayer(e.getPlayer()).removePlayer(e.getPlayer());
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        ((CraftPlayer) e.getEntity()).getHandle().playerConnection.a(new PacketPlayInClientCommand(PacketPlayInClientCommand.EnumClientCommand.PERFORM_RESPAWN));
        StatsAPI.addDeaths(e.getEntity(), 1);
        if (e.getEntity().getKiller() != null) {
            StatsAPI.addKills(e.getEntity().getKiller(), 1);
            e.getEntity().getKiller().getInventory().addItem(new ItemStack(Material.ARROW));
        }
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        Player p = e.getPlayer();
        if (OITQ.getInstance().getArenaManager().getArenaFromPlayer(p) != null) {
            Arena arena = OITQ.getInstance().getArenaManager().getArenaFromPlayer(p);
            if (arena.isStarted()) {
                p.getInventory().setItem(0, new ItemBuilder(Material.BOW).name("&a&lKatniss").build());
                p.getInventory().setItem(1, new ItemBuilder(Material.IRON_AXE).name("&a&lWeapon").build());
                p.getInventory().setItem(8, new ItemBuilder(Material.ARROW).build());
                p.teleport(arena.getSpawns().get(OITQ.getInstance().getRandom().nextInt(arena.getSpawns().size())));
            }
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        if (OITQ.getInstance().getArenaManager().isInArena(e.getPlayer())) {
            e.setCancelled(true);
        }
        if (!e.getPlayer().isOp()) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPickup(PlayerPickupItemEvent e) {
        if (OITQ.getInstance().getArenaManager().isInArena(e.getPlayer())) {
            e.setCancelled(true);
        }
        if (!e.getPlayer().isOp()) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (e.getPlayer().getItemInHand() == null) {
            return;
        }
        if (!e.getPlayer().getItemInHand().hasItemMeta()) {
            return;
        }
        String name = ChatColor.stripColor(e.getPlayer().getItemInHand().getItemMeta().getDisplayName());
        switch (e.getPlayer().getItemInHand().getType()) {
            case ARROW:
                if (name.equalsIgnoreCase("Games")) {
                    OITQ.getInstance().getArenaManager().loadArenas();
                    inv.clear();
                    for (Arena arena : OITQ.getInstance().getArenaManager().getLoadedArenas()) {
                        int space = OITQ.getInstance().getArenaManager().getLoadedArenas().indexOf(arena);
                        if (!arena.isStarted()) {
                            inv.setItem(space, new ItemBuilder(Material.STAINED_CLAY).durability(DyeColor.WHITE.getData()).name("&a&nArena #" + space).lore(" ")
                                    .lore("&b&l- - - - - - - -")
                                    .lore(" ")
                                    .lore(" ")
                                    .lore("&6Game Status: &2&lLOBBY")
                                    .lore("&6Map: &a" + arena.getName())
                                    .lore(" ")
                                    .lore("&dThanks for playing!")
                                    .lore(" ")
                                    .lore("&b&l- - - - - - - -")
                                    .build());
                        } else {
                            int slot = inv.getSize() - space;
                            inv.setItem(slot, new ItemBuilder(Material.STAINED_CLAY).durability(DyeColor.RED.getData()).name("&c&nArena #" + slot).lore(" ")
                                    .lore("&b&l- - - - - - - -")
                                    .lore(" ")
                                    .lore(" ")
                                    .lore("&6Game Status: &c&lINGAME")
                                    .lore("&6Map: &a" + arena.getName())
                                    .lore(" ")
                                    .lore("&dThanks for playing!")
                                    .lore(" ")
                                    .lore("&b&l- - - - - - - -")
                                    .build());
                        }
                    }
                    e.getPlayer().openInventory(inv);
                }
        }
    }
}
