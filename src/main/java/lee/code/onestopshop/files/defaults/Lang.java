package lee.code.onestopshop.files.defaults;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

@AllArgsConstructor
public enum Lang {
    PREFIX("PREFIX", "&2&lShop &e➔ &r"),
    INTERFACE_SHOP_MENU_TITLE("INTERFACE_SHOP_MENU_TITLE", "{0} &e➔ &8Page &8{1}"),
    INTERFACE_TRANSACTION_MENU_TITLE("INTERFACE_TRANSACTION_MENU_TITLE", "&2&lShop &e➔ &r{0}"),
    INTERFACE_BUY_NAME("INTERFACE_BUY_NAME", "&a&lBuy"),
    INTERFACE_BUY_LORE_1("INTERFACE_BUY_LORE_1", "&eAmount&7: &a{0}"),
    INTERFACE_BUY_LORE_2("INTERFACE_BUY_LORE_2", "&eCost&7: &6{0}"),
    INTERFACE_BUY_INVENTORY_NAME("INTERFACE_BUY_INVENTORY_NAME", "&a&lBuy Inventory"),
    INTERFACE_SELL_NAME("INTERFACE_SELL_NAME", "&c&lSell"),
    INTERFACE_SELL_LORE_1("INTERFACE_SELL_LORE_1", "&eAmount&7: &a{0}"),
    INTERFACE_SELL_LORE_2("INTERFACE_SELL_LORE_2", "&eReceive&7: &6{0}"),
    INTERFACE_SELL_INVENTORY_NAME("INTERFACE_SELL_INVENTORY_NAME", "&c&lSell Inventory"),
    INTERFACE_NEXT_PAGE_NAME("INTERFACE_NEXT_PAGE_NAME", "&eNext Page >"),
    INTERFACE_PREVIOUS_PAGE_NAME("INTERFACE_PREVIOUS_PAGE_NAME", "&e< Previous Page"),
    INTERFACE_CLOSE_MENU_NAME("INTERFACE_CLOSE_MENU_NAME", "&c&lClose"),
    INTERFACE_BACK_MENU_NAME("INTERFACE_BACK_MENU_NAME", "&6&l<- Back"),
    MESSAGE_RELOAD("MESSAGE_RELOAD", "&aThe plugin has been reloaded."),
    MESSAGE_GIVE_SPAWNER_SUCCESSFUL("MESSAGE_GIVE_SPAWNER_SUCCESSFUL", "&aYou have been given &6{0} {1}&a."),
    MESSAGE_GAVE_SPAWNER_SUCCESSFUL("MESSAGE_GAVE_SPAWNER_SUCCESSFUL", "&aYou successfully gave &6{0} &6{1} {2}&a."),
    MESSAGE_COMMAND_WORTH("MESSAGE_COMMAND_WORTH", "&aItem &6{0} &ais worth {1}&a."),
    MESSAGE_TRANSACTION_BUY_SUCCESSFUL("MESSAGE_TRANSACTION_BUY_SUCCESSFUL", "&aYou just bought &3{0} {1} &afor {2}&a!"),
    MESSAGE_TRANSACTION_SELL_SUCCESSFUL("MESSAGE_TRANSACTION_SELL_SUCCESSFUL", "&aYou just sold &3{0} {1} &afor {2}&a!"),
    MESSAGE_SHOP_ITEM_ADDED_SUCCESSFUL("MESSAGE_SHOP_ITEM_ADDED_SUCCESSFUL", "&aYou have successfully added the item &6{0} &ato the &6{1} &ashop!"),
    MESSAGE_COMMAND_REMOVE_SUCCESSFUL("MESSAGE_COMMAND_REMOVE_SUCCESSFUL", "&aThe item &6{0} &awas successfully removed from shop &6{1}&a."),
    MESSAGE_HELP_DIVIDER("MESSAGE_HELP_DIVIDER", "&e▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬"),
    MESSAGE_HELP_TITLE("MESSAGE_HELP_TITLE", "                   &3-== &6&l&nShop Help&r &3==-"),
    MESSAGE_HELP_SUB_COMMAND("MESSAGE_HELP_SUB_COMMAND", "&3{0}&b. &e{1} &c| &7{2}"),
    MESSAGE_HELP_SHOP_COMMAND("MESSAGE_HELP_SHOP_COMMAND", "&3{0}&b. &e/shop &c|&7 Opens shop category item menu."),
    MESSAGE_HELP_SUB_COMMAND_ADD_ITEM("MESSAGE_HELP_SUB_COMMAND_ADD_ITEM", "Add the item you're holding to a shop."),
    MESSAGE_HELP_SUB_COMMAND_OPEN("MESSAGE_HELP_SUB_COMMAND_OPEN", "Opens specified menu or shop."),
    MESSAGE_HELP_SUB_COMMAND_SPAWNER("MESSAGE_HELP_SUB_COMMAND_SPAWNER", "Gives you or targeted player a specified spawner."),
    MESSAGE_HELP_SUB_COMMAND_ITEM_INFO("MESSAGE_HELP_SUB_COMMAND_ITEM_INFO", "Check the item ID of the item you're holding."),
    MESSAGE_HELP_SUB_COMMAND_RELOAD("MESSAGE_HELP_SUB_COMMAND_RELOAD", "Reload the plugin."),
    MESSAGE_HELP_SUB_COMMAND_REMOVE_ITEM("MESSAGE_HELP_SUB_COMMAND_REMOVE_ITEM", "Remove a item from a shop."),
    MESSAGE_HELP_SUB_COMMAND_SELL("MESSAGE_HELP_SUB_COMMAND_SELL", "Sell the item you're holding."),
    MESSAGE_HELP_SUB_COMMAND_SELL_ALL("MESSAGE_HELP_SUB_COMMAND_SELL_ALL", "Sell all the items of the item you're holding in your inventory."),
    MESSAGE_HELP_SUB_COMMAND_WORTH("MESSAGE_HELP_SUB_COMMAND_WORTH", "Check the worth of a item."),
    MESSAGE_HELP_SUB_COMMAND_SELL_WAND("MESSAGE_HELP_SUB_COMMAND_SELL_WAND", "Gives you or targeted player a sell wand."),
    MESSAGE_COMMAND_ITEM_INFO("MESSAGE_COMMAND_ITEM_INFO", "&aThe item &6{0} &ahas the ID &6{1}&a."),
    MESSAGE_COMMAND_SELL_WAND_GIVEN_SUCCESSFUL("MESSAGE_COMMAND_SELL_WAND_GIVEN_SUCCESSFUL", "&aYou have been given &3{0} {1}&a!"),
    MESSAGE_COMMAND_SELL_WAND_GAVE_SUCCESSFUL("MESSAGE_COMMAND_SELL_WAND_GAVE_SUCCESSFUL", "&aYou successfully gave &6{0} &6{1} {2}&a."),
    MESSAGE_SELL_WAND_SUCCESSFUL("MESSAGE_SELL_WAND_SUCCESSFUL", "&aYou sold &3{0} items &afor {1}&a!"),
    ERROR_SELL_WAND_NO_VALUE("ERROR_SELL_WAND_NO_VALUE", "&cSadly that container did not have any items of value."),
    ERROR_SELL_WAND_NO_SPACE_ITEM_PAYMENT("ERROR_SELL_WAND_NO_SPACE_ITEM_PAYMENT", "&aYou ran out of inventory space but you sold &3{0} items &afor {1}&a! "),
    ERROR_COMMAND_OPEN_ARG_1("ERROR_COMMAND_OPEN_ARG_1", "&cYou need to input a menu and shop."),
    ERROR_SHOP_ITEM_ADD_FAILED("ERROR_SHOP_ITEM_ADD_FAILED", "&cThe item &6{0} &chas already been added to the &6{1} &cshop."),
    ERROR_TRANSACTION_BALANCE("ERROR_TRANSACTION_BALANCE", "&cSadly you only have &6{0} &cand you need &6{1} &cto buy &3{2} {3}&c."),
    ERROR_TRANSACTION_ECONOMY_ITEM_SPACE("ERROR_TRANSACTION_ECONOMY_ITEM_SPACE", "&cSadly you do not have enough inventory space to receive your payment."),
    ERROR_COMMAND_ADD_ARG_1("ERROR_COMMAND_ADD_ARG_1", "&cYou need to select a shop and set a sell and buy value while holding the item you want to add."),
    ERROR_COMMAND_ADD_ARG_2("ERROR_COMMAND_ADD_ARG_2", "&cYou need to input a sell and buy value, if you don't want to allow users to sell or buy this item set them to 0."),
    ERROR_COMMAND_ADD_ARG_3("ERROR_COMMAND_ADD_ARG_3", "&cYou need to input a buy value, if you don't want to allow users to buy this item set it to 0."),
    ERROR_SHOP_DOES_NOT_EXIST("ERROR_SHOP_DOES_NOT_EXIST", "&cThe shop &6{0} &cdoes not exist."),
    ERROR_MENU_IN_SHOP_DOES_NOT_EXIST("ERROR_MENU_IN_SHOP_DOES_NOT_EXIST", "&cThe shop &6{0} &cdoes not exist in the menu &6{1}&c."),
    ERROR_MENU_DOES_NOT_EXIST("ERROR_MENU_DOES_NOT_EXIST", "&cThe menu &6{0} &cdoes not exist."),
    ERROR_COMMAND_ADD_VALUE("ERROR_COMMAND_ADD_VALUE", "&cThe input &6{0} &cis not a number. &a&lUse&7: &e(1 or 1.5)"),
    ERROR_COMMAND_SPAWNER_VALUE("ERROR_COMMAND_SPAWNER_VALUE", "&cThe input &6{0} &cis not a number. &a&lUse&7: &e(1)"),
    ERROR_COMMAND_SELL_WAND_VALUE("ERROR_COMMAND_SELL_WAND_VALUE", "&cThe input &6{0} &cis not a number. &a&lUse&7: &e(1)"),
    ERROR_COMMAND_SPAWNER_DOES_NOT_EXIST("ERROR_COMMAND_SPAWNER_DOES_NOT_EXIST", "&cThe input &6{0} &cis not a mob type."),
    ERROR_COMMAND_SPAWNER_ARG_1("ERROR_COMMAND_SPAWNER_ARG_1", "&cYou need to specify a mob type."),
    ERROR_PLAYER_NOT_ONLINE("ERROR_PLAYER_NOT_ONLINE", "&cThe player &6{0} &cis not online."),
    ERROR_COMMAND_REMOVE_ARG_1("ERROR_COMMAND_REMOVE_ARG_1", "&cYou need to input a shop already created while holding the item you want removed."),
    ERROR_COMMAND_REMOVE_NO_ITEM("ERROR_COMMAND_REMOVE_NO_ITEM", "&cYou need to be holding a item."),
    ERROR_COMMAND_REMOVE_ITEM_NOT_FOUND("ERROR_COMMAND_REMOVE_ITEM_NOT_FOUND", "&cCould not find the item &6{0} &cin shop &6{1}&c."),
    ERROR_TRANSACTION_NO_SPACE("ERROR_TRANSACTION_NO_SPACE", "&cSadly you do not have enough inventory space to receive this item."),
    ERROR_TRANSACTION_NOT_ENOUGH_ITEMS("ERROR_TRANSACTION_NOT_ENOUGH_ITEMS", "&cSadly you do not have enough &6{0} &cto sell."),
    ERROR_TRANSACTION_BUY_ZERO_ITEMS("ERROR_TRANSACTION_BUY_ZERO_ITEMS", "&cYou can not buy zero items."),
    ERROR_TRANSACTION_BUY_VALUE_ZERO("ERROR_TRANSACTION_BUY_VALUE_ZERO", "&cThis item currently has buying disabled."),
    ERROR_TRANSACTION_SELL_VALUE_ZERO("ERROR_TRANSACTION_SELL_VALUE_ZERO", "&cThis item currently has selling disabled."),
    ERROR_NEXT_PAGE("ERROR_NEXT_PAGE", "&7You are on the last page."),
    ERROR_PREVIOUS_PAGE("ERROR_PREVIOUS_PAGE", "&7You are already on the first page."),
    ERROR_NO_PERMISSION("ERROR_NO_PERMISSION", "&cYou sadly do not have permission for this."),
    ERROR_TRANSACTION_SELL_ITEM_NO_VALUE("ERROR_COMMAND_SELL_ITEM", "&cThe item &6{0} &ccan not be sold to the shop at this time."),
    ERROR_COMMAND_ADD_NO_ITEM("ERROR_COMMAND_ADD_NO_ITEM", "&cYou need to be holding a item."),
    ERROR_NO_TARGET_PLAYER("ERROR_NO_TARGET_PLAYER", "&cYou need to specify a online player."),
    ERROR_NOT_A_CONSOLE_COMMAND("ERROR_NOT_A_CONSOLE_COMMAND", "&cThis is not a console command."),
    WAND_SELL_WAND_NAME("WAND_SELL_WAND_NAME", "&6&lSell Wand"),
    WAND_SELL_WAND_LORE("WAND_SELL_WAND_LORE", "&7Right click a container to sell the items inside."),
    ;

    @Getter private final String path;
    @Getter private final String def;
    @Setter private static FileConfiguration file;

    public String getDefault() {
        return def;
    }

    public String getConfigValue(final String[] args) {
        String fileValue = file.getString(this.path, this.def);
        if (fileValue == null) fileValue = "";
        String value = ChatColor.translateAlternateColorCodes('&', fileValue);
        if (args == null) return value;
        else if (args.length == 0) return value;
        for (int i = 0; i < args.length; i++) value = value.replace("{" + i + "}", args[i]);
        return value;
    }
}
