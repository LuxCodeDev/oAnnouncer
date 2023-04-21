package es.luxcode.oannouncer.commands;

import java.io.IOException;

import es.luxcode.oannouncer.oAnnouncer;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.config.Configuration;

public class AnnouncerCommand extends Command {
    public AnnouncerCommand(String name, String permission, String ...aliases) {
        super(name, permission, aliases);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        final oAnnouncer announcer = oAnnouncer.getInstance();
        final Configuration config = announcer.getConfig();

        if (!sender.hasPermission("oannouncer.admin")) {
            sender.sendMessage(TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', config.getString("messages.permission"))));
        } else {
            try {
                announcer.reload();
                sender.sendMessage(TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', config.getString("messages.reloaded"))));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
