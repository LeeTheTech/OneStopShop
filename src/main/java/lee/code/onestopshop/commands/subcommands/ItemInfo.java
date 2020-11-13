package lee.code.onestopshop.commands.subcommands;

import lee.code.onestopshop.commands.SubCommand;
import lee.code.onestopshop.files.defaults.Lang;
import lee.code.onestopshop.OneStopShop;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemInfo extends SubCommand {

    @Override
    public String getName() {
        return "iteminfo";
    }

    @Override
    public String getDescription() {
        return Lang.MESSAGE_HELP_SUB_COMMAND_ITEM_INFO.getConfigValue(null);
    }

    @Override
    public String getSyntax() {
        return "/shop iteminfo";
    }

    @Override
    public String getPermission() {
        return "oss.shop.iteminfo";
    }

    @Override
    public void perform(Player player, String[] args) {

        OneStopShop plugin = OneStopShop.getPlugin();

        if (args.length > 0) {

            ItemStack item = plugin.getPluginUtility().getHandItem(player);

            String itemString = plugin.getPluginUtility().formatMatFriendly(item);
            String itemID = plugin.getPluginUtility().formatMat(item);

            player.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.MESSAGE_COMMAND_ITEM_INFO.getConfigValue(new String[]{itemString, itemID}));
        }
    }

    @Override
    public void performConsole(CommandSender console, String[] args) {
        console.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.ERROR_NOT_A_CONSOLE_COMMAND.getConfigValue(null));
    }
}
