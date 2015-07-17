package me.frostythedev.oitq.runnables;

import me.frostythedev.oitq.OITQ;
import me.frostythedev.oitq.arena.Arena;
import me.frostythedev.oitq.utils.Lang;
import org.bukkit.Bukkit;

/**
 * Programmed by Tevin on 7/12/2015.
 */
public class GameRunnable implements Runnable {

    private Arena arena;

    private int ticks = 6000;
    private int task = OITQ.getInstance().getRandom().nextInt(123456789);
    private int time = 0;

    public GameRunnable(Arena arena) {
        this.arena = arena;
        task = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(OITQ.getInstance(), this, 20, 20);
        time = ticks;
    }

    @Override
    public void run() {
        if (time > 0) {
            if (time % 1000 == 0) {
                Lang.broadcastArena(arena, "&e&lThe game will end in " + time / 60 / 20 + " minutes!");
            } else if (time <= 300) {
                Lang.broadcastArena(arena, "&e&lThe game will end in " + time / 20 + " seconds!");
            }
        } else {
            Bukkit.getServer().getScheduler().cancelTask(task);
            OITQ.getInstance().getGameManager().endGame(arena);
        }
        time--;
    }

    public Arena getArena() {
        return arena;
    }

    public void setArena(Arena arena) {
        this.arena = arena;
    }

    public int getTicks() {
        return ticks;
    }

    public void setTicks(int ticks) {
        this.ticks = ticks;
    }

    public int getTask() {
        return task;
    }

    public void setTask(int task) {
        this.task = task;
    }
}
