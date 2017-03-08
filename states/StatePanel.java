package states;

/**
 * This interface defines the methods and properties which a panel must implement in order to allow it to change its state and update
 * accordingly. This is done to keep a consistent interface between all panels which must change their states and update themselves
 * when they change.
 */
public interface StatePanel {

    /**
     * Gets the current state of this panel.
     * @return The current state of this panel.
     */
    GameStates getState();

    /**
     * Causes the panel to update itself with the new state.
     * @param gs: the new game state
     */
    void updatePanel(GameStates gs);

    /**
     * Causes the panel to update itself with the new state and score
     * @param gs: the new game state
     * @param score: updated score
     */
    void updatePanel(GameStates gs, int score);
}
