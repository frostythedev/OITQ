package me.frostythedev.oitq.api;

import me.frostythedev.oitq.OITQ;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Programmed by Tevin on 7/17/2015.
 */
public class StatsAPI {

    public static void addKills(Player player, int amount) {
        new BukkitRunnable() {
            @Override
            public void run() {
                OITQ.getInstance().getStatsManager().addKills(player, amount);
            }
        }.runTaskAsynchronously(OITQ.getInstance());

    }

    public static void addDeaths(Player player, int amount) {
        new BukkitRunnable() {
            @Override
            public void run() {
                OITQ.getInstance().getStatsManager().addDeaths(player, amount);
            }
        }.runTaskAsynchronously(OITQ.getInstance());
    }

    public static void addKills(String name, int amount) {
        addKills(Bukkit.getPlayer(name), amount);
    }

    public static void addDeaths(String name, int amount) {
        addDeaths(Bukkit.getPlayer(name), amount);
    }

    public static void resetStats(Player p) {
        new BukkitRunnable() {
            @Override
            public void run() {
                OITQ.getInstance().getStatsManager().resetStats(p);
            }
        }.runTaskAsynchronously(OITQ.getInstance());
    }

    public static void resetStats(String name) {
        resetStats(Bukkit.getOfflinePlayer(name).getPlayer());
    }
}
