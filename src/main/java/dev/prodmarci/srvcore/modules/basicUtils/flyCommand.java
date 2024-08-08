package dev.prodmarci.srvcore.modules.basicUtils;

import dev.prodmarci.srvcore.main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class flyCommand implements CommandExecutor {
    main mainClass;
    public flyCommand(main m) {this.mainClass = m;}

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        // Gets messages from config
        String fly_enabled = mainClass.getConfig().getString("modules-settings.basic-utils.messages.fly.enabled");
        String fly_disabled = mainClass.getConfig().getString("modules-settings.basic-utils.messages.fly.disabled");

        // If player is a sender
        if (sender instanceof Player p) {

            // If player is flying
            if (p.getAllowFlight()) {

                // Disable fly and send a message
                p.setAllowFlight(false);
                mainClass.playerStateLogger(p,fly_disabled);
            } else {

                // Enable fly and send a message
                p.setAllowFlight(true);
                mainClass.playerStateLogger(p,fly_enabled);
            }
        }
        return true;
    }
}
