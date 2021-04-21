package lee.code.onestopshop.menusystem;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

@RequiredArgsConstructor
public class PlayerMenuUtility {
    private final UUID owner;

    @Getter @Setter private String shop;
    @Getter @Setter private ItemStack selectedShopItem;
    @Getter @Setter private String currentMenu;

    public Player getOwner() {
        return Bukkit.getPlayer(owner);
    }
}