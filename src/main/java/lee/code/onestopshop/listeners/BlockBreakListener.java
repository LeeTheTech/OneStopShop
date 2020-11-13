package lee.code.onestopshop.listeners;

import lee.code.onestopshop.events.SpawnerBreakEvent;
import lee.code.onestopshop.xseries.XMaterial;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.Material;

public class BlockBreakListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Block blockBroken = e.getBlock();
        Material spawner = XMaterial.SPAWNER.parseMaterial();
        if (blockBroken.getType().equals(spawner)) {
            e.setExpToDrop(0);
            Bukkit.getServer().getPluginManager().callEvent(new SpawnerBreakEvent(e.getPlayer(), blockBroken));
        }
    }
}
