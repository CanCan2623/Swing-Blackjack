package panels; // Place this class with the other panels

/* State imports */
import states.GameStates; // Game states
import states.StatePanel; // Interface which defines what methods a panel must implement in order to be considered a stateful panel

/* Blackjack imports */
import blackjack.Deck; // Deck for Blackjack

/* Swing imports */
import javax.swing.*; // Swing classes
import java.awt.*; // AWT classes

/**
 * Created by Victor on 22-Dec-15.
 *
 * This panel is the main panel. It contains the dealer's panel, the user's panel, the status panel, and the controls panel.
 */
public class BlackJackPanel extends JPanel implements StatePanel {

    /** Properties **/

    /* Blackjack variables */
    Deck deck; // The deck which is used for the game

    /* Subpanels */
    ControlPanel cp; // The panel which contains the user's controls
    StatusPanel sp; // The panel which displays information about the user's status
    DealerPanel dp; // The panel which displays the dealer's hand
    UserPanel up; // The panel which displays the user's hand

    /* State variables */
    GameStates curStat; // The current state of the game

    /** Methods **/

    /**
     * Constructor.
     */
    public BlackJackPanel()
    {
        /* Create Blackjack variables */
        deck = new Deck(); // Create the deck
        deck.shuffle(); // Shuffle the deck

        /* Game state */
        curStat = GameStates.BEFORESTART; // Start with initial state

        /* GUI Stuff */
        setLayout(new GridLayout(4, 1)); // Use 4 rows (dealer panel, user panel, status panel, control panel) and 1 column (align everything)

        /** Subpanels **/

        /* Create subpanels */
        cp = new ControlPanel(); // Create the control panel
        sp = new StatusPanel(); // Create the status panel
        dp = new DealerPanel(); // Create the dealer's panel
        up = new UserPanel(); // Create the user's panel

        /* Add subpanels to the window */
        add(dp); // Add the dealer's panel to the main window
        add(up); // Add the user's panel to the main window
        add(sp); // Add the status panel to the main panel
        add(cp); // Add the control panel to the main panel

        JOptionPane.showMessageDialog(this, "Welcome to Victor's game of Blackjack!", "Welcome!", JOptionPane.PLAIN_MESSAGE); // Show a welcome message

        updatePanel(GameStates.BEFORESTART); // Update the panel for the start of the game
    }

    /* Stateful panel interface methods */

    /**
     * Returns the current game status.
     * @return The current game status.
     */
    public GameStates getState()
    {
        return curStat; // Return the current status
    }

