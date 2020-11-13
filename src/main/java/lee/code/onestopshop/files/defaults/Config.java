package lee.code.onestopshop.files.defaults;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

@AllArgsConstructor
public enum Config {

     //Value format $
    VALUE_FORMAT("ValueFormat", "#,###"),
    //Amount format for item count
    AMOUNT_FORMAT("AmountFormat", "#,###"),
    //Currency format, placement of currency symbol
    CURRENCY_FORMAT("CurrencyFormat", "&6${0}"),
    //Economy item mode item
    ECONOMY_MODE_ITEM("EconomyMaterial", "EMERALD"),
    //Spawner drop chance
    SPAWNER_DROP_CHANCE("SpawnerDropChance", "100"),
    //Click delay amount in ticks
    INTERFACE_CLICK_DELAY("MenuClickDelay", "5"),
    //Glass type that is filled
    INTERFACE_ITEM_FILLER_GLASS("FillerGlass", "GRAY_STAINED_GLASS_PANE"),
    //Interface back item
    INTERFACE_ITEM_BACK("InterfaceMaterials.Back", "BARRIER"),
    //Interface close item
    INTERFACE_ITEM_CLOSE("InterfaceMaterials.Close", "BARRIER"),
    //Interface next page item
    INTERFACE_ITEM_NEXT_PAGE("InterfaceMaterials.NextPage", "PAPER"),
    //Interface previous page item
    INTERFACE_ITEM_PREVIOUS_PAGE("InterfaceMaterials.PreviousPage", "PAPER"),
    //Interface buy item
    INTERFACE_ITEM_BUY("InterfaceMaterials.Buy", "LIME_STAINED_GLASS_PANE"),
    //Interface buy inventory item
    INTERFACE_ITEM_BUY_INVENTORY("InterfaceMaterials.BuyInventory", "CHEST"),
    //Interface sell item
    INTERFACE_ITEM_SELL("InterfaceMaterials.Sell", "RED_STAINED_GLASS_PANE"),
    //Interface sell inventory
    INTERFACE_ITEM_SELL_INVENTORY("InterfaceMaterials.SellInventory", "TRAPPED_CHEST"),
    //Sound transaction successful
    SOUND_TRANSACTION_SUCCESSFUL("Sounds.TransactionSuccessful.Sound", "ENTITY_PLAYER_LEVELUP"),
    //Sound transaction successful volume
    SOUND_VOLUME_TRANSACTION_SUCCESSFUL("Sounds.TransactionSuccessful.Volume", "1.0"),
    //Sound transaction successful pitch
    SOUND_PITCH_TRANSACTION_SUCCESSFUL("Sounds.TransactionSuccessful.Pitch", "1.0"),
    //Sound transaction failed
    SOUND_TRANSACTION_FAILED("Sounds.TransactionFailed.Sound", "ENTITY_VILLAGER_TRADE"),
    //Sound transaction failed volume
    SOUND_VOLUME_TRANSACTION_FAILED("Sounds.TransactionFailed.Volume", "1.0"),
    //Sound transaction failed pitch
    SOUND_PITCH_TRANSACTION_FAILED("Sounds.TransactionFailed.Pitch", "1.0"),
    //Sound menu clicked
    SOUND_MENU_CLICK("Sounds.MenuClick.Sound", "UI_BUTTON_CLICK"),
    //Sound menu clicked volume
    SOUND_VOLUME_MENU_CLICK("Sounds.MenuClick.Volume", "1.0"),
    //Sound menu clicked pitch
    SOUND_PITCH_MENU_CLICK("Sounds.MenuClick.Pitch", "1.0"),
    //Sound open menu
    SOUND_MENU_OPEN("Sounds.MenuOpen.Sound", "BLOCK_CHEST_OPEN"),
    //Sound open menu volume
    SOUND_VOLUME_MENU_OPEN("Sounds.MenuOpen.Volume", "1.0"),
    //Sound open menu pitch
    SOUND_PITCH_MENU_OPEN("Sounds.MenuOpen.Pitch", "1.0"),
    //Sell Wand item
    WAND_SELL_ITEM("Wands.SellWand.Material", "BLAZE_ROD"),
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
