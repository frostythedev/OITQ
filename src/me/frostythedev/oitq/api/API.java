package me.frostythedev.oitq.api;

import me.frostythedev.oitq.arena.Arena;

/**
 * Programmed by Tevin on 7/17/2015.
 */
public class API {

    public static Arena createArena(String name, int size) {
        Arena arena = new Arena(name);
        arena.setMax(size);
        return arena;
    }
}
