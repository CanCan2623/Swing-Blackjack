import panels.BlackJackPanel;
import javax.swing.*;

/**
 * The main class of the programs. Creates the window and runs the program.
 */
public class BlackJackGUIMain implements Runnable {

    public static void main(String[] args)
    {
        //sets the GUI look based on OS
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println("Unable to apply OS theme to GUI.  Switching to default Java.");
        }
        // Add the program to the queue to be started later
        SwingUtilities.invokeLater(new BlackJackGUIMain()); 
    }

    /**
     * Starts the GUI.
     */
    public void run()
    {
        JFrame blackJackWindow = new JFrame("Victor's Game of BlackJack");
        BlackJackPanel blackJackPanel = new BlackJackPanel();

        /* Window setup and display */
        blackJackWindow.setContentPane(blackJackPanel);
        blackJackWindow.setLocation(120, 70);
        blackJackWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        blackJackWindow.setSize(5000, 5000);
        blackJackWindow.pack();
        blackJackWindow.setVisible(true);
    }
}
