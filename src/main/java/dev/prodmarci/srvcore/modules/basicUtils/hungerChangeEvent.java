package dev.prodmarci.srvcore.modules.basicUtils;

import dev.prodmarci.srvcore.main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class hungerChangeEvent implements Listener {

    @EventHandler
    public void onHungerChange(FoodLevelChangeEvent e) {
        boolean disable_hunger = main.getInstance().getConfig().getBoolean("modules-settings.basic-utils.disable-hunger");
        if (disable_hunger) {
            e.setCancelled(true);
        }
    }
}
