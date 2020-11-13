package lee.code.onestopshop.commands.subcommands;

import lee.code.onestopshop.xseries.SkullUtils;
import lee.code.onestopshop.xseries.XItemStack;
import lee.code.onestopshop.commands.SubCommand;
import lee.code.onestopshop.files.defaults.Config;
import lee.code.onestopshop.files.defaults.Lang;
import lee.code.onestopshop.OneStopShop;
import lee.code.onestopshop.xseries.XMaterial;
import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class AddItem extends SubCommand {

    @Override
    public String getName() {
        return "add";
    }

    @Override
    public String getDescription() {
        return Lang.MESSAGE_HELP_SUB_COMMAND_ADD_ITEM.getConfigValue(null);
    }

    @Override
    public String getSyntax() {
        return "/shop add &f<shop> <sell> <buy>";
    }

    @Override
    public String getPermission() {
        return "oss.shop.add";
    }

    @Override
    public void perform(Player player, String[] args) {
        OneStopShop plugin = OneStopShop.getPlugin();

        //shop add
        if (args.length == 1) {
            player.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.ERROR_COMMAND_ADD_ARG_1.getConfigValue(null));
            return;
        }

        if (args.length > 1) {

            //shop add <category>
            if (args.length == 2) {
                player.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.ERROR_COMMAND_ADD_ARG_2.getConfigValue(null));
                return;
            } else if (args.length == 3) {
                player.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.ERROR_COMMAND_ADD_ARG_3.getConfigValue(null));
                return;
            }

            //shop check
            String shop;
            if (plugin.getData().getShopList().contains(args[1].toLowerCase())) {
                shop = args[1].toLowerCase();
            } else {
                player.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.ERROR_SHOP_DOES_NOT_EXIST.getConfigValue(new String[]{args[1]}));
                return;
            }

            //sell value check
            double sell;
            Scanner sellScanner = new Scanner(args[2]);
            if (sellScanner.hasNextInt() || sellScanner.hasNextDouble()) {
                sell = Double.parseDouble(args[2]);
            } else {
                player.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.ERROR_COMMAND_ADD_VALUE.getConfigValue(new String[]{args[2]}));
                return;
            }

            //buy value check
            double buy;
            Scanner buyScanner = new Scanner(args[3]);
            if (buyScanner.hasNextInt() || buyScanner.hasNextDouble()) {
                buy = Double.parseDouble(args[3]);
            } else {
                player.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.ERROR_COMMAND_ADD_VALUE.getConfigValue(new String[]{args[3]}));
                return;
            }

            boolean hasItemInHand = false;

            ItemStack item = plugin.getPluginUtility().getHandItem(player);

            //check if the item is saved
            if (plugin.getData().getDataShopUtil().getShopItems(shop).contains(item)) {
                player.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.ERROR_SHOP_ITEM_ADD_FAILED.getConfigValue(new String [] { plugin.getPluginUtility().formatMatFriendly(item), shop }));
                return;
            } else if (item.getType() == XMaterial.PLAYER_HEAD.parseMaterial()) {
                String skullSkin = SkullUtils.getSkinValue(item.getItemMeta());
                if (plugin.getData().getDataShopUtil().getSkullSkin(shop).contains(skullSkin)) {
                    player.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.ERROR_SHOP_ITEM_ADD_FAILED.getConfigValue(new String [] { plugin.getPluginUtility().formatMatFriendly(item), shop }));
                    return;
                }
            }

            if (!item.getType().equals(Material.AIR)) hasItemInHand = true;

            if (hasItemInHand) {

                FileConfiguration file = plugin.getFile("shops").getData();

                //format friendly mat to Item Name
                String itemString = plugin.getPluginUtility().formatMatFriendly(item);

                //key for item in file
                int var = 1;
                List<String> number = new ArrayList<>();

                //get all item keys
                if (file.contains("shops")) number.addAll(file.getConfigurationSection("shops." + shop + ".items").getKeys(false));

                //system for making sure a key is never used two times
                if (number.contains(String.valueOf(var))) {
                    for (int i = 0; i < 10000; i++) {
                        var++;
                        if (!number.contains(String.valueOf(var))) break;
                    }
                }

                file.set("shops." + shop + ".items." + var + ".buy", buy);
                file.set("shops." + shop + ".items." + var + ".sell", sell);

                ConfigurationSection section = file.getConfigurationSection("shops." + shop + ".items." + var);
                XItemStack.serialize(item, section);

                plugin.saveFile("shops");
                plugin.getData().loadData();
                player.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.MESSAGE_SHOP_ITEM_ADDED_SUCCESSFUL.getConfigValue(new String[]{itemString, shop}));
                plugin.getPluginUtility().playXSound(player, Config.SOUND_TRANSACTION_SUCCESSFUL.getConfigValue(null), Double.parseDouble(Config.SOUND_VOLUME_TRANSACTION_SUCCESSFUL.getConfigValue(null)), Double.parseDouble(Config.SOUND_PITCH_TRANSACTION_SUCCESSFUL.getConfigValue(null)));

            } else {
                //player is not holding item
                player.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.ERROR_COMMAND_ADD_NO_ITEM.getConfigValue(null));
            }
        }
    }

    @Override
    public void performConsole(CommandSender console, String[] args) {
        console.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.ERROR_NOT_A_CONSOLE_COMMAND.getConfigValue(null));
    }
}
