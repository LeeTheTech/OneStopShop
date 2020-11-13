package lee.code.onestopshop;

import org.bukkit.inventory.ItemStack;

public class OneStopShopAPI {

    public void addShopItem(ItemStack item, String shop, double sell, double buy) {
        OneStopShop plugin = OneStopShop.getPlugin();
        plugin.getData().getDataAPIUtility().addShopItem(shop, item);
        plugin.getData().getDataAPIUtility().setBuyValue(item, buy);
        plugin.getData().getDataAPIUtility().setSellValue(item, sell);
        plugin.getData().loadApiItems();
    }

    public double getItemSellValue(ItemStack item) {
        OneStopShop plugin = OneStopShop.getPlugin();
        return plugin.getData().getDataShopUtil().getSellValue(item);
    }

    public double getItemBuyValue(ItemStack item) {
        OneStopShop plugin = OneStopShop.getPlugin();
        return plugin.getData().getDataShopUtil().getBuyValue(item);
    }
}
