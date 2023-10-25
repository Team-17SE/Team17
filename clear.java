import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileWriter;
import java.io.IOException;
public class clear {

    private ActionScreen transfer;
    clear(){
        JFrame frame = buildFrame();
        frame.setVisible(true);
    }

    private JFrame buildFrame()
    {
        JFrame frame = new JFrame("Welcome to our game!"); 
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 

        JLabel title = new JLabel("Player list cleared!!!! Press button to continue: ");
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JButton button = new JButton("Enter");
        panel.add(title);
        panel.add(button);

        frame.add(panel, BorderLayout.CENTER);
        pressEnter(button, frame);

        return frame;
    }

    private void pressEnter(JButton button, JFrame frame)
    {
        button.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent arg0) {
                frame.setVisible(false);
                transfer = new ActionScreen();
            }

         });
    }

    //   private void clearUserInfoFile() {
    //     try {
    //         FileWriter writer = new FileWriter("userinfo.txt");
    //         writer.close();
    //      //   JOptionPane.showMessageDialog(this, "Information cleared successfully!");
    //     } catch (IOException e) {
    //        // JOptionPane.showMessageDialog(this, "An error occurred while clearing information.");
    //         e.printStackTrace();
    //     }
    // }
}