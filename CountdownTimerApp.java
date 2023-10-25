import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.util.Vector;
import java.util.stream.Stream;

public class CountdownTimerApp {
    private JLabel timerLabel;
        private int totalSeconds = 0;
        private Timer timer;
        private boolean gameOverDisplayed = false;
        private Vector<String> GreencodeNames = new Vector<>();
        private Vector<String> redcodeNames = new Vector<>();
     CountdownTimerApp() {
        buildList();
         JFrame frame = new JFrame("Welcome to our game!"); 
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        JPanel greenPanel = new JPanel(new BorderLayout());
         JPanel redPanel = new JPanel(new BorderLayout());
         JPanel bottomePanel = new JPanel(new BorderLayout());

         JTextArea greenTeamTextArea = new JTextArea(10, 20);
         JTextArea redTeamTextArea = new JTextArea(10, 20);
        for (int i = 0; i < GreencodeNames.size(); i++){
            String userinfo = GreencodeNames.get(i);
            greenTeamTextArea.append(userinfo + "\n");
        }

        for (int i = 0; i < redcodeNames.size(); i++) {
            String redinfo = redcodeNames.get(i);
            redTeamTextArea.append(redinfo + "\n");
        }

         JLabel topRightLabel = new JLabel("Green Team");
         JLabel topLeftLabel = new JLabel("Red Team");



        greenPanel.add(topRightLabel, BorderLayout.NORTH);
        redPanel.add(topLeftLabel, BorderLayout.NORTH);

        greenPanel.add(new JScrollPane(greenTeamTextArea), BorderLayout.CENTER);
        redPanel.add(new JScrollPane(redTeamTextArea), BorderLayout.CENTER);

        frame.add(greenPanel, BorderLayout.WEST);
        frame.add(redPanel, BorderLayout.EAST);
        frame.getContentPane().add(bottomePanel, BorderLayout.SOUTH);

        JPanel timed = setupTimer();

        frame.getContentPane().add(timed);
        startTimer(frame);
        frame.setFocusable(true);
         frame.setVisible(true);

    }

private void buildList(){
    String filePath = "greenteam.txt";
    String filePath2 = "redteam.txt";
    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                
                if (parts.length >= 4) {
                    String codeName = parts[3];
                    GreencodeNames.add(codeName);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    
    try (BufferedReader reader = new BufferedReader(new FileReader(filePath2))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                
                if (parts.length >= 4) {
                    String codeName = parts[3];
                    redcodeNames.add(codeName);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
}
    

    private JPanel setupTimer()
    {
        JLabel title = new JLabel("Action Starting...");
        title.setHorizontalAlignment(JLabel.CENTER);
        timerLabel = new JLabel("00:00");
        timerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        timerLabel.setHorizontalAlignment(JLabel.CENTER);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(timerLabel, BorderLayout.CENTER);
        panel.add(title, BorderLayout.NORTH);

        return panel;
    }

    private void startTimer(JFrame frame) {
        totalSeconds = 240;
        updateTimerLabel();

        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                totalSeconds--;
                updateTimerLabel();
                if (totalSeconds <= 0 && !gameOverDisplayed) {
                    frame.dispose();
                    openGameOverFrame();
                    gameOverDisplayed = true;
                }
            }
        });
        timer.start();
    }

    private void openGameOverFrame(){
        JFrame gameOverFrame = new JFrame("Game Over");
        gameOverFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel messageLabel = new JLabel("Game is over. Please exit.");
        gameOverFrame.add(messageLabel);
        gameOverFrame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        // gameOverFrame.setUndecorated(true); 
        gameOverFrame.setVisible(true);
    }
    private void updateTimerLabel() {
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        String formattedTime = String.format("%02d:%02d", minutes, seconds);
        timerLabel.setText(formattedTime);
    }

    
}