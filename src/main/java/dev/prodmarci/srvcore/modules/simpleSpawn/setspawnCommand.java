package dev.prodmarci.srvcore.modules.simpleSpawn;

import dev.prodmarci.srvcore.main;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class setspawnCommand implements CommandExecutor {

    main mainClass;

    public setspawnCommand(main m) {
        this.mainClass = m;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        // Checks if sender is a player also gets a player object
        if (sender instanceof Player p) {

            // Get player location, yaw and pitch
            int Xlocation = p.getLocation().getBlockX();
            int Ylocation = p.getLocation().getBlockY();
            int Zlocation = p.getLocation().getBlockZ();
            float yaw = p.getLocation().getYaw();
            float pitch = p.getLocation().getPitch();

            // Get message from config and replace %coords% for actual coords
            String coords = Xlocation + ", " + Ylocation + ", " + Zlocation;
            String spawn_set_msg = mainClass.getConfig().getString("modules-settings.simple-spawn.messages.spawn-set");
            assert spawn_set_msg != null;
            spawn_set_msg = spawn_set_msg.replace("%coords%", coords);

            // Set spawn location
            Location spawnlocation = new Location(p.getWorld(), Xlocation, Ylocation, Zlocation, yaw, pitch);
            p.getWorld().setSpawnLocation(spawnlocation);

            // Send player a message
            mainClass.playerStateLogger(p, spawn_set_msg);
        }
        return true;
    }
}
