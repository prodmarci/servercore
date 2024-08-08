package dev.prodmarci.srvcore.modules.simpleSpawn;

import dev.prodmarci.srvcore.main;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class onRespawnEvent implements Listener {

    main mainClass;
    public onRespawnEvent(main m) {this.mainClass = m;}

    // Respawns on exact spawn location
    @EventHandler
    void respawnOnSpawn(PlayerRespawnEvent e) {

        // Get player instance
        Player p = e.getPlayer();

        // Get spawn location
        Location spawnlocation = p.getWorld().getSpawnLocation();
        e.setRespawnLocation(spawnlocation);
    }
}
