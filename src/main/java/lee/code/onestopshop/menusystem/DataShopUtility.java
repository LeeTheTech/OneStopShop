package lee.code.onestopshop.menusystem;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;

public class DataShopUtility {

    private final HashMap<ItemStack, Double> buyValue = new HashMap<>();
    private final HashMap<ItemStack, Double> sellValue = new HashMap<>();
    private final HashMap<String, List<ItemStack>> shopItems = new HashMap<>();
    @Getter @Setter private ItemStack economyItem;
    private final HashMap<String, List<String>> skullSkins = new HashMap<>();

    public void addSkullSkin(String shop, List<String> skins) {
        skullSkins.put(shop, skins);
    }

    public List<String> getSkullSkin(String shop) {
        return skullSkins.get(shop);
    }

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

    public void setShopItems(String shop, List<ItemStack> items) {
        shopItems.put(shop, items);
    }

}
