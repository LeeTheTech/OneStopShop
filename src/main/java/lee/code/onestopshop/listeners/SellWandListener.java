package lee.code.onestopshop.listeners;

import lee.code.onestopshop.files.defaults.Settings;
import lee.code.onestopshop.xseries.XMaterial;
import lee.code.onestopshop.events.SellWandInteractEvent;
import lee.code.onestopshop.files.defaults.Config;
import lee.code.onestopshop.files.defaults.Lang;
import lee.code.onestopshop.OneStopShop;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Location;
import org.bukkit.block.Barrel;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.ShulkerBox;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class SellWandListener implements Listener {

    @EventHandler
    public void onSellWandInteract(SellWandInteractEvent e) {
        OneStopShop plugin = OneStopShop.getPlugin();

        Player player = e.getWandUser();
        boolean vaultEnabled = Settings.BOOLEAN_ECONOMY_VAULT.getConfigValue();
        int totalAmount = 0;
        double totalValue = 0.0;

        for (ItemStack item : getItems(e.getContainer())) {
            if (item != null) {
                ItemStack copy = new ItemStack(item);
                copy.setAmount(1);

                if (plugin.getData().getDataShopUtil().getSellValue(copy) != 0.0) {

                    int amount = item.getAmount();
                    double value = plugin.getData().getDataShopUtil().getSellValue(copy);
                    double stackValue = (value * amount);

                    if (vaultEnabled) {
                        Economy economy = plugin.getEconomy();
                        economy.depositPlayer(player, stackValue);
                    } else {
                        ItemStack economyItem = new ItemStack(plugin.getData().getDataShopUtil().getEconomyItem());
                        if (plugin.getPU().getAmountOfFreeSpace(player, economyItem) < (int) stackValue) {
                            if ((int) totalValue == 0) {
                                player.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.ERROR_TRANSACTION_ECONOMY_ITEM_SPACE.getConfigValue(null));
                                plugin.getPU().playXSound(player, Config.SOUND_TRANSACTION_FAILED.getConfigValue(null), Double.parseDouble(Config.SOUND_VOLUME_TRANSACTION_FAILED.getConfigValue(null)), Double.parseDouble(Config.SOUND_PITCH_TRANSACTION_FAILED.getConfigValue(null)));
                            } else {
                                player.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.ERROR_SELL_WAND_NO_SPACE_ITEM_PAYMENT.getConfigValue(new String[] { plugin.getPU().formatAmount(totalAmount), plugin.getPU().formatValue(totalValue) }));
                                plugin.getPU().playXSound(player, Config.SOUND_TRANSACTION_SUCCESSFUL.getConfigValue(null), Double.parseDouble(Config.SOUND_VOLUME_TRANSACTION_SUCCESSFUL.getConfigValue(null)), Double.parseDouble(Config.SOUND_PITCH_TRANSACTION_SUCCESSFUL.getConfigValue(null)));
                            }
                            return;
                        }

                        //give player economy item
                        economyItem.setAmount((int) stackValue);
                        player.getInventory().addItem(economyItem);
                    }
                    totalValue = totalValue + (value * amount);
                    totalAmount = totalAmount + amount;
                    removeItem(e.getContainer(), item);
                }
            }
        }
        if (totalValue != 0.0) {
            player.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.MESSAGE_SELL_WAND_SUCCESSFUL.getConfigValue(new String[] {
                    plugin.getPU().formatAmount(totalAmount), plugin.getPU().formatValue(totalValue) }));
            plugin.getPU().playXSound(player, Config.SOUND_TRANSACTION_SUCCESSFUL.getConfigValue(null), Double.parseDouble(Config.SOUND_VOLUME_TRANSACTION_SUCCESSFUL.getConfigValue(null)), Double.parseDouble(Config.SOUND_PITCH_TRANSACTION_SUCCESSFUL.getConfigValue(null)));
        } else {
            player.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.ERROR_SELL_WAND_NO_VALUE.getConfigValue(null));
            plugin.getPU().playXSound(player, Config.SOUND_TRANSACTION_FAILED.getConfigValue(null), Double.parseDouble(Config.SOUND_VOLUME_TRANSACTION_FAILED.getConfigValue(null)), Double.parseDouble(Config.SOUND_PITCH_TRANSACTION_FAILED.getConfigValue(null)));
        }
    }

    public List<ItemStack> getItems(Block container) {

        Location loc = container.getLocation();
        List<ItemStack> items = new ArrayList<>();

        if (container.getState() instanceof Chest) {
            Chest chest = (Chest) loc.getBlock().getState();
            items = Arrays.asList(chest.getInventory().getContents());
        } else if (XMaterial.supports(11) && container.getState() instanceof ShulkerBox) {
            ShulkerBox shulkerBox = (ShulkerBox) loc.getBlock().getState();
            items = Arrays.asList(shulkerBox.getInventory().getContents());
        } else if (XMaterial.supports(14) && container.getState() instanceof Barrel) {
            Barrel barrel = (Barrel) loc.getBlock().getState();
            items = Arrays.asList(barrel.getInventory().getContents());
        }

        return items;
    }

    public void removeItem(Block container, ItemStack item) {

        Location loc = container.getLocation();

        if (container.getState() instanceof Chest) {
            Chest chest = (Chest) loc.getBlock().getState();
            chest.getInventory().removeItem(item);
        } else if (XMaterial.supports(11) && container.getState() instanceof ShulkerBox) {
            ShulkerBox shulkerBox = (ShulkerBox) loc.getBlock().getState();
            shulkerBox.getInventory().removeItem(item);
        } else if (XMaterial.supports(14) && container.getState() instanceof Barrel) {
            Barrel barrel = (Barrel) loc.getBlock().getState();
            barrel.getInventory().removeItem(item);
        }
    }
}
