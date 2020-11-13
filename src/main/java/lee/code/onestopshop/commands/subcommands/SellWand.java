package lee.code.onestopshop.commands.subcommands;

import lee.code.onestopshop.commands.SubCommand;
import lee.code.onestopshop.files.defaults.Lang;
import lee.code.onestopshop.itembuilders.SellWandBuilder;
import lee.code.onestopshop.OneStopShop;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Scanner;

public class SellWand extends SubCommand {

    @Override
    public String getName() {
        return "sellwand";
    }

    @Override
    public String getDescription() {
        return Lang.MESSAGE_HELP_SUB_COMMAND_SELL_WAND.getConfigValue(null);
    }

    @Override
    public String getSyntax() {
        return "/shop sellwand &f<amount> <player>";
    }

    @Override
    public String getPermission() {
        return "oss.shop.sellwand";
    }

    @Override
    public void perform(Player player, String[] args) {
        OneStopShop plugin = OneStopShop.getPlugin();

        int amount = 1;
        Player target = player;

        if (args.length >= 2) {

            Scanner buyScanner = new Scanner(args[1]);
            if (buyScanner.hasNextInt()) {
                amount = Integer.parseInt(args[1]);
            } else {
                player.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.ERROR_COMMAND_SELL_WAND_VALUE.getConfigValue(new String[]{ args[1]} ));
                return;
            }

            //check if player is online and if a target was given
            if (args.length >= 3) {

                if (plugin.getPluginUtility().getOnlinePlayers().contains(args[2])) {
                    //target player
                    target = Bukkit.getPlayer(args[2]);
                } else {
                    player.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.ERROR_PLAYER_NOT_ONLINE.getConfigValue(new String[]{args[2]}));
                    return;
                }
            }
        }

        ItemStack item = new SellWandBuilder().setName(Lang.WAND_SELL_WAND_NAME.getConfigValue(null)).setLore(Arrays.asList(Lang.WAND_SELL_WAND_LORE.getConfigValue(null))).buildItemStack();

        String itemString = plugin.getPluginUtility().formatMatFriendly(item);
        if (plugin.getPluginUtility().getAmountOfFreeSpace(target, item) > 0) {
            item.setAmount(amount);
            target.getInventory().addItem(item);

        } else target.getLocation().getWorld().dropItemNaturally(target.getLocation(), item);

        target.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.MESSAGE_COMMAND_SELL_WAND_GIVEN_SUCCESSFUL.getConfigValue(new String[] { plugin.getPluginUtility().formatAmount(amount), itemString }));

        if (player != target) {
            player.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.MESSAGE_COMMAND_SELL_WAND_GAVE_SUCCESSFUL.getConfigValue(new String[] { target.getDisplayName(), plugin.getPluginUtility().formatAmount(amount), itemString }));
        }
    }

    @Override
    public void performConsole(CommandSender console, String[] args) {

        OneStopShop plugin = OneStopShop.getPlugin();

        int amount;
        Player target;

        if (args.length == 1) {
            console.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.ERROR_NO_TARGET_PLAYER.getConfigValue(null));
            return;
        }

        if (args.length > 1) {

            Scanner buyScanner = new Scanner(args[1]);
            if (buyScanner.hasNextInt()) {
                amount = Integer.parseInt(args[1]);
            } else {
                console.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.ERROR_COMMAND_SELL_WAND_VALUE.getConfigValue(new String[]{ args[1]} ));
                return;
            }

            //check if player is online and if a target was given
            if (args.length >= 3) {

                if (plugin.getPluginUtility().getOnlinePlayers().contains(args[2])) {
                    //target player
                    target = Bukkit.getPlayer(args[2]);
                } else {
                    console.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.ERROR_PLAYER_NOT_ONLINE.getConfigValue(new String[]{args[2]}));
                    return;
                }
            } else {
                console.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.ERROR_NO_TARGET_PLAYER.getConfigValue(null));
                return;
            }

            ItemStack item = new SellWandBuilder().setName(Lang.WAND_SELL_WAND_NAME.getConfigValue(null)).setLore(Arrays.asList(Lang.WAND_SELL_WAND_LORE.getConfigValue(null))).buildItemStack();

            String itemString = plugin.getPluginUtility().formatMatFriendly(item);
            if (plugin.getPluginUtility().getAmountOfFreeSpace(target, item) > 0) {
                item.setAmount(amount);
                target.getInventory().addItem(item);
            } else {
                target.getLocation().getWorld().dropItemNaturally(target.getLocation(), item);
            }
            console.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.MESSAGE_COMMAND_SELL_WAND_GAVE_SUCCESSFUL.getConfigValue(new String[] { target.getDisplayName(), plugin.getPluginUtility().formatAmount(amount), itemString }));
            target.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.MESSAGE_COMMAND_SELL_WAND_GIVEN_SUCCESSFUL.getConfigValue(new String[] { plugin.getPluginUtility().formatAmount(amount), itemString }));
        }
    }
}
