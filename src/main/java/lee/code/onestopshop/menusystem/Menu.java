package lee.code.onestopshop.menusystem;

import lee.code.onestopshop.files.defaults.Config;
import lee.code.onestopshop.files.defaults.Lang;
import lee.code.onestopshop.OneStopShop;
import lee.code.onestopshop.xseries.XMaterial;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.logging.Level;

public abstract class Menu implements InventoryHolder {

    //protected values that can be accessed in the menus
    protected PlayerMenuUtility playerMenuUtility;
    protected Inventory inventory;
    protected ItemStack FILLER_GLASS = makeItem(Config.INTERFACE_ITEM_FILLER_GLASS.getConfigValue(null), "&r");
    protected ItemStack BACK_ITEM = makeItem(Config.INTERFACE_ITEM_BACK.getConfigValue(null), Lang.INTERFACE_BACK_MENU_NAME.getConfigValue(null));
    protected ItemStack NEXT_PAGE_ITEM = makeItem(Config.INTERFACE_ITEM_NEXT_PAGE.getConfigValue(null), Lang.INTERFACE_NEXT_PAGE_NAME.getConfigValue(null));
    protected ItemStack PREVIOUS_PAGE_ITEM = makeItem(Config.INTERFACE_ITEM_PREVIOUS_PAGE.getConfigValue(null), Lang.INTERFACE_PREVIOUS_PAGE_NAME.getConfigValue(null));

    //constructor for Menu. Pass in a PlayerMenuUtility so that
    public Menu(PlayerMenuUtility playerMenuUtility) {
        this.playerMenuUtility = playerMenuUtility;
    }

    //let each menu decide their name
    public abstract String getMenuName();

    //let each menu decide their slot amount
    public abstract int getSlots();

    //let each menu decide how the items in the menu will be handled when clicked
    public abstract void handleMenu(InventoryClickEvent e);

    //let each menu decide what items are to be placed in the inventory menu
    public abstract void setMenuItems(String menu);

    //when called, an inventory is created and opened for the player with targeted menu
    public void openMenu(String menu) {

        //the owner of the inventory created is the Menu itself
        inventory = Bukkit.createInventory(this, getSlots(), getMenuName());

        //grab all the items specified to be used for this menu and add to inventory
        this.setMenuItems(menu);

        //open the inventory for the player
        playerMenuUtility.getOwner().openInventory(inventory);
    }

    //when called, an inventory is created and opened for the player
    public void open() {
        //the owner of the inventory created is the Menu itself
        inventory = Bukkit.createInventory(this, getSlots(), getMenuName());

        //grab all the items specified to be used for this menu and add to inventory
        this.setMenuItems("none");

        //open the inventory for the player
        playerMenuUtility.getOwner().openInventory(inventory);
    }

    //overridden method from the InventoryHolder interface
    @Override
    public Inventory getInventory() {
        return inventory;
    }

    //helpful utility method to fill all remaining slots with "filler glass"
    public void setFillerGlass(){
        for (int i = 0; i < getSlots(); i++) {
            if (inventory.getItem(i) == null){
                inventory.setItem(i, FILLER_GLASS);
            }
        }
    }

    //create menu interface item ItemStack
    public ItemStack makeItem(String string, String displayName, String... lore) {

        if (XMaterial.matchXMaterial(string).isPresent()) {
            if (XMaterial.matchXMaterial(string).get().isSupported()) {
                ItemStack item = XMaterial.valueOf(string).parseItem();
                ItemMeta itemMeta = item.getItemMeta();
                itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayName));

                itemMeta.setLore(Arrays.asList(lore));
                item.setItemMeta(itemMeta);
                return item;

            } else {
                Bukkit.getLogger().log(Level.SEVERE,"[OneStopShop] The item " + string + " is not supported on your server version, you need to fix this in your config.yml.");
                return XMaterial.WHITE_STAINED_GLASS_PANE.parseItem();
            }
        } else {
            Bukkit.getLogger().log(Level.SEVERE, "[OneStopShop] The item " + string + " is not a item in Minecraft, you need to fix this in your config.yml.");
            return XMaterial.WHITE_STAINED_GLASS_PANE.parseItem();
        }
    }
}
