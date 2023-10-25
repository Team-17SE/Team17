import javax.swing.*;
import java.awt.*;

public class SplashJava extends JWindow {
    private JFrame frame;
    private final int time = 3000;
    private Timer timer;
    public JFrame Splash(){
        frame = buildFrame();
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel label = new JLabel("Welcome to Team 17's game!");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(label, BorderLayout.CENTER);

        getContentPane().add(panel);
        pack();
        setLocationRelativeTo(null);
        frame.add(panel, BorderLayout.CENTER);
        createSplashScreen();

        return frame;
    }
    private JFrame createSplashScreen()
    {
        frame.setVisible(true);
        return frame;
    }

    private JFrame buildFrame(){
        JFrame frame = new JFrame("Welcome to our game!"); 
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        return frame;
    }
}