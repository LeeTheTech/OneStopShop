package lee.code.onestopshop.menusystem.menus;

import lee.code.onestopshop.files.defaults.Config;
import lee.code.onestopshop.files.defaults.Lang;
import lee.code.onestopshop.menusystem.PlayerMenuUtility;
import lee.code.onestopshop.OneStopShop;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import lee.code.onestopshop.menusystem.PaginatedMenu;

import java.util.ArrayList;

public class ShopMenu extends PaginatedMenu {

    public ShopMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        OneStopShop plugin = OneStopShop.getPlugin();
        String menu = playerMenuUtility.getCurrentMenu();
        String shopTitle = plugin.getData().getDataMenuUtil(menu).getShopTitle(playerMenuUtility.getShop());
        return Lang.INTERFACE_SHOP_MENU_TITLE.getConfigValue(new String [] { plugin.getPU().format(shopTitle), String.valueOf(page + 1) });
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

        ItemStack item = e.getCurrentItem();

        //return if players inventory
        if (e.getClickedInventory() == playerMenuUtility.getOwner().getInventory()) return;

        //check for air
        if (item == null || item.getType().equals(Material.AIR)) return;

        //check for filler glass
        if (item.equals(FILLER_GLASS)) return;

        Player p = (Player) e.getWhoClicked();
        String shop = playerMenuUtility.getShop();
        ArrayList<ItemStack> items = new ArrayList<>(plugin.getData().getDataShopUtil().getShopItems(shop));
        
        if (item.equals(BACK_ITEM)) {
            new MainMenu(playerMenuUtility).openMenu(playerMenuUtility.getCurrentMenu());
            plugin.getPU().playXSound(playerMenuUtility.getOwner(), Config.SOUND_MENU_CLICK.getConfigValue(null), Double.parseDouble(Config.SOUND_VOLUME_MENU_CLICK.getConfigValue(null)), Double.parseDouble(Config.SOUND_PITCH_MENU_CLICK.getConfigValue(null)));

        } else if (item.equals(PREVIOUS_PAGE_ITEM)) {
            if (page == 0) p.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.ERROR_PREVIOUS_PAGE.getConfigValue(null));
            else {
                page = page - 1;
                super.open();
                plugin.getPU().playXSound(playerMenuUtility.getOwner(), Config.SOUND_MENU_CLICK.getConfigValue(null), Double.parseDouble(Config.SOUND_VOLUME_MENU_CLICK.getConfigValue(null)), Double.parseDouble(Config.SOUND_PITCH_MENU_CLICK.getConfigValue(null)));
            }

        } else if (item.equals(NEXT_PAGE_ITEM)) {
            if (!((index + 1) >= items.size())) {
                page = page + 1;
                super.open();
                plugin.getPU().playXSound(playerMenuUtility.getOwner(), Config.SOUND_MENU_CLICK.getConfigValue(null), Double.parseDouble(Config.SOUND_VOLUME_MENU_CLICK.getConfigValue(null)), Double.parseDouble(Config.SOUND_PITCH_MENU_CLICK.getConfigValue(null)));
            } else p.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.ERROR_NEXT_PAGE.getConfigValue(null));

        } else if (plugin.getData().getDataShopUtil().getItemCommand(item) != null) {
            playerMenuUtility.setSelectedShopItem(e.getCurrentItem());
            new CMDTransactionMenu(playerMenuUtility).open();
            plugin.getPU().playXSound(playerMenuUtility.getOwner(), Config.SOUND_MENU_CLICK.getConfigValue(null), Double.parseDouble(Config.SOUND_VOLUME_MENU_CLICK.getConfigValue(null)), Double.parseDouble(Config.SOUND_PITCH_MENU_CLICK.getConfigValue(null)));

        } else if (items.contains(item)) {
            playerMenuUtility.setSelectedShopItem(item);
            new TransactionMenu(playerMenuUtility).open();
            plugin.getPU().playXSound(playerMenuUtility.getOwner(), Config.SOUND_MENU_CLICK.getConfigValue(null), Double.parseDouble(Config.SOUND_VOLUME_MENU_CLICK.getConfigValue(null)), Double.parseDouble(Config.SOUND_PITCH_MENU_CLICK.getConfigValue(null)));
        }
    }


    @Override
    public void setMenuItems(String menu) {
        OneStopShop plugin = OneStopShop.getPlugin();
        addMenuBorder();

        //items to loop through
        String shop = playerMenuUtility.getShop();

        //create category in shops.yml if missing
        if (plugin.getData().getDataShopUtil().getShopItems(shop) == null) {
            FileConfiguration file = plugin.getFile("shops").getData();
            file.set("shops." + shop + ".items.1.material", "DIRT");
            file.set("shops." + shop + ".items.1.buy", 100.0);
            file.set("shops." + shop + ".items.1.sell", 20.0);
            plugin.saveFile("shops");
            plugin.getData().loadData();
        }

        ArrayList<ItemStack> items = new ArrayList<>(plugin.getData().getDataShopUtil().getShopItems(shop));

        //pagination loop
        if(!items.isEmpty()) {
            for(int i = 0; i < getMaxItemsPerPage(); i++) {
                index = getMaxItemsPerPage() * page + i;
                if(index >= items.size()) break;
                if (items.get(index) != null) {
                    ItemStack theItem = items.get(index);
                    inventory.addItem(theItem);
                }
            }
        }
    }
}
