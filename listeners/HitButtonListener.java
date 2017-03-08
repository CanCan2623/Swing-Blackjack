package listeners;

import panels.BlackJackPanel;
import panels.ControlPanel;

import states.GameStates;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class listens for and handles click events dispatched from the "Hit!" button
 */
public class HitButtonListener implements ActionListener {

    /**
     * This function handles the click event.
     * @param e The click event.
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {
        // gets the black jack panel through the button that generated the 
        // event, and initiates the response to a user's "hit"
        JButton hitButton = (JButton)e.getSource(); 
        ControlPanel cp = (ControlPanel)hitButton.getParent(); 
        BlackJackPanel bjp = (BlackJackPanel)cp.getParent(); 

        bjp.updatePanel(GameStates.UHIT); 
    }
}
