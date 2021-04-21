package lee.code.onestopshop.menusystem.menus;

import lee.code.onestopshop.files.defaults.Config;
import lee.code.onestopshop.files.defaults.Lang;
import lee.code.onestopshop.files.defaults.Settings;
import lee.code.onestopshop.menusystem.Menu;
import lee.code.onestopshop.menusystem.PlayerMenuUtility;
import lee.code.onestopshop.OneStopShop;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class TransactionMenu extends Menu {

    public TransactionMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        OneStopShop plugin = OneStopShop.getPlugin();
        ItemStack item = playerMenuUtility.getSelectedShopItem();
        String mat = plugin.getPU().formatMatFriendly(item);
        return Lang.INTERFACE_TRANSACTION_MENU_TITLE.getConfigValue(new String[]{ plugin.getPU().format( mat )});
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        OneStopShop plugin = OneStopShop.getPlugin();

        //click delay
        if (plugin.getData().getPlayerClickDelay(playerMenuUtility.getOwner().getUniqueId())) return;
        else plugin.getPU().addPlayerClickDelay(playerMenuUtility.getOwner().getUniqueId());

        Player player = playerMenuUtility.getOwner();

        ItemStack item = new ItemStack(playerMenuUtility.getSelectedShopItem());

        //return if players inventory
        if (e.getClickedInventory() == player.getInventory()) return;

        boolean update;
        int amount;

        switch (e.getSlot()) {
            //buy 1
            case 12:
                update = plugin.getPU().givePlayerItems(player, item, 1);
                if (update) {
                    buyInventoryItem();
                    sellInventoryItem();
                }
                break;
            //buy 8
            case 21:
                update = plugin.getPU().givePlayerItems(player, item, 8);
                if (update) {
                    buyInventoryItem();
                    sellInventoryItem();
                }
                break;
            //buy 64
            case 30:
                update = plugin.getPU().givePlayerItems(player, item, 64);
                if (update) {
                    buyInventoryItem();
                    sellInventoryItem();
                }
                break;
            //buy inventory
            case 39:
                amount = plugin.getPU().getAmountOfFreeSpace(player, item);
                update = plugin.getPU().givePlayerItems(player, item, amount);
                if (update) {
                    buyInventoryItem();
                    sellInventoryItem();
                }
                break;
            //sell 1
            case 14:
                update = plugin.getPU().takePlayerItems(player, item, 1, false);
                if (update) {
                    buyInventoryItem();
                    sellInventoryItem();
                }
                break;
            //sell 8
            case 23:
                update = plugin.getPU().takePlayerItems(player, item, 8, false);
                if (update) {
                    buyInventoryItem();
                    sellInventoryItem();
                }
                break;
            //sell 64
            case 32:
                update = plugin.getPU().takePlayerItems(player, item, 64, false);
                if (update) {
                    buyInventoryItem();
                    sellInventoryItem();
                }
                break;
            //sell inventory
            case 41:
                amount = plugin.getPU().getItemAmount(player, item);
                update = plugin.getPU().takePlayerItems(player, item, amount, false);
                if (update) {
                    buyInventoryItem();
                    sellInventoryItem();
                }
                break;

            //back
            case 46:
                new ShopMenu(playerMenuUtility).open();
                plugin.getPU().playXSound(playerMenuUtility.getOwner(), Config.SOUND_MENU_CLICK.getConfigValue(null), Double.parseDouble(Config.SOUND_VOLUME_MENU_CLICK.getConfigValue(null)), Double.parseDouble(Config.SOUND_PITCH_MENU_CLICK.getConfigValue(null)));
                break;
            //close
            case 52:
                player.closeInventory();
                plugin.getPU().playXSound(playerMenuUtility.getOwner(), Config.SOUND_MENU_CLICK.getConfigValue(null), Double.parseDouble(Config.SOUND_VOLUME_MENU_CLICK.getConfigValue(null)), Double.parseDouble(Config.SOUND_PITCH_MENU_CLICK.getConfigValue(null)));
                break;
        }
    }

    @Override
    public void setMenuItems(String menu) {
        OneStopShop plugin = OneStopShop.getPlugin();

        if (Settings.INTERFACE_BOOLEAN_TRANSACTION_FILLER_GLASS.getConfigValue()) setFillerGlass();

        ItemStack buyItem = plugin.getPU().createXItemStack(Config.INTERFACE_ITEM_BUY.getConfigValue(null));
        ItemStack sellItem = plugin.getPU().createXItemStack(Config.INTERFACE_ITEM_SELL.getConfigValue(null));
        ItemStack backItem = plugin.getPU().createXItemStack(Config.INTERFACE_ITEM_BACK.getConfigValue(null));
        ItemStack closeItem = plugin.getPU().createXItemStack(Config.INTERFACE_ITEM_CLOSE.getConfigValue(null));
        
        ItemStack selectedItem = new ItemStack(playerMenuUtility.getSelectedShopItem());

        ItemMeta buyItemMeta = buyItem.getItemMeta();
        ItemMeta sellItemMeta = sellItem.getItemMeta();
        ItemMeta backItemMeta = backItem.getItemMeta();
        ItemMeta closeItemMeta = closeItem.getItemMeta();

        buyItemMeta.setDisplayName(Lang.INTERFACE_BUY_NAME.getConfigValue(null));
        sellItemMeta.setDisplayName(Lang.INTERFACE_SELL_NAME.getConfigValue(null));
        backItemMeta.setDisplayName(Lang.INTERFACE_BACK_MENU_NAME.getConfigValue(null));
        closeItemMeta.setDisplayName(Lang.INTERFACE_CLOSE_MENU_NAME.getConfigValue(null));

        double buyItemValue = plugin.getData().getDataShopUtil().getBuyValue(selectedItem);
        double sellItemValue = plugin.getData().getDataShopUtil().getSellValue(selectedItem);

        //buy item lore
        ArrayList<String> buyItemLore = new ArrayList<>();
        double buyValue = buyItemValue;
        for (int i = 1; i <= 65; i++) {

            switch (i) {

                case 1:
                    buyItemLore.add(Lang.INTERFACE_BUY_LORE_1.getConfigValue(new String [] { String.valueOf(i) }));
                    buyItemLore.add(Lang.INTERFACE_BUY_LORE_2.getConfigValue(new String [] { plugin.getPU().formatValue(buyValue) }));

                    buyItemMeta.setLore(buyItemLore);
                    buyItem.setItemMeta(buyItemMeta);
                    inventory.setItem(12, buyItem);
                    buyItemLore.clear();
                    break;

                case 8:
                    buyValue = buyItemValue * i;
                    buyItemLore.add(Lang.INTERFACE_BUY_LORE_1.getConfigValue(new String [] { String.valueOf(i) }));
                    buyItemLore.add(Lang.INTERFACE_BUY_LORE_2.getConfigValue(new String [] { plugin.getPU().formatValue(buyValue) }));

                    buyItemMeta.setLore(buyItemLore);
                    buyItem.setItemMeta(buyItemMeta);
                    inventory.setItem(21, buyItem);
                    buyItemLore.clear();
                    break;

                case 64:
                    buyValue = buyItemValue * i;
                    buyItemLore.add(Lang.INTERFACE_BUY_LORE_1.getConfigValue(new String [] { String.valueOf(i) }));
                    buyItemLore.add(Lang.INTERFACE_BUY_LORE_2.getConfigValue(new String [] { plugin.getPU().formatValue(buyValue) }));

                    buyItemMeta.setLore(buyItemLore);
                    buyItem.setItemMeta(buyItemMeta);
                    inventory.setItem(30, buyItem);
                    buyItemLore.clear();
                    break;

                //buy inventory
                case 65:
                    buyInventoryItem();
                    break;
            }

        }

        //sell item lore
        ArrayList<String> sellItemLore = new ArrayList<>();
        double sellValue = sellItemValue;
        for (int i = 1; i <= 65; i++) {

            switch (i) {

                case 1:
                    sellItemLore.add(Lang.INTERFACE_SELL_LORE_1.getConfigValue(new String [] { String.valueOf(i) }));
                    sellItemLore.add(Lang.INTERFACE_SELL_LORE_2.getConfigValue(new String [] { plugin.getPU().formatValue(sellValue) }));

                    sellItemMeta.setLore(sellItemLore);
                    sellItem.setItemMeta(sellItemMeta);
                    inventory.setItem(14, sellItem);
                    sellItemLore.clear();
                    break;
                case 8:
                    sellValue = sellItemValue * i;
                    sellItemLore.add(Lang.INTERFACE_SELL_LORE_1.getConfigValue(new String [] { String.valueOf(i) }));
                    sellItemLore.add(Lang.INTERFACE_SELL_LORE_2.getConfigValue(new String [] { plugin.getPU().formatValue(sellValue) }));

                    sellItemMeta.setLore(sellItemLore);
                    sellItem.setItemMeta(sellItemMeta);
                    inventory.setItem(23, sellItem);
                    sellItemLore.clear();
                    break;
                case 64:
                    sellValue = sellItemValue * i;
                    sellItemLore.add(Lang.INTERFACE_SELL_LORE_1.getConfigValue(new String [] { String.valueOf(i) }));
                    sellItemLore.add(Lang.INTERFACE_SELL_LORE_2.getConfigValue(new String [] { plugin.getPU().formatValue(sellValue) }));

                    sellItemMeta.setLore(sellItemLore);
                    sellItem.setItemMeta(sellItemMeta);
                    inventory.setItem(32, sellItem);
                    sellItemLore.clear();
                    break;
                //sell inventory
                case 65:
                    sellInventoryItem();
                    break;
            }
        }

        //selected item
        selectedItem.setAmount(1);
        inventory.setItem(13, selectedItem);
        selectedItem.setAmount(8);
        inventory.setItem(22, selectedItem);
        selectedItem.setAmount(64);
        inventory.setItem(31, selectedItem);

        //back icon
        backItem.setItemMeta(backItemMeta);
        closeItem.setItemMeta(closeItemMeta);
        inventory.setItem(46, backItem);
        inventory.setItem(52, closeItem);
    }

    private void buyInventoryItem() {
        OneStopShop plugin = OneStopShop.getPlugin();
        ItemStack selectedItem = new ItemStack(playerMenuUtility.getSelectedShopItem());
        double buyItemValue = plugin.getData().getDataShopUtil().getBuyValue(selectedItem);

        //buy inventory item lore
        ArrayList<String> buyItemLore = new ArrayList<>();

        ItemStack buyAllItem = plugin.getPU().createXItemStack(Config.INTERFACE_ITEM_BUY_INVENTORY.getConfigValue(null));
        ItemMeta buyAllItemMeta = buyAllItem.getItemMeta();
        buyAllItemMeta.setDisplayName(Lang.INTERFACE_BUY_INVENTORY_NAME.getConfigValue(null));

        double buyValue = buyItemValue * plugin.getPU().getAmountOfFreeSpace(playerMenuUtility.getOwner(), selectedItem);
        int amount = plugin.getPU().getAmountOfFreeSpace(playerMenuUtility.getOwner().getPlayer(), selectedItem);

        buyItemLore.add(Lang.INTERFACE_BUY_LORE_1.getConfigValue(new String [] { plugin.getPU().formatAmount(amount) }));
        buyItemLore.add(Lang.INTERFACE_BUY_LORE_2.getConfigValue(new String [] { plugin.getPU().formatValue(buyValue) }));

        buyAllItemMeta.setLore(buyItemLore);
        buyAllItem.setItemMeta(buyAllItemMeta);
        inventory.setItem(39, buyAllItem);
        buyItemLore.clear();
    }

    private void sellInventoryItem() {
        OneStopShop plugin = OneStopShop.getPlugin();
        ItemStack selectedItem = new ItemStack(playerMenuUtility.getSelectedShopItem());

        ItemStack sellAllItem = plugin.getPU().createXItemStack(Config.INTERFACE_ITEM_SELL_INVENTORY.getConfigValue(null));
        ItemMeta sellAllItemMeta = sellAllItem.getItemMeta();
        sellAllItemMeta.setDisplayName(Lang.INTERFACE_SELL_INVENTORY_NAME.getConfigValue(null));

        double sellItemValue = plugin.getData().getDataShopUtil().getSellValue(selectedItem);

        //sell inventory item lore
        ArrayList<String> sellItemLore = new ArrayList<>();

        double sellValue = sellItemValue * plugin.getPU().getItemAmount(playerMenuUtility.getOwner(), selectedItem);
        int amount = plugin.getPU().getItemAmount(playerMenuUtility.getOwner().getPlayer(), selectedItem);
        sellItemLore.add(Lang.INTERFACE_SELL_LORE_1.getConfigValue(new String [] { plugin.getPU().formatAmount(amount) }));
        sellItemLore.add(Lang.INTERFACE_SELL_LORE_2.getConfigValue(new String [] { plugin.getPU().formatValue(sellValue) }));

        sellAllItemMeta.setLore(sellItemLore);
        sellAllItem.setItemMeta(sellAllItemMeta);
        inventory.setItem(41, sellAllItem);
        sellItemLore.clear();
    }
}
