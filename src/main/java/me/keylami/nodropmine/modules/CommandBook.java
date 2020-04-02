package me.keylami.nodropmine.modules;

import me.keylami.nodropmine.NoDropMine;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class CommandBook extends AbstractCommand {

    final NoDropMine instance;

    public CommandBook(NoDropMine instance) {
        super(false, "book");
        this.instance = instance;
    }

    @Override
    protected ReturnType runCommand(CommandSender sender, String... args) {
        if (args.length == 0) {
            if (sender instanceof Player) {
                ((Player) sender).getInventory().addItem(instance.enchantmentHandler.getbook());
                return ReturnType.SUCCESS;
            }
        } else if (Bukkit.getPlayerExact(args[0]) == null) {
                    sender.sendMessage("&cThat username does not exist, or the user is not online!");
            return ReturnType.FAILURE;
        } else {
            Bukkit.getPlayerExact(args[0]).getInventory().addItem(instance.enchantmentHandler.getbook());
            return ReturnType.SUCCESS;
        }
        return ReturnType.FAILURE;
    }

    @Override
    protected List<String> onTab(CommandSender sender, String... args) {
        return null;
    }

    @Override
    public String getPermissionNode() {
        return "nodropmine.admin";
    }

    @Override
    public String getSyntax() {
        return "/ndm book [player]";
    }

    @Override
    public String getDescription() {
        return "Gives Sync_Touch2 book to you or a player.";
    }

}
