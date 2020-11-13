package lee.code.onestopshop.menusystem;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
public class DataMenuUtility {

    //menu name in config
    @NonNull @Getter @Setter private String menu;

    @Getter private final List<ItemStack> menuItems = new ArrayList<>();
    @Getter private final List<String> menuShops = new ArrayList<>();
    private final HashMap<String, String> menuTitle = new HashMap<>();
    private final HashMap<String, Integer> menuSize = new HashMap<>();
    private final HashMap<ItemStack, Integer> menuItemSlot = new HashMap<>();
    private final HashMap<ItemStack, String> shop = new HashMap<>();
    private final HashMap<String, String> shopTitle = new HashMap<>();
    private final HashMap<ItemStack, String> itemSubMenu = new HashMap<>();

    public String getItemSubMenu(ItemStack item) {
        return itemSubMenu.get(item);
    }

    public void setItemSubMenu(ItemStack item, String menu) {
        itemSubMenu.put(item, menu);
    }

    public String getShopTitle(String shop) {
        return shopTitle.get(shop);
    }

    public void setShopTitle(String shop, String shopName) {
        shopTitle.put(shop, shopName);
    }

    public String getShop(ItemStack item) {
        return shop.get(item);
    }

    public void setShop(ItemStack item, String shopName) {
        shop.put(item, shopName);
    }

    public void setMenuItemSlot(ItemStack item, int slot) {
        menuItemSlot.put(item, slot);
    }

    public void addMenuItem(ItemStack item) {
        menuItems.add(item);
    }

    public void addMenuShop(String string) {
        menuShops.add(string);
    }

    public void setMenuTitle(String menu, String title) {
        menuTitle.put(menu, title);
    }

    public void setMenuSize(String menu, Integer size) {
        menuSize.put(menu, size);
    }

    public String getMenuTitle(String menu) {
        return menuTitle.get(menu);
    }

    public int getMenuSize(String menu) {
        return menuSize.get(menu);
    }

    public int getMenuItemSlot(ItemStack item) {
        return menuItemSlot.get(item);
    }
}

