package dev.prodmarci.srvcore.utilities.models;

import dev.prodmarci.srvcore.main;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

public final class schedulerModel {

    public static void run(Runnable runnable) {
            Bukkit.getScheduler().runTask(main.getInstance(), runnable);
    }

    public static Task runLater(Runnable runnable, long delayTicks) {
            return new Task(Bukkit.getScheduler().runTaskLater(main.getInstance(), runnable, delayTicks));
    }

    public static Task runTimer(Runnable runnable, long delayTicks, long periodTicks) {
            return new Task(Bukkit.getScheduler().runTaskTimer(main.getInstance(), runnable, delayTicks, periodTicks));
    }

    public static class Task {
        private BukkitTask bukkitTask;

        Task(BukkitTask bukkitTask) {
            this.bukkitTask = bukkitTask;
        }

        public void cancel() {
                bukkitTask.cancel();
        }
    }
}