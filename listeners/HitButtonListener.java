package listeners;

/* Panels */
import panels.BlackJackPanel;
import panels.ControlPanel;

/* States */
import states.GameStates;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Victor on 23-Dec-15.
 *
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
        JButton hitButton = (JButton)e.getSource(); // Get the button which dispatched the event
        ControlPanel cp = (ControlPanel)hitButton.getParent(); // Get the ControlPanel which contains the button
        BlackJackPanel bjp = (BlackJackPanel)cp.getParent(); // Get the BlackJackPanel which contains the ControlPanel

        /* Handle the "Hit!" event */
        bjp.updatePanel(GameStates.UHIT); // Send a "Hit!" message to the main panel
    }
}
