package me.frostythedev.oitq.managers;

import me.frostythedev.oitq.OITQ;
import me.frostythedev.oitq.sql.SQLite;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Programmed by Tevin on 7/17/2015.
 */
public class StatsManager {

    private OITQ plugin;

    public StatsManager(OITQ plugin) {
        this.plugin = plugin;
    }

    public void addKills(Player p, int amount) {
        ResultSet rs = SQLite.query("SELECT kills FROM `stats` WHERE uuid = '" + p.getUniqueId().toString() + "'");
        try {
            if (rs != null) {
                amount = rs.getInt("kills") + amount;
                SQLite.updateTable("stats", "kills", amount, "uuid", p.getUniqueId().toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addDeaths(Player p, int amount) {
        ResultSet rs = SQLite.query("SELECT deaths FROM `stats` WHERE uuid = '" + p.getUniqueId().toString() + "'");
        try {
            if (rs != null) {
                amount = rs.getInt("deaths") + amount;
                SQLite.updateTable("stats", "deaths", amount, "uuid", p.getUniqueId().toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getKills(Player p) {
        ResultSet rs = SQLite.query("SELECT kills FROM `stats` WHERE uuid = '" + p.getUniqueId().toString() + "'");
        try {
            if (rs != null) {
                return rs.getInt("kills");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getDeaths(Player p) {
        ResultSet rs = SQLite.query("SELECT deaths FROM `stats` WHERE uuid = '" + p.getUniqueId().toString() + "'");
        try {
            if (rs != null) {
                return rs.getInt("deaths");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void resetStats(Player p) {
        SQLite.updateTable("stats", "kills", 0, "uuid", p.getUniqueId().toString());
        SQLite.updateTable("stats", "deaths", 0, "uuid", p.getUniqueId().toString());
    }
}
