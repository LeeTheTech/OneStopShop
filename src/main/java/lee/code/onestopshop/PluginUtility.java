package lee.code.onestopshop;

import lee.code.onestopshop.xseries.XMaterial;
import lee.code.onestopshop.xseries.XSound;
import lee.code.onestopshop.files.defaults.Config;
import lee.code.onestopshop.files.defaults.Lang;
import lee.code.onestopshop.files.defaults.Settings;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.Material;

import java.text.DecimalFormat;
import java.util.*;
import java.util.logging.Level;

public class PluginUtility {

    //color formatting string
    public String format(String format) {
        return ChatColor.translateAlternateColorCodes('&', format);
    }

    //format value of item
    public String formatValue(Double value) {
        DecimalFormat formatter = new DecimalFormat(Config.VALUE_FORMAT.getConfigValue(null));
        return Config.CURRENCY_FORMAT.getConfigValue(new String[]{formatter.format(value)});
    }

    //format item amount being sold
    public String formatAmount(Integer value) {
        DecimalFormat formatter = new DecimalFormat(Config.AMOUNT_FORMAT.getConfigValue(null));
        return formatter.format(value);
    }

    //formats item from XMat as Item Name
    public String formatMatFriendly(ItemStack item) {
        if (item.hasItemMeta() && item.getItemMeta().hasDisplayName()) {
            String dName = item.getItemMeta().getDisplayName().replaceAll("§", "&");
            return format(dName);
        }
        if (XMaterial.isOneEight() && item.getType().equals(Material.POTION)) return "Potion";
        return XMaterial.matchXMaterial(item).toString();
    }

    //formats item from XMat as ITEM_NAME
    public String formatMat(ItemStack item) {
        if (XMaterial.isOneEight() && item.getType().equals(Material.POTION)) return "POTION";

        XMaterial XMat = XMaterial.matchXMaterial(item);
        return XMat.toString().toUpperCase().replaceAll(" ", "_");
    }

    //gets amount of free space in a player inventory
    public int getAmountOfFreeSpace(Player player, ItemStack item) {
        int freeSpaceCount = 0;
        for (int slot = 0; slot <= 35; slot++) {
            ItemStack slotItem = player.getInventory().getItem(slot);
            if (slotItem == null || slotItem.getType() == Material.AIR) {
                freeSpaceCount += item.getMaxStackSize();
            } else {
                if (slotItem.isSimilar(item)) {
                    freeSpaceCount += Math.max(0, slotItem.getMaxStackSize() - slotItem.getAmount());
                }
            }
        }
        return freeSpaceCount;
    }

    //get amount of item in player inventory
    public int getItemAmount(Player player, ItemStack item) {
        if (item == null) return 0;
        int amount = 0;
        for (int i = 0; i < 36; i++) {
            final ItemStack slot = player.getInventory().getItem(i);
            if (slot == null || !slot.isSimilar(item)) continue;
            amount += slot.getAmount();
        }
        return amount;
    }

    //take items from player
    private void consumeItems(Player player, ItemStack item, int count) {
        Material mat = item.getType();
        Map<Integer, ? extends ItemStack> ammo = player.getInventory().all(mat);

        int found = 0;
        for (ItemStack stack : ammo.values()) found += stack.getAmount();
        if (count > found) return;

        for (Integer index : ammo.keySet()) {
             ItemStack stack = ammo.get(index);

            if (stack.isSimilar(item)) {
                int removed = Math.min(count, stack.getAmount());
                count -= removed;

                if (stack.getAmount() == removed) player.getInventory().setItem(index, null);
                else stack.setAmount(stack.getAmount() - removed);

                if (count <= 0) break;
            }
        }
        player.updateInventory();
    }

