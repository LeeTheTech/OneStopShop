package lee.code.onestopshop.menusystem;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
public class DataMenuUtility {

    @NonNull @Getter @Setter private String menu;

    @Getter private final List<ItemStack> menuItems = new ArrayList<>();
    @Getter private final List<String> menuShops = new ArrayList<>();
    private final ConcurrentHashMap<String, String> menuTitle = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Integer> menuSize = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<ItemStack, Integer> menuItemSlot = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<ItemStack, String> shop = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, String> shopTitle = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<ItemStack, String> itemSubMenu = new ConcurrentHashMap<>();

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

