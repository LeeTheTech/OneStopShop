package lee.code.onestopshop.listeners;

import lee.code.onestopshop.xseries.XMaterial;
import lee.code.onestopshop.events.SellWandInteractEvent;
import lee.code.onestopshop.OneStopShop;
import org.bukkit.Bukkit;
import org.bukkit.block.*;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class InteractListener implements Listener  {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onInteract(PlayerInteractEvent e) {
        OneStopShop plugin = OneStopShop.getPlugin();

        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {

            ItemStack wand = plugin.getData().getSellWand();
            ItemStack handItem = plugin.getPU().getHandItem(e.getPlayer());

            if (handItem.equals(wand)) {
                if (plugin.getData().getPlayerClickDelay(e.getPlayer().getUniqueId())) {
                    e.setCancelled(true);
                    return;
                } else plugin.getPU().addPlayerClickDelay(e.getPlayer().getUniqueId());

                if (e.useInteractedBlock().equals(Event.Result.ALLOW) || e.useItemInHand().equals(Event.Result.ALLOW)) {
                    Block block = e.getClickedBlock();
                    if (block != null) {
                        BlockState state = block.getState();
                        if (state instanceof Chest || XMaterial.supports(11) && state instanceof ShulkerBox || XMaterial.supports(14) && state instanceof Barrel) {
                            Bukkit.getServer().getPluginManager().callEvent(new SellWandInteractEvent(e.getPlayer(), e.getClickedBlock()));
                            e.setCancelled(true);
                        }
                    }
                }
            }
        }
    }
}
