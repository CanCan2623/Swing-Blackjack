package panels;

import blackjack.BlackjackHand;
import blackjack.Card;

import states.GameStates;
import states.StatePanel;

import javax.swing.*;
import java.awt.*;

/**
 * This class defines the panel which displays the user's cards.
 */
public class UserPanel extends JPanel implements StatePanel, PlayerPanel {
    BlackjackHand myHand;                      // The Player's hand
    CardPanel[] cardPanels = new CardPanel[5]; // used to display the cards in the hand
    JLabel panLabel;                // Used to indicates that this is the Player's panel
    GridBagConstraints gbc = new GridBagConstraints(); // Used to set look of UI
    GameStates curStat; // The current status of the panel

    /**
     * Creates and sets the look of a new UserPanel
     */
    public UserPanel()
    {
        /*  Create the dealer's hand */
        myHand = new BlackjackHand(); 

        /* Look and feel stuff */
        setLayout(new GridBagLayout()); // use a GridBag layout so that we can keep the label by itself, but align the 5 cards in a row
        setBackground(new Color(19, 168, 66)); // Use a dark green background

        /** Create and show the label which lets the user know that this is the Player's panel **/
        panLabel = new JLabel("Your cards"); // Create the label with some text which indicates that this is the Player's panel
        panLabel.setForeground(Color.WHITE);
        panLabel.setFont(new Font("Times New Roman", Font.BOLD, 20)); 

        /* Set up constraints */
        gbc.gridx = 0; // Left-most cell
        gbc.gridy = 0; // Top-most cell
        gbc.fill = GridBagConstraints.HORIZONTAL; // Let it fill the container horizontally
        gbc.weighty = 0.2;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        add(panLabel, gbc);

        /* Set up constraints for the cards */
        gbc.gridy = 1; // The row beneath the label
        gbc.fill = GridBagConstraints.VERTICAL; // The cards shouldn't fill the area
        gbc.weightx = 0.5;
        gbc.gridwidth = 1;

        /* Card creation and display */
        for (int i = 0; i < cardPanels.length; i++) // Creates the card panels
        {                                           // using the index as the 
            gbc.gridx = i;                          // x- coordinate
            cardPanels[i] = new CardPanel(); 
            add(cardPanels[i], gbc); 
        }

        curStat = GameStates.BEFORESTART;
    }

    /**
     * Gets the curent state of the panel.
     * @return The current state of the panel.
     */
    public GameStates getState()
    {
        return curStat;
    }

    public void updatePanel(GameStates gs)
    {
        curStat = gs; 

        switch (curStat)
        {
            case BEFORESTART: //clears the user's hand
            {
                myHand.clear(); 

                /* Clear the card panels */
                for (int i = 0; i < cardPanels.length; i++) // Loop through the card panels
                {
                    cardPanels[i].reset(); // Clear the card panel and reset it
                }

                break;
            }

            case GAMESTART: // Display the 2 cards that were added to the user's hand
            {
                for (int i = 0; i < 2; i++) 
                {
                    cardPanels[i].drawCard();
                }

                break;
            }

            case UHIT: // Update cards being displayed, now that the user has hit
            {
                for (int i = 0; i < cardPanels.length; i++) 
                {
                    cardPanels[i].drawCard(); 
                }

                break;
            }

            default:
            {
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
        //adds a card to the user's hand and pudates the panels to display card to user
        myHand.addCard(aCard); 
        cardPanels[myHand.getCardCount()-1].setCurCard(aCard);
    }

    /**
     * Gets the value of the hand.
     * @return The value of the hand.
     */
    public int getHandValue()
    {
        return myHand.getBlackjackValue();
    }

    /**
     * Gets the number of cards in the user's hand.
     * @return The number of cards in the user's hand.
     */
    public int numCardsInHand()
    {
        return myHand.getCardCount();
    }

    /**
     * Dummy method in this one.
     * @param gs Game state.
     * @param score The score.
     */
    public void updatePanel(GameStates gs, int score)
    {
        // updatePanel ignores score
        updatePanel(gs);
    }
}
