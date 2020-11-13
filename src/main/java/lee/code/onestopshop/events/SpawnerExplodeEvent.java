package lee.code.onestopshop.events;

import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import lombok.Getter;

public class SpawnerExplodeEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    @Getter private final Entity breaker;
    @Getter private final Block spawner;

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public SpawnerExplodeEvent(Entity breaker, Block spawner) {
        this.breaker = breaker;
        this.spawner = spawner;
    }
}
