package lee.code.onestopshop.files.defaults;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.file.FileConfiguration;

@AllArgsConstructor
public enum Settings {
    INTERFACE_BOOLEAN_MENU_FILLER_GLASS("FillerGlass.Menu", true),
    INTERFACE_BOOLEAN_TRANSACTION_FILLER_GLASS("FillerGlass.Transaction", true),
    BOOLEAN_ECONOMY_VAULT("Economy.Vault", false),
    SPAWNER_SUPPORT("Spawners.Enabled", true),
    SPAWNER_DENY_MOB_EGGS("Spawners.DenyMobEggs", true),
    SPAWNER_DENY_ANVIL_RENAME("Spawners.DenyAnvilRename", true),
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
