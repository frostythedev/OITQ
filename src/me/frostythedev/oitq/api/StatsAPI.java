package me.frostythedev.oitq.api;

import me.frostythedev.oitq.OITQ;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Programmed by Tevin on 7/17/2015.
 */
public class StatsAPI {

    public static void addKills(Player player, int amount) {
        OITQ.getInstance().getStatsManager().addKills(player, amount);
    }

    public static void addDeaths(Player player, int amount) {
        OITQ.getInstance().getStatsManager().addDeaths(player, amount);
    }

    public static void addKills(String name, int amount) {
        OITQ.getInstance().getStatsManager().addKills(Bukkit.getPlayer(name), amount);
    }

    public static void addDeaths(String name, int amount) {
        OITQ.getInstance().getStatsManager().addDeaths(Bukkit.getPlayer(name), amount);
    }

    public static void resetStats(Player p) {
        OITQ.getInstance().getStatsManager().resetStats(p);
    }

    public static void resetStats(String name) {
        OITQ.getInstance().getStatsManager().resetStats(Bukkit.getPlayer(name));
    }
}
