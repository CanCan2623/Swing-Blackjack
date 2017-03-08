package panels;

import javax.swing.*;
import java.awt.*;

import blackjack.Card; 

/**
 * This class displays a card.
 */
public class CardPanel extends JPanel {

    Card curCard = null; // The card to draw
    JLabel cardNumLabel; // displays the number of the card
    JLabel ofLabel;      // displays the word "of"
    JLabel cardSuitLabel; // displays the card's suit
    boolean cardShowing = true; // Whether or not the card should be shown

    /**
     * Creates a new card and sets its look
     */
    public CardPanel()
    {
        /* Set the labels to show default text */
        cardNumLabel = new JLabel(); 
        ofLabel = new JLabel(); 
        cardSuitLabel = new JLabel(); 

        /* Window appearance and layout */
        setBackground(new Color(255, 255, 255)); // Set a white background
        setLayout(new GridLayout(3, 1)); // 3 rows with 1 column - 1 row for each label

        /* Add the labels to the panel */
        add(cardNumLabel);
        add(ofLabel);
        add(cardSuitLabel);
    }

    /**
     * Sets the current card drawn by this panel.
     * @param c The card to draw on this panel.
     */
    public void setCurCard(Card c)
    {
        curCard = c; 
    }

    /**
     * This method clears the panel and resets the variables.
     */
    public void reset()
    {
        /* Clear the labels */
        cardNumLabel.setText(""); 
        cardSuitLabel.setText(""); 
        ofLabel.setText("");

        /* Clear the card variables */
        curCard = null; 
        cardShowing = true; // default value
    }

    /**
     * Allows control over whether or not the card is showing.
     *
     * @param isVisible True if the card should be visible, false otherwise.
     */
    public void setVisibility(boolean isVisible)
    {
        cardShowing = isVisible;
    }

    /**
     * Adds text to card that is to be displayed
     */
    public void drawCard()
    {
        String suit; 
        String val;

        /* Only show the card's information if it should be */
        if (cardShowing && curCard != null)
        {
            suit = curCard.getSuitAsString(); 
            val = curCard.getValueAsString(); 

            /* Set the labels to display the appropriate values */
            cardNumLabel.setText(val); 
            cardSuitLabel.setText(suit); 
            ofLabel.setText("of"); 

            /* Determine what colour to display the labels as */

            if (suit.toLowerCase().equals("diamonds") || suit.toLowerCase().equals("hearts")) 
            {
                cardNumLabel.setForeground(Color.RED); 
                cardSuitLabel.setForeground(Color.RED); 
                ofLabel.setForeground(Color.RED); 
            }

            else 
            {
                cardNumLabel.setForeground(Color.BLACK); 
                cardSuitLabel.setForeground(Color.BLACK);
                ofLabel.setForeground(Color.BLACK);
            }
        }

        else if (curCard == null) // No card set
        {
            /* Blank out the text */
            cardNumLabel.setText("");
            cardSuitLabel.setText("");
            ofLabel.setText("");
        }

        else
        {
            cardNumLabel.setText("Hidden");
            cardSuitLabel.setText("Hidden");
            ofLabel.setText("of");
        }
    }
}
