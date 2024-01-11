package ru.samsonium.primate.reloader;

import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;

public class CheckTask implements Runnable {
    @Override
    public void run() {
        PrimateReloader main = PrimateReloader.get();
        Map<String, Long> plugins;
        Map<String, Long> prev = main.getModifies();

        main.getLogger().log(Level.INFO, "Checking plugins for changes...");

        try {
            plugins = main.getPlugins();
        } catch (Exception e) {
            main.getLogger().log(Level.SEVERE, e.getMessage());
            return;
        }

        for (String name : plugins.keySet()) {
            if (!prev.containsKey(name))
                continue;

            Long nowTime = plugins.get(name);
            Long prevTime = prev.get(name);

            if (!Objects.equals(nowTime, prevTime)) {
                main.getLogger().log(Level.INFO, "Plugin " + name + " has been modified. Updating...");
                main.getServer().reload();
            }
        }

        main.setModifies(plugins);
    }
}
