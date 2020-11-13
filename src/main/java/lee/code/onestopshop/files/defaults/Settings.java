package lee.code.onestopshop.files.defaults;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.file.FileConfiguration;

@AllArgsConstructor
public enum Settings {
    //boolean for filling glass on menu
    INTERFACE_BOOLEAN_MENU_FILLER_GLASS("FillerGlass.Menu", true),
    //boolean for filling glass on transaction menu
    INTERFACE_BOOLEAN_TRANSACTION_FILLER_GLASS("FillerGlass.Transaction", true),
    //boolean for switching economy mode
    BOOLEAN_ECONOMY_VAULT("Economy.Vault", true),
    //boolean for switching economy mode
    BOOLEAN_ECONOMY_ITEM("Economy.Item", false),
    //boolean spawner support enabled
    SPAWNER_SUPPORT("Spawners.Enabled", true),
    //boolean spawner egg change
    SPAWNER_DENY_MOB_EGGS("Spawners.DenyMobEggs", true),
    //boolean spawner anvil rename
    SPAWNER_DENY_ANVIL_RENAME("Spawners.DenyAnvilRename", true),
    //boolean spawner explode drop
    SPAWNER_EXPLODE_DROP("Spawners.ExplodeDrop", true),
    ;

    @Getter private final String path;
    @Getter private final boolean def;
    @Setter private static FileConfiguration file;

    public Boolean getDefault() {
        return this.def;
    }

    public Boolean getConfigValue() {
        return file.getBoolean(this.path, this.def);
    }
}
