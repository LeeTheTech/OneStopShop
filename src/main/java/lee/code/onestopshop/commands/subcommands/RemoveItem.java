package lee.code.onestopshop.commands.subcommands;

import lee.code.onestopshop.xseries.SkullUtils;
import lee.code.onestopshop.xseries.XItemStack;
import lee.code.onestopshop.commands.SubCommand;
import lee.code.onestopshop.files.defaults.Config;
import lee.code.onestopshop.files.defaults.Lang;
import lee.code.onestopshop.OneStopShop;
import lee.code.onestopshop.xseries.XMaterial;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class RemoveItem extends SubCommand {

    @Override
    public String getName() {
        return "remove";
    }

    @Override
    public String getDescription() {
        return Lang.MESSAGE_HELP_SUB_COMMAND_REMOVE_ITEM.getConfigValue(null);
    }

    @Override
    public String getSyntax() {
        return "/shop remove &f<shop>";
    }

    @Override
    public String getPermission() {
        return "oss.shop.remove";
    }

    @Override
    public void perform(Player player, String[] args) {

        OneStopShop plugin = OneStopShop.getPlugin();

        if (args.length == 1) {
            player.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.ERROR_COMMAND_REMOVE_ARG_1.getConfigValue(null));
            return;
        }

        if (args.length > 1) {

            boolean hasItemInHand = false;
            ItemStack item = plugin.getPluginUtility().getHandItem(player);

            //shop check
            String shop;
            if (plugin.getData().getShopList().contains(args[1].toLowerCase())) {
                shop = args[1].toLowerCase();
            } else {
                player.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.ERROR_SHOP_DOES_NOT_EXIST.getConfigValue(new String[]{args[1]}));
                return;
            }

            String friendlyString = plugin.getPluginUtility().formatMatFriendly(item);

            if (item.getType() == XMaterial.PLAYER_HEAD.parseMaterial()) {
                String skullSkin = SkullUtils.getSkinValue(item.getItemMeta());
                if (!plugin.getData().getDataShopUtil().getSkullSkin(shop).contains(skullSkin)) {
                    player.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.ERROR_COMMAND_REMOVE_ITEM_NOT_FOUND.getConfigValue(new String[]{friendlyString, shop}));
                    return;
                }
            } else if (!plugin.getData().getDataShopUtil().getShopItems(shop).contains(item)) {
                player.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.ERROR_COMMAND_REMOVE_ITEM_NOT_FOUND.getConfigValue(new String[]{friendlyString, shop}));
                return;
            }

            //check to make sure they are holding a item
            if (!item.getType().equals(Material.AIR)) hasItemInHand = true;

            if (hasItemInHand) {

                FileConfiguration file = plugin.getFile("shops").getData();

                if (file.contains("shops")) {
                    for (String key : file.getConfigurationSection("shops." + shop + ".items").getKeys(false)) {
                        ConfigurationSection section = file.getConfigurationSection("shops." + shop + ".items." + key);
                        String savedSkullSkin = file.getString("shops." + shop + ".items." + key + ".skull");
                        ItemStack savedItem = XItemStack.deserialize(section);

                        //friendly item string
                        if (item.equals(savedItem) || savedSkullSkin != null) {
                            if (savedSkullSkin != null) {
                                String skullSkin = SkullUtils.getSkinValue(item.getItemMeta());
                                if (!savedSkullSkin.equals(skullSkin)) continue;
                            }
                            file.set("shops." + shop + ".items." + key, null);
                            player.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.MESSAGE_COMMAND_REMOVE_SUCCESSFUL.getConfigValue(new String[]{friendlyString, shop}));
                            plugin.saveFile("shops");
                            plugin.getData().loadData();
                            plugin.getPluginUtility().playXSound(player, Config.SOUND_TRANSACTION_SUCCESSFUL.getConfigValue(null), Double.parseDouble(Config.SOUND_VOLUME_TRANSACTION_SUCCESSFUL.getConfigValue(null)), Double.parseDouble(Config.SOUND_PITCH_TRANSACTION_SUCCESSFUL.getConfigValue(null)));
                            return;
                        }
                    }
                    System.out.println("Did not find saved item.");
                }
            } else {
                player.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.ERROR_COMMAND_REMOVE_NO_ITEM.getConfigValue(null));
            }
        }
    }

    @Override
    public void performConsole(CommandSender console, String[] args) {
        console.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.ERROR_NOT_A_CONSOLE_COMMAND.getConfigValue(null));
    }
}
