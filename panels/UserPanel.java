package panels;

/* Blackjack imports */
import blackjack.BlackjackHand;
import blackjack.Card;

/* State imports */
import states.GameStates;
import states.StatePanel;

/* Swing imports */
import javax.swing.*;
import java.awt.*;

/**
 * Created by Victor on 24-Dec-15.
 *
 * This class defines the panel which displays the user's cards.
 */
public class UserPanel extends JPanel implements StatePanel, PlayerPanel {
    BlackjackHand myHand; // The dealer's hand
    CardPanel[] cardPanels = new CardPanel[5]; // An array of 5 card panels. These are used to display the cards in the hand
    JLabel panLabel; // The label which indicates that this is the dealer's panel
    GridBagConstraints gbc = new GridBagConstraints(); // Constraints for the GridBag layout
    GameStates curStat; // The current status of the panel

    /**
     * Constructor
     */
    public UserPanel()
    {
        /* Blackjack stuff */
        myHand = new BlackjackHand(); // Create the dealer's hand

        /* Look and feel stuff */
        setLayout(new GridBagLayout()); // use a GridBag layout so that we can keep the label by itself, but align the 5 cards in a row
        setBackground(new Color(19, 168, 66)); // Use a dark green background

        /** Create and show the label which lets the user know that this is the dealer's panel **/
        panLabel = new JLabel("Your cards"); // Create the label with some text which indicates that this is the dealer's panel
        panLabel.setForeground(Color.WHITE); // Red label to stand out
        panLabel.setFont(new Font("Times New Roman", Font.BOLD, 20)); // Times New Roman, Bold, 20pt

        /* Set up constraints */
        gbc.gridx = 0; // Left-most cell
        gbc.gridy = 0; // Top-most cell
        gbc.fill = GridBagConstraints.HORIZONTAL; // Let it fill the container horizontally
        gbc.weighty = 0.2; // Testing
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        add(panLabel, gbc); // Add the label to the panel with the given constraints

        /* Set up constraints for the cards */
        gbc.gridy = 1; // The row beneath the label
        gbc.fill = GridBagConstraints.VERTICAL; // The cards shouldn't fill the area
        gbc.weightx = 0.5;
        gbc.gridwidth = 1;

        /* Card creation and display */
        for (int i = 0; i < cardPanels.length; i++) // Create the card panels
        {
            gbc.gridx = i; // Use the current index as the x value
            cardPanels[i] = new CardPanel(); // Create the panel
            add(cardPanels[i], gbc); // Add the card panel to this panel with the given constraints
        }

        curStat = GameStates.BEFORESTART; // The state which the program starts in, as a game hasn't yet started
    }

    /**
     * Gets the curent state of the panel.
     * @return The current state of the panel.
     */
    public GameStates getState()
    {
        return curStat; // Return the current state
    }

    public void updatePanel(GameStates gs)
    {
        curStat = gs; // Store the new state

        switch (curStat)
        {
            case BEFORESTART: // Clear the user's hand
            {
                myHand.clear(); // Clear the hand object

                /* Clear the card panels */
                for (int i = 0; i < cardPanels.length; i++) // Loop through the card panels
                {
                    cardPanels[i].reset(); // Clear the card panel and reset it
                }

                break;
            }

            case GAMESTART: // Draw the 2 cards that were added to the user's hand
            {
                /* Draw the cards on the card panels */
                for (int i = 0; i < 2; i++) // Loop through the first 2 card panels
                {
                    cardPanels[i].drawCard(); // Draw the card
                }

                break;
            }

            case UHIT: // Draw the cards again, now that the user has hit
            {
                /* Draw the cards on the card panels */
                for (int i = 0; i < cardPanels.length; i++) // Loop through the card panels
                {
                    cardPanels[i].drawCard(); // Draw the card
                }

                break;
            }

            default:
            {
                //System.out.println("UserPanel: current state = " + curStat);
                break;
            }
        }
    }

    /**
     * Adds a card to the user's hand
     * @param aCard The card to add to the user's hand
     */
    public void addCard(Card aCard)
    {
        myHand.addCard(aCard); // Add the card to the user's hand
        cardPanels[myHand.getCardCount()-1].setCurCard(aCard); // Give the corresponding card panel a reference to the card, so that it can draw it
    }

    /**
     * Gets the value of the hand.
     * @return The value of the hand.
     */
    public int getHandValue()
    {
        return myHand.getBlackjackValue(); // Return the value of the blackjack hand
    }

    /**
     * Gets the number of cards in the user's hand.
     * @return The number of cards in the user's hand.
     */
    public int numCardsInHand()
    {
        return myHand.getCardCount(); // Return the number of cards in the hand
    }

    /**
     * Dummy method in this one.
     * @param gs Game state.
     * @param score The score.
     */
    public void updatePanel(GameStates gs, int score)
    {
        updatePanel(gs); // Call the other method which doesn't take a score
    }
}
