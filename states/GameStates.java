package states;

/**
 * Created by Victor on 22-Dec-15.
 *
 * This enumeration contains the possible states of the game. This is used to determine how to draw the various panels.
 */
public enum GameStates {
    BEFORESTART, /**
                *   In this state, the game has not yet started.
                *
                * STEPS:
                * ------
                * 1) Clear the panels which display the dealer's hand and the player's hand. - done
                * 2) Clear the panel which displays the user's points. - done
                * 3) Wait for the user to click "New Game". When they do so, goto GAMESTART.
                **/
    GAMESTART,   /**
                * In this state, 2 cards are dealt into each player's hand.
                * Both of the user's cards are dealt face-up.
                * 1 of the dealer's cards is dealt face-up.
                * The other is dealt face-down.
                *
                *   Possible actions from here:
                *   --------------------------
                * If value(dealerHand) == 21, dealer wins. Goto ULOSE state.
                * Else if value(userHand) == 21, user wins. Goto UWIN state.
                * Else goto UADD state
                **/
    UADD, /**
            * State in which the user gets a chance to add cards to their hand.
            *
            * STEPS:
            * -----
            * 1) User sees their own cards (the ones dealt previously).
            * 2) User sees !one! of the dealer's cards (the one dealt previously).
            * 3) User decides whether they want to "Hit" (take a card) or "Stand" (stop taking cards).
            * 4) If the user chooses to Hit, go to the UHIT state.
            * 5) If the user chooses to Stand, go to USTAND
            *
            * NOTE: Nothing needs to be done for this state. All we need to do is wait for the user to click on one of the "Hit!" or "Stand" buttons,
            * which is handled by Java and the event listeners. Therefore, after the GAMESTART state is handled, we simply need to wait for a click
            * on a button.
            **/
    UHIT, /**
            * State in which the user hits.
            *
            * STEPS:
            * ======
            * 1) Add a card from the deck to the user's hand.
            * 2) Check if the value of the user's hand has gone over 21. If yes, goto ULOSE state.
            * 3) If the value of the user's hand isn't > 21, check the number of cards in the hand. If numCardsInHand == 5, goto UWIN. Else, goto UADD.
            **/
    USTAND, /**
            * State in which the user has decided to Stand.
            *
            * STEPS
            * =====
            * 1) While the value of the dealer's hand is <= 16:
            *   a) The dealer hits.
            *   b) Increment the # of cards taken. If this value reaches 5, the dealer wins automatically. Goto ULOSE state.
            * 2) Reveal all of the dealer's cards.
            * 3) If dealerHand.value() > 21, goto UWIN state.
            * 4) Else if dealerHand.value() >= userHand.value(), goto ULOSE state.
            * 5) Else, user wins. GOTO UWIN state.
            **/
    UWIN,   /**
            *   State in which the user wins.
            *
            *  STEPS
            *  =====
            *  1) Display "You Win!" message.
            *  2) Goto BEFORESTART state.
            **/
    ULOSE,   /**
            * State in which the user loses.
            *
            * STEPS
            * =====
            * 1) Display "You Lose!" message.
            * 2) Goto BEFORESTART state.
            **/

    ENDSTAT // Special state used to indicate that the while loop which processes state events should end
}