package fallingblocks.model;

import java.util.EventListener;

/**
 * A listener that responds to BlockAddedEvents.
 * 
 * @author jeremiah
 * 
 */
public interface BlockAddedListener extends EventListener {
    /**
     * This method is called when a BlockAddedEvent is fired.
     * @param event the event that is fired.
     */
    void blocksAdded(BlockAddedEvent event);
}
