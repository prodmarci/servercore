package dev.prodmarci.srvcore.modules.customHolo.models;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;

import static dev.prodmarci.srvcore.utilities.colorizeText.colorize;

public final class hologramModel {

    private final String[] lines;

    public hologramModel(String... lines) {
        this.lines = lines;
    }

    public void spawn(Location originLocation) {
        for (String line : lines) {
            ArmorStand stand = originLocation.getWorld().spawn(originLocation, ArmorStand.class);

            stand.setVisible(false);
            stand.setGravity(false);
            stand.setInvulnerable(true);

            stand.setCustomNameVisible(true);
            stand.setCustomName(colorize(line));

            originLocation.subtract(0, 0.25, 0);
        }
    }
}
