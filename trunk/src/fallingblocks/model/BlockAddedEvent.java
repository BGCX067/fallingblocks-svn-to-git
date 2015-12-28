package fallingblocks.model;

import java.util.EventObject;

/**
 * Indicates a GameBoard has had blocks added to it.
 * @author jeremiah
 *
 */
public class BlockAddedEvent extends EventObject {
	private static final long serialVersionUID = 1L;
	
        /**
         * Creates a new Event with the specified source.
         * @param source the GameBoard firing the event.
         */
	public BlockAddedEvent(GameBoard source) {
		super(source);
	}
}
