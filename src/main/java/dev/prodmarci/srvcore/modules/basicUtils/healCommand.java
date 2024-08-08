package dev.prodmarci.srvcore.modules.basicUtils;
import dev.prodmarci.srvcore.main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class healCommand implements CommandExecutor {
    main mainClass;
    public healCommand(main m) {this.mainClass = m;}
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        // Gets messages from config
        String healed_yourself = mainClass.getConfig().getString("modules-settings.basic-utils.messages.heal.yourself");
        String healed_someone = mainClass.getConfig().getString("modules-settings.basic-utils.messages.heal.someone");

        // If there is no agruments and sender is a player, also gets a player instance
        if (args.length == 0 && sender instanceof Player p) {

            // Sets player food level to maximum and sends a message
            p.setHealth(20);
            mainClass.playerStateLogger(p, healed_yourself);
        } else if (args.length == 1 && Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(args[0]))) {

            // Get player instance
            Player playertoheal = (Player) Bukkit.getPlayer(args[0]);
            assert playertoheal != null;

            // Replace %player% for an actual player displayname
            healed_someone = healed_someone.replace("%player%", playertoheal.getDisplayName());

            // Heals the player to max hp
            playertoheal.setHealth(20);

            // Send confirmation message to player issuing the command
            if (sender instanceof Player p) {
                mainClass.playerStateLogger(p, healed_someone);
            }
        }
        return true;
    }
}
