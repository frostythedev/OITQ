package me.frostythedev.oitq.arena;

import me.frostythedev.oitq.OITQ;
import me.frostythedev.oitq.configuration.Configuration;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Programmed by Tevin on 7/12/2015.
 */
public class ArenaManager {

    private OITQ plugin;

    public ArenaManager(OITQ plugin) {
        this.plugin = plugin;
    }

    private ArrayList<Arena> loadedArenas = new ArrayList<>();

    public Map<UUID, Arena> playerArena = new HashMap<>();

    public Arena getArena(int id) {
        for (Arena arena : getLoadedArenas()) {
            if (arena.getId() == id) return arena;
        }
        return null;
    }

    public Arena getArena(String name) {
        for (Arena arena : getLoadedArenas()) {
            if (arena.getName().equalsIgnoreCase(name)) return arena;
        }
        return null;
    }

    public Arena getArenaFromPlayer(Player p) {
        return playerArena.get(p.getUniqueId());
    }

    public boolean deleteArena(String name) {
        Configuration config = new Configuration("arenas", plugin.getDataFolder());
        if (config.doesExists()) {
            config.loadFile();
            if (config.getSection("Arenas." + name) != null) {
                try {
                    config.set("Arenas." + name, null);
                    config.saveFile();
                    return true;
                } catch (NullPointerException e) {
                    Bukkit.getLogger().warning("Arena " + name + " could not be found.");
                    return false;
                }
            }
        }
        return false;
    }

    public boolean isInArena(Player p) {
        return getArenaFromPlayer(p) != null;
    }

    public Arena getSmallestArena() {
        Arena smallest = null;
        int size = 99999;
        for (Arena a : getLoadedArenas()) {
            if (a.getSize() < size) {
                size = a.getSize();
                smallest = a;
            }
        }
        return smallest;
    }

    public void loadArenas() {
        getLoadedArenas().clear();
        Configuration config = new Configuration("arenas", plugin.getDataFolder());
        if (config.doesExists()) {
            config.loadFile();
            if (config.getSection("Arenas") != null) {
                for (String id : config.getSection("Arenas").getKeys(false)) {
                    Arena arena = new Arena(config.getString("Arenas." + id + ".name"));
                    for (String locs : config.getSection("Arenas." + id + ".spawns").getKeys(false)) {
                        Location s = config.getLocation("Arenas." + id + ".spawns." + locs);
                        if (!arena.getSpawns().contains(s)) {
                            arena.getSpawns().add(s);
                        }
                    }
                    if (!getLoadedArenas().contains(arena)) {
                        getLoadedArenas().add(arena);
                    }
                }
            }
        }
    }

    public ArrayList<Arena> getLoadedArenas() {
        return loadedArenas;
    }
}
