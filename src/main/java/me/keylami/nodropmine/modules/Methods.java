package me.keylami.nodropmine.modules;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Methods {

    private static final Map<String, Location> serializeCache = new HashMap<>();

    public static boolean isSync(Player p) {
        if (p.getItemInHand().hasItemMeta()
                && p.getItemInHand().getType() != Material.AIR
                && p.getItemInHand().getType() != Material.ENCHANTED_BOOK
                && p.getItemInHand().getItemMeta().hasLore()) {
            for (String str : p.getItemInHand().getItemMeta().getLore()) {
                if (str.contains(Methods.formatText("&7Sync_Touch2")) || str.contains(Methods.formatText("&aSync_Touch2"))) {
                    return true;
                }
            }
        }
        return false;
    }

    public static Location unserializeLocation(String str) {
        if (str == null || str.equals(""))
            return null;
        if (serializeCache.containsKey(str)) {
            return serializeCache.get(str).clone();
        }
        String cacheKey = str;
        str = str.replace("y:", ":").replace("z:", ":").replace("w:", "").replace("x:", ":").replace("/", ".");
        List<String> args = Arrays.asList(str.split("\\s*:\\s*"));

        World world = Bukkit.getWorld(args.get(0));
        double x = Double.parseDouble(args.get(1)), y = Double.parseDouble(args.get(2)), z = Double.parseDouble(args.get(3));
        Location location = new Location(world, x, y, z, 0, 0);
        serializeCache.put(cacheKey, location.clone());
        return location;
    }

    public static String convertToInvisibleString(String s) {
        if (s == null || s.equals(""))
            return "";
        StringBuilder hidden = new StringBuilder();
        for (char c : s.toCharArray()) hidden.append(ChatColor.COLOR_CHAR + "").append(c);
        return hidden.toString();
    }

    public static String serializeLocation(Location location) {
        if (location == null || location.getWorld() == null)
            return "";
        String w = location.getWorld().getName();
        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();
        String str = w + ":" + x + ":" + y + ":" + z;
        str = str.replace(".0", "").replace(".", "/");
        return str;
    }

    public static String serializeLocation(Block b) {
        if (b == null)
            return "";
        return serializeLocation(b.getLocation());
    }

    public static String formatText(String text) {
        if (text == null || text.equals(""))
            return "";
        return formatText(text, false);
    }

    public static String formatText(String text, boolean cap) {
        if (text == null || text.equals(""))
            return "";
        if (cap)
            text = text.substring(0, 1).toUpperCase() + text.substring(1);
        return ChatColor.translateAlternateColorCodes('&', text);
    }

}
