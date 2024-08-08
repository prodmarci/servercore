package dev.prodmarci.srvcore;

import dev.prodmarci.srvcore.modules.basicUtils.*;
import dev.prodmarci.srvcore.modules.customHolo.hologramCommand;
import dev.prodmarci.srvcore.modules.customMotd.setMotdEvent;
import dev.prodmarci.srvcore.modules.customScoreboard.models.scoreboardModel;
import dev.prodmarci.srvcore.modules.simpleSpawn.joinSpawnEvent;
import dev.prodmarci.srvcore.modules.simpleSpawn.onRespawnEvent;
import dev.prodmarci.srvcore.modules.simpleSpawn.setspawnCommand;
import dev.prodmarci.srvcore.modules.simpleSpawn.spawnCommand;
import dev.prodmarci.srvcore.modules.welcomeMessage.joinMessageEvent;
import dev.prodmarci.srvcore.modules.welcomeMessage.leaveMessageEvent;
import dev.prodmarci.srvcore.utilities.models.schedulerModel;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

import static dev.prodmarci.srvcore.utilities.colorizeText.colorize;

public final class main extends JavaPlugin implements Listener {
    public void stateLogger(String message, Boolean success) {
        String plugin_prefix = this.getConfig().getString("general-settings.plugin-prefix");
        assert plugin_prefix != null;
        plugin_prefix = ChatColor.translateAlternateColorCodes('&', plugin_prefix);
        message = colorize(message);
        if (success) {
            getServer().getConsoleSender().sendMessage(plugin_prefix + ChatColor.GRAY + ": " + ChatColor.GREEN + message);
        } else {
            getServer().getConsoleSender().sendMessage(plugin_prefix + ChatColor.GRAY + ": " + ChatColor.RED + message);
        }
    }

    public void playerStateLogger(Player p, String message) {
        String server_name = this.getConfig().getString("general-settings.server-name");
        String plugin_suffix = this.getConfig().getString("general-settings.plugin-suffix");
        assert server_name != null;
        assert plugin_suffix != null;
        server_name = colorize(server_name);
        message = colorize(message);
        plugin_suffix = colorize(plugin_suffix);
        p.sendMessage(server_name + " " + plugin_suffix + ChatColor.WHITE + " " + message);
    }

    public void playerTitleLogger(Player p, String message) {
        String server_name = this.getConfig().getString("general-settings.server-name");
        assert server_name != null;
        server_name = colorize(server_name);
        message = colorize(message);
        p.sendTitle(server_name, message, 1, 60, 1);
    }

    public void playerActionbarLogger(Player p, String message) {
        message = colorize(message);
        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(message));
    }

    public void configExist() {
        File configFile = new File(this.getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            stateLogger("Config file not found! Creating a new one...", false);
        } else {
            stateLogger("Loading config files...", true);
        }
        stateLogger("Config successfully loaded!", true);
        this.saveDefaultConfig();
    }

    private schedulerModel.Task scoreboardTask;

    @Override
    public void onEnable() {
        stateLogger("Initializing...", true);
        configExist();
        Bukkit.getServer().getPluginManager().registerEvents(this, this);

        // Enables welcome message module according to config.yml
        boolean welcomeMessage = this.getConfig().getBoolean("modules-enabled.welcome-message");
        if (welcomeMessage) {
            Bukkit.getServer().getPluginManager().registerEvents(new joinMessageEvent(this), this);
            Bukkit.getServer().getPluginManager().registerEvents(new leaveMessageEvent(this), this);
            stateLogger("Module Welcome Message is enabled!", true);
        }

        // Enables custom motd module according to config.yml
        boolean customMotd = this.getConfig().getBoolean("modules-enabled.custom-motd");
        if (customMotd) {
            Bukkit.getServer().getPluginManager().registerEvents(new setMotdEvent(this), this);
            stateLogger("Module Custom MOTD is enabled!", true);
        }

        // Enables basicUtils module according to config.yml
        boolean basicUtils = this.getConfig().getBoolean("modules-enabled.basic-utils");
        if (basicUtils) {
            getCommand("feed").setExecutor(new feedCommand(this));
            getCommand("heal").setExecutor(new healCommand(this));
            getCommand("fly").setExecutor(new flyCommand(this));
            getCommand("gamemode").setExecutor(new gamemodeCommand(this));
            Bukkit.getServer().getPluginManager().registerEvents(new entityDamageEvent(), this);
            Bukkit.getServer().getPluginManager().registerEvents(new hungerChangeEvent(), this);
            stateLogger("Module Basic Utils is enabled!", true);
        }

        // Enables simpleSpawn module according to config.yml
        boolean simpleSpawn = this.getConfig().getBoolean("modules-enabled.simple-spawn");
        if (simpleSpawn) {
            Bukkit.getServer().getPluginManager().registerEvents(new onRespawnEvent(this), this);
            Bukkit.getServer().getPluginManager().registerEvents(new joinSpawnEvent(this), this);
            getCommand("spawn").setExecutor(new spawnCommand(this));
            getCommand("setspawn").setExecutor(new setspawnCommand(this));
            stateLogger("Module Simple Spawn is enabled!", true);
        }

        boolean customHolo = this.getConfig().getBoolean("modules-enabled.custom-holo");
        if (customHolo) {
            getCommand("hologram").setExecutor(new hologramCommand(this));
            stateLogger("Module Custom Holo is enabled!", true);
        }

        scoreboardTask = schedulerModel.runTimer(scoreboardModel.getInstance(), 0, 2*20);
    }

    public static main getInstance() {
        return getPlugin(main.class);
    }

    @Override
    public void onDisable() {
        stateLogger("Shutting down...", false);
        if (scoreboardTask != null) {
            scoreboardTask.cancel();
        }
    }
}
