package dev.prodmarci.srvcore.modules.simpleSpawn;

import dev.prodmarci.srvcore.main;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;

public class joinSpawnEvent implements Listener {
    main mainClass;
    public joinSpawnEvent(main m) {this.mainClass = m;}

    // Deletes default join message
    @EventHandler
    void spawnOnFirstJoin(PlayerSpawnLocationEvent e) {

        // Get player instance
        Player p = e.getPlayer();

        // Get spawn location
        Location spawnlocation = p.getWorld().getSpawnLocation();

        boolean lobby = mainClass.getConfig().getBoolean("modules-settings.simple-spawn.tp-on-join");

        // If the server is a lobby
        if (lobby) {
            e.setSpawnLocation(spawnlocation);
            p.teleport(spawnlocation);
        } else {
            // If player never played before teleport to spawn
            if (!p.hasPlayedBefore()) {
                e.setSpawnLocation(spawnlocation);
                p.teleport(spawnlocation);
            }
        }
    }
}
