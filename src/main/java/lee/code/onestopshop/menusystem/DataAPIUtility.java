package lee.code.onestopshop.menusystem;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataAPIUtility {

    private final HashMap<ItemStack, Double> buyValue = new HashMap<>();
    private final HashMap<ItemStack, Double> sellValue = new HashMap<>();
    private final HashMap<String, List<ItemStack>> shopItems = new HashMap<>();

    public double getBuyValue(ItemStack item) {
        return buyValue.getOrDefault(item, 0.0);
    }

    public void setBuyValue(ItemStack item, double value) {
        buyValue.put(item, value);
    }

    public double getSellValue(ItemStack item) {
        return sellValue.getOrDefault(item, 0.0);
    }

    public void setSellValue(ItemStack item, double value) {
        sellValue.put(item, value);
    }

    public List<ItemStack> getShopItems(String shop) {
        return shopItems.get(shop);
    }

    public void addShopItem(String shop, ItemStack item) {
        List<ItemStack> items = new ArrayList<>();
        items.add(item);
        if (shopItems.get(shop) == null) shopItems.put(shop, items);
        else if (!shopItems.get(shop).contains(item)) shopItems.get(shop).add(item);
    }
}
