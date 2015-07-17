package me.frostythedev.oitq.cmds;

import me.frostythedev.oitq.OITQ;
import me.frostythedev.oitq.configuration.Configuration;
import me.frostythedev.oitq.sql.SQLite;
import me.frostythedev.oitq.utils.Lang;
import me.frostythedev.oitq.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Programmed by Tevin on 7/16/2015.
 */
public class CMD_Stats implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args.length == 0) {
                ResultSet rs = SQLite.query("SELECT kills,deaths FROM `stats` WHERE uuid = '" + p.getUniqueId().toString() + "'");
                try {
                    if (rs != null) {
                        Lang.sendMessage(p, "&7&l- - - - - &a&lStats &7&l- - - - -");
                        Lang.sendMessage(p, "&7&lKills: &a" + rs.getInt("kills"));
                        Lang.sendMessage(p, "&7&lDeaths: &a" + rs.getInt("deaths"));
                        rs.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else if (args.length == 1 && p.hasPermission("oitq.admin")) {
                if (args[0].equalsIgnoreCase("setloc")) {
                    OITQ.getInstance().getConfig().set("stats-location", Configuration.seraliseLocation(p.getLocation()));
                    OITQ.getInstance().saveConfig();
                    Utils.setStatsLocation(Configuration.deseraliseLocation(OITQ.getInstance().getConfig().getString("stats-location")));
                    Lang.sendMessage(p, "&6&lStats Location have been set to your location.");
                }
            }
        }
        return false;
    }

}
