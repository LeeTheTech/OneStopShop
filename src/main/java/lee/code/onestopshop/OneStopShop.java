package lee.code.onestopshop;

import lee.code.onestopshop.commands.CommandManager;
import lee.code.onestopshop.commands.TabCompletion;
import lee.code.onestopshop.files.*;
import lee.code.onestopshop.files.defaults.Config;
import lee.code.onestopshop.files.defaults.Lang;
import lee.code.onestopshop.files.defaults.Settings;
import lee.code.onestopshop.listeners.*;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import lombok.Getter;

import java.util.logging.Level;

public class OneStopShop extends JavaPlugin {

    @Getter private Data data;
    @Getter private PU pU;
    @Getter private FileManager fileManager;
    @Getter private Economy economy;
    @Getter private OneStopShopAPI api;

    @Override
    public void onEnable() {

        //object classes
        this.data = new Data();
        this.fileManager = new FileManager();
        this.pU = new PU();
        this.api = new OneStopShopAPI();

        //file manager for configs
        fileManager.addConfig("config");
        fileManager.addConfig("settings");
        fileManager.addConfig("lang");
        fileManager.addConfig("menus");
        fileManager.addConfig("shops");

        registerCommands();
        registerListeners();

        //load defaults for configs
        loadDefaults();

        //load plugin shop data
        data.loadData();

        //load vault economy
        if (Settings.BOOLEAN_ECONOMY_VAULT.getConfigValue()) {
            if (!setupEconomy()) {
                Bukkit.getLogger().log(Level.SEVERE, "[OneStopShop] Vault is set to true in the config but the Vault economy isn't being handled by any other plugin, OneStopShop is now disabled.");
                getServer().getPluginManager().disablePlugin(this);
            }
        }
    }

    private void registerCommands() {
        getCommand("shop").setExecutor(new CommandManager());
        getCommand("shop").setTabCompleter(new TabCompletion());
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new MenuListener(), this);
        getServer().getPluginManager().registerEvents(new BlockBreakListener(), this);
        getServer().getPluginManager().registerEvents(new BlockExplodeListener(), this);
        getServer().getPluginManager().registerEvents(new BlockPlaceListener(), this);
        getServer().getPluginManager().registerEvents(new SpawnerListener(), this);
        getServer().getPluginManager().registerEvents(new SellWandListener(), this);
        getServer().getPluginManager().registerEvents(new InteractListener(), this);
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) return false;
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) return false;
        economy = rsp.getProvider();
        return economy != null;
    }

    public void loadDefaults() {

        //lang
        Lang.setFile(fileManager.getConfig("lang").getData());
        for (Lang value : Lang.values()) fileManager.getConfig("lang").getData().addDefault(value.getPath(), value.getDefault());
        fileManager.getConfig("lang").getData().options().copyDefaults(true);
        fileManager.getConfig("lang").save();

        //config
        Config.setFile(fileManager.getConfig("config").getData());
        for (Config value : Config.values()) fileManager.getConfig("config").getData().addDefault(value.getPath(), value.getDefault());
        fileManager.getConfig("config").getData().options().copyDefaults(true);
        fileManager.getConfig("config").save();

        //settings
        Settings.setFile(fileManager.getConfig("settings").getData());
        for (Settings value : Settings.values()) fileManager.getConfig("settings").getData().addDefault(value.getPath(), value.getDefault());
        fileManager.getConfig("settings").getData().options().copyDefaults(true);
        fileManager.getConfig("settings").save();
    }

    //gets a file
    public CustomFile getFile(String file) {
        return fileManager.getConfig(file);
    }

    //saves a file
    public void saveFile(String file) {
        fileManager.getConfig(file).save();
    }

    //reloads all files
    public void reloadFiles() {
        fileManager.reloadAll();
    }

    public static OneStopShop getPlugin() {
        return OneStopShop.getPlugin(OneStopShop.class);
    }
}