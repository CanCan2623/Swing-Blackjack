package panels; 

import listeners.NewGameButtonListener;
import listeners.HitButtonListener;
import listeners.StandButtonListener;

import states.GameStates;
import states.StatePanel;

import javax.swing.*;
import java.awt.*;

/**
 * This class defines the panel which contains the user's controls (buttons).
 */
public class ControlPanel extends JPanel implements StatePanel {

    /* Controls */
    JButton newGameButton;
    JButton hitButton;
    JButton standButton;
    JTextField betField;

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
        newGameButton = new JButton("New Game");
        ngbl = new NewGameButtonListener();
        newGameButton.addActionListener(ngbl);
        newGameButton.setToolTipText("Start a new game");

        /* Build the "Hit!" button */
        hitButton = new JButton("Hit!"); 
        hbl = new HitButtonListener(); 
        hitButton.addActionListener(hbl); 
        hitButton.setToolTipText("Take a card from the deck.");

        /* Build the "Stand" button */
        standButton = new JButton("Stand"); 
        sbl = new StandButtonListener(); 
        standButton.addActionListener(sbl); 
        standButton.setToolTipText("End the game and see who wins");

        /* Build the bet text field */
        betField = new JTextField(); 
        betField.setToolTipText("Enter the amount of money which you wish to bet on this game here.");

        /** Add the buttons to the panel **/
        add(newGameButton); 
        add(hitButton); 
        add(standButton); 
        add(betField); 

        /* Handle the initial state of the game */
        curStat = GameStates.BEFORESTART; 
        updatePanel(curStat); 
    }

    /**
     * Updates this panel for a new state.
     * @param gameStates The new state to switch to.
     */
    public void updatePanel(GameStates gameStates)
    {
        curStat = gameStates; 

        switch (curStat) 
        {
            case BEFORESTART: 
            {
                // Before the game starts, eable the "New Game" button and bet
                // value field, but disable the "Hit" and "Stand" buttons
                newGameButton.setEnabled(true);
                hitButton.setEnabled(false);
                standButton.setEnabled(false);
                betField.setEnabled(true);
                break;
            }

            case GAMESTART:
            {
                // When the game starts, disable the "New Game" button and bet
                // value field, but enable the "Hit" and "Stand" buttons
                newGameButton.setEnabled(false); 
                hitButton.setEnabled(true);
                standButton.setEnabled(true); 
                betField.setEnabled(false); 
                break;
            }

            default:
            {
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
        return curStat; 
    }

    /**
     * Dummy method defined to satisy Java compiler.
     * @param gs The game state.
     * @param score The score.
     */
    public void updatePanel(GameStates gs, int score)
    {
        updatePanel(gs);
    }

    /**
     * Gets the value of the bet text field.
     * @return The value of the bet text field.
     */
    public String getBetTextFieldValue()
    {
        return betField.getText(); 
    }
}
