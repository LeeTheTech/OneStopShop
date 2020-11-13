package lee.code.onestopshop.events;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import lombok.Getter;

public class SellWandInteractEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    @Getter private final Player wandUser;
    @Getter private final Block container;

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public SellWandInteractEvent(Player breaker, Block container) {
        this.wandUser = breaker;
        this.container = container;
    }
}
