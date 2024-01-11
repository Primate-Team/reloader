package ru.samsonium.primate.reloader;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public final class PrimateReloader extends JavaPlugin {
    private Map<String, Long> modifies;

    @Override
    public void onEnable() {
        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    /**
     * Get map of files and it's last modified time
     * @return Map of files with last modified time
     * @throws Exception if got an error on reading list files
     */
    public Map<String, Long> getPlugins() throws Exception {
        File pluginsFolder = getServer().getPluginsFolder();
        File[] fileList = pluginsFolder.listFiles();
        if (fileList == null)
            throw new Exception("Got an error on list files");

        Map<String, Long> result = new HashMap<>();
        for (File pluginDir : fileList) {
            if (!pluginDir.isDirectory()) continue;

            String name = pluginDir.getName();
            Plugin plugin = getServer().getPluginManager().getPlugin(name);
            if (plugin == null) continue;

            File pluginJar = new File(plugin.getClass().getProtectionDomain().getCodeSource().getLocation().toURI());
            result.put(pluginDir.getName(), pluginJar.lastModified());
        }

        return result;
    }

    /**
     * Get base plugin class instance
     * @return PrimateReloader instance
     */
    public static PrimateReloader get() {
        return getPlugin(PrimateReloader.class);
    }
}
