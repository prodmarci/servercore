package dev.prodmarci.srvcore.modules.welcomeMessage;
import dev.prodmarci.srvcore.main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class leaveMessageEvent implements Listener {
    main mainClass;
    public leaveMessageEvent(main m) {this.mainClass = m;}

    // Deletes default quit message
    @EventHandler
    void cancelMessageOnPlayerQuit(PlayerQuitEvent e) {
        e.setQuitMessage(null);
    }
}
