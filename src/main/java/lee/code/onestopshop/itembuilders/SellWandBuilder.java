package lee.code.onestopshop.itembuilders;

import lee.code.onestopshop.xseries.XEnchantment;
import lee.code.onestopshop.xseries.XMaterial;
import lee.code.onestopshop.files.defaults.Config;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class SellWandBuilder {

    private String name;
    private List<String> lore;

    public SellWandBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public SellWandBuilder setLore(List<String> lore) {
        this.lore = lore;
        return this;
    }

    public SellWand buildObject(String name,List<String> lore) {
        return new SellWand(name, lore);
    }

    public ItemStack buildItemStack() {
        ItemStack item = XMaterial.valueOf(Config.WAND_SELL_ITEM.getConfigValue(null)).parseItem();
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));

        List<String> lines = new ArrayList<>();
        for (String line : lore) {
            lines.add(ChatColor.translateAlternateColorCodes('&', line));
        }
        meta.setLore(lines);

        meta.addEnchant(XEnchantment.KNOCKBACK.parseEnchantment(), 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        item.setItemMeta(meta);
        return item;
    }
}
