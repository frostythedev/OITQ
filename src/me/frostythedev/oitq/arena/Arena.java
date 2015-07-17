package me.frostythedev.oitq.arena;

import me.frostythedev.oitq.OITQ;
import me.frostythedev.oitq.configuration.ConfigField;
import me.frostythedev.oitq.configuration.Configuration;
import me.frostythedev.oitq.runnables.GameRunnable;
import me.frostythedev.oitq.utils.Lang;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Programmed by Tevin on 7/12/2015.
 */
public class Arena {

    private int id;
    private String name;
    private ArrayList<Location> spawns;
    private Location lobby;

    private boolean started;
    private boolean full;

    private int max;

    private ArrayList<UUID> players;

    private Configuration config = new Configuration("arenas", OITQ.getInstance().getDataFolder());

    public Arena(String name) {
        if (this.config.getSection("Arenas") != null) {
            this.id = this.config.getSection("Arenas").getKeys(false).size() + 1;
        } else {
            this.id = 0;
        }
        this.name = name;
        this.spawns = new ArrayList<>();
        this.started = false;
        this.full = false;
        this.players = new ArrayList<>();
        this.max = 16;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Location> getSpawns() {
        return spawns;
    }

    public void setSpawns(ArrayList<Location> spawns) {
        this.spawns = spawns;
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public boolean isFull() {
        return full;
    }

    public void setFull(boolean full) {
        this.full = full;
    }

    public ArrayList<UUID> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<UUID> players) {
        this.players = players;
    }

    public Location getLobby() {
        return lobby;
    }

    public void setLobby(Location lobby) {
        this.lobby = lobby;
    }

    public void addSpawn(Location loc) {
        if (!getSpawns().contains(loc)) {
            getSpawns().add(loc);
        }
    }

    public int getSize() {
        return getPlayers().size();
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public void addPlayer(Player p) {
        if (!started) {
            if (!OITQ.getInstance().getArenaManager().isInArena(p)) {
                this.getPlayers().add(p.getUniqueId());
                OITQ.getInstance().getArenaManager().playerArena.put(p.getUniqueId(), this);
                Lang.sendMessage(p, "&a&lYou have joined " + name + "!");
            }
            if (getSize() < 8) {
                new BukkitRunnable() {
                    int countdown = 60;
                    @Override
                    public void run() {
                        if (countdown > 0) {
                            if (countdown % 15 == 0) {
                                Lang.broadcastArena(Arena.this, "&b&lThe game will begin in " + countdown + " seconds!");
                            } else if (countdown <= 10 && countdown > 0) {
                                Lang.broadcastArena(Arena.this, "&b&lThe game will begin in " + countdown + " seconds!");
                            }
                        } else {
                            this.cancel();
                            startGame();
                            setStarted(true);
                        }
                        countdown--;
                    }
                }.runTaskTimer(OITQ.getInstance(), 20, 20);
            }
        } else {
            Lang.sendMessage(p, "&c&lThe game has already started. Please join another game.");
        }
    }

    public boolean containsPlayer(Player p) {
        return OITQ.getInstance().getArenaManager().isInArena(p) && OITQ.getInstance().getArenaManager().getArenaFromPlayer(p) == this;
    }

    public void removePlayer(Player p) {
        if (containsPlayer(p)) {
            Lang.broadcastArena(this, "&c&l" + p.getName() + " have left the game!");
            this.getPlayers().remove(p.getUniqueId());
        }
    }

    public void startGame() {
        if (!started) {
            new GameRunnable(this);
            OITQ.getInstance().getGameManager().startGame(this);
        }
    }

    public void saveArena() {
        if (this.config.doesExists()) {
            this.config.set("Arenas." + id + ".name", ConfigField.STRING, getName());
            if (!getSpawns().isEmpty()) {
                int size;
                for (Location loc : getSpawns()) {
                    if (config.getSection("Arenas." + id + ".spawns") != null) {
                        size = config.getSection("Arenas." + id + ".spawns").getKeys(false).size() + 1;
                        this.config.set("Arenas." + id + ".spawns." + size, ConfigField.LOCATION, getSpawns().get(getSpawns().indexOf(loc)));
                    } else {
                        size = 1;
                        this.config.set("Arenas." + id + ".spawns." + size, ConfigField.LOCATION, getSpawns().get(getSpawns().indexOf(loc)));
                    }
                }
            }
        } else {
            this.config.createFile();
            saveArena();
        }
        this.config.saveFile();
    }
}
