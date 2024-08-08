package dev.prodmarci.srvcore.modules.welcomeMessage;
import dev.prodmarci.srvcore.main;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class joinMessageEvent implements Listener {
    main mainClass;
    public joinMessageEvent(main m) {this.mainClass = m;}

    // Deletes default join message
    @EventHandler
    void cancelMessageOnPlayerJoin(PlayerJoinEvent e) {
        e.setJoinMessage(null);
    }

    // Shows welcome messages
    @EventHandler
    void onPlayerJoin(PlayerJoinEvent e) {

        // Gets player instance and name
        Player player = e.getPlayer();
        String playerName = player.getName();

        // Gets all messages
        String message = mainClass.getConfig().getString("modules-settings.welcome-message.messages.message");
        String type = mainClass.getConfig().getString("modules-settings.welcome-message.type");
        assert type != null; assert message != null;

        // Replaces %player% with an actual player name
        message = message.replace("%player%", playerName);

        // Translates colors
        message = ChatColor.translateAlternateColorCodes('&', message);

        // Checks type and sends a welcome message of certain type
        switch(type) {
            case "title":
                mainClass.playerTitleLogger(player, message);
                break;
            case "actionbar":
                mainClass.playerActionbarLogger(player, message);
                break;
        }
    }
}
