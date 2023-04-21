package es.luxcode.oannouncer;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import es.luxcode.oannouncer.commands.AnnouncerCommand;
import es.luxcode.oannouncer.config.BungeeConfiguration;
import es.luxcode.oannouncer.tasks.AnnouncerTask;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;

public class oAnnouncer extends Plugin {
    private Configuration config;

    public Configuration getConfig() {
        return config;
    }

    public void unload() {
        final ProxyServer proxyServer = this.getProxy();

        proxyServer.getPluginManager().unregisterCommands(this);
        proxyServer.getScheduler().cancel(this);
    }

    public void load() throws IOException {
        final ProxyServer proxyServer = this.getProxy();
        final BungeeConfiguration bungeeConfiguration = new BungeeConfiguration(this, "config.yml");

        bungeeConfiguration.saveDefaults();
        config = bungeeConfiguration.load();

        proxyServer.getPluginManager().registerCommand(this,
                new AnnouncerCommand("oannouncer", "oannouncer.admin", ""));

        final long interval = config.getInt("interval");
        final List<String> announcements = config.getStringList("announcements");

        proxyServer.getScheduler().schedule(this, new AnnouncerTask(announcements), interval, interval,
                TimeUnit.SECONDS);
    }

    public void reload() throws IOException {
        unload();
        load();
    }

    @Override
    public void onEnable() {
        oAnnouncer.instance = this;

        try {
            load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static oAnnouncer instance;

    public static oAnnouncer getInstance() {
        return oAnnouncer.instance;
    }
}