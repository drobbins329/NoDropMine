package me.keylami.nodropmine;

import me.keylami.nodropmine.modules.*;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public final class NoDropMine extends JavaPlugin implements Listener {

    //private double chestx = 99.00;
    //private double chesty = 68.00;
    //private double chestz = 95.00;
    Location chestLoc;
    private Random random;
    private static NoDropMine INSTANCE;
    public Enchantment enchantmentHandler;
    private CommandManager commandManager;

    public static NoDropMine getInstance() {
        return INSTANCE;
    }

    // private final NoDropMine instance;
    // public NoDropMine(NoDropMine instance){this.instance = instance;}


    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("NoDropMine loaded");
        getServer().getPluginManager().registerEvents(this,this);
        INSTANCE = this;
        this.enchantmentHandler = new Enchantment();
        this.commandManager = new CommandManager(this);
        this.commandManager.addCommand(new CommandNoDropMine(this))
                .addSubCommands(
                        new CommandBook(this)
                );
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new InventoryListeners(this), this);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent blockBreak){
        //getServer().broadcastMessage("Block broken");

        Location location = null;
        if (!Methods.isSync(blockBreak.getPlayer())) return;


        ItemStack tool = blockBreak.getPlayer().getInventory().getItemInHand();
        ItemMeta meta = tool.getItemMeta();
        for (String lore : meta.getLore()) {
            if (!lore.contains(Methods.formatText("&aSync_Touch2"))) continue;
            String[] loreSplit = lore.split("~");
            location = Methods.unserializeLocation(loreSplit[0].replace(ChatColor.COLOR_CHAR + "", "")
                    .replace("~", ""));
            break;
        }

        if (location == null
                || location.getBlock().getType() != Material.CHEST
                //|| blockBreak.getBlock().getType().name().contains("SHULKER")
                || blockBreak.getPlayer().getGameMode() == GameMode.CREATIVE
                || blockBreak.getBlock().getType() == Material.SPAWNER) {
            return;
        }

        this.random = new Random();
        Player player = blockBreak.getPlayer();
        Block block = blockBreak.getBlock();
        chestLoc = location;
        Chest chest = (Chest) chestLoc.getBlock().getState();
        Inventory inv = chest.getInventory();
        for(ItemStack i : block.getDrops(player.getInventory().getItemInMainHand()))
        {
            if(i != null)
            {
                if (blockBreak.getPlayer().getInventory().getItemInHand().getItemMeta().hasEnchant(org.bukkit.enchantments.Enchantment.SILK_TOUCH)) {
                    if(blockBreak.getBlock().getType() == Material.SHULKER_BOX
                        || blockBreak.getBlock().getType() == Material.BLACK_SHULKER_BOX
                        || blockBreak.getBlock().getType() == Material.BLUE_SHULKER_BOX
                        || blockBreak.getBlock().getType() == Material.BROWN_SHULKER_BOX
                        || blockBreak.getBlock().getType() == Material.CYAN_SHULKER_BOX
                        || blockBreak.getBlock().getType() == Material.GRAY_SHULKER_BOX
                        || blockBreak.getBlock().getType() == Material.GREEN_SHULKER_BOX
                            || blockBreak.getBlock().getType() == Material.LIGHT_BLUE_SHULKER_BOX
                            || blockBreak.getBlock().getType() == Material.LIGHT_GRAY_SHULKER_BOX
                            || blockBreak.getBlock().getType() == Material.LIME_SHULKER_BOX
                            || blockBreak.getBlock().getType() == Material.MAGENTA_SHULKER_BOX
                            || blockBreak.getBlock().getType() == Material.ORANGE_SHULKER_BOX
                            || blockBreak.getBlock().getType() == Material.PINK_SHULKER_BOX
                            || blockBreak.getBlock().getType() == Material.PURPLE_SHULKER_BOX
                            || blockBreak.getBlock().getType() == Material.RED_SHULKER_BOX
                            || blockBreak.getBlock().getType() == Material.WHITE_SHULKER_BOX
                            || blockBreak.getBlock().getType() == Material.YELLOW_SHULKER_BOX)
                        {

                                Map<Integer, ItemStack> notDropped = inv.addItem(i);
                                if (!notDropped.isEmpty()){
                                    blockBreak.getPlayer().sendMessage("Your synced chest is full!");
                                    blockBreak.getPlayer().getWorld().dropItemNaturally(blockBreak.getBlock().getLocation(), i);}


                        }
                    else{

                            Map<Integer, ItemStack> notDropped = inv.addItem(new ItemStack(blockBreak.getBlock().getType(), 1));
                        if (!notDropped.isEmpty()){
                            blockBreak.getPlayer().getWorld().dropItemNaturally(blockBreak.getBlock().getLocation(), new ItemStack(blockBreak.getBlock().getType(), 1));
                            blockBreak.getPlayer().sendMessage("Your synced chest is full!");
                        }


                    }
                }
                else{
                    if (player.getInventory().getItemInHand().getItemMeta().hasEnchant(org.bukkit.enchantments.Enchantment.LOOT_BONUS_BLOCKS)) {
                        int level = player.getInventory().getItemInHand().getItemMeta().getEnchantLevel(org.bukkit.enchantments.Enchantment.LOOT_BONUS_BLOCKS);
                        int dropAmount = calculateFortuneDrops(blockBreak.getBlock().getType(), level, random);
                        Map<Integer, ItemStack> notDropped = null;
                        for (int j = 0; j < dropAmount; j++) {

                                notDropped = inv.addItem(i);

                                if (!notDropped.isEmpty()){
                                    location.getWorld().dropItemNaturally(blockBreak.getBlock().getLocation(), new ArrayList<>(notDropped.values()).get(0));
                                    blockBreak.getPlayer().sendMessage("Your synced chest is full!");}

                        }
                    } else {

                            Map<Integer, ItemStack> notDropped = inv.addItem(i);
                            if (!notDropped.isEmpty()){
                                location.getWorld().dropItemNaturally(blockBreak.getBlock().getLocation(), new ArrayList<>(notDropped.values()).get(0));blockBreak.getPlayer().sendMessage("Your synced chest is full!");}

                    }
                }
            }
        }
        blockBreak.setDropItems(false);
        blockBreak.getPlayer().getItemInHand().setDurability((short) (blockBreak.getPlayer().getItemInHand().getDurability() + 1));
        if (blockBreak.getPlayer().getItemInHand().getDurability() >= blockBreak.getPlayer().getItemInHand().getType().getMaxDurability()) {
            blockBreak.getPlayer().getItemInHand().setType(null);
        }
        if (blockBreak.getExpToDrop() > 0)
            blockBreak.getPlayer().getWorld().spawn(blockBreak.getBlock().getLocation(), ExperienceOrb.class).setExperience(blockBreak.getExpToDrop());
        blockBreak.getBlock().setType(Material.AIR);
    }

    private int calculateFortuneDrops(Material material, int level, Random random) {
        if (material != Material.COAL_ORE
                && material != Material.DIAMOND_ORE
                && material != Material.EMERALD_ORE
                && material != Material.NETHER_QUARTZ_ORE
                && material != Material.LAPIS_ORE) return 1;
        if (level <= 0) return 1;
        int drops = random.nextInt(level + 2) - 1;
        if (drops < 0) drops = 0;
        return applyLapisDrops(material, random) * (drops + 1);
    }

    private int applyLapisDrops(Material material, Random random) {
        return material == Material.LAPIS_ORE ? 4 + random.nextInt(5) : 1;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("NoDropMine unloaded");
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBlockInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction() != Action.LEFT_CLICK_BLOCK
                || event.getClickedBlock() == null
                || player.isSneaking()
                || !player.hasPermission("nodropmine.overview")
                || !(event.getClickedBlock().getState() instanceof InventoryHolder || event.getClickedBlock().getType().equals(Material.ENDER_CHEST))) {
            return;
        }

        if (event.getClickedBlock().getType() == Material.CHEST && Methods.isSync(player)) {
            ItemStack item = event.getPlayer().getInventory().getItemInHand();
            boolean isLinked = false;

            for (String lore : item.getItemMeta().getLore()) {
                if (!lore.contains(Methods.formatText("&aSync_Touch2"))) continue;
                isLinked = true;
                break;
            }

            if (isLinked) {
                event.getPlayer().sendMessage("Chest unsynced");
                getInstance().enchantmentHandler.createSyncTouch(item, null);
            } else {
                event.getPlayer().sendMessage("Chest synced");
                getInstance().enchantmentHandler.createSyncTouch(item, null);
                getInstance().enchantmentHandler.createSyncTouch(item, event.getClickedBlock());
            }
            event.setCancelled(true);
            return;
        }
    }

}
