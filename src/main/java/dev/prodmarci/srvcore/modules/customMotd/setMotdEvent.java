package dev.prodmarci.srvcore.modules.customMotd;

import dev.prodmarci.srvcore.main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import java.util.Objects;

import static dev.prodmarci.srvcore.utilities.colorizeText.colorize;

public class setMotdEvent implements Listener {
    main mainClass;
    public setMotdEvent(main m) {this.mainClass = m;}

    @EventHandler
    void onServerStart(ServerListPingEvent event) {

        // Gets all the messages
        String first_line = mainClass.getConfig().getString("modules-settings.custom-motd.first-line");
        String second_line = mainClass.getConfig().getString("modules-settings.custom-motd.second-line");
        assert first_line != null; assert second_line != null;

        // Translates color codes
        first_line = colorize(first_line);
        second_line = colorize(second_line);

        // Create a full motd string
        String full_motd = first_line + "\n" + second_line;

        // Gets max players display integer
        int max_players = Integer.parseInt(Objects.requireNonNull(mainClass.getConfig().getString("modules-settings.custom-motd.max-players")));

        // Sets motd and max players display
        event.setMotd(full_motd);
        event.setMaxPlayers(max_players);
    }
}
