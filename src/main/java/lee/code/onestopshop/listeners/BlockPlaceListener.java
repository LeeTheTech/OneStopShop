package lee.code.onestopshop.listeners;

import lee.code.onestopshop.events.SpawnerPlaceEvent;
import lee.code.onestopshop.xseries.XMaterial;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceListener implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        Material spawner = XMaterial.SPAWNER.parseMaterial();
        Block block = e.getBlock();
        if (block.getType().equals(spawner)) {
            Bukkit.getServer().getPluginManager().callEvent(new SpawnerPlaceEvent(e.getPlayer(), block));
        }
    }
}
