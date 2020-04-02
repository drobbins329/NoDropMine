package me.keylami.nodropmine.modules;

import me.keylami.nodropmine.NoDropMine;
import me.keylami.nodropmine.modules.CommandManager;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.List;

public class CommandNoDropMine extends AbstractCommand {

    final NoDropMine instance;

    public CommandNoDropMine(NoDropMine instance) {
        super(false, "NoDropMine");
        this.instance = instance;
    }

    @Override
    protected AbstractCommand.ReturnType runCommand(CommandSender sender, String... args) {
        sender.sendMessage("");
        sender.sendMessage("&7Version " + instance.getDescription().getVersion()
                + " Created with <3 by &5&l&oSongoda, re-write by KeylAmi");

        for (AbstractCommand command : instance.getCommandManager().getAllCommands()) {
            if (command.getPermissionNode() == null || sender.hasPermission(command.getPermissionNode())) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8 - &a" + command.getSyntax() + "&7 - " + command.getDescription()));
            }
        }
        sender.sendMessage("");

        return ReturnType.SUCCESS;
    }

    @Override
    protected List<String> onTab(CommandSender sender, String... args) {
        return null;
    }

    @Override
    public String getPermissionNode() {
        return null;
    }

    @Override
    public String getSyntax() {
        return "/NoDropMine";
    }

    @Override
    public String getDescription() {
        return "Displays this page.";
    }
}
