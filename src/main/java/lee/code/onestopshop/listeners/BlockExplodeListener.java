package lee.code.onestopshop.listeners;

import lee.code.onestopshop.events.SpawnerExplodeEvent;
import lee.code.onestopshop.xseries.XMaterial;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

public class BlockExplodeListener implements Listener {

    @EventHandler
    public void onBlockExplode(EntityExplodeEvent e) {
        Material spawner = XMaterial.SPAWNER.parseMaterial();
        for (Block block : e.blockList()) {
            if (block.getType().equals(spawner)) {
                Bukkit.getServer().getPluginManager().callEvent(new SpawnerExplodeEvent(e.getEntity(), block));
                return;
            }
        }
    }
}
