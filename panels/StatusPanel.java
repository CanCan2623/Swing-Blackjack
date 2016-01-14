package panels;

/* State information */
import states.GameStates;
import states.StatePanel;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Victor on 23-Dec-15.
 *
 * This panel displays information about the user's status. It gets this information from the main container panel.
 */
public class StatusPanel extends JPanel implements StatePanel{
    /* Subcomponents */
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
        scoreLabel = new JLabel("Score: "); // Create the label with no score text
        scoreLabel.setForeground(Color.WHITE); // White label to stand out
        scoreLabel.setFont(new Font("Times New Roman", Font.BOLD, 20)); // Times New Roman, Bold, 20pt

        /* Create the prompt label */
        promptLabel = new JLabel("Hit or Stand?"); // Label which asks the user what they'd like to do
        promptLabel.setForeground(Color.WHITE); // White label to stand out
        promptLabel.setFont(new Font("Times New Roman", Font.BOLD, 20)); // Times New Roman, Bold, 20pt

        /* Create the money label */
        moneyLabel = new JLabel("Your Holdings: $" + Integer.toString(money)); // The label which displays the amount of money in the user's pot
        moneyLabel.setForeground(Color.WHITE); // White label to stand out
        moneyLabel.setFont(new Font("Times New Roman", Font.BOLD, 20)); // Times New Roman, Bold, 20pt

        /* Graphics stuff */
        setLayout(new GridLayout(3, 1)); // 3 row, 1 column layout
        setBackground(new Color(19, 168, 66)); // Use a dark green background

        /* Add components to the panel */
        add(scoreLabel); // Add the score label to the panel
        add(promptLabel); // Add the prompt label to the panel
        add(moneyLabel); // Add the label which displays the amount of money held by the user to the panel

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
        return curStat; // Return the current state
    }

    /**
     * Updates the current panel with a new state.
     * @param gs The new state to switch to. (ignored)
     * @param score The new score to display
     */
    public void updatePanel(GameStates gs, int score)
    {
        switch (gs) // Perform different actions, depending on the state
        {
            case ULOSE: // The user has lost
            {
                scoreLabel.setText("Score: "); // Reset the score label
                money -= bet; // Subtract the bet from the user's total
                moneyLabel.setText("Your Holdings: $" + Integer.toString(money)); // Update the money label

                if (money == 0) // User has lost
                {
                    JOptionPane.showMessageDialog(this,"Your holdings have reached $0, so you can't play any longer", "No Holdings", JOptionPane.PLAIN_MESSAGE);
                    System.exit(0); // Exitz
                }

                break;
            }

            case UWIN: // The user has won
            {
                scoreLabel.setText("Score: "); // Reset the score label
                money += bet; // Add the bet to the user's total
                moneyLabel.setText("Your Holdings: $" + Integer.toString(money)); // Update the money label
                break;
            }

            default:
            {
                scoreLabel.setText("Score: " + Integer.toString(score)); // Clear the label by resetting it so that it doesn't show any score
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
        return money; // Return the amount of money which the user has
    }

    /**
     * Stores a bet value.
     *
     * @param newBetValue The new bet value to store.
     */
    public void setBet(int newBetValue)
    {
        bet = newBetValue; // Store the new bet value
    }
}
