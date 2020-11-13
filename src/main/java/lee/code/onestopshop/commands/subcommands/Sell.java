package lee.code.onestopshop.commands.subcommands;

import lee.code.onestopshop.commands.SubCommand;
import lee.code.onestopshop.files.defaults.Config;
import lee.code.onestopshop.files.defaults.Lang;
import lee.code.onestopshop.OneStopShop;
import lee.code.onestopshop.xseries.XMaterial;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Sell extends SubCommand {

    @Override
    public String getName() {
        return "sell";
    }

    @Override
    public String getDescription() {
        return Lang.MESSAGE_HELP_SUB_COMMAND_SELL.getConfigValue(null);
    }

    @Override
    public String getSyntax() {
        return "/shop sell";
    }

    @Override
    public String getPermission() {
        return "oss.shop.sell";
    }

    @Override
    public void perform(Player player, String[] args) {
        OneStopShop plugin = OneStopShop.getPlugin();

        ItemStack item;
        //util was not used, need stack number
        if (XMaterial.isOneEight()) item = new ItemStack(player.getInventory().getItemInHand());
        else item = new ItemStack(player.getInventory().getItemInMainHand());

        //gets amount in stack
        int amount = item.getAmount();
        item.setAmount(1);

        if (plugin.getData().getDataShopUtil().getSellValue(item) != 0.0) {
            plugin.getPluginUtility().takePlayerItems(player, item, amount, true);
        } else {
            player.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.ERROR_TRANSACTION_SELL_ITEM_NO_VALUE.getConfigValue(new String [] { plugin.getPluginUtility().formatMatFriendly(item) }));
            plugin.getPluginUtility().playXSound(player, Config.SOUND_TRANSACTION_FAILED.getConfigValue(null), Double.parseDouble(Config.SOUND_VOLUME_TRANSACTION_FAILED.getConfigValue(null)), Double.parseDouble(Config.SOUND_PITCH_TRANSACTION_FAILED.getConfigValue(null)));
        }
    }

    @Override
    public void performConsole(CommandSender console, String[] args) {
        console.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.ERROR_NOT_A_CONSOLE_COMMAND.getConfigValue(null));
    }
}
