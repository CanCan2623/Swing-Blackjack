package panels;

import states.GameStates;
import states.StatePanel;

import javax.swing.*;
import java.awt.*;

/**
 * This panel displays information about the user's status. It gets this information from the main container panel.
 */
public class StatusPanel extends JPanel implements StatePanel{
    /* UI Components */
    JLabel scoreLabel; // The label which displays the user's score.
    JLabel promptLabel; // The user prompt
    JLabel moneyLabel; // The label which displays the amount of money which the user has

    /* Game variables */
    GameStates curStat; // The current state of the panel
    int money = 100; // The amount of money which the user currently has (in dollars)
    int bet = 0; // The amount of money which the user wants to bet on the current game

    public StatusPanel()
    {
        /** Create labels **/

        /* Create the score label */
        scoreLabel = new JLabel("Score: ");
        scoreLabel.setForeground(Color.WHITE); 
        scoreLabel.setFont(new Font("Times New Roman", Font.BOLD, 20)); 

        /* Create the prompt label */
        promptLabel = new JLabel("Hit or Stand?");
        promptLabel.setForeground(Color.WHITE); 
        promptLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));

        /* Create the money label */
        moneyLabel = new JLabel("Your Holdings: $" + Integer.toString(money));
        moneyLabel.setForeground(Color.WHITE); 
        moneyLabel.setFont(new Font("Times New Roman", Font.BOLD, 20)); 

        /* Sets the GUI's layout and background colour */
        setLayout(new GridLayout(3, 1)); // 3 row, 1 column layout
        setBackground(new Color(19, 168, 66)); // Use a dark green background

        /* Add components to the panel */
        add(scoreLabel); 
        add(promptLabel); 
        add(moneyLabel);

        
        curStat = GameStates.BEFORESTART; // Set the state to the state before a game starts
        updatePanel(curStat); // Update the panel with the initial status
    }

    /**
     * Fetches the current state of the panel.
     * @return The current state of the panel.
     */
    @Override
    public GameStates getState()
    {
        return curStat;
    }

    /**
     * Updates the current panel with a new state.
     * @param gs The new state to switch to. (ignored)
     * @param score The new score to display
     */
    public void updatePanel(GameStates gs, int score)
    {
        // checks if the player has won or lost the hand, resets the scoreLabel
        // to a default value, and updates the palyer's money 
        switch (gs)
        {
            case ULOSE: // The user has lost
            {
                scoreLabel.setText("Score: "); 
                money -= bet;
                moneyLabel.setText("Your Holdings: $" + Integer.toString(money));

                if (money == 0) // Game over
                {
                    JOptionPane.showMessageDialog(this,"Your holdings have reached $0, so you can't play any longer", "No Holdings", JOptionPane.PLAIN_MESSAGE);
                    System.exit(0);
                }

                break;
            }

            case UWIN: // The user has won
            {
                scoreLabel.setText("Score: ");
                money += bet;
                moneyLabel.setText("Your Holdings: $" + Integer.toString(money));
                break;
            }

            default:
            {
                scoreLabel.setText("Score: " + Integer.toString(score));
                break;
            }
        }
    }

    /**
     * Dummy method. Unused. Only defined to placate the Java compiler.
     * @param gs The game state.
     */
    public void updatePanel(GameStates gs)
    {
        updatePanel(gs, 0); // Update the panel with a score of 0
    }

    /**
     * Gets the amount of money held by the user.
     * @return The amount of money held by the user.
     */
    public int getMoney()
    {
        return money; 
    }

    /**
     * Stores a bet value.
     *
     * @param newBetValue The new bet value to store.
     */
    public void setBet(int newBetValue)
    {
        bet = newBetValue;
    }
}