    /**
     * Pushes an event to the event queue and calls the handler function.
     */
    public void updatePanel(GameStates gs)
    {
        GameStates myCurStat = gs; // Current state of the game - copy of the given one
        String endInfo = ""; // A string which describes the reason for the end of the game (shown in the various informational dialogues shown at the end of a game)

        while (myCurStat != GameStates.ENDSTAT) // While we have not reached a state which signals the end of the while loop...
        {
            switch (myCurStat) // Perform different actions, depending upon the state
            {
                /**
                 *   In this state, the game has not yet started.
                 *
                 * STEPS:
                 * ------
                 * 1) Clear the panels which display the dealer's hand and the player's hand. - done
                 * 2) Clear the panel which displays the user's points. - done
                 * 3) Wait for the user to click "New Game". When they do so, goto GAMESTART. - Handled by NewGameButtonListener.
                 **/
                case BEFORESTART:
                {
                 //   //System.out.println("BlackJackPanel.updatePanel: BEFORESTART message received");
                    up.updatePanel(myCurStat); // Clear the user panel
                    dp.updatePanel(myCurStat); // Clear the dealer panel
                    sp.updatePanel(myCurStat); // Clear the status panel and display a score of 0
                    cp.updatePanel(myCurStat); // Disable the "Hit" and "Stand" buttons and enable the "New Game" button
                    myCurStat = GameStates.ENDSTAT; // End the loop after resetting the panels
                    break;
                }

                /**
                 * In this state, 2 cards are dealt into each player's hand. - Handled here. - done
                 * Both of the user's cards are dealt face-up. - Handled here, cards are drawn by UserPanel. - done.
                 * 1 of the dealer's cards is dealt face-up. - Handled here, card is drawn by DealerPanel. - done
                 * The other is dealt face-down. - Handled here, card is drawn by DealerPanel - done
                 *
                 *   Possible actions from here:
                 *   --------------------------
                 * If value(dealerHand) == 21, dealer wins. Goto ULOSE state. - done
                 * Else if value(userHand) == 21, user wins. Goto UWIN state. - done
                 * Else goto UADD state - done
                 **/
                case GAMESTART:
                {
                    //System.out.println("BlackJackPanel.updatePanel: GAMESTART message received");

                    /* Deal 2 cards into each player's hand */
                    up.addCard(deck.dealCard()); // Deal a card into the user's hand
                    dp.addCard(deck.dealCard(), false); // Deal a card into the dealer's hand and hide it
                    up.addCard(deck.dealCard()); // Deal a second card into the player's hand
                    dp.addCard(deck.dealCard(), true); // Deal a card into the dealer's hand and show it

                    /* Update the panels */
                    up.updatePanel(myCurStat); // Update the user panel (draw the user's cards)
                    dp.updatePanel(myCurStat); // Update the dealer panel (draw the dealer's cards)
                    sp.updatePanel(myCurStat, up.getHandValue()); // Update the status panel with the user's initial score
                    cp.updatePanel(myCurStat); // Disable the "New Game" button

                    /* Determine which state to go to next using conditionals */

                    if (dp.getHandValue() == 21) // Dealer wins if they already have a hand with a value of 21, regardless of the value of the user's hand
                    {
                        myCurStat = GameStates.ULOSE; // Go to "user loses" state
                        endInfo = "The dealer had a hand with a value of 21 after the initial deal."; // Tell the user why they lost
                    }

                    else if (up.getHandValue() == 21) // User wins if the dealer doesn't have a hand with a value of 21, and they have a hand with a value of 21
                    {
                        myCurStat = GameStates.UWIN; // Go to "user wins" state
                        endInfo = "You won because you had a hand with a value of 21 on the first draw, while the dealer didn't."; // Explain why the user won
                    }

                    else
                    {
                        myCurStat = GameStates.ENDSTAT; // Go to "user adds cards to their hand" state - end the while loop and wait for them to press a button.
                    }

                    break;
                }

                /**
                 * State in which the user gets a chance to add cards to their hand.
                 *
                 * STEPS:
                 * -----
                 * 1) User sees their own cards (the ones dealt previously). - Handled in previous step: already drawn on panel
                 * 2) User sees !one! of the dealer's cards (the one dealt previously). - Handled in previous step: already drawn on panel.
                 * 3) User decides whether they want to "Hit" (take a card) or "Stand" (stop taking cards). - Handled by letting the user click on buttons in Control Panel.
                 * 4) If the user chooses to Hit, go to the UHIT state. - Handled by HitButtonListener
                 * 5) If the user chooses to Stand, go to USTAND - Handled by StandButtonListener.
                 *
                 * NOTE: Nothing needs to be done for this state. All we need to do is wait for the user to click on one of the "Hit!" or "Stand" buttons,
                 * which is handled by Java and the event listeners. Therefore, after the GAMESTART state is handled, we simply need to wait for a click
                 * on a button.

                case UADD: // State where the user adds cards to their hand
                {
                    break;
                }**/

                /**
                 * State in which the user hits.
                 *
                 * STEPS:
                 * ======
                 * 1) Add a card from the deck to the user's hand and display it. - done
                 * 2) Check if the value of the user's hand has gone over 21. If yes, goto ULOSE state. - done
                 * 3) If the value of the user's hand isn't > 21, check the number of cards in the hand. If numCardsInHand == 5, goto UWIN. Else, goto UADD. - done
                 **/
                case UHIT: // The user wants to hit
                {
                    //System.out.println("BlackJackPanel.updatePanel: UHIT message received");

                    /* 1- Add a card from the deck to the user's hand and display it */
                    up.addCard(deck.dealCard()); // Add a card from the deck to the user's hand
                    up.updatePanel(GameStates.UHIT); // Display the new card
                    sp.updatePanel(GameStates.UHIT, up.getHandValue()); // Display the user's new score

                    /* Determine which state to go to next */

                    if (up.getHandValue() > 21) // Determine if hit caused user to lose
                    {
                        myCurStat = GameStates.ULOSE; // Go to the "user lost" state
                        endInfo = "Your hit caused your hand's value to go over 21 points"; // Tell the user why they lost
                    }

                    else
                    {
                        if (up.numCardsInHand() == 5) // The user has drawn 5 cards without going over 21, therefore they have an automatic win
                        {
                            myCurStat = GameStates.UWIN; // Go to winning state
                            endInfo = "You won because you drew 5 cards in a row without going over 21."; // Explain why the user has won
                        }

                        else
                        {
                            myCurStat = GameStates.ENDSTAT; // Exit the while loop, so that we can wait for them to click a button again
                        }
                    }

                    break;
                }

                /**
                 * State in which the user has decided to Stand.
                 *
                 * STEPS
                 * =====
                 * 1) While the value of the dealer's hand is <= 16: - dome
                 *   a) The dealer hits. - done
                 *   b) If the # of cards taken reaches 5 and the value of the dealer's hand is still <= 21, the dealer wins automatically. Goto ULOSE state. - done
                 * 2) Reveal all of the dealer's cards. - done
                 * 3) If dealerHand.value() > 21, goto UWIN state. - done
                 * 4) Else if dealerHand.value() >= userHand.value(), goto ULOSE state. - done
                 * 5) Else, user wins. GOTO UWIN state. - done
                 **/
                case USTAND: // State in which the user has decided to stand
                {
                    //System.out.println("BlackJackPanel.updatePanel: USTAND message received");

                    while (dp.getHandValue() <= 16) // Loop while the value of the dealer's hand is <= 16
                    {
                        /* The dealer hits */
                        dp.addCard(deck.dealCard(), true); // The dealer hits
                        dp.updatePanel(GameStates.UHIT); // Redraw the panel with the new card

                        if (dp.numCardsInHand() == 5 && dp.getHandValue() <= 21) // If the number of cards taken reaches 5 and the dealer's hand still has a value <= 21 after the hit, the dealer has an automatic win
                        {
                            myCurStat = GameStates.ULOSE; // Go to state where user loses
                            endInfo = "You lost because you stood and the dealer managed to draw 5 cards without going over a hand value of 21. The value of the dealer's hand is " + Integer.toString(dp.getHandValue()) + "."; // Explain why the user lost
                            break;
                        }
                    }

                    if (myCurStat != GameStates.ULOSE) // Continue only if the player has not already lost
                    {
                        dp.revealAllCards(); // Reveal all of the dealer's cards - set visible property to true and redraw

                        if (dp.getHandValue() > 21) // The dealer has lost
                        {
                            myCurStat = GameStates.UWIN; // Go to state where user wins
                            endInfo = "You have won because the value of the dealer's hand went over 21. The value of the dealer's hand was " + Integer.toString(dp.getHandValue()) + "."; // Explain why the user won
                        }

                        else if (dp.getHandValue() >= up.getHandValue()) // The dealer has won if their hand's value is >= the user's hand value
                        {
                            myCurStat = GameStates.ULOSE; // Go to state where user loses
                            endInfo = "You lost because the value of the dealer's hand was less than 21, but greater than or equal to the value of your hand. The value of the dealer's hand was " + Integer.toString(dp.getHandValue()) + "."; // Explain why the user lost
                        }

                        else // The dealer must have lost
                        {
                            myCurStat = GameStates.UWIN; // Go to state where user wins
                            endInfo = "You have won because the value of the dealer's hand is less than that of yours. The value of the dealer's hand was " + Integer.toString(dp.getHandValue()) + "."; // Explain why the user won
                        }
                    }

                    break;
                }

                /**
                 *   State in which the user wins.
                 *
                 *  STEPS
                 *  =====
                 *  1) Display "You Win!" message.
                 *  2) Reset dealer panel
                 *  3) Reset user panel.
                 *  4) Reset status panel.
                 *  5) Goto BEFORESTART state.
                 **/
                case UWIN: // The user has won
                {
                    //System.out.println("BlackJackPanel.updatePanel: UWIN message received");

                    dp.revealAllCards(); // Reveal all of the dealer's cards
                    JOptionPane.showMessageDialog(this, endInfo, "You Have Won! :=)", JOptionPane.INFORMATION_MESSAGE); // Display a confirmation message and explain why the user won
                    myCurStat = GameStates.BEFORESTART; // Go back to the starting state, so that everything will be reset

                    /** Reset for the new game **/

                    /* Reset the deck */
                    deck = new Deck(); // Create a new deck
                    deck.shuffle(); // Shuffle the new deck

                    /* Reset the dealer, user, and status panels */
                    dp.updatePanel(GameStates.UWIN); // Reset the dealer panel
                    up.updatePanel(GameStates.UWIN); // Reset the user panel
                    sp.updatePanel(GameStates.UWIN); // Increase the user's holdings by their bet amount

                    break;
                }

                /**
                 * State in which the user loses.
                 *
                 * STEPS
                 * =====
                 * 1) Display "You Lose!" message.
                 * 2) Goto BEFORESTART state.
                 **/
                case ULOSE: // The user has lost
                {
                    //System.out.println("BlackJackPanel.updatePanel: ULOSE message received");

                    dp.revealAllCards(); // Reveal the dealer's hidden card
                    JOptionPane.showMessageDialog(this, endInfo, "You Have Lost... :=(", JOptionPane.INFORMATION_MESSAGE); // Display a confirmation message
                    myCurStat = GameStates.BEFORESTART; // Go back to the starting state, so that everything will be reset

                    /* Reset the dealer, user, and status panels */
                    dp.updatePanel(GameStates.ULOSE); // Reset the dealer panel
                    up.updatePanel(GameStates.ULOSE); // Reset the user panel
                    sp.updatePanel(GameStates.ULOSE); // Increase the user's holdings by their bet amount

                    /* Reset the deck */
                    deck = new Deck(); // Create a new deck
                    deck.shuffle(); // Shuffle the new deck

                    break;
                }

                default:
                {
                    //System.out.println("Exiting... unhandled state reached");
                    System.exit(0);
                    break;
                }
            }
        }
    }

    /**
     * Dummy method defined to placate the Java compiler.
     * @param gs The game state.
     * @param score The score (unused).
     */
    public void updatePanel(GameStates gs, int score)
    {
        updatePanel(gs); // Update the panel
    }

    /**
     * Gets the status panel.
     * @return The status panel.
     */
    public StatusPanel getSp()
    {
        return sp; // Return the status panel
    }
}
