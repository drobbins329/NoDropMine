package me.keylami.nodropmine.modules;

import me.keylami.nodropmine.modules.Methods;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Enchantment {

    public ItemStack createSyncTouch(ItemStack item, Block block) {
        ItemMeta itemmeta = item.getItemMeta();
        List<String> lore = itemmeta.hasLore() ? itemmeta.getLore() : new ArrayList<>();

        for (String str : lore) {
            if (!str.contains("Sync_Touch2")) continue;
            lore.remove(str);
            break;
        }

        if (block != null) {
            lore.add(Methods.convertToInvisibleString(Methods.serializeLocation(block) + "~")
                    + Methods.formatText("&aSync_Touch2"));
        } else {
            lore.add(Methods.formatText("&7Sync_Touch2"));
        }
        itemmeta.setLore(lore);
        item.setItemMeta(itemmeta);
        return item;
    }

    public ItemStack getbook() {
        ItemStack book = new ItemStack(Material.ENCHANTED_BOOK);
        ItemMeta meta = book.getItemMeta();
        meta.setDisplayName(Methods.formatText("&eEnchanted Book"));

        ArrayList<String> lore = new ArrayList<>();
        lore.add(Methods.formatText("&7Sync_Touch2"));
        meta.setLore(lore);
        book.setItemMeta(meta);
        return book;
    }

}
