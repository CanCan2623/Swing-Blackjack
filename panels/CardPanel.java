package panels;

/* Swing imports */
import javax.swing.*;
import java.awt.*;

/* Blackjack imports */
import blackjack.Card; // The class which defines a playing card

/**
 * Created by Victor on 23-Dec-15.
 *
 * This class displays a card.
 */
public class CardPanel extends JPanel {

    Card curCard = null; // The card to draw
    JLabel cardNumLabel; // The label which displays the number of the card
    JLabel ofLabel; // The label which displays the word "of"
    JLabel cardSuitLabel; // The label which displays the card's suit
    boolean cardShowing = true; // Whether or not the card should be shown

    /**
     * Constructor.
     */
    public CardPanel()
    {
        /* Set the labels to show default text */
        cardNumLabel = new JLabel(); // Create the JLabel, with no text
        ofLabel = new JLabel(); // Create the JLabel, with no text
        cardSuitLabel = new JLabel(); // Create the JLabel with no text

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
        curCard = c; // Store a reference to the current card
    }

    /**
     * This method clears the panel and resets the variables.
     */
    public void reset()
    {
        /* Clear the labels */
        cardNumLabel.setText(""); // Clear the number label
        cardSuitLabel.setText(""); // Clear the suit label
        ofLabel.setText(""); // Clear the "of" label

        /* Clear the card variables */
        curCard = null; // Remove the current card
        cardShowing = true; // Reset the boolean to its default value
    }

    /**
     * Allows control over whether or not the card is showing.
     *
     * @param isVisible True if the card should be visible, false otherwise.
     */
    public void setVisibility(boolean isVisible)
    {
        cardShowing = isVisible; // Store the controlling boolean value for use by drawCard.
    }

    public void drawCard()
    {
        String suit; // The suit of the card
        String val; // The value of the card

        /* Only show the card's information if it should be */
        if (cardShowing && curCard != null) // The card should be shown, and we have a card assigned to us
        {
            suit = curCard.getSuitAsString(); // Store the card's suit
            val = curCard.getValueAsString(); // Store the card's value

            /* Set the labels to display the appropriate values */
            cardNumLabel.setText(val); // Display the card's value
            cardSuitLabel.setText(suit); // Display the card's suit
            ofLabel.setText("of"); // Display the word "of"

            /* Determine what colour to display the labels as */

            if (suit.toLowerCase().equals("diamonds") || suit.toLowerCase().equals("hearts")) // Red suits
            {
                /* Make the labels red */
                cardNumLabel.setForeground(Color.RED); // Make the card number label red
                cardSuitLabel.setForeground(Color.RED); // Make the card suit label red
                ofLabel.setForeground(Color.RED); // Make the "of" label red
            }

            else // Black suits
            {
                 /* Make the labels black */
                cardNumLabel.setForeground(Color.BLACK); // Make the card number label black
                cardSuitLabel.setForeground(Color.BLACK); // Make the card suit label black
                ofLabel.setForeground(Color.BLACK); // Make the "of" label black
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
