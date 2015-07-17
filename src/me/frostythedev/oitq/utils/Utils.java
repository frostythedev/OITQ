package me.frostythedev.oitq.utils;

import me.frostythedev.oitq.OITQ;
import me.frostythedev.oitq.sql.SQLite;
import net.minecraft.server.v1_8_R2.EntityArmorStand;
import net.minecraft.server.v1_8_R2.PacketPlayOutSpawnEntityLiving;
import net.minecraft.server.v1_8_R2.WorldServer;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Programmed by Tevin on 7/12/2015.
 */
public class Utils {

    private static Location statsLocation;

    public static boolean isNull(Object... objects) {
        for (Object object : objects) {
            if (object == null) {
                return true;
            }
        }
        return false;
    }

    private static void spawnPPH(Location loc, Player p, String text) {
        WorldServer s = ((CraftWorld) loc.getWorld()).getHandle();
        EntityArmorStand stand = new EntityArmorStand(s);

        stand.setLocation(loc.getX(), loc.getY(), loc.getZ(), 0, 0);
        stand.setCustomName(OITQ.getInstance().colorize(text));
        stand.setCustomNameVisible(true);
        stand.setGravity(true);
        stand.setInvisible(true);
        PacketPlayOutSpawnEntityLiving packet = new PacketPlayOutSpawnEntityLiving(stand);
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
    }

    public static void buildStats(Player player) {
        if (getStatsLocation() != null) {
            ResultSet rs = SQLite.query("SELECT kills,deaths FROM `stats` WHERE uuid = '" + player.getUniqueId().toString() + "'");
            try {
                if (rs != null) {
                    spawnPPH(getStatsLocation().add(0, -3, 0), player, "&7&l- - - - - &a&lStats &7&l- - - - -");
                    spawnPPH(getStatsLocation().add(0, -3.31, 0), player, "&7&lName: &a" + player.getName());
                    spawnPPH(getStatsLocation().add(0, -3.62, 0), player, "&7&lKills: &a" + rs.getInt("kills"));
                    spawnPPH(getStatsLocation().add(0, -3.93, 0), player, "&7&lDeaths: &a" + rs.getInt("deaths"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static Location getStatsLocation() {
        return statsLocation;
    }

    public static void setStatsLocation(Location statsLocation) {
        Utils.statsLocation = statsLocation;
    }
}
