package dev.prodmarci.srvcore.modules.basicUtils;

import dev.prodmarci.srvcore.main;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class gamemodeCommand implements CommandExecutor {
    main mainClass;
    public gamemodeCommand(main m) {this.mainClass = m;}

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        // If player is a sender
        if (args.length == 2) {

            // Get player instance
            Player player = (Player) Bukkit.getPlayer(args[1]);
            assert player != null;

            String gamemode = args[0];
            setGamemode(sender, player, gamemode);
        } else if (args.length == 1 && sender instanceof Player p) {

            String gamemode = args[0];
            setGamemode(p, p, gamemode);
        }
        return true;
    }

    void setGamemode(CommandSender sender, Player target, String gamemode) {
        // Gets messages from config
        String gamemode_set_msg = mainClass.getConfig().getString("modules-settings.basic-utils.messages.gamemode.someone");
        String gamemode_set_yourself_msg = mainClass.getConfig().getString("modules-settings.basic-utils.messages.gamemode.yourself");
        String not_exist_msg = mainClass.getConfig().getString("modules-settings.basic-utils.messages.gamemode.not-exist");
        assert gamemode_set_msg != null; assert not_exist_msg != null; assert gamemode_set_yourself_msg != null;
        gamemode_set_msg = gamemode_set_msg.replace("%player%", target.getName());

        switch (gamemode) {
            case "0":
            case "s":
            case "survival":
                target.setGameMode(GameMode.SURVIVAL);
                gamemode_set_msg = gamemode_set_msg.replace("%gamemode%", "Survival");
                gamemode_set_yourself_msg = gamemode_set_yourself_msg.replace("%gamemode%", "Survival");
                if (sender instanceof Player p) {
                    if (p == target.getPlayer()) {
                        mainClass.playerStateLogger(p, gamemode_set_yourself_msg);
                    } else {
                        mainClass.playerStateLogger(p, gamemode_set_msg);
                    }
                } else {mainClass.stateLogger(gamemode_set_msg, true);}
                break;
            case "1":
            case "c":
            case "creative":
                target.setGameMode(GameMode.CREATIVE);
                gamemode_set_msg = gamemode_set_msg.replace("%gamemode%", "Creative");
                gamemode_set_yourself_msg = gamemode_set_yourself_msg.replace("%gamemode%", "Creative");
                if (sender instanceof Player p) {
                    if (p == target.getPlayer()) {
                        mainClass.playerStateLogger(p, gamemode_set_yourself_msg);
                    } else {
                        mainClass.playerStateLogger(p, gamemode_set_msg);
                    }
                } else {mainClass.stateLogger(gamemode_set_msg, true);}
                break;
            case "2":
            case "adventure":
                target.setGameMode(GameMode.ADVENTURE);
                gamemode_set_msg = gamemode_set_msg.replace("%gamemode%", "Adventure");
                gamemode_set_yourself_msg = gamemode_set_yourself_msg.replace("%gamemode%", "Adventure");
                if (sender instanceof Player p) {
                    if (p == target.getPlayer()) {
                        mainClass.playerStateLogger(p, gamemode_set_yourself_msg);
                    } else {
                        mainClass.playerStateLogger(p, gamemode_set_msg);
                    }
                } else {mainClass.stateLogger(gamemode_set_msg, true);}
                break;
            case "3":
            case "spectator":
                target.setGameMode(GameMode.SPECTATOR);
                gamemode_set_msg = gamemode_set_msg.replace("%gamemode%", "Spectator");
                gamemode_set_yourself_msg = gamemode_set_yourself_msg.replace("%gamemode%", "Spectator");
                if (sender instanceof Player p) {
                    if (p == target.getPlayer()) {
                        mainClass.playerStateLogger(p, gamemode_set_yourself_msg);
                    } else {
                        mainClass.playerStateLogger(p, gamemode_set_msg);
                    }
                } else {mainClass.stateLogger(gamemode_set_msg, true);}
                break;
            default:
                if (sender instanceof Player p) {
                    mainClass.playerStateLogger(p, not_exist_msg);
                } else {mainClass.stateLogger(not_exist_msg, true);}
                break;
        }
    }
}
