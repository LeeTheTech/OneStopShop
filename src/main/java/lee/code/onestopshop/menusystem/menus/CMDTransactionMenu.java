package lee.code.onestopshop.menusystem.menus;

import lee.code.onestopshop.OneStopShop;
import lee.code.onestopshop.files.defaults.Config;
import lee.code.onestopshop.files.defaults.Lang;
import lee.code.onestopshop.files.defaults.Settings;
import lee.code.onestopshop.menusystem.PaginatedMenu;
import lee.code.onestopshop.menusystem.PlayerMenuUtility;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class CMDTransactionMenu extends PaginatedMenu {

    public CMDTransactionMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        OneStopShop plugin = OneStopShop.getPlugin();
        ItemStack item = playerMenuUtility.getSelectedShopItem();
        String mat = plugin.getPluginUtility().formatMatFriendly(item);
        return Lang.INTERFACE_TRANSACTION_MENU_TITLE.getConfigValue(new String[]{ plugin.getPluginUtility().format( mat )});
    }

    @Override
    public int getSlots() {
        return 45;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        OneStopShop plugin = OneStopShop.getPlugin();

        //click delay
        if (plugin.getData().getPlayerClickDelay(playerMenuUtility.getOwner().getUniqueId())) return;
        else plugin.getPluginUtility().addPlayerClickDelay(playerMenuUtility.getOwner().getUniqueId());

        Player player = playerMenuUtility.getOwner();

        ItemStack item = new ItemStack(playerMenuUtility.getSelectedShopItem());

        //return if players inventory
        if (e.getClickedInventory() == player.getInventory()) return;

        switch (e.getSlot()) {
            //buy 1
            case 31:
                plugin.getPluginUtility().givePlayerItems(player, item, 1);
                break;
            //back
            case 38:
                new ShopMenu(playerMenuUtility).open();
                plugin.getPluginUtility().playXSound(playerMenuUtility.getOwner(), Config.SOUND_MENU_CLICK.getConfigValue(null), Double.parseDouble(Config.SOUND_VOLUME_MENU_CLICK.getConfigValue(null)), Double.parseDouble(Config.SOUND_PITCH_MENU_CLICK.getConfigValue(null)));
                break;
            //close
            case 42:
                player.closeInventory();
                plugin.getPluginUtility().playXSound(playerMenuUtility.getOwner(), Config.SOUND_MENU_CLICK.getConfigValue(null), Double.parseDouble(Config.SOUND_VOLUME_MENU_CLICK.getConfigValue(null)), Double.parseDouble(Config.SOUND_PITCH_MENU_CLICK.getConfigValue(null)));
                break;
        }

    }

    @Override
    public void setMenuItems(String menu) {

        OneStopShop plugin = OneStopShop.getPlugin();

        if (Settings.INTERFACE_BOOLEAN_TRANSACTION_FILLER_GLASS.getConfigValue()) setFillerGlass();

        ItemStack buyItem = plugin.getPluginUtility().createXItemStack(Config.INTERFACE_ITEM_BUY.getConfigValue(null));
        ItemStack backItem = plugin.getPluginUtility().createXItemStack(Config.INTERFACE_ITEM_BACK.getConfigValue(null));
        ItemStack closeItem = plugin.getPluginUtility().createXItemStack(Config.INTERFACE_ITEM_CLOSE.getConfigValue(null));

        ItemStack selectedItem = new ItemStack(playerMenuUtility.getSelectedShopItem());

        ItemMeta buyItemMeta = buyItem.getItemMeta();
        ItemMeta backItemMeta = backItem.getItemMeta();
        ItemMeta closeItemMeta = closeItem.getItemMeta();

        buyItemMeta.setDisplayName(Lang.INTERFACE_BUY_NAME.getConfigValue(null));
        backItemMeta.setDisplayName(Lang.INTERFACE_BACK_MENU_NAME.getConfigValue(null));
        closeItemMeta.setDisplayName(Lang.INTERFACE_CLOSE_MENU_NAME.getConfigValue(null));

        double buyItemValue = plugin.getData().getDataShopUtil().getBuyValue(selectedItem);

        ArrayList<String> buyItemLore = new ArrayList<>();

        buyItemLore.add(Lang.INTERFACE_BUY_LORE_1.getConfigValue(new String [] { String.valueOf(1) }));
        buyItemLore.add(Lang.INTERFACE_BUY_LORE_2.getConfigValue(new String [] { plugin.getPluginUtility().formatValue(buyItemValue) }));

        buyItemMeta.setLore(buyItemLore);
        buyItem.setItemMeta(buyItemMeta);
        inventory.setItem(31, buyItem);

        //selected item
        selectedItem.setAmount(1);
        inventory.setItem(13, selectedItem);

        //back and close item
        backItem.setItemMeta(backItemMeta);
        closeItem.setItemMeta(closeItemMeta);
        inventory.setItem(38, backItem);
        inventory.setItem(42, closeItem);
    }
}
