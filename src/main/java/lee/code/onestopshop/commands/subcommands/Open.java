package lee.code.onestopshop.commands.subcommands;

import lee.code.onestopshop.OneStopShop;
import lee.code.onestopshop.commands.SubCommand;
import lee.code.onestopshop.files.defaults.Config;
import lee.code.onestopshop.files.defaults.Lang;
import lee.code.onestopshop.menusystem.menus.MainMenu;
import lee.code.onestopshop.menusystem.menus.ShopMenu;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

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
        return "/shop open <menu> <shop>";
    }

    @Override
    public String getPermission() {
        return "oss.shop.open";
    }

    @Override
    public void perform(Player player, String[] args) {
        OneStopShop plugin = OneStopShop.getPlugin();

        if (args.length > 1) {
            UUID uuid = player.getUniqueId();
            String menu = args[1].toLowerCase();
            if (plugin.getData().getMenuList().contains(menu)) {
                if (args.length > 2) {
                    String shop = args[2].toLowerCase();
                    if (plugin.getData().getShopList().contains(shop)) {
                        if (plugin.getData().getDataMenuUtil(menu).getMenuShops().contains(shop)) {
                            plugin.getData().getPlayerMenuUtil(uuid).setCurrentMenu(menu);
                            plugin.getData().getPlayerMenuUtil(uuid).setShop(shop);
                            new ShopMenu(plugin.getData().getPlayerMenuUtil(uuid)).open();
                            plugin.getPU().playXSound(player, Config.SOUND_MENU_OPEN.getConfigValue(null), Double.parseDouble(Config.SOUND_VOLUME_MENU_OPEN.getConfigValue(null)), Double.parseDouble(Config.SOUND_PITCH_MENU_OPEN.getConfigValue(null)));
                        } else player.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.ERROR_MENU_IN_SHOP_DOES_NOT_EXIST.getConfigValue(new String[]{ shop, menu }));
                    } else player.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.ERROR_SHOP_DOES_NOT_EXIST.getConfigValue(new String[]{args[2]}));
                } else {
                    plugin.getData().getPlayerMenuUtil(uuid).setCurrentMenu(menu);
                    new MainMenu(plugin.getData().getPlayerMenuUtil(uuid)).openMenu(menu);
                    plugin.getPU().playXSound(player, Config.SOUND_MENU_OPEN.getConfigValue(null), Double.parseDouble(Config.SOUND_VOLUME_MENU_OPEN.getConfigValue(null)), Double.parseDouble(Config.SOUND_PITCH_MENU_OPEN.getConfigValue(null)));
                }
            } else player.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.ERROR_MENU_DOES_NOT_EXIST.getConfigValue(new String[]{ args[1] }));
        } else player.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.ERROR_COMMAND_OPEN_ARG_1.getConfigValue(null));
    }

    @Override
    public void performConsole(CommandSender console, String[] args) {
        OneStopShop plugin = OneStopShop.getPlugin();

        if (args.length > 1) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.getName().equals(args[1])) {
                    UUID uuid = player.getUniqueId();
                    if (args.length > 2) {
                        if (plugin.getData().getMenuList().contains(args[2].toLowerCase())) {
                            String menu = args[2].toLowerCase();
                            if (args.length > 3) {
                                String shop = args[3].toLowerCase();
                                if (plugin.getData().getShopList().contains(shop)) {
                                    if (plugin.getData().getDataMenuUtil(menu).getMenuShops().contains(shop)) {
                                        plugin.getData().getPlayerMenuUtil(uuid).setCurrentMenu(menu);
                                        plugin.getData().getPlayerMenuUtil(uuid).setShop(shop);
                                        new ShopMenu(plugin.getData().getPlayerMenuUtil(player.getUniqueId())).open();
                                        plugin.getPU().playXSound(player, Config.SOUND_MENU_OPEN.getConfigValue(null), Double.parseDouble(Config.SOUND_VOLUME_MENU_OPEN.getConfigValue(null)), Double.parseDouble(Config.SOUND_PITCH_MENU_OPEN.getConfigValue(null)));
                                    } else console.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.ERROR_MENU_IN_SHOP_DOES_NOT_EXIST.getConfigValue(new String[]{ shop, menu }));
                                } else console.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.ERROR_SHOP_DOES_NOT_EXIST.getConfigValue(new String[]{args[3]}));
                            } else {
                                plugin.getData().getPlayerMenuUtil(uuid).setCurrentMenu(menu);
                                new MainMenu(plugin.getData().getPlayerMenuUtil(uuid)).openMenu(menu);
                                plugin.getPU().playXSound(player, Config.SOUND_MENU_OPEN.getConfigValue(null), Double.parseDouble(Config.SOUND_VOLUME_MENU_OPEN.getConfigValue(null)), Double.parseDouble(Config.SOUND_PITCH_MENU_OPEN.getConfigValue(null)));
                            }
                        } else console.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.ERROR_MENU_DOES_NOT_EXIST.getConfigValue(new String[]{ args[2] }));
                    } else console.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.ERROR_COMMAND_OPEN_CONSOLE_ARG_1.getConfigValue(null));
                    return;
                }
            }
            console.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.ERROR_PLAYER_NOT_ONLINE.getConfigValue(new String[] { args[1] }));
        } else console.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.ERROR_COMMAND_OPEN_CONSOLE_ARG_1.getConfigValue(null));
    }
}
