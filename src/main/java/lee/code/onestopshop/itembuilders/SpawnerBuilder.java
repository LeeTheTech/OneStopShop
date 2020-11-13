package lee.code.onestopshop.itembuilders;

import lee.code.onestopshop.xseries.XMaterial;
import org.bukkit.ChatColor;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;


public class SpawnerBuilder {

    private EntityType mob;

    public SpawnerBuilder setMob(EntityType mob) {
        this.mob = mob;
        return this;
    }

    public Spawner buildObject() {
        return new Spawner(mob);
    }

    public ItemStack buildItemStack() {
        ItemStack item = XMaterial.SPAWNER.parseItem();

        BlockStateMeta meta = (BlockStateMeta) item.getItemMeta();
        CreatureSpawner cs = (CreatureSpawner) meta.getBlockState();

        cs.setSpawnedType(mob);
        meta.setBlockState(cs);

        String name = mob.toString();
        name = name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&f" + name.replace("_", " ") + " Spawner"));
        item.setItemMeta(meta);

        return item;
    }
}
