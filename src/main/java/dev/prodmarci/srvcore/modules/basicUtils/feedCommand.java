package dev.prodmarci.srvcore.modules.basicUtils;
import dev.prodmarci.srvcore.main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class feedCommand implements CommandExecutor {
    main mainClass;
    public feedCommand(main m) {this.mainClass = m;}

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        // Gets messages from config
        String fed_yourself = mainClass.getConfig().getString("modules-settings.basic-utils.messages.feed.yourself");
        String fed_someone = mainClass.getConfig().getString("modules-settings.basic-utils.messages.feed.someone");

        // If there is no agruments and sender is a player, also gets a player instance
        if (args.length == 0 && sender instanceof Player p) {

            // Sets player food level to maximum and sends a message
            p.setFoodLevel(20);
            mainClass.playerStateLogger(p, fed_yourself);
        } else if (args.length == 1 && Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(args[0]))) {

            // Get player instance
            Player playertofeed = (Player) Bukkit.getPlayer(args[0]);
            assert playertofeed != null;

            // Replace %player% for an actual player displayname
            fed_someone = fed_someone.replace("%player%", playertofeed.getDisplayName());

            // Feed the player
            playertofeed.setFoodLevel(20);

            // Send confirmation message to player issuing the command
            if (sender instanceof Player p) {
                mainClass.playerStateLogger(p, fed_someone);
            }
        }
        return true;
    }
}
