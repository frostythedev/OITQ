package me.frostythedev.oitq.configuration;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Programmed by Tevin on 7/5/2015.
 */
public class Configuration {

    private String name;
    private File folder;

    private File file;
    private YamlConfiguration config;
    private boolean exists;

    public Configuration(String name, File folder) {
        this.name = name;
        this.folder = folder;
        this.file = new File(folder, name + ".yml");
        this.config = YamlConfiguration.loadConfiguration(this.file);
    }

    public void set(String path, ConfigField field, Object object) {
        switch (field) {
            case STRING:
                this.config.set(path, String.valueOf(object));
                break;
            case INTEGER:
            case BOOLEAN:
            case DOUBLE:
            case SHORT:
                this.config.set(path, object);
                break;
            case CUSTOM:
            case DEFAULT:
                this.config.set(path, object);
                break;
        }
    }

    public void set(String path, Object object) {
        set(path, ConfigField.DEFAULT, object);
    }

    public void set(String path, ConfigField field, ItemStack stack) {
        switch (field) {
            case ITEMSTACK:
                this.config.set(path, seraliseItemStack(stack));
                break;
        }
        saveFile();
    }

    public void set(String path, ConfigField field, Location loc) {
        switch (field) {
            case LOCATION:
                this.config.set(path, seraliseLocation(loc));
                break;
        }
    }

    public Object retrieve(String path, ConfigField field) {
        switch (field) {
            case STRING:
                return this.config.getString(path);
            case INTEGER:
                return this.config.getInt(path);
            case BOOLEAN:
                return this.config.getBoolean(path);
            case SHORT:
                return this.config.get(path);
            case ITEMSTACK:
                return deseraliseItemStack(this.config.getString(path));
            case LOCATION:
                return deseraliseLocation(this.config.getString(path));
            case CUSTOM:
                this.config.get(path);
                break;
        }
        return null;
    }

    public String getString(String path) {
        return this.config.getString(path);
    }

    public int getInt(String path) {
        return this.config.getInt(path);
    }

    public boolean getBoolean(String path) {
        return this.config.getBoolean(path);
    }

    public ItemStack getItemStack(String path) {
        return deseraliseItemStack(getString(path));
    }

    public Location getLocation(String path) {
        return deseraliseLocation(getString(path));
    }

    public Object getObject(String path) {
        return this.config.get(path);
    }

    public void createSection(String path) {
        this.config.createSection(path);
    }


    public void saveFile() {
        try {
            this.config.save(this.file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean doesExists() {
        return this.file.exists();
    }

    public void createFile() {
        try {
            if (this.file.createNewFile()) {
                loadFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadFile() {
        try {
            this.config.load(this.file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public File getFolder() {
        return folder;
    }

    public void setFolder(File folder) {
        this.folder = folder;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public boolean isExists() {
        return exists;
    }

    public void setExists(boolean exists) {
        this.exists = exists;
    }

    public ConfigurationSection getSection(String path) {
        if (this.config.isConfigurationSection(path)) return this.config.getConfigurationSection(path);
        return null;
    }

    public static String seraliseItemStack(ItemStack stack) {
        String item = "";
        ItemMeta meta = stack.getItemMeta();
        if (meta.hasDisplayName()) {
            item += "@name:" + meta.getDisplayName();
        }
        item += ";@type:" + stack.getType().toString();
        item += ";@data:" + stack.getData().getData();
        if (meta.hasLore()) {
            for (String s : meta.getLore()) {
                item += ";@lore:" + s;
            }
        }
        if (meta.hasEnchants()) {
            for (Map.Entry<Enchantment, Integer> ench : meta.getEnchants().entrySet()) {
                item += ";@enchant:" + ench.getKey() + ":" + ench.getValue();
            }
        }
        return item;
    }

    public static ItemStack deseraliseItemStack(String s) {
        ItemStack stack = new ItemStack(Material.AIR);
        ItemMeta meta = stack.getItemMeta();
        List<String> lore = new ArrayList<>();
        String[] att = s.split(";");
        for (String attribute : att) {
            String[] split = attribute.split(":");
            if (split[0].equalsIgnoreCase("@name")) {
                meta.setDisplayName(split[1]);
            }
            if (split[0].equalsIgnoreCase("@type")) {
                stack.setType(Material.getMaterial(split[1]));
            }
            if (split[0].equalsIgnoreCase("@enchant")) {
                stack.addEnchantment(Enchantment.getByName(split[1]), Integer.parseInt(split[2]));
            }
            if (split[0].equalsIgnoreCase("@lore")) {
                lore.add(split[1]);
            }
            if (split[0].equalsIgnoreCase("@data")) {
                stack.getData().setData(Byte.parseByte(split[1]));
            }
        }
        meta.setLore(lore);
        stack.setItemMeta(meta);
        return stack;
    }

    public static String seraliseLocation(Location loc) {
        return "@W:" + loc.getWorld().getName() + ";@X:" + loc.getBlockX() + ";@Y:" + loc.getBlockY() + ";@Z:" + loc.getBlockZ()
                + ";@P:" + loc.getPitch() + ";@YA:" + loc.getYaw();
    }

    public static Location deseraliseLocation(String path) {
        String[] prop = path.split(";");
        Location loc = new Location(Bukkit.getWorld("world"), 0, 0, 0);
        for (String detail : prop) {
            String[] cords = detail.split(":");
            if (cords[0].equalsIgnoreCase("@W")) {
                loc.setWorld(Bukkit.getWorld(cords[1]));
            }
            if (cords[0].equalsIgnoreCase("@X")) {
                loc.setX(Double.parseDouble(cords[1]));
            }
            if (cords[0].equalsIgnoreCase("@Y")) {
                loc.setY(Double.parseDouble(cords[1]));
            }
            if (cords[0].equalsIgnoreCase("@Z")) {
                loc.setZ(Double.parseDouble(cords[1]));
            }
            if (cords[0].equalsIgnoreCase("@P")) {
                loc.setPitch(Float.parseFloat(cords[1]));
            }
            if (cords[0].equalsIgnoreCase("@YA")) {
                loc.setYaw(Float.parseFloat(cords[1]));
            }
        }
        return loc;
    }
}