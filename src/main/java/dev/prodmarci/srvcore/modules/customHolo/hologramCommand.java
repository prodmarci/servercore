package dev.prodmarci.srvcore.modules.customHolo;

import dev.prodmarci.srvcore.main;
import dev.prodmarci.srvcore.modules.customHolo.models.hologramModel;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class hologramCommand implements CommandExecutor {
    main mainClass;

    public hologramCommand(main m) {
        this.mainClass = m;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player p)) {
            mainClass.stateLogger("Only players can use this command!", false);
            return true;
        }

        if (args.length == 0)
            return false;

        hologramModel hologram = new hologramModel(String.join(" ", args).split("\\|"));
        hologram.spawn(p.getLocation());
        return true;
    }
}
