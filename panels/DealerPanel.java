package panels;

/* Blackjack classes */
import blackjack.BlackjackHand;
import blackjack.Card;

/* State imports */
import states.GameStates;
import states.StatePanel;

/* Swing classes */
import javax.swing.*;
import java.awt.*;

/**
 * Created by Victor on 23-Dec-15.
 *
 * This class displays the dealer's cards.
 */
public class DealerPanel extends JPanel implements StatePanel, PlayerPanel {

    BlackjackHand myHand; // The dealer's hand
    CardPanel[] cardPanels = new CardPanel[5]; // An array of 5 card panels. These are used to display the cards in the hand
    JLabel panLabel; // The label which indicates that this is the dealer's panel
    GridBagConstraints gbc = new GridBagConstraints(); // Constraints for the GridBag layout
    GameStates curStat; // The current status
    boolean cardShowing[] = new boolean[5]; // Booleans which describe whether or not a given card is showing

    /**
     * Constructor
     */
    public DealerPanel()
    {
        /* Blackjack stuff */
        myHand = new BlackjackHand(); // Create the dealer's hand

        /* Look and feel stuff */
        setLayout(new GridBagLayout()); // Use a GridBag layout so that we can keep the label by itself, but align the 5 cards in a row
        setBackground(new Color(19, 168, 66)); // Use a dark green background

        /** Create and show the label which lets the user know that this is the dealer's panel **/
        panLabel = new JLabel("Dealer's cards"); // Create the label with some text which indicates that this is the dealer's panel
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

        curStat = GameStates.BEFORESTART; // The program starts with the BEFORESTART status
    }

    /**
     * Returns the current state of the panel.
     * @return The current state of the panel.
     */
    public GameStates getState()
    {
        return curStat; // Return the panel's current state
    }

    /**
     * Updates the panel so that it displays itself properly for a given state.
     * @param gs The state which determines how the panel should display itself.
     */
    public void updatePanel(GameStates gs)
    {
        curStat = gs; // Store the new status

        switch (curStat) // Display the panel differently, depending on the state
        {
            case BEFORESTART: // Clear all of the card panels
            {
                myHand.clear(); // Clear the hand object

                /* Clear the card panels */
                for (int i = 0; i < cardPanels.length; i++) // Loop through the card panels
                {
                    cardPanels[i].reset(); // Clear the panel and reset it
                }

                break;
            }

            case GAMESTART: // Start of the game, draw the 2 cards which have been added to the hand
            {
                /* Draw the cards on the card panels */
                for (int i = 0; i < 2; i++) // Loop through the card panels
                {
                    cardPanels[i].setVisibility(cardShowing[i]); // Set the card's visibility to the stored value
                    cardPanels[i].drawCard(); // Draw the card
                }

                break;
            }

            case UHIT: // The dealer has hit, redraw his hand
            {
                /* Draw the cards on the card panels */
                for (int i = 0; i < cardPanels.length; i++) // Loop through the card panels
                {
                    cardPanels[i].setVisibility(cardShowing[i]); // Set the card's visibility to the stored value
                    cardPanels[i].drawCard(); // Draw the card
                }

                break;
            }

            default:
            {
                //System.out.println("DealerPanel: Current state = " + curStat);
                break;
            }
        }
    }

    /**
     * Adds a card to the dealer's hand.
     * @param aCard The card to add.
     * @param faceShowing Whether or not this card's face is showing
     */
    public void addCard(Card aCard, boolean faceShowing)
    {
        myHand.addCard(aCard); // Add the card to the hand
        cardShowing[myHand.getCardCount()-1] = faceShowing; // Store the boolean which describes whether or not this card's face is showing
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
     * Gets the number of cards in the dealer's hand.
     * @return The number of cards in the dealer's hand.
     */
    public int numCardsInHand()
    {
        return myHand.getCardCount(); // Return the number of cards in the hand
    }

    /**
     * This method reveals all of the dealer's cards.
     */
    public void revealAllCards()
    {
        for (int i = 0; i < cardShowing.length; i++) // Loop through array which holds Boolean values which describe whether or not a card is showing
        {
            cardShowing[i] = true; // Show the card
        }

        updatePanel(GameStates.UHIT); // Redraw the panel to show all of the cards
    }

    /**
     * Dummy method defined to satify the Java compiler.
     * @param gs The game state.
     * @param score The score.
     */
    public void updatePanel(GameStates gs, int score)
    {
        updatePanel(gs); // Call the other method
    }
}
