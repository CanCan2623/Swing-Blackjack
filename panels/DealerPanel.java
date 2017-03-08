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
 * This class displays the dealer's cards.
 */
public class DealerPanel extends JPanel implements StatePanel, PlayerPanel {

    BlackjackHand myHand;                       // The dealer's hand
    CardPanel[] cardPanels = new CardPanel[5];  // used to display the cards in the hand
    JLabel panLabel;                            // used to indicates that this is the dealer's panel
    GridBagConstraints gbc = new GridBagConstraints();      // Used for UI Aethetics
    GameStates curStat;                         // The current status
    boolean cardShowing[] = new boolean[5];     // Booleans which describe whether or not a given card is showing

    /**
     * Create's and sets the look of the dealer's panel
     */
    public DealerPanel()
    {
        /* Create the dealer's hand */
        myHand = new BlackjackHand();

        /* Look and feel stuff */
        setLayout(new GridBagLayout()); // Use a GridBag layout so that we can keep the label by itself, but align the 5 cards in a row
        setBackground(new Color(19, 168, 66)); // Use a dark green background

        /** Create and show the label which lets the user know that this is the dealer's panel **/
        panLabel = new JLabel("Dealer's cards"); // indicates that this is the dealer's panel
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
        for (int i = 0; i < cardPanels.length; i++) // Create the card panels
        {                                           // using index as the x
            gbc.gridx = i;                          // coordinate
            cardPanels[i] = new CardPanel();
            add(cardPanels[i], gbc); 
        }

        curStat = GameStates.BEFORESTART; 
    }

    /**
     * Returns the current state of the panel.
     * @return The current state of the panel.
     */
    public GameStates getState()
    {
        return curStat; 
    }

    /**
     * Updates the panel so that it displays itself properly for a given state.
     * @param gs The state which determines how the panel should display itself.
     */
    public void updatePanel(GameStates gs)
    {
        curStat = gs; 

        switch (curStat) 
        {
            case BEFORESTART: // Clear all of the card panels
            {
                myHand.clear(); 

                /* Clear the card panels */
                for (int i = 0; i < cardPanels.length; i++) 
                {
                    cardPanels[i].reset();
                }

                break;
            }

            case GAMESTART: // Start of the game, display the 2 cards which have been added to the hand
            {
                for (int i = 0; i < 2; i++)
                {
                    cardPanels[i].setVisibility(cardShowing[i]); 
                    cardPanels[i].drawCard();
                }

                break;
            }

            case UHIT: // The dealer has hit, update his hand's dispaly
            {
                for (int i = 0; i < cardPanels.length; i++) 
                {
                    cardPanels[i].setVisibility(cardShowing[i]); 
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
     * Adds a card to the dealer's hand.
     * @param aCard The card to add.
     * @param faceShowing Whether or not this card's face is showing
     */
    public void addCard(Card aCard, boolean faceShowing)
    {
        myHand.addCard(aCard);
        cardShowing[myHand.getCardCount()-1] = faceShowing;
        cardPanels[myHand.getCardCount()-1].setCurCard(aCard); // adds card to array of cards to be displayed
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
     * Gets the number of cards in the dealer's hand.
     * @return The number of cards in the dealer's hand.
     */
    public int numCardsInHand()
    {
        return myHand.getCardCount();
    }

    /**
     * This method reveals all of the dealer's cards.
     */
    public void revealAllCards()
    {
        for (int i = 0; i < cardShowing.length; i++) 
        {
            cardShowing[i] = true;
        }

        updatePanel(GameStates.UHIT); // use the UHIT state to redraw the panel to show all of the cards
    }

    /**
     * Dummy method defined to satify the Java compiler.
     * @param gs The game state.
     * @param score The score.
     */
    public void updatePanel(GameStates gs, int score)
    {
        //updatePanel ignores the score
        updatePanel(gs);
    }
}