    //gives player items
    public boolean givePlayerItems(Player player, ItemStack item, int amount) {
        OneStopShop plugin = OneStopShop.getPlugin();

        double buyValue = plugin.getData().getDataShopUtil().getBuyValue(item) * amount;
        boolean vaultEnabled = Settings.BOOLEAN_ECONOMY_VAULT.getConfigValue();
        boolean itemEconomyEnabled = Settings.BOOLEAN_ECONOMY_ITEM.getConfigValue();
        String mat = formatMatFriendly(item);

        //vault economy check
        if (vaultEnabled) {
            Economy economy = plugin.getEconomy();
            double playerBalance = economy.getBalance(player);

            //balance check
            if (playerBalance < buyValue) {
                player.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.ERROR_TRANSACTION_BALANCE.getConfigValue(new String[]{
                        formatValue(playerBalance), formatValue(buyValue), formatAmount(amount), mat}));
                playXSound(player, Config.SOUND_TRANSACTION_FAILED.getConfigValue(null), Double.parseDouble(Config.SOUND_VOLUME_TRANSACTION_FAILED.getConfigValue(null)), Double.parseDouble(Config.SOUND_PITCH_TRANSACTION_FAILED.getConfigValue(null)));
                return false;
            }
        } else if (itemEconomyEnabled) {
            int playerBalance = getItemAmount(player, plugin.getData().getDataShopUtil().getEconomyItem());

            if (playerBalance < (int) buyValue) {
                player.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.ERROR_TRANSACTION_BALANCE.getConfigValue(new String[]{
                        formatValue((double) playerBalance), formatValue(buyValue), formatAmount(amount), mat}));
                playXSound(player, Config.SOUND_TRANSACTION_FAILED.getConfigValue(null), Double.parseDouble(Config.SOUND_VOLUME_TRANSACTION_FAILED.getConfigValue(null)), Double.parseDouble(Config.SOUND_PITCH_TRANSACTION_FAILED.getConfigValue(null)));
                return false;
            }
        }

        int space = getAmountOfFreeSpace(player, item);

        //check if trying to buy 0 items
        if (amount == 0) {
            player.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.ERROR_TRANSACTION_BUY_ZERO_ITEMS.getConfigValue(null));
            playXSound(player, Config.SOUND_TRANSACTION_FAILED.getConfigValue(null), Double.parseDouble(Config.SOUND_VOLUME_TRANSACTION_FAILED.getConfigValue(null)), Double.parseDouble(Config.SOUND_PITCH_TRANSACTION_FAILED.getConfigValue(null)));
            return false;
        }

        //space check
        if (space < amount) {
            player.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.ERROR_TRANSACTION_NO_SPACE.getConfigValue(null));
            playXSound(player, Config.SOUND_TRANSACTION_FAILED.getConfigValue(null), Double.parseDouble(Config.SOUND_VOLUME_TRANSACTION_FAILED.getConfigValue(null)), Double.parseDouble(Config.SOUND_PITCH_TRANSACTION_FAILED.getConfigValue(null)));
            return false;
        }

        //check if the item stacks by 1 or 16
        if (item.getMaxStackSize() == 1 || item.getMaxStackSize() == 16) {
            item.setAmount(1);
            for (int give = 0; give < amount; give++) {
                player.getInventory().addItem(item);
            }
        } else {
            item.setAmount(amount);
            player.getInventory().addItem(item);
        }

        //take money if using Vault
        if (vaultEnabled) {
            Economy economy = plugin.getEconomy();
            economy.withdrawPlayer(player, buyValue);

        } else if (itemEconomyEnabled) {
            ItemStack economyItem = plugin.getData().getDataShopUtil().getEconomyItem();
            consumeItems(player, economyItem, (int) buyValue);
        }

        player.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.MESSAGE_TRANSACTION_BUY_SUCCESSFUL.getConfigValue(new String[]{String.valueOf(formatAmount(amount)), mat, formatValue(buyValue)}));
        playXSound(player, Config.SOUND_TRANSACTION_SUCCESSFUL.getConfigValue(null), Double.parseDouble(Config.SOUND_VOLUME_TRANSACTION_SUCCESSFUL.getConfigValue(null)), Double.parseDouble(Config.SOUND_PITCH_TRANSACTION_SUCCESSFUL.getConfigValue(null)));
        return true;
    }

    public boolean takePlayerItems(Player player, ItemStack item, int amount, boolean mainHand) {

        OneStopShop plugin = OneStopShop.getPlugin();

        double sellValue = plugin.getData().getDataShopUtil().getSellValue(item) * amount;
        boolean vaultEnabled = Settings.BOOLEAN_ECONOMY_VAULT.getConfigValue();
        boolean itemEconomyEnabled = Settings.BOOLEAN_ECONOMY_ITEM.getConfigValue();
        String mat = formatMatFriendly(item);

        //check players item count for item being sold
        int itemCount = getItemAmount(player, item);

        if (itemCount < amount || itemCount == 0) {
            player.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.ERROR_TRANSACTION_NOT_ENOUGH_ITEMS.getConfigValue(new String[]{mat}));
            playXSound(player, Config.SOUND_TRANSACTION_FAILED.getConfigValue(null), Double.parseDouble(Config.SOUND_VOLUME_TRANSACTION_FAILED.getConfigValue(null)), Double.parseDouble(Config.SOUND_PITCH_TRANSACTION_FAILED.getConfigValue(null)));
            return false;
        }

        //vault payment
        if (vaultEnabled) {
            Economy economy = plugin.getEconomy();
            economy.depositPlayer(player, sellValue);

            // /shop sell check
            if (mainHand) {

                ItemStack air = new ItemStack(Material.AIR);
                if (XMaterial.isOneEight()) player.getInventory().setItemInHand(air);
                else player.getInventory().setItemInMainHand(air);

            } else {
                consumeItems(player, item, amount);
            }
        } else if (itemEconomyEnabled) {
            ItemStack economyItem = new ItemStack(plugin.getData().getDataShopUtil().getEconomyItem());
            int space = getAmountOfFreeSpace(player, economyItem);

            if (space < (int) sellValue) {
                player.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.ERROR_TRANSACTION_ECONOMY_ITEM_SPACE.getConfigValue(null));
                playXSound(player, Config.SOUND_TRANSACTION_FAILED.getConfigValue(null), Double.parseDouble(Config.SOUND_VOLUME_TRANSACTION_FAILED.getConfigValue(null)), Double.parseDouble(Config.SOUND_PITCH_TRANSACTION_FAILED.getConfigValue(null)));
                return false;
            } else {
                //give player economy item
                economyItem.setAmount((int) sellValue);
                player.getInventory().addItem(economyItem);
            }

            // /shop sell check
            if (mainHand) {
                ItemStack air = new ItemStack(Material.AIR);
                if (XMaterial.isOneEight()) player.getInventory().setItemInHand(air);
                else player.getInventory().setItemInMainHand(air);
            } else {
                //takes items
                consumeItems(player, item, amount);
            }
        }

        player.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.MESSAGE_TRANSACTION_SELL_SUCCESSFUL.getConfigValue(new String[]{String.valueOf(formatAmount(amount)), mat, formatValue(sellValue)}));
        playXSound(player, Config.SOUND_TRANSACTION_SUCCESSFUL.getConfigValue(null), Double.parseDouble(Config.SOUND_VOLUME_TRANSACTION_SUCCESSFUL.getConfigValue(null)), Double.parseDouble(Config.SOUND_PITCH_TRANSACTION_SUCCESSFUL.getConfigValue(null)));
        return true;
    }

    //plays sound for player, supports 1.8+
    public void playXSound(Player player, String sound, Double volume, Double pitch) {
        if (XSound.matchXSound(sound).isPresent()) {
            if (XSound.matchXSound(sound).get().isSupported()) player.playSound(player.getLocation(), Objects.requireNonNull(XSound.matchXSound(sound).get().parseSound()), volume.floatValue(), pitch.floatValue());
            else Bukkit.getLogger().log(Level.SEVERE, "[OneStopShop] The sound " + sound + " is not supported on this version, fix the sound in your config.yml.");

        } else {
            Bukkit.getLogger().log(Level.SEVERE, "[OneStopShop] The sound " + sound + " is not a sound, fix the sound in your config.yml.");
        }
    }

    //create XMat ItemStack
    public ItemStack createXItemStack(String string) {
        if (XMaterial.matchXMaterial(string).isPresent()) {
            if (XMaterial.matchXMaterial(string).get().isSupported()) {
                return XMaterial.valueOf(string).parseItem();
            } else {
                Bukkit.getLogger().log(Level.SEVERE, "[OneStopShop] The item " + string + " is not supported on your server version, this needs to be fixed.");
                return XMaterial.DIAMOND_PICKAXE.parseItem();
            }
        } else {
            Bukkit.getLogger().log(Level.SEVERE, "[OneStopShop] The item " + string + " is not a item in Minecraft, this needs to be fixed.");
            return XMaterial.DIAMOND_PICKAXE.parseItem();
        }
    }

    //delay runnable for clicking menus
    public void addPlayerClickDelay(UUID uuid) {
        OneStopShop plugin = OneStopShop.getPlugin();

        //adds player to list for delay
        plugin.getData().addPlayerClickDelay(uuid);

        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        scheduler.runTaskLater(plugin, () ->
                plugin.getData().removePlayerClickDelay(uuid), Integer.parseInt(Config.INTERFACE_CLICK_DELAY.getConfigValue(null)));
    }

    public List<String> getOnlinePlayers(){
        List<String> players = new ArrayList<>();
        for (Player player : Bukkit.getOnlinePlayers()) {
            players.add(player.getName());
        }
        return players;
    }

    //gets the players hand item depending on version
    public ItemStack getHandItem(Player player) {
        ItemStack item;
        if (XMaterial.isOneEight()) item = new ItemStack(player.getInventory().getItemInHand());
        else item = new ItemStack(player.getInventory().getItemInMainHand());
        item.setAmount(1);
        return item;
    }
}
