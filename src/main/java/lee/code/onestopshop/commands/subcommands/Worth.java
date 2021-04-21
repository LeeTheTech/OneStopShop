package lee.code.onestopshop.commands.subcommands;

import lee.code.onestopshop.commands.SubCommand;
import lee.code.onestopshop.files.defaults.Config;
import lee.code.onestopshop.files.defaults.Lang;
import lee.code.onestopshop.OneStopShop;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Worth extends SubCommand {

    @Override
    public String getName() {
        return "worth";
    }

    @Override
    public String getDescription() {
        return Lang.MESSAGE_HELP_SUB_COMMAND_WORTH.getConfigValue(null);
    }

    @Override
    public String getSyntax() {
        return "/shop worth";
    }

    @Override
    public String getPermission() {
        return "oss.shop.worth";
    }

    @Override
    public void perform(Player player, String[] args) {

        OneStopShop plugin = OneStopShop.getPlugin();
        ItemStack item = plugin.getPU().getHandItem(player);
        String itemString = plugin.getPU().formatMatFriendly(item);

        if (plugin.getData().getDataShopUtil().getSellValue(item) != 0.0) {
            double worth = plugin.getData().getDataShopUtil().getSellValue(item);
            player.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.MESSAGE_COMMAND_WORTH.getConfigValue(new String [] { itemString, plugin.getPU().formatValue(worth) }));

        } else {
            player.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.ERROR_TRANSACTION_SELL_ITEM_NO_VALUE.getConfigValue(new String [] { itemString }));
            plugin.getPU().playXSound(player, Config.SOUND_TRANSACTION_FAILED.getConfigValue(null), Double.parseDouble(Config.SOUND_VOLUME_TRANSACTION_FAILED.getConfigValue(null)), Double.parseDouble(Config.SOUND_PITCH_TRANSACTION_FAILED.getConfigValue(null)));
        }
    }

    @Override
    public void performConsole(CommandSender console, String[] args) {
        console.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.ERROR_NOT_A_CONSOLE_COMMAND.getConfigValue(null));
    }
}
