import javax.swing.*;
import java.awt.event.*;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.*;
import java.util.Vector;
import java.util.stream.Stream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class ActionScreen {

   private playerEntry Entry;
   private CountdownTimerApp timer;
   private int waringTime = 30;
   private JPanel timerDisplay;
   private CountdownTimerApp coundownTimer;
   private Vector<String> lines = new Vector<>();
   private Vector<String> redTeam = new Vector<>();
   private Vector<String> GreencodeNames = new Vector<>();
//    private
    ActionScreen(){ 
        JFrame test = displayActionScreen();
        f5KeyPress(test);
        
    }

    private JFrame displayActionScreen() {
        JFrame frame = buildFrame();
         JLabel title = new JLabel("Player List/Entry");
         title.setHorizontalAlignment(JLabel.CENTER);
         JPanel greenPanel = new JPanel(new BorderLayout());
         JPanel redPanel = new JPanel(new BorderLayout());
         JPanel bottomePanel = new JPanel(new BorderLayout());

         JTextArea greenTeamTextArea = new JTextArea(10, 20);
         JTextArea redTeamTextArea = new JTextArea(10, 20);

        buildList();
        for (int i = 0; i < lines.size(); i++){
            String userinfo = lines.get(i);
            greenTeamTextArea.append(userinfo + "\n");
        }

        for (int i = 0; i < redTeam.size(); i++) {
            String redinfo = redTeam.get(i);
            redTeamTextArea.append(redinfo + "\n");
        }

         JLabel topRightLabel = new JLabel("Green Team");
         JLabel topLeftLabel = new JLabel("Red Team");

        JLabel label = new JLabel("[f1 to add new player]");
         JLabel lowerLeftLabel = new JLabel("[f5 to start game]");

         JLabel lowerRightLabel = new JLabel("[f12 to clear player list]");

         bottomePanel.add(lowerLeftLabel, BorderLayout.WEST);
         bottomePanel.add(lowerRightLabel, BorderLayout.EAST);
         bottomePanel.add(label, BorderLayout.CENTER);

        greenPanel.add(topRightLabel, BorderLayout.NORTH);
        redPanel.add(topLeftLabel, BorderLayout.NORTH);

        greenPanel.add(new JScrollPane(greenTeamTextArea), BorderLayout.CENTER);
        redPanel.add(new JScrollPane(redTeamTextArea), BorderLayout.CENTER);

        frame.add(greenPanel, BorderLayout.WEST);
        frame.add(redPanel, BorderLayout.EAST);
        frame.add(title, BorderLayout.CENTER);
        frame.getContentPane().add(bottomePanel, BorderLayout.SOUTH);

         frame.setFocusable(true);
        frame.requestFocus();
       // frame.pack();
        frame.setVisible(true);
        f1KeyPress(frame, greenTeamTextArea, redTeamTextArea);
        
        clearForm(frame, greenTeamTextArea, redTeamTextArea);
        return frame;
    }

    private JFrame buildFrame(){
        JFrame frame = new JFrame("Welcome to our game!"); 
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 

        return frame;
    }

    private void f5KeyPress(JFrame frame)
    {
        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                
                if (e.getKeyCode() == KeyEvent.VK_F5) {
                    // JFrame popup = warningPopup();
                    // popup.setLocationRelativeTo(frame);
                    // String breaked = "0";

                    
                    
                    // Timer timer = new Timer(30000, new ActionListener() {
                    // @Override
                    // public void actionPerformed(ActionEvent e) {
                    // String breaked = "1111";
                    // try {
                    //     breakSocket(breaked);
                    // } catch (IOException e1) {
                    //     // TODO Auto-generated catch block
                    //     e1.printStackTrace();
                    // }
                    // popup.dispose();
                    frame.setVisible(false);
                    coundownTimer = new CountdownTimerApp();

                    }
                //     });
                //     timer.setRepeats(false);
                //     timer.start();
                // }
            }

            @Override
            public void keyReleased(KeyEvent e) {
               
            }
        });

    }

    // private JFrame warningPopup()
    // {
    //     JFrame popUp = new JFrame("Warning popup");
    //     popUp.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    //     popUp.setSize(300, 500);

    //     JLabel WarningMessage = new JLabel("Game will start in 30 seconds.");
    //     popUp.add(WarningMessage, BorderLayout.CENTER);
    //     popUp.setVisible(true);

    //     return popUp;
    // }

     private void f1KeyPress(JFrame frame, JTextArea greenTeamTextArea, JTextArea redTeamTextArea)
    {
        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_F1) {
                    playerEntry test = new playerEntry(greenTeamTextArea, redTeamTextArea);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
               
            }
        });

    }

    private void clearForm(JFrame frame, JTextArea greenTeamTextArea, JTextArea redTeamTextArea){
        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_F12) {
                    try {
                        FileWriter writer = new FileWriter("greenteam.txt");
                        writer.close();
                             //   JOptionPane.showMessageDialog(this, "Information cleared successfully!");
                    } catch (IOException b) {
                               // JOptionPane.showMessageDialog(this, "An error occurred while clearing information.");
                    b.printStackTrace();
                    }
                    try {
                        FileWriter writer = new FileWriter("redteam.txt");
                        writer.close();
                             //   JOptionPane.showMessageDialog(this, "Information cleared successfully!");
                    } catch (IOException b) {
                               // JOptionPane.showMessageDialog(this, "An error occurred while clearing information.");
                    b.printStackTrace();
                    }
                    try {
                        FileWriter writer = new FileWriter("greenteamequip.txt");
                        writer.close();
                             //   JOptionPane.showMessageDialog(this, "Information cleared successfully!");
                    } catch (IOException b) {
                               // JOptionPane.showMessageDialog(this, "An error occurred while clearing information.");
                    b.printStackTrace();
                    }
                    try {
                        FileWriter writer = new FileWriter("redteamequip.txt");
                        writer.close();
                             //   JOptionPane.showMessageDialog(this, "Information cleared successfully!");
                    } catch (IOException b) {
                               // JOptionPane.showMessageDialog(this, "An error occurred while clearing information.");
                    b.printStackTrace();
                    }

                    JFrame cleared = new JFrame("Clearing List");
                    JLabel label = new JLabel("Player List Cleared!");
                    // cleared.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    cleared.setSize(600, 600);

                    //  frame.setVisible(false);
                    //  clear clearForm = new clear();
                    greenTeamTextArea.setText("");
                    redTeamTextArea.setText("");
                    cleared.add(label, BorderLayout.CENTER);
                    cleared.setVisible(true);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
               
            }
        });
    }

private void buildList(){
    Path filePath = Path.of("greenteam.txt");
    try {
        if (Files.exists(filePath)) {
            Stream<String> fileLines = Files.lines(filePath);

            fileLines.forEach(line -> {
                if (!line.isEmpty()) {
                    lines.add(line);
                }
            });

            fileLines.close(); 
        }
    }
    catch (IOException e) {
        e.printStackTrace();
    }

    Path redPath = Path.of("redteam.txt");

    try {
        if (Files.exists(redPath)) {
            Stream<String> fileLines = Files.lines(redPath);

            fileLines.forEach(line -> {
                if (!line.isEmpty()) {
                    redTeam.add(line);
                }
            });

            fileLines.close(); 
        }
    }
    catch (IOException e) {
        e.printStackTrace();
    }
}

}
