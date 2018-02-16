package vip.creeper.mcserverplugins.creeperkits.managers;

import vip.creeper.mcserverplugins.creeperkits.CreeperKits;
import vip.creeper.mcserverplugins.creeperkits.Kit;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * Created by July on 2018/02/16.
 */
public class CacheKitManager {
    private KitManager kitManager;
    private HashMap<String, Kit> caches = new HashMap<>();

    public CacheKitManager(CreeperKits plugin) {
        this.kitManager = plugin.getKitManager();
    }

    public Kit getOrLoadCacheKit(String name) {
        if (!caches.containsKey(name)) {
            Kit kit = kitManager.getKit(name);

            if (kit != null) {
                caches.put(name, kit);
            }
        }

        return caches.get(name);
    }

    public void removeKitFromCache(String name) {
        caches.remove(name);
    }

    public Collection<Kit> getCacheKits() {
        return caches.values();
    }

    public void loadCacheKits() {
        caches.clear();

        for (Kit kit : kitManager.getKits()) {
            caches.put(kit.getName(), kit);
        }
    }
}
