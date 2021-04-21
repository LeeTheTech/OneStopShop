package lee.code.onestopshop;

import lee.code.onestopshop.files.defaults.Lang;
import lee.code.onestopshop.itembuilders.SellWandBuilder;
import lee.code.onestopshop.menusystem.DataAPIUtility;
import lee.code.onestopshop.xseries.SkullUtils;
import lee.code.onestopshop.xseries.XItemStack;
import lee.code.onestopshop.files.defaults.Settings;
import lee.code.onestopshop.menusystem.DataMenuUtility;
import lee.code.onestopshop.menusystem.DataShopUtility;
import lee.code.onestopshop.menusystem.PlayerMenuUtility;
import lee.code.onestopshop.xseries.XMaterial;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;

import static org.bukkit.Bukkit.getServer;

public class Data {

    //click delay map
    private final List<UUID> playerClickDelay = new ArrayList<>();

    //menu system player utility
    private final ConcurrentHashMap<UUID, PlayerMenuUtility> playerMenuUtilityMap = new ConcurrentHashMap<>();

    //menu system data utility
    private final ConcurrentHashMap<String, DataMenuUtility> dataMenuUtilityMap = new ConcurrentHashMap<>();

    //shop menu system data utility
    private final DataShopUtility dataShopUtility = new DataShopUtility();

    //plugin api utility
    private final DataAPIUtility dataAPIUtility = new DataAPIUtility();

    //list of shops
    private final ArrayList<String> shopList = new ArrayList<>();

    //list of menus
    private final ArrayList<String> menuList = new ArrayList<>();

    //list of possible mob spawners
    private final ArrayList<String> spawnerMobs = new ArrayList<>();

    //gets spawner mob list
    public List<String> getSpawnerMobs() {
        return spawnerMobs;
    }

    //add mob to spawner mob list
    private void addSpawnerMob(String mob) {
        spawnerMobs.add(mob);
    }

    //gets shop string list
    public List<String> getShopList() {
        return shopList;
    }

    //add shop to string list
    private void addShopList(String string) {
        shopList.add(string);
    }

    //gets menu string list
    public List<String> getMenuList() {
        return menuList;
    }

    //add menu to string list
    private void addMenuList(String string) {
        menuList.add(string);
    }

    //check if player is on menu click delay
    public boolean getPlayerClickDelay(UUID uuid) {
        return playerClickDelay.contains(uuid);
    }

    //add a player to a click delay list for runnable
    public void addPlayerClickDelay(UUID uuid) {
        playerClickDelay.add(uuid);
    }

    //remove a player to a click delay list
    public void removePlayerClickDelay(UUID uuid) {
        playerClickDelay.remove(uuid);
    }

    //main menu
    @Getter @Setter private String mainMenu;

    //sell wand
    @Getter @Setter private ItemStack sellWand;

    //menu utility for managing players using the plugin menus
    public PlayerMenuUtility getPlayerMenuUtil(UUID uuid) {
        PlayerMenuUtility playerMenuUtility;

        if (playerMenuUtilityMap.containsKey(uuid)) {
            return playerMenuUtilityMap.get(uuid);

        } else {

            playerMenuUtility = new PlayerMenuUtility(uuid);
            playerMenuUtilityMap.put(uuid, playerMenuUtility);
            return playerMenuUtility;
        }

    }

    public DataMenuUtility getDataMenuUtil(String menu) {

        DataMenuUtility dataMenuUtility;

        if (dataMenuUtilityMap.containsKey(menu)) {
            return dataMenuUtilityMap.get(menu);
        } else {

            dataMenuUtility = new DataMenuUtility(menu);
            dataMenuUtilityMap.put(menu, dataMenuUtility);
            return dataMenuUtility;
        }
    }

    public DataShopUtility getDataShopUtil() {
        return dataShopUtility;
    }

    public DataAPIUtility getDataAPIUtility() {
        return dataAPIUtility;
    }

    //clears saved data
    private void clearData() {
        dataMenuUtilityMap.clear();
        shopList.clear();
        menuList.clear();
    }

    //loads menu and shop items
    public void loadData() {
        loadMenus();
        loadShops();
        loadEconomyItem();
        loadSpawnerMobs();
        loadApiItems();
        loadSellWand();
    }

    //loads menus data
    private void loadMenus() {
        OneStopShop plugin = OneStopShop.getPlugin();

        //clears some saved data for reload
        clearData();

        FileConfiguration file = plugin.getFile("menus").getData();

        ConfigurationSection menus = file.getConfigurationSection("menus");

        if (menus != null) {
            menus.getKeys(false).forEach(menu -> {

                boolean mainMenu = file.getBoolean("menus." + menu + ".main-menu");

                if (mainMenu) setMainMenu(menu);

                int menuSize = file.getInt("menus." + menu + ".size");
                String menuTitle = file.getString("menus." + menu + ".title");

                //menu size
                getDataMenuUtil(menu).setMenuSize(menu, menuSize);
                //menu title
                getDataMenuUtil(menu).setMenuTitle(menu, menuTitle);

                //loop through menu config
                ConfigurationSection items = file.getConfigurationSection("menus." + menu + ".items");
                if (items != null) {
                    items.getKeys(false).forEach(key -> {

                        ConfigurationSection section = file.getConfigurationSection("menus." + menu + ".items." + key);
                        if (section != null) {
                            String shopTitle = file.getString("menus." + menu + ".items." + key + ".name");
                            String shop = file.getString("menus." + menu + ".items." + key + ".shop");
                            String subMenuName = file.getString("menus." + menu + ".items." + key + ".sub-menu");

                            //get item and hide flags
                            ItemStack item = XItemStack.deserialize(section);
                            if (item != null) {
                                ItemMeta itemMeta = item.getItemMeta();
                                if (itemMeta != null) {
                                    itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                                    itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                                    itemMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
                                    item.setItemMeta(itemMeta);

                                    //item slot
                                    int slot = file.getInt("menus." + menu + ".items." + key + ".menu-slot");
                                    //menu items
                                    getDataMenuUtil(menu).addMenuItem(item);
                                    //item slot
                                    getDataMenuUtil(menu).setMenuItemSlot(item, slot);

                                    if (subMenuName != null) {
                                        getDataMenuUtil(menu).setItemSubMenu(item, subMenuName);
                                    } else {
                                        //menu item shop
                                        getDataMenuUtil(menu).setShop(item, shop);
                                        //menu item shop title
                                        getDataMenuUtil(menu).setShopTitle(shop, shopTitle);
                                        //menu shop list
                                        getDataMenuUtil(menu).addMenuShop(shop);
                                        //global list of shops
                                        addShopList(shop);
                                    }
                                    //global list of menus
                                    if (!getMenuList().contains(menu)) addMenuList(menu);
                                }
                            }
                        }
                    });
                }
            });
        }

        if (getMainMenu() == null) {
            Bukkit.getLogger().log(Level.SEVERE, "[OneStopShop] There was no main menu set in the menus.yml. You need to add (main-menu: true) to a menu.");
            getServer().getPluginManager().disablePlugin(plugin);
        }
    }

    //loads shops data
    private void loadShops() {

        OneStopShop plugin = OneStopShop.getPlugin();
        FileConfiguration file = plugin.getFile("shops").getData();

        if (file.contains("shops")) {
            ConfigurationSection shops = file.getConfigurationSection("shops");
            if (shops != null) {
                shops.getKeys(false).forEach(shop -> {

                    List<ItemStack> itemList = new ArrayList<>();
                    List<String> skinList = new ArrayList<>();
                    double sell;
                    double buy;
                    ItemStack item;
                    String command;

                    //loop through shops config
                    ConfigurationSection items = file.getConfigurationSection("shops." + shop + ".items");
                    if (items != null) {
                        for (String key : items.getKeys(false)) {

                            ConfigurationSection section = file.getConfigurationSection("shops." + shop + ".items." + key);

                            if (section != null) {
                                sell = file.getDouble("shops." + shop + ".items." + key + ".sell");
                                buy = file.getDouble("shops." + shop + ".items." + key + ".buy");
                                item = XItemStack.deserialize(section);
                                command = file.getString("shops." + shop + ".items." + key + ".command");

                                //check for duplicate item
                                if (item != null && !itemList.contains(item)) {
                                    getDataShopUtil().setSellValue(item, sell);
                                    getDataShopUtil().setBuyValue(item, buy);
                                    itemList.add(item);
                                    if (command != null) getDataShopUtil().setItemCommand(item, command);

                                    //skull skin check
                                    if (item.getType() == XMaterial.PLAYER_HEAD.parseMaterial() && item.getItemMeta() != null) {
                                        if (item.hasItemMeta()) {
                                            String skullSkin = SkullUtils.getSkinValue(item.getItemMeta());
                                            skinList.add(skullSkin);
                                        }
                                    }
                                } else {
                                    Bukkit.getLogger().log(Level.SEVERE, "[OneStopShop] The shop " + shop + " has a duplicate item: " + plugin.getPU().formatMat(item));
                                    Bukkit.getLogger().log(Level.SEVERE, "[OneStopShop] This needs to be fixed in the shops.yml.");
                                }
                            }
                        }
                        getDataShopUtil().addSkullSkin(shop, skinList);
                        getDataShopUtil().setShopItems(shop, itemList);
                    }
                });
            }
        }
    }

    //loads api ItemStack with shop with values
    public void loadApiItems() {
        for (String shop : getShopList()) {
            if (getDataAPIUtility().getShopItems(shop) != null) {
                List<ItemStack> shopItems = getDataAPIUtility().getShopItems(shop);
                for (ItemStack item : shopItems) {
                    if (!getDataShopUtil().getShopItems(shop).contains(item)) {
                        getDataShopUtil().getShopItems(shop).add(item);
                        getDataShopUtil().setBuyValue(item, getDataAPIUtility().getBuyValue(item));
                        getDataShopUtil().setSellValue(item, getDataAPIUtility().getSellValue(item));
                    }
                }
            }
        }
    }

    private void loadEconomyItem() {
        OneStopShop plugin = OneStopShop.getPlugin();

        FileConfiguration file = plugin.getFile("config").getData();
        boolean itemEconomyEnabled = Settings.BOOLEAN_ECONOMY_VAULT.getConfigValue();

        if (!itemEconomyEnabled) {
            if (file.contains("EconomyMaterial")) {
                getDataShopUtil().setEconomyItem(plugin.getPU().createXItemStack(file.getString("EconomyMaterial")));
            }
        }
    }

    private void loadSpawnerMobs() {
        if (getSpawnerMobs().isEmpty()) {
            for (EntityType mob : EntityType.values()) {
                addSpawnerMob(mob.toString());
            }
        }
    }

    private void loadSellWand() {
        if (getSellWand() == null) {
            setSellWand(new SellWandBuilder().setName(Lang.WAND_SELL_WAND_NAME.getConfigValue(null)).setLore(Collections.singletonList(Lang.WAND_SELL_WAND_LORE.getConfigValue(null))).buildItemStack());
        }
    }
}
