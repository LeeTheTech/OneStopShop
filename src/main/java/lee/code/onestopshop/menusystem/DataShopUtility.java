package lee.code.onestopshop.menusystem;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class DataShopUtility {

    private final ConcurrentHashMap<ItemStack, Double> buyValue = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<ItemStack, Double> sellValue = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<ItemStack, String> itemCommand = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, List<ItemStack>> shopItems = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, List<String>> skullSkins = new ConcurrentHashMap<>();
    @Getter @Setter private ItemStack economyItem;

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
    public void setItemCommand(ItemStack item, String command) {
        itemCommand.put(item, command);
    }
    public String getItemCommand(ItemStack item) {
        return itemCommand.get(item);
    }

}
