package lee.code.onestopshop.events;

import lombok.Getter;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class SpawnerBreakEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    @Getter private final Player breaker;
    @Getter private final Block spawner;

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public SpawnerBreakEvent(Player breaker, Block spawner) {
        this.breaker = breaker;
        this.spawner = spawner;
    }
}
