package lee.code.onestopshop.commands.subcommands;

import lee.code.onestopshop.OneStopShop;
import lee.code.onestopshop.commands.SubCommand;
import lee.code.onestopshop.files.defaults.Config;
import lee.code.onestopshop.files.defaults.Lang;
import lee.code.onestopshop.menusystem.menus.MainMenu;
import lee.code.onestopshop.menusystem.menus.ShopMenu;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Open extends SubCommand  {

    @Override
    public String getName() {
        return "open";
    }

    @Override
    public String getDescription() {
        return Lang.MESSAGE_HELP_SUB_COMMAND_OPEN.getConfigValue(null);
    }

    @Override
    public String getSyntax() {
        return "/shop open &f<menu> <shop>";
    }

    @Override
    public String getPermission() {
        return "oss.shop.open";
    }

    @Override
    public void perform(Player player, String[] args) {

        OneStopShop plugin = OneStopShop.getPlugin();

        if (args.length == 1) {
            player.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.ERROR_COMMAND_OPEN_ARG_1.getConfigValue(null));
            return;
        }

        if (args.length > 1) {

            //menu check
            String menu;
            if (plugin.getData().getMenuList().contains(args[1].toLowerCase())) {
                menu = args[1].toLowerCase();
            } else {
                player.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.ERROR_MENU_DOES_NOT_EXIST.getConfigValue(new String[]{args[1]}));
                return;
            }

            if (args.length > 2) {

                //shop check
                String shop;
                if (plugin.getData().getShopList().contains(args[2].toLowerCase())) {
                    shop = args[2].toLowerCase();
                } else {
                    player.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.ERROR_SHOP_DOES_NOT_EXIST.getConfigValue(new String[]{args[2]}));
                    return;
                }

                if (plugin.getData().getDataMenuUtil(menu).getMenuShops().contains(shop)) {
                    plugin.getData().getPlayerMenuUtil(player.getUniqueId()).setCurrentMenu(menu);
                    plugin.getData().getPlayerMenuUtil(player.getUniqueId()).setShop(shop);
                    new ShopMenu(plugin.getData().getPlayerMenuUtil(player.getUniqueId())).open();
                } else {
                    player.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.ERROR_MENU_IN_SHOP_DOES_NOT_EXIST.getConfigValue(new String[]{ shop, menu }));
                    return;
                }

            } else {
                plugin.getData().getPlayerMenuUtil(player.getUniqueId()).setCurrentMenu(menu);
                new MainMenu(plugin.getData().getPlayerMenuUtil(player.getUniqueId())).openMenu(menu);
            }
            plugin.getPU().playXSound(player, Config.SOUND_MENU_OPEN.getConfigValue(null), Double.parseDouble(Config.SOUND_VOLUME_MENU_OPEN.getConfigValue(null)), Double.parseDouble(Config.SOUND_PITCH_MENU_OPEN.getConfigValue(null)));
        }
    }

    @Override
    public void performConsole(CommandSender console, String[] args) {
        console.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.ERROR_NOT_A_CONSOLE_COMMAND.getConfigValue(null));
    }
}
