package panels; // This class is a panel, and as such is a member of the panels package

/* Event listeners */
import listeners.NewGameButtonListener;
import listeners.HitButtonListener;
import listeners.StandButtonListener;

/* Classes which define game states */
import states.GameStates;
import states.StatePanel;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Victor on 23-Dec-15.
 *
 * This class defines the panel which contains the user's controls (buttons).
 */
public class ControlPanel extends JPanel implements StatePanel {

    /* Controls */
    JButton newGameButton; // The button which the user clicks to start a new game
    JButton hitButton; // The button which the user clicks when they want to hit
    JButton standButton; // The button which the user clicks when they want to stand
    JTextField betField; // The field into which the user types their bet

    /* Event listeners */
    NewGameButtonListener ngbl; // The listener for the "New Game" button
    HitButtonListener hbl; // The listener for the "Hit!" button
    StandButtonListener sbl; // The listener for the "Stand" button

    /* Game variables */
    GameStates curStat; // The current state of the panel

    /**
     * Constructor. Builds the panel by adding the buttons and their event handlers. It also sets the layout for this panel.
     */
    public ControlPanel()
    {
        /** Styling stuff **/
        setLayout(new GridLayout(1, 4)); // Use a grid layout with 1 row and 4 columns

        /** Build the inputs **/

        /* Build the "New Game" button */
        newGameButton = new JButton("New Game"); // Create the button
        ngbl = new NewGameButtonListener(); // Create the button's listener
        newGameButton.addActionListener(ngbl); // Add the listener to the button
        newGameButton.setToolTipText("Start a new game");

        /* Build the "Hit!" button */
        hitButton = new JButton("Hit!"); // Create the button
        hbl = new HitButtonListener(); // Create the button's listener
        hitButton.addActionListener(hbl); // Add the listener to the button
        hitButton.setToolTipText("Take a card from the deck.");

        /* Build the "Stand" button */
        standButton = new JButton("Stand"); // Create the button
        sbl = new StandButtonListener(); // Create the button's listener
        standButton.addActionListener(sbl); // Add the listener to the button
        standButton.setToolTipText("End the game and see who wins");

        /* Build the bet text field */
        betField = new JTextField(); // Create the text field
        betField.setToolTipText("Enter the amount of money which you wish to bet on this game here."); // Set some tool tip text on the field

        /** Add the buttons to the panel **/
        add(newGameButton); // Add the "New Game" button to the panel
        add(hitButton); // Add the "Hit!" button to the panel
        add(standButton); // Add the "Stand" button to the panel
        add(betField); // Add the bet text field to the panel

        /* Handle the initial state of the game */
        curStat = GameStates.BEFORESTART; // State before a game states
        updatePanel(curStat); // Update the panel with the new state
    }

    /**
     * Updates this panel for a new state.
     * @param gameStates The new state to switch to.
     */
    public void updatePanel(GameStates gameStates)
    {
        curStat = gameStates; // Save the new state

        switch (curStat) // Draw the panel differently, depending on the game's state
        {
            case BEFORESTART: // While waiting for the player to click the "New Game" button
            {
                newGameButton.setEnabled(true); // Enable the "New Game" button
                hitButton.setEnabled(false); // Disable the "Hit" button
                standButton.setEnabled(false); // Disable the "Stand" button
                betField.setEnabled(true); // Enable the bet value field
                break;
            }

            case GAMESTART: // Start of the game - disable the "New Game" button and enable the "Hit" and "Stand" buttons
            {
                newGameButton.setEnabled(false); // Disable the "New Game" button
                hitButton.setEnabled(true); // Enable the "Hit" button
                standButton.setEnabled(true); // Enable the "Stand" button
                betField.setEnabled(false); // Disable the bet value field
                break;
            }

            default:
            {
                //System.out.println("ControlPanel: curStat = " + curStat);
                break;
            }
        }
    }

    /**
     * Gets the current state of the game.
     * @return The current state of the game.
     */
    public GameStates getState()
    {
        return curStat; // Return the current state
    }

    /**
     * Dummy method defined to satisy Java compiler.
     * @param gs The game state.
     * @param score The score.
     */
    public void updatePanel(GameStates gs, int score)
    {
        updatePanel(gs); // Update the panel
    }

    /**
     * Gets the value of the bet text field.
     * @return The value of the bet text field.
     */
    public String getBetTextFieldValue()
    {
        return betField.getText(); // Return the text in the bet field
    }
}
