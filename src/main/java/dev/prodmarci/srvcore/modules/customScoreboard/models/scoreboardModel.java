package dev.prodmarci.srvcore.modules.customScoreboard.models;

import dev.prodmarci.srvcore.main;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.Arrays;
import java.util.List;

import static dev.prodmarci.srvcore.utilities.colorizeText.colorize;

public final class scoreboardModel implements Runnable {

    private final static scoreboardModel instance = new scoreboardModel();

    private scoreboardModel() {
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getScoreboard() != null && player.getScoreboard().getObjective(main.getInstance().getName()) != null)
                updateScoreboard(player);
            else
                createNewScoreboard(player);
        }
    }

    private void createNewScoreboard(Player player) {
        // Get messages from config
        String server_name = main.getInstance().getConfig().getString("general-settings.server-name");
        String displayname = main.getInstance().getConfig().getString("modules-settings.custom-scoreboard.display-name");
        List<String> content = main.getInstance().getConfig().getStringList("modules-settings.custom-scoreboard.content");
        assert displayname != null; assert server_name != null;
        displayname = displayname.replace("%server_name%", server_name);
        displayname = colorize(displayname);

        // Create a scoreboard
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective(main.getInstance().getName(), "dummy_srvcore");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        // Set scoreboard displayname
        objective.setDisplayName(displayname);

        int iterations = content.size();
        int colorCharNum = 0;
        List<String> colorChar = Arrays.asList("a", "b", "c", "d", "e", "f");
        for (String line : content) {
            if (line == null) {
                objective.getScore(ChatColor.WHITE + " ").setScore(iterations);
            } else {
                String temp = translateLine(line, player);

                String teamKey = "";
                Team team = scoreboard.registerNewTeam(String.valueOf(iterations));
                if (!(iterations > 9)) {
                    teamKey = ChatColor.translateAlternateColorCodes('&', "&" + iterations) + "";
                } else {
                    teamKey = ChatColor.translateAlternateColorCodes('&', "&" + colorChar.get(colorCharNum)) + "";
                    colorCharNum++;
                }
                team.addEntry(teamKey);
                team.setSuffix(temp);
                objective.getScore(teamKey).setScore(iterations);
            }
            iterations--;
        }
        player.setScoreboard(scoreboard);
    }

    private void updateScoreboard(Player player) {
    }

    public static scoreboardModel getInstance() {
        return instance;
    }

    String translateLine(String s, Player p) {
        s = s.replace("%player_name%", p.getName());
        s = s.replace("%players%", String.valueOf(main.getInstance().getServer().getOnlinePlayers().size()));
        if (p.hasPermission("srvcore.staff")) {
            s = s.replace("%rank%", "Developer");
        } else {
            s = s.replace("%rank%", "Tester");
        }
        s = colorize(s);
        return s;
    }
}