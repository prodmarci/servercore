package dev.prodmarci.srvcore.modules.simpleSpawn;

import dev.prodmarci.srvcore.main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class spawnCommand implements CommandExecutor, Listener {
    main mainClass;

    public spawnCommand(main m) {
        this.mainClass = m;
    }

    public List<UUID> playersWithTimer = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        // Checks if sender is a player also gets a player object
        if (sender instanceof Player p) {

            // Get spawn location
            Location spawnlocation = p.getWorld().getSpawnLocation();

            // Gets teleport delay
            int spawn_delay = mainClass.getConfig().getInt("modules-settings.simple-spawn.teleport-delay");

            // Gets message and replaces %time_left% for actual time left
            String teleport_msg = mainClass.getConfig().getString("modules-settings.simple-spawn.messages.teleport");
            String teleport_success_msg = mainClass.getConfig().getString("modules-settings.simple-spawn.messages.teleport-success");
            assert teleport_msg != null;
            teleport_msg = teleport_msg.replace("%time_left%", String.valueOf(spawn_delay));

            // Gets type
            String type = mainClass.getConfig().getString("modules-settings.simple-spawn.type");
            assert type != null;

            if (!(spawn_delay == 0)) {
                // Starts teleportation countdown and sends the first teleport message.
                if (!playersWithTimer.contains(p.getUniqueId())) {
                    playersWithTimer.add(p.getUniqueId());
                    startTimer(p, spawnlocation);
                    switch (type) {
                        case "message":
                            mainClass.playerStateLogger(p, teleport_msg);
                            break;
                        case "actionbar":
                            mainClass.playerActionbarLogger(p, teleport_msg);
                            break;
                    }
                }
            } else {
                p.teleport(spawnlocation);
                switch (type) {
                    case "message":
                        mainClass.playerStateLogger(p, teleport_success_msg);
                        break;
                    case "actionbar":
                        mainClass.playerActionbarLogger(p, teleport_success_msg);
                        break;
                }
            }
        }
        return true;
    }

    private BukkitTask task;
    private int count;

    public void startTimer(Player p, Location spawnLocation) {

        // Gets a teleport delay
        int spawn_delay = mainClass.getConfig().getInt("modules-settings.simple-spawn.teleport-delay");

        // Gets type
        String type = mainClass.getConfig().getString("modules-settings.simple-spawn.type");
        assert type != null;

        // Get player location
        Location oldLocation = p.getLocation();
        int oldX = oldLocation.getBlockX();
        int oldY = oldLocation.getBlockY();
        int oldZ = oldLocation.getBlockZ();
        count = spawn_delay;

        // Checks if player has not arleady started spawn tp

        // Creates a repeating task for countdown
        task = Bukkit.getScheduler().runTaskTimer(mainClass, () -> {

            // Get player new location
            Location newLocation = p.getLocation();
            int newX = newLocation.getBlockX();
            int newY = newLocation.getBlockY();
            int newZ = newLocation.getBlockZ();

            // Get teleport message and replaces %time_left% for an actual time left
            String teleport_msg = mainClass.getConfig().getString("modules-settings.simple-spawn.messages.teleport");
            assert teleport_msg != null;
            teleport_msg = teleport_msg.replace("%time_left%", String.valueOf(count));

            // Get the fail and success messages
            String teleport_fail_msg = mainClass.getConfig().getString("modules-settings.simple-spawn.messages.teleport-failed");
            String teleport_success_msg = mainClass.getConfig().getString("modules-settings.simple-spawn.messages.teleport-success");

            // Compare command start location with a new location
            if (oldX == newX && oldY == newY && oldZ == newZ) {

                // If the countdown ended
                if (count == 0) {

                    // Teleports player, sends success message and exits countdown loop.
                    p.teleport(spawnLocation);
                    switch (type) {
                        case "message":
                            mainClass.playerStateLogger(p, teleport_success_msg);
                            break;
                        case "actionbar":
                            mainClass.playerActionbarLogger(p, teleport_success_msg);
                            break;
                    }
                    playersWithTimer.remove(p.getUniqueId());
                    task.cancel();
                } else if (count != spawn_delay) {

                    // Sends countdown message
                    switch (type) {
                        case "message":
                            mainClass.playerStateLogger(p, teleport_msg);
                            break;
                        case "actionbar":
                            mainClass.playerActionbarLogger(p, teleport_msg);
                            break;
                    }
                }
            } else {

                // Cancels countdown, sends teleport fail message
                switch (type) {
                    case "message":
                        mainClass.playerStateLogger(p, teleport_fail_msg);
                        break;
                    case "actionbar":
                        mainClass.playerActionbarLogger(p, teleport_fail_msg);
                        break;
                }
                playersWithTimer.remove(p.getUniqueId());
                task.cancel();
            }
            count--;
        }, 20, 20);
    }
}
