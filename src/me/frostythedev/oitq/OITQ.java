package me.frostythedev.oitq;

import me.frostythedev.oitq.arena.ArenaManager;
import me.frostythedev.oitq.cmds.*;
import me.frostythedev.oitq.configuration.Configuration;
import me.frostythedev.oitq.events.PlayerEvent;
import me.frostythedev.oitq.events.UtilEvent;
import me.frostythedev.oitq.game.GameManager;
import me.frostythedev.oitq.sql.SQLite;
import me.frostythedev.oitq.utils.Utils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Random;

/**
 * Programmed by Tevin on 7/12/2015.
 */
public class OITQ extends JavaPlugin {

    private static OITQ instance;

    public static OITQ getInstance() {
        return instance;
    }

    private ArenaManager arenaManager;
    private GameManager gameManager;
    private Random random;

    @Override
    public void onEnable() {
        instance = this;

        if (!new File(getDataFolder(), "config.yml").exists()) {
            saveDefaultConfig();
        }

        if (!new File(getDataFolder(), "database.sqlite3").exists()) {
            SQLite.createTable("stats", "`uuid` TEXT NOT NULL, `kills` INTEGER NOT NULL, `deaths` INTEGER NOT NULL");
        }

        random = new Random();

        arenaManager = new ArenaManager(this);
        gameManager = new GameManager(this);

        getServer().getPluginManager().registerEvents(new PlayerEvent(), this);
        getServer().getPluginManager().registerEvents(new UtilEvent(), this);

        getCommand("createarena").setExecutor(new CMD_Create());
        getCommand("removearena").setExecutor(new CMD_Remove());
        getCommand("savearena").setExecutor(new CMD_Save());
        getCommand("setlobby").setExecutor(new CMD_SetLobby());
        getCommand("addspawn").setExecutor(new CMD_AddSpawn());
        getCommand("join").setExecutor(new CMD_Join());
        getCommand("leave").setExecutor(new CMD_Leave());
        getCommand("stats").setExecutor(new CMD_Stats());

        Utils.setStatsLocation(Configuration.deseraliseLocation(getConfig().getString("stats-location")));
    }

    public String colorize(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public ArenaManager getArenaManager() {
        return arenaManager;
    }

    public GameManager getGameManager() {
        return gameManager;
    }

    public Random getRandom() {
        return random;
    }
}
