package vip.creeper.mcserverplugins.creeperkits.managers;

import vip.creeper.mcserverplugins.creeperkits.Kit;

import java.util.Collection;
import java.util.HashMap;

/**
 * Created by July on 2018/02/16.
 */
public class CacheKitManager {
    private KitManager kitManager;
    private HashMap<String, Kit> caches = new HashMap<>();

    public CacheKitManager(KitManager kitManager) {
        this.kitManager = kitManager;
    }

    public Kit getOrLoadCacheKit(String name) {
        if (!caches.containsKey(name)) {
            Kit kit = kitManager.getKit(name);

            if (kit != null) {
                caches.put(name.toLowerCase(), kit);
            }
        }

        return caches.get(name.toLowerCase());
    }

    public void removeKitFromCache(String name) {
        caches.remove(name.toLowerCase());
    }

    public Collection<Kit> getCacheKits() {
        return caches.values();
    }

    public void loadCacheKits() {
        caches.clear();

        for (Kit kit : kitManager.getKits()) {
            caches.put(kit.getName().toLowerCase(), kit);
        }
    }
}
