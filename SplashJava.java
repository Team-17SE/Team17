import javax.swing.*;
import java.awt.*;
import java.awt.BorderLayout;

public class SplashJava extends JFrame {
    private JFrame frame;
    private final int time = 3000;
    private Timer timer;
    private JLabel imageLabel;

    public JFrame Splash(){
        JFrame frame = new JFrame("Welcome to our game!");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setUndecorated(true); 

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ImageIcon imageIcon = new ImageIcon("logo.jpg");
        int width = imageIcon.getIconWidth();
        int height = imageIcon.getIconHeight();
        double scale = 1.0;
        int screenWidth = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
        int screenHeight = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
        if (width > screenWidth || height > screenHeight) {
            double scaleX = (double) screenWidth / width;
            double scaleY = (double) screenHeight / height;
            scale = Math.min(scaleX, scaleY);
            width = (int) (width * scale);
            height = (int) (height * scale);
        }
        imageIcon.setImage(imageIcon.getImage().getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH));
        imageLabel = new JLabel(imageIcon);


        frame.add(imageLabel);

        frame.pack();
        frame.setLocationRelativeTo(null); 
        frame.setVisible(true);

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
        // frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        return frame;
    }
}