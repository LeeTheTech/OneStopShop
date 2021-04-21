package lee.code.onestopshop.menusystem.menus;

import lee.code.onestopshop.files.defaults.Config;
import lee.code.onestopshop.files.defaults.Settings;
import lee.code.onestopshop.menusystem.Menu;
import lee.code.onestopshop.menusystem.PlayerMenuUtility;
import lee.code.onestopshop.OneStopShop;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class MainMenu extends Menu {

    public MainMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        OneStopShop plugin = OneStopShop.getPlugin();
        String menu = playerMenuUtility.getCurrentMenu();
        return plugin.getPU().format(plugin.getData().getDataMenuUtil(menu).getMenuTitle(menu));
    }

    @Override
    public int getSlots() {
        OneStopShop plugin = OneStopShop.getPlugin();
        String menu = playerMenuUtility.getCurrentMenu();
        return plugin.getData().getDataMenuUtil(menu).getMenuSize(menu);
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        OneStopShop plugin = OneStopShop.getPlugin();

        //click delay
        if (plugin.getData().getPlayerClickDelay(playerMenuUtility.getOwner().getUniqueId())) return;
        else plugin.getPU().addPlayerClickDelay(playerMenuUtility.getOwner().getUniqueId());

        //return if players inventory
        if (e.getClickedInventory() == playerMenuUtility.getOwner().getInventory()) return;

        ItemStack item = e.getCurrentItem();

        if (item != null && !item.equals(FILLER_GLASS)) {
            String menu = playerMenuUtility.getCurrentMenu();

            //back button
            if (item.equals(BACK_ITEM)) {
                playerMenuUtility.setCurrentMenu(plugin.getData().getMainMenu());
                new MainMenu(playerMenuUtility).openMenu(plugin.getData().getMainMenu());
                plugin.getPU().playXSound(playerMenuUtility.getOwner(), Config.SOUND_MENU_CLICK.getConfigValue(null), Double.parseDouble(Config.SOUND_VOLUME_MENU_CLICK.getConfigValue(null)), Double.parseDouble(Config.SOUND_PITCH_MENU_CLICK.getConfigValue(null)));
                return;
            }

            //opens menu or sub menu
            if (plugin.getData().getDataMenuUtil(menu).getItemSubMenu(item) != null) {
                String subMenu = plugin.getData().getDataMenuUtil(menu).getItemSubMenu(item);
                playerMenuUtility.setCurrentMenu(subMenu);
                new MainMenu(playerMenuUtility).openMenu(subMenu);
            } else {
                playerMenuUtility.setShop(plugin.getData().getDataMenuUtil(menu).getShop(item));
                new ShopMenu(playerMenuUtility).open();
            }
            plugin.getPU().playXSound(playerMenuUtility.getOwner(), Config.SOUND_MENU_CLICK.getConfigValue(null), Double.parseDouble(Config.SOUND_VOLUME_MENU_CLICK.getConfigValue(null)), Double.parseDouble(Config.SOUND_PITCH_MENU_CLICK.getConfigValue(null)));
        }
    }

    @Override
    public void setMenuItems(String menu) {
        OneStopShop plugin = OneStopShop.getPlugin();
        playerMenuUtility.setCurrentMenu(menu);

        if (Settings.INTERFACE_BOOLEAN_MENU_FILLER_GLASS.getConfigValue()) setFillerGlass();
        int maxSize = plugin.getData().getDataMenuUtil(menu).getMenuSize(menu);
        if (!menu.equals(plugin.getData().getMainMenu()) && maxSize > 9) inventory.setItem(maxSize - 5, BACK_ITEM);
        ArrayList<ItemStack> items = new ArrayList<>(plugin.getData().getDataMenuUtil(menu).getMenuItems());
        for (ItemStack item : items) inventory.setItem(plugin.getData().getDataMenuUtil(menu).getMenuItemSlot(item), item);
    }
}
