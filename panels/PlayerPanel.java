package panels;

/**
 * Created by Victor on 24-Dec-15.
 *
 * This interface defines the methods that a panel must implement if it is to be considered a panel which draws the hand of a player in the game.
 */
public interface PlayerPanel {

    /**
     * Gets the value of the blackjack hand drawn on this panel.
     * @return The value of the hand.
     */
    public int getHandValue();

    /**
     * Gets the number of cards in the user's hand.
     * @return The number of cards in the user's hand.
     */
    public int numCardsInHand();
}
