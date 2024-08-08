package dev.prodmarci.srvcore.modules.basicUtils;

import dev.prodmarci.srvcore.main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class entityDamageEvent implements Listener {

    @EventHandler
    void onEntityDamage(EntityDamageEvent e) {
        boolean invincible = main.getInstance().getConfig().getBoolean("modules-settings.basic-utils.invincibility");
        if (invincible) {
            e.setCancelled(true);
        }
    }
}
