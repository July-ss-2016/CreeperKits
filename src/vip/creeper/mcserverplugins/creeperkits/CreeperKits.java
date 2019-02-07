package vip.creeper.mcserverplugins.creeperkits;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import vip.creeper.mcserverplugins.creeperkits.commands.*;
import vip.creeper.mcserverplugins.creeperkits.managers.CacheKitManager;
import vip.creeper.mcserverplugins.creeperkits.managers.KitManager;
import vip.creeper.mcserverplugins.creeperkits.managers.SQLiteManager;

import java.util.logging.Logger;

/**
 * Created by July on 2018/2/14.
 */
public class CreeperKits extends JavaPlugin {
    private static CreeperKits instance;
    private Logger logger = getLogger();
    private Settings settings;
    private SQLiteManager sqLiteManager;
    private KitManager kitManager;
    private CacheKitManager cacheKitManager;
    private KitCommandExecutor kitCommandExecutor;

    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage("§c[" + getName() + "] 作者QQ: 884633197");
        Bukkit.getConsoleSender().sendMessage("§c[" + getName() + "] 有偿接各类MC插件定制");
        instance = this;
        this.settings = new Settings();

        loadConfig();

        this.sqLiteManager = new SQLiteManager("plugins/CreeperKits", settings.getDatabase());
        this.kitCommandExecutor = new KitCommandExecutor();
        this.kitManager = new KitManager(this);
        this.cacheKitManager = new CacheKitManager(kitManager);

        kitManager.init();

        if (!sqLiteManager.connect()) {
            warring("初始化失败: SQLite 连接失败!");
            setEnabled(false);
            return;
        }

        if (!sqLiteManager.executeStatement("create table if not exists kits(name TEXT, items BLOB)")) {
            warring("初始化失败: 创建数据表失败!");
            setEnabled(false);
            return;
        }

        // 使用异步线程，防护假死过久崩溃，载入后才注册命令
        Bukkit.getScheduler().runTaskAsynchronously(this, () -> Bukkit.getScheduler().runTask(instance, () -> {
            long startTime = System.currentTimeMillis();

            cacheKitManager.loadCacheKits(); // 把已有的Kit载入的Cache

            info("载入Kits完毕, 耗时: " + (System.currentTimeMillis() - startTime) + "(ms).");
            getCommand("ckit").setExecutor(kitCommandExecutor);
            registerCmds();
        }));
    }

    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage("§c[" + getName() + "] 作者QQ: 884633197");
        Bukkit.getConsoleSender().sendMessage("§c[" + getName() + "] 有偿接各类MC插件定制");

        if (sqLiteManager.isConnected()) {
            sqLiteManager.disconnect();
        }
    }

    private void registerCmds() {
        kitCommandExecutor.registerCmd("create", new KitCreateCommand(this));
        kitCommandExecutor.registerCmd("get", new KitGetCommand(this));
        kitCommandExecutor.registerCmd("aget", new KitGetWithoutReplaceVariablesCommand(this));
        kitCommandExecutor.registerCmd("give", new KitGiveCommand(this));
        kitCommandExecutor.registerCmd("list", new KitListCommand(this));
        kitCommandExecutor.registerCmd("remove", new KitRemoveCommand(this));
        kitCommandExecutor.registerCmd("rename", new KitRenameCommand(this));
    }

    public SQLiteManager getSqLiteManager() {
        return sqLiteManager;
    }

    public KitManager getKitManager() {
        return kitManager;
    }

    public CacheKitManager getCacheKitManager() {
        return cacheKitManager;
    }

    public static CreeperKits getInstance() {
        return instance;
    }

    private void warring(String s) {
        logger.warning(s);
    }

    private void info(String s) {
        logger.info(s);
    }

    private void loadConfig() {
        saveDefaultConfig();
        reloadConfig();

        FileConfiguration config = getConfig();

        settings.setDatabase(config.getString("database"));
    }
}
