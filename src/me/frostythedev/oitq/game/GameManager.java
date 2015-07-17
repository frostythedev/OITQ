package me.frostythedev.oitq.game;

import me.frostythedev.oitq.OITQ;
import me.frostythedev.oitq.arena.Arena;
import me.frostythedev.oitq.utils.ItemBuilder;
import me.frostythedev.oitq.utils.Lang;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

/**
 * Programmed by Tevin on 7/12/2015.
 */
public class GameManager {

    private OITQ plugin;

    public GameManager(OITQ plugin) {
        this.plugin = plugin;
    }

    public void startGame(Arena a) {
        for (UUID uuid : a.getPlayers()) {
            Player p = Bukkit.getPlayer(uuid);
            p.getInventory().clear();
            p.getInventory().setArmorContents(null);
            p.teleport(a.getSpawns().get(a.getPlayers().indexOf(uuid)));
            p.getInventory().setItem(0, new ItemBuilder(Material.BOW).name("&a&lKatniss").build());
            p.getInventory().setItem(1, new ItemBuilder(Material.IRON_AXE).name("&a&lWeapon").build());
            p.getInventory().setItem(8, new ItemBuilder(Material.ARROW).build());
        }
        a.setStarted(true);
        Lang.broadcastArena(a, "&6&l- - - - - - - - - - - - - - -");
        Lang.broadcastArena(a, "&7&oTHE GAME HAS BEGAN");
        Lang.broadcastArena(a, "&e&lMatch Time: &f5mins");
        Lang.broadcastArena(a, "&e&oPlayers: &f" + a.getSize());
        Lang.broadcastArena(a, "&a&lGood Luck!");
        Lang.broadcastArena(a, "&6&l- - - - - - - - - - - - - - -");
    }

    public void endGame(Arena a) {
        for (UUID uuid : a.getPlayers()) {
            Player p = Bukkit.getPlayer(uuid);
            p.getInventory().clear();
            p.getInventory().setArmorContents(null);
            ItemStack games = new ItemBuilder(Material.ARROW).name("&a&lGames").build();
            ItemStack shop = new ItemBuilder(Material.EMERALD).name("&b&lCosmetic Shop").build();
            p.getInventory().setItem(0, games);
            p.getInventory().setItem(1, shop);
            p.teleport(a.getLobby());
        }
        a.getPlayers().clear();
        a.setFull(false);
        a.setStarted(false);
    }
}
