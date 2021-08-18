package lee.code.onestopshop.listeners;

import lee.code.onestopshop.events.SpawnerBreakEvent;
import lee.code.onestopshop.events.SpawnerExplodeEvent;
import lee.code.onestopshop.events.SpawnerPlaceEvent;
import lee.code.onestopshop.files.defaults.Config;
import lee.code.onestopshop.files.defaults.Lang;
import lee.code.onestopshop.files.defaults.Settings;
import lee.code.onestopshop.itembuilders.SpawnerBuilder;
import lee.code.onestopshop.OneStopShop;
import lee.code.onestopshop.xseries.XEnchantment;
import lee.code.onestopshop.xseries.XMaterial;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
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
import org.bukkit.inventory.meta.BlockStateMeta;

import java.util.Random;

public class SpawnerListener implements Listener {

    private final Random random = new Random();

    @EventHandler
    public void onSpawnerPlace(SpawnerPlaceEvent e) {
        if (Settings.SPAWNER_SUPPORT.getConfigValue()) {
            Block block = e.getSpawner();
            Player player = e.getPlacer();
            if (block.getState() instanceof CreatureSpawner) {
                CreatureSpawner cs = (CreatureSpawner) block.getState();
                ItemStack spawner = player.getInventory().getItemInMainHand();
                BlockStateMeta spawnerMeta = (BlockStateMeta) spawner.getItemMeta();
                if (spawnerMeta != null) {
                    CreatureSpawner spawnerCS = (CreatureSpawner) spawnerMeta.getBlockState();
                    cs.setSpawnedType(spawnerCS.getSpawnedType());
                    cs.update();
                }
            }
        }
    }

    @EventHandler
    public void onSpawnerBreak(SpawnerBreakEvent e) {
        OneStopShop plugin = OneStopShop.getPlugin();

        if (Settings.SPAWNER_SUPPORT.getConfigValue()) {

            ItemStack handItem;
            Player player = e.getBreaker();
            GameMode playerGameMode = player.getGameMode();
            Enchantment silk = XEnchantment.SILK_TOUCH.parseEnchantment();
            boolean hasItemInHand = false;

            handItem = plugin.getPU().getHandItem(player);
            if (!handItem.getType().equals(Material.AIR)) hasItemInHand = true;

            if (player.hasPermission("oss.spawner.break")) {
                if (silk != null) {
                    if (playerGameMode.equals(GameMode.CREATIVE) || handItem.getType().equals(XMaterial.DIAMOND_PICKAXE.parseMaterial()) || handItem.getType().equals(XMaterial.NETHERITE_PICKAXE.parseMaterial())) {
                        if (playerGameMode.equals(GameMode.CREATIVE) || hasItemInHand && handItem.containsEnchantment(silk)) {

                            int chance = random.nextInt(100) + 1;
                            if (chance <= Integer.parseInt(Config.SPAWNER_DROP_CHANCE.getConfigValue(null))) {
                                CreatureSpawner cs = (CreatureSpawner) e.getSpawner().getState();
                                EntityType mob = cs.getSpawnedType();
                                ItemStack item = new SpawnerBuilder().setMob(mob).buildItemStack();
                                World world = e.getSpawner().getLocation().getWorld();
                                if (world != null) world.dropItemNaturally(e.getSpawner().getLocation(), item);
                            }
                        }
                    }
                }
            } else player.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.ERROR_NO_PERMISSION.getConfigValue(null));
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
                World world = e.getSpawner().getLocation().getWorld();
                if (world != null) world.dropItemNaturally(e.getSpawner().getLocation(), item);
            }
        }
    }

    @EventHandler
    public void onSpawnerEggInteract(PlayerInteractEvent e) {
        OneStopShop plugin = OneStopShop.getPlugin();
        if (Settings.SPAWNER_SUPPORT.getConfigValue() && Settings.SPAWNER_DENY_MOB_EGGS.getConfigValue()) {
            if (e.hasBlock()) {
                if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && e.getClickedBlock() != null && e.getClickedBlock().getType().equals(XMaterial.SPAWNER.parseMaterial())) {
                    ItemStack item = plugin.getPU().getHandItem(e.getPlayer());
                    e.setCancelled(!item.getType().equals(XMaterial.SPAWNER.parseMaterial()));
                }
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

                if (clicked != null) {
                    if (clicked.getItemMeta() != null) {
                        //sell wand check
                        if (clicked.getItemMeta().getDisplayName().equals(Lang.WAND_SELL_WAND_NAME.getConfigValue(null))) e.setCancelled(true);
                        //spawner check
                        if (clicked.getType() == XMaterial.SPAWNER.parseMaterial()) e.setCancelled(true);
                    }
                }
            }
        }
    }
}