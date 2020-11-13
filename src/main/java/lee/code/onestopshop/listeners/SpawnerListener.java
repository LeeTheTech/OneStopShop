package lee.code.onestopshop.listeners;

import lee.code.onestopshop.events.SpawnerBreakEvent;
import lee.code.onestopshop.events.SpawnerExplodeEvent;
import lee.code.onestopshop.files.defaults.Config;
import lee.code.onestopshop.files.defaults.Lang;
import lee.code.onestopshop.files.defaults.Settings;
import lee.code.onestopshop.itembuilders.SpawnerBuilder;
import lee.code.onestopshop.OneStopShop;
import lee.code.onestopshop.xseries.XEnchantment;
import lee.code.onestopshop.xseries.XMaterial;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class SpawnerListener implements Listener {

    private static final Random random = new Random();

    @EventHandler
    public void onSpawnerBreak(SpawnerBreakEvent e) {

        OneStopShop plugin = OneStopShop.getPlugin();

        //check for spawner support
        if (Settings.SPAWNER_SUPPORT.getConfigValue()) {
            ItemStack handItem;
            Player player = e.getBreaker();
            GameMode playerGameMode = player.getGameMode();
            Enchantment silk = XEnchantment.SILK_TOUCH.parseEnchantment();
            boolean hasItemInHand = false;

            handItem = plugin.getPluginUtility().getHandItem(player);

            String itemID = plugin.getPluginUtility().formatMat(handItem);

            if (!handItem.getType().equals(Material.AIR)) hasItemInHand = true;
            if (!player.hasPermission("oss.spawner.break")) {
                player.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.ERROR_NO_PERMISSION.getConfigValue(null));
                return;
            }

            if (playerGameMode.equals(GameMode.CREATIVE) || itemID.equals("DIAMOND_PICKAXE") || itemID.equals("NETHERITE_PICKAXE")) {
                if (playerGameMode.equals(GameMode.CREATIVE) || hasItemInHand && handItem.containsEnchantment(silk)) {

                    //Chance system
                    int chance = random.nextInt(100) + 1;
                    if (chance <= Integer.parseInt(Config.SPAWNER_DROP_CHANCE.getConfigValue(null))) {

                        CreatureSpawner cs = (CreatureSpawner) e.getSpawner().getState();
                        EntityType mob = cs.getSpawnedType();
                        ItemStack item = new SpawnerBuilder().setMob(mob).buildItemStack();
                        e.getSpawner().getLocation().getWorld().dropItemNaturally(e.getSpawner().getLocation(), item);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onSpawnerExplode(SpawnerExplodeEvent e) {
        if (Settings.SPAWNER_SUPPORT.getConfigValue() && Settings.SPAWNER_EXPLODE_DROP.getConfigValue()) {

            //Chance system
            int chance = random.nextInt(100) + 1;
            if (chance <= Integer.parseInt(Config.SPAWNER_DROP_CHANCE.getConfigValue(null))) {
                CreatureSpawner cs = (CreatureSpawner) e.getSpawner().getState();
                EntityType mob = cs.getSpawnedType();
                ItemStack item = new SpawnerBuilder().setMob(mob).buildItemStack();
                e.getSpawner().getLocation().getWorld().dropItemNaturally(e.getSpawner().getLocation(), item);
            }
        }
    }

    @EventHandler
    public void onSpawnerEggInteract(PlayerInteractEvent e) {

        OneStopShop plugin = OneStopShop.getPlugin();

        if (Settings.SPAWNER_SUPPORT.getConfigValue() && Settings.SPAWNER_DENY_MOB_EGGS.getConfigValue()) {
            //check if spawner and right-click
            if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && e.getClickedBlock().getType().equals(XMaterial.SPAWNER.parseMaterial())) {

                ItemStack item = plugin.getPluginUtility().getHandItem(e.getPlayer());

                //check if player is holding spawner so they can place it on it
                e.setCancelled(!item.getType().equals(XMaterial.SPAWNER.parseMaterial()));
            }
        }
    }

    @EventHandler
    public void onSpawnerAnvilRename(InventoryClickEvent e) {
        if (Settings.SPAWNER_SUPPORT.getConfigValue() && Settings.SPAWNER_DENY_ANVIL_RENAME.getConfigValue()) {
            if (e.getCurrentItem() != null && e.getCurrentItem().getType() != Material.AIR) {
                if (e.getInventory().getType() != InventoryType.ANVIL) return;
                if (e.getSlotType() != InventoryType.SlotType.RESULT) return;

                ItemStack clicked = e.getInventory().getItem(0);
                //sell wand check
                if (clicked.getItemMeta().getDisplayName().equals(Lang.WAND_SELL_WAND_NAME.getConfigValue(null))) e.setCancelled(true);
                //spawner check
                if (clicked.getType() == XMaterial.SPAWNER.parseMaterial()) e.setCancelled(true);
            }
        }
    }
}