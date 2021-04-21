package lee.code.onestopshop.files.defaults;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

@AllArgsConstructor
public enum Config {

    VALUE_FORMAT("ValueFormat", "#,###.00"),
    AMOUNT_FORMAT("AmountFormat", "#,###"),
    CURRENCY_FORMAT("CurrencyFormat", "&6${0}"),
    ECONOMY_MODE_ITEM("EconomyMaterial", "EMERALD"),
    SPAWNER_DROP_CHANCE("SpawnerDropChance", "100"),
    INTERFACE_CLICK_DELAY("MenuClickDelay", "5"),
    INTERFACE_ITEM_FILLER_GLASS("FillerGlass", "GRAY_STAINED_GLASS_PANE"),
    INTERFACE_ITEM_BACK("InterfaceMaterials.Back", "BARRIER"),
    INTERFACE_ITEM_CLOSE("InterfaceMaterials.Close", "BARRIER"),
    INTERFACE_ITEM_NEXT_PAGE("InterfaceMaterials.NextPage", "PAPER"),
    INTERFACE_ITEM_PREVIOUS_PAGE("InterfaceMaterials.PreviousPage", "PAPER"),
    INTERFACE_ITEM_BUY("InterfaceMaterials.Buy", "LIME_STAINED_GLASS_PANE"),
    INTERFACE_ITEM_BUY_INVENTORY("InterfaceMaterials.BuyInventory", "CHEST"),
    INTERFACE_ITEM_SELL("InterfaceMaterials.Sell", "RED_STAINED_GLASS_PANE"),
    INTERFACE_ITEM_SELL_INVENTORY("InterfaceMaterials.SellInventory", "TRAPPED_CHEST"),
    SOUND_TRANSACTION_SUCCESSFUL("Sounds.TransactionSuccessful.Sound", "ENTITY_PLAYER_LEVELUP"),
    SOUND_VOLUME_TRANSACTION_SUCCESSFUL("Sounds.TransactionSuccessful.Volume", "1.0"),
    SOUND_PITCH_TRANSACTION_SUCCESSFUL("Sounds.TransactionSuccessful.Pitch", "1.0"),
    SOUND_TRANSACTION_FAILED("Sounds.TransactionFailed.Sound", "ENTITY_VILLAGER_TRADE"),
    SOUND_VOLUME_TRANSACTION_FAILED("Sounds.TransactionFailed.Volume", "1.0"),
    SOUND_PITCH_TRANSACTION_FAILED("Sounds.TransactionFailed.Pitch", "1.0"),
    SOUND_MENU_CLICK("Sounds.MenuClick.Sound", "UI_BUTTON_CLICK"),
    SOUND_VOLUME_MENU_CLICK("Sounds.MenuClick.Volume", "1.0"),
    SOUND_PITCH_MENU_CLICK("Sounds.MenuClick.Pitch", "1.0"),
    SOUND_MENU_OPEN("Sounds.MenuOpen.Sound", "BLOCK_CHEST_OPEN"),
    SOUND_VOLUME_MENU_OPEN("Sounds.MenuOpen.Volume", "1.0"),
    SOUND_PITCH_MENU_OPEN("Sounds.MenuOpen.Pitch", "1.0"),
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
