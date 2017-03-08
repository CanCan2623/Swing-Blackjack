package blackjack;

public class BlackjackHand extends Hand {

    /**
     * Computes and returns the value of this hand in the game
     * of Blackjack.
     */
    public int getBlackjackValue() {

        int val;      // The value computed for the hand.
        boolean ace;  // This will be set to true if the hand contains an ace.
        int cards;    // Number of cards in the hand.

        val = 0;
        ace = false;
        cards = getCardCount();

        //sums up the value of the cards in the hand
        for ( int i = 0;  i < cards;  i++ ) {
            Card card;
            int cardVal;
            card = getCard(i);
            cardVal = card.getValue();  // The normal value, 1 to 13.
            if (cardVal > 10) {         // In blackjack, the face cards have a
                cardVal = 10;           // value of 10 instead of 11, 12, or 13
            }
            if (cardVal == 1) { // As aces are special in blackjack, we check 
                ace = true;     // if the hand contains one
            }
            val = val + cardVal;
        }

        // In blackjack, an ace can have the value 1 or 11.  If there is an 
        // ace, and if changing its value from 1 to 11 would leave the score 
        // less than or equal to 21, then we increase it's current value to 11 
        if ( ace == true  &&  val + 10 <= 21 )
            val = val + 10;

        return val;

    }

}