package me.frostythedev.oitq.utils;

import me.frostythedev.oitq.OITQ;
import me.frostythedev.oitq.arena.Arena;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Programmed by Tevin on 7/12/2015.
 */
public class Lang {

    public static String PREFIX = "&7[&eOITQ&7] ";

    public static void sendMessage(Player p, String msg) {
        p.sendMessage(OITQ.getInstance().colorize(PREFIX + msg));
    }

    public static void broadcastArena(Arena arena, String msg) {
        for (UUID uuid : arena.getPlayers()) {
            sendMessage(Bukkit.getPlayer(uuid), msg);
        }
    }

    public static void broadcastServer(String msg) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            sendMessage(p, msg);
        }
    }
}
