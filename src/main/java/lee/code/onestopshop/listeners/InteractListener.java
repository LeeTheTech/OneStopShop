package lee.code.onestopshop.listeners;

import lee.code.onestopshop.xseries.XMaterial;
import lee.code.onestopshop.events.SellWandInteractEvent;
import lee.code.onestopshop.files.defaults.Lang;
import lee.code.onestopshop.itembuilders.SellWandBuilder;
import lee.code.onestopshop.OneStopShop;
import org.bukkit.Bukkit;
import org.bukkit.block.Barrel;
import org.bukkit.block.Chest;
import org.bukkit.block.ShulkerBox;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class InteractListener implements Listener  {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onInteract(PlayerInteractEvent e) {
        OneStopShop plugin = OneStopShop.getPlugin();

        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {

            ItemStack wand = plugin.getData().getSellWand();
            ItemStack handItem = plugin.getPluginUtility().getHandItem(e.getPlayer());

            if (!handItem.equals(wand)) return;

            //click delay
            if (plugin.getData().getPlayerClickDelay(e.getPlayer().getUniqueId())) {
                e.setCancelled(true);
                return;
            } else plugin.getPluginUtility().addPlayerClickDelay(e.getPlayer().getUniqueId());

            if (e.useInteractedBlock().equals(Event.Result.ALLOW) || e.useItemInHand().equals(Event.Result.ALLOW)) {
                if (e.getClickedBlock().getState() instanceof Chest || XMaterial.supports(11) && e.getClickedBlock().getState() instanceof ShulkerBox || XMaterial.supports(14) && e.getClickedBlock().getState() instanceof Barrel) {
                    Bukkit.getServer().getPluginManager().callEvent(new SellWandInteractEvent(e.getPlayer(), e.getClickedBlock()));
                    e.setCancelled(true);
                }
            }
        }
    }
}
