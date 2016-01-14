package states;

/**
 * Created by Victor on 22-Dec-15.
 *
 * This interface defines the methods and properties which a panel must implement in order to allow it to change its state and update
 * accordingly. This is done to keep a consistent interface between all panels which must change their states and update themselves
 * when they change.
 */
public interface StatePanel {

    /* Variables */
    //GameStates curStat = GameStates.BEFORESTART; // The state of the game begins with waiting for the User to press the "New Game" button.

    /**
     * Gets the current state of this panel.
     * @return The current state of this panel.
     */
    GameStates getState();

    /**
     * Causes the panel to update itself with the new state.
     */
    void updatePanel(GameStates gs);

    void updatePanel(GameStates gs, int score);
}
