import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Comparator;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.sound.sampled.*;
import java.io.*;

public class CountdownTimerApp {
        
        private Clip clip;

        
        private Map<String, Player> playerScores = new HashMap<>();

        // private JFrame interactions = new JFrame("Interactions");
        // private JTextField inputField;
        // private JButton submitButton;

        private JLabel timerLabel;
        private int totalSeconds = 360;
        private int greenTeamCulminative = 0;
        private int redTeamCulminative = 0;
       
        private Timer warning;
        private Timer timer;
        private Timer updateScor; 
        private Timer messageTimer;
        private Timer highestScoreTimer;

        private boolean gameOverDisplayed = false;
        private JTextArea greenTeamTextArea;
        private JTextArea redTeamTextArea;

        private ArrayList<Player> greenTeamPlayers;
        private ArrayList<Player> redTeamPlayers;
        private ArrayList<String> messages = new ArrayList<>();
        private ArrayList<Integer> greenID;
        private ArrayList<Integer> redID;
        private ArrayList<Integer> allID;
        private ArrayList<String> playersWhoTagged = new ArrayList<>();
        private ArrayList<String> songs = new ArrayList<>();

        private JTextArea messageTextArea;
        private JLabel title = new JLabel("Action Starting...");
        private JPanel panel = new JPanel();

        private JLabel greenTeamLabel = new JLabel();
        private JLabel redTeamLabel = new JLabel();

        JPanel greenPanel = new JPanel(new BorderLayout());
         JPanel redPanel = new JPanel(new BorderLayout());
     CountdownTimerApp() {
        fileinsongs();
        JFrame popUp = new JFrame("Warning popup");
        popUp.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        popUp.setSize(300, 500);

        JLabel WarningMessage = new JLabel("Game will start in 30 seconds.");
        popUp.add(WarningMessage, BorderLayout.CENTER);
        popUp.setVisible(true);

         warning = new Timer(30000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                popUp.dispose();
                game();
                warning.stop();
            }
        });
        warning.start();
        

   }
private void game() {
        playRandomSong();
        greenID = new ArrayList<>();
        redID = new ArrayList<>();
        allID = new ArrayList<>();
        loadEquipmentIdsFromFile(greenID, redID, allID);
         JFrame frame = new JFrame("Welcome to our game!"); 
         greenTeamPlayers = new ArrayList<>();
         redTeamPlayers = new ArrayList<>();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        greenTeamPlayers = loadTeamFromFile("greenteam.txt");
        redTeamPlayers = loadTeamFromFile("redteam.txt");
        // JPanel greenPanel = new JPanel(new BorderLayout());
        //  JPanel redPanel = new JPanel(new BorderLayout());
         

        //   greenTeamTextArea = new JTextArea(10, 20);
        //   redTeamTextArea = new JTextArea(10, 20);
          messageTextArea = new JTextArea();

        updateGui();
        
        // greenTeamTextArea.setEditable(false);
        // redTeamTextArea.setEditable(false);
        messageTextArea.setEditable(false);

        //  JLabel topRightLabel = new JLabel("Green Team");
        //  JLabel topLeftLabel = new JLabel("Red Team");
        JPanel messagePanel = new JPanel(new BorderLayout());
        messagePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        messagePanel.add(new JScrollPane(messageTextArea), BorderLayout.CENTER);

        // greenPanel.add(topRightLabel, BorderLayout.NORTH);
        // redPanel.add(topLeftLabel, BorderLayout.NORTH);

        // greenPanel.add(new JScrollPane(greenTeamTextArea), BorderLayout.CENTER);
        // redPanel.add(new JScrollPane(redTeamTextArea), BorderLayout.CENTER);

        // greenPanel.add(greenTeamLabel, BorderLayout.SOUTH);
        // redPanel.add(redTeamLabel, BorderLayout.SOUTH);

        frame.add(greenPanel, BorderLayout.WEST);
        frame.add(redPanel, BorderLayout.EAST);
        frame.add(messagePanel, BorderLayout.SOUTH);
        Timer flashTimer = new Timer(500, new ActionListener() {
            boolean isFlashing = false;
            @Override
            public void actionPerformed(ActionEvent e) {
                isFlashing = !isFlashing;

                if (calGreenTeamCulminative() > calRedTeamCulminative()) {
                    flashLabel(greenTeamLabel, isFlashing);
                    redTeamLabel.setForeground(Color.BLACK);
                }

                if (calRedTeamCulminative() > calGreenTeamCulminative()) {
                    flashLabel(redTeamLabel, isFlashing);
                    greenTeamLabel.setForeground(Color.BLACK);
                }

                if (calGreenTeamCulminative() == calRedTeamCulminative())
                {
                    greenTeamLabel.setForeground(Color.BLACK);
                    redTeamLabel.setForeground(Color.BLACK);
                }
            }
        });
        flashTimer.start();


        updateScor = new Timer(10000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updatePlayerScores();
            }
        });
        messageTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateMessages(frame);
            }
        });
        messageTimer.start();
        updateScor.start();
        JPanel timed = setupTimer();

        frame.getContentPane().add(timed);
        frame.setFocusable(true);
         frame.setVisible(true);
        JFrame inter = interactions(messageTextArea);
        inter.setVisible(true);
        startTimer(frame, inter, updateScor, messageTimer);

}
   private void fileinsongs(){
    songs.add("Track01.wav");
    songs.add("Track02.wav");
    songs.add("Track03.wav");
    songs.add("Track04.wav");
    songs.add("Track05.wav");
    songs.add("Track06.wav");
    songs.add("Track07.wav");
    songs.add("Track08.wav");
   }
   private void playRandomSong() { 
    try {
    Random ranNum = new Random();
    String ranSong = songs.get(ranNum.nextInt(songs.size()));

    File audioFile = new File(ranSong);
    AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

    clip = AudioSystem.getClip();
    clip.open(audioStream);
    clip.start();

    clip.addLineListener(new LineListener() {
        public void update(LineEvent event) {
            if (event.getType() == LineEvent.Type.STOP) {
                clip.close();
            }
        }
    });
    } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
        e.printStackTrace();
    }
}
   
    private ArrayList<Player> loadTeamFromFile(String filename) {
        ArrayList<Player> teamPlayers = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                
                if (parts.length >= 4) {
                    int score = 0;
                    String codeName = parts[3];
                    String FullName = parts[1] + " " + parts[2];
                    teamPlayers.add(new Player(codeName, score, FullName));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return teamPlayers;
    }
    private void updateMessages(JFrame Frame) {
        if (totalSeconds == 360)
        {
            addMessage("Let the games begin!");
        }
        else if  (totalSeconds == 180) {
            String message = "Warning, we're at the half-way point!";
            addMessage(message);
        } else if (totalSeconds == 60) {
            addMessage("Warning: 1 minute remaining!");
        } else if (totalSeconds <= 10) {
            String message = "Counting down the last 10 seconds!";
            addMessage(message);
        }
    }
    
    private void addMessage(String message) {
        messages.add(message);
        if (messages.size() > 5) {
            messages.remove(0); // Remove the oldest message
        }

        updateMessageTextArea();
    }

    private void updateMessageTextArea() {
        for (String message : messages) {
            messageTextArea.setText(message + "\n");
        }
        
    }
private void updatePlayerScores() {
    for (Player player : greenTeamPlayers) {
        player.addToScore(10);
    }

    for (Player player : redTeamPlayers) {
        player.addToScore(10);
    }

    updateGui();
}
private void updateGui() {
    // updatePlayerScoresTextArea( greenTeamPlayers);
    // updatePlayerScoresTextArea( redTeamPlayers);
    updatePlayers();
    // redTeamLabel.setText("Culminative Score: " + String.valueOf(calRedTeamCulminative()));
    // greenTeamLabel.setText("Culminative Score: " + String.valueOf(calGreenTeamCulminative()));
}
// private void updatePlayerScoresTextArea(JTextArea textArea, List<Player> teamPlayers) {
private void updatePlayers(){
        // teamPlayers.sort(Comparator.comparingInt(Player::getScore).reversed());
        greenTeamPlayers.sort(Comparator.comparingInt(Player::getScore).reversed());
        redTeamPlayers.sort(Comparator.comparingInt(Player::getScore).reversed());
        // textArea.setText("");
        greenPanel.removeAll();
        greenPanel.revalidate();
        greenPanel.repaint();

        redPanel.removeAll();
        redPanel.revalidate();
        redPanel.repaint();
        
        String players = new String();
        greenPanel.setLayout(new BoxLayout(greenPanel, BoxLayout.Y_AXIS));
        JLabel greenLabel = new JLabel("Green Team");
        greenLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        greenPanel.add(greenLabel, BorderLayout.NORTH);
        String styledText = "<html><i>B</i></html>";
        for (int i = 0; i < greenTeamPlayers.size(); i++)
        {
            String playerName = greenTeamPlayers.get(i).getName();
            String playerCodeName = greenTeamPlayers.get(i).getCodeName();
            int playerScore = greenTeamPlayers.get(i).getScore();
            JLabel playerLabel;
            if (playersWhoTagged.contains(greenTeamPlayers.get(i).getName()))
            {
                playerLabel = new JLabel("<html><i>B</i> " + playerName + " " + playerCodeName + ": " + playerScore + "</html>");
                // players = "B " + greenTeamPlayers.get(i).getName() +  " "+ greenTeamPlayers.get(i).getCodeName() + ": " + greenTeamPlayers.get(i).getScore();
            }

            else {
                playerLabel = new JLabel(playerName + " " + playerCodeName + ": " + playerScore);
                // players = greenTeamPlayers.get(i).getName() + " "+ greenTeamPlayers.get(i).getCodeName() + ": " + greenTeamPlayers.get(i).getScore();
            }
            // JLabel playerLabel = new JLabel(players);
            playerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            greenPanel.add(playerLabel);
        }
            // System.out.println(greenTeamPlayers.get(i).getName());

            redPanel.setLayout(new BoxLayout(redPanel, BoxLayout.Y_AXIS));
            JLabel redLabel = new JLabel("Red Team");
            redLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            redPanel.add(redLabel, BorderLayout.NORTH);
        for (int i = 0; i < redTeamPlayers.size(); i++)
        {
            String playerName = redTeamPlayers.get(i).getName();
            String playerCodeName = redTeamPlayers.get(i).getCodeName();
            int playerScore = redTeamPlayers.get(i).getScore();
            JLabel playerLabel;
            if (playersWhoTagged.contains(redTeamPlayers.get(i).getName()))
            {
                playerLabel = new JLabel("<html><i>B</i> " + playerName + " " + playerCodeName + ": " + playerScore + "</html>");
            }
            else {
                playerLabel = new JLabel(playerName + " " + playerCodeName + ": " + playerScore);
            }
            playerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            redPanel.add(playerLabel);
        }

        redTeamLabel.setText("Culminative Score: " + String.valueOf(calRedTeamCulminative()));
        greenTeamLabel.setText("Culminative Score: " + String.valueOf(calGreenTeamCulminative()));
        greenPanel.add(greenTeamLabel, BorderLayout.SOUTH);
        redPanel.add(redTeamLabel, BorderLayout.SOUTH);
        Component[] componentsGreen = greenPanel.getComponents();
        Component[] componentsRed = redPanel.getComponents();
        JLabel firstGreenLabel = (JLabel) componentsGreen[1];
        JLabel firstRedLabel = (JLabel) componentsRed[1];
        // System.out.println(firstGreenLabel.getText());
        
        highestScoreTimer = new Timer(500, new ActionListener() {
            boolean isFlashing = false;
            @Override
            public void actionPerformed(ActionEvent e) {
                isFlashing = !isFlashing;

                if (greenTeamPlayers.get(0).getScore() > redTeamPlayers.get(0).getScore()) {
                    // System.out.println(greenTeamPlayers.get(0).getScore());
                    // String label = firstGreenLabel.getText();
                    // System.out.println(label);
                    flashLabel(firstGreenLabel, isFlashing);
                    firstRedLabel.setForeground(Color.BLACK);

                    for (int i = 2; i < componentsGreen.length - 1; i++)
                    {
                        JLabel clear = (JLabel)componentsGreen[i];
                        clear.setForeground(Color.BLACK);
                    }

                    for (int i = 2; i < componentsRed.length - 1; i++)
                    {
                        JLabel clear = (JLabel)componentsRed[i];
                        clear.setForeground(Color.BLACK);
                    }
                }

                if (greenTeamPlayers.get(0).getScore() < redTeamPlayers.get(0).getScore()) {
                    flashLabel(firstRedLabel, isFlashing);
                    firstGreenLabel.setForeground(Color.BLACK);
                    for (int i = 2; i < componentsGreen.length - 1; i++)
                    {
                        JLabel clear = (JLabel)componentsGreen[i];
                        clear.setForeground(Color.BLACK);
                    }

                    for (int i = 2; i < componentsRed.length - 1; i++)
                    {
                        JLabel clear = (JLabel)componentsRed[i];
                        clear.setForeground(Color.BLACK);
                    }
                }

                if (greenTeamPlayers.get(0).getScore() == redTeamPlayers.get(0).getScore())
                {
                    firstRedLabel.setForeground(Color.BLACK);
                    firstGreenLabel.setForeground(Color.BLACK);
                    for (int i = 2; i < componentsGreen.length - 1; i++)
                    {
                        JLabel clear = (JLabel)componentsGreen[i];
                        clear.setForeground(Color.BLACK);
                    }

                    for (int i = 2; i < componentsRed.length - 1; i++)
                    {
                        JLabel clear = (JLabel)componentsRed[i];
                        clear.setForeground(Color.BLACK);
                    }
                }
            }
        });
        highestScoreTimer.start();
        // for (Player player : teamPlayers) {
        //     if (playersWhoTagged.contains(player.getName()))
        //     {
        //         textArea.append(player.getName() + " B " + player.getCodeName() + ": " + player.getScore() + "\n");
        //     }
        //     else {
        //     textArea.append(player.getName() + " " + player.getCodeName() + ": " + player.getScore() + "\n");
        //     }
        // }
    

    
}

private int calGreenTeamCulminative(){
    int greenTeamCulminative = 0;
    for (int i = 0; i < greenTeamPlayers.size(); i++)
    {
        greenTeamCulminative += greenTeamPlayers.get(i).getScore();
    }

    return greenTeamCulminative;
}

private int calRedTeamCulminative() {
    int redTeamCulminative = 0;
    for (int i = 0; i < redTeamPlayers.size(); i++)
    {
        redTeamCulminative += redTeamPlayers.get(i).getScore();
    }

    return redTeamCulminative;
}
    private JPanel setupTimer()
    {
        // JLabel title = new JLabel("Action Starting...");
        title.setHorizontalAlignment(JLabel.CENTER);
        timerLabel = new JLabel("00:00");
        timerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        timerLabel.setHorizontalAlignment(JLabel.CENTER);

        // JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(timerLabel, BorderLayout.CENTER);
        panel.add(title, BorderLayout.NORTH);

        return panel;
    }

    private void startTimer(JFrame frame, JFrame interactions, Timer time2, Timer time3) {
        updateTimerLabel();
        // playRandomSong();
        JButton returnButton = new JButton("Game is over, press to return to player entry");
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                frame.dispose();
                // ClearPlayers("greenteam.txt");
                // ClearPlayers("redteam.txt");
                // ClearPlayers("greenteamequip.txt");
                // ClearPlayers("redteamequip.txt");
                ActionScreen action = new ActionScreen();
            }
        });
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                totalSeconds--;
                updateTimerLabel();
                if (totalSeconds <= 0 && !gameOverDisplayed) {
                   // frame.dispose();
                   // openGameOverFrame();
                   panel.remove(title);
                   clip.stop();
                   JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                   buttonPanel.add(returnButton);
                //    frame.getContentPane().removeAll();
                   frame.add(buttonPanel, BorderLayout.CENTER);
                   frame.revalidate();
                    gameOverDisplayed = true;
                    interactions.setVisible(false);
                    timer.stop();
                    time2.stop();
                    time3.stop();
                    highestScoreTimer.stop();
                }
            }
        });
        timer.start();
    }

private void ClearPlayers(String filename){
    try {
        FileWriter fileWriter = new FileWriter(filename);
        fileWriter.close(); 

        
    } catch (IOException e) {
        e.printStackTrace();
    }
}
    private void flashLabel(JLabel label, boolean isFlashing) {
        if (isFlashing) {
            label.setForeground(Color.YELLOW);
        } else {
            label.setForeground(Color.BLACK);
        }
    }
    private void updateTimerLabel() {
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        String formattedTime = String.format("%02d:%02d", minutes, seconds);
        timerLabel.setText(formattedTime);
    }
    private JFrame interactions(JTextArea messageBox) {
        JFrame interaction = new JFrame("Interactions");
         JTextField inputField;
         JButton submitButton;
        JTextArea Box = messageBox;
         interaction.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel panel = new JPanel();
        
        inputField = new JTextField(15);
        submitButton = new JButton("Submit");


        panel.add(inputField);
        panel.add(submitButton);
        
        interaction.add(panel);
        
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String input = inputField.getText();
                {
                    try {
                        simulateTaggingEvent(Box, input);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Invalid input format");
                    }
                } 
            }
        });
        interaction.pack();
        interaction.setLocationRelativeTo(null);
        return interaction;
    }
    // private void simulateTaggingEvent(String input, JTextArea messageBox) {
       private void simulateTaggingEvent(JTextArea messageBox, String input){ 

        // interactions.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // JPanel panel = new JPanel();
        // System.out.println(input);
        String inputting = input;
        // inputField = new JTextField(15);
        // submitButton = new JButton("Submit");
        String[] parts = inputting.split(":");
        int playerTagging = Integer.parseInt(parts[0]);
        int playerTagged = Integer.parseInt(parts[1]);
        // System.out.println(playerTagging + " " + playerTagged);
        
            if (greenID.contains(playerTagging) && greenID.contains(playerTagged)) {
                int index1 = greenID.indexOf(playerTagging);
                int index2 = greenID.indexOf(playerTagged);
                // System.out.println("Green team self-injury!");
                
                // addMessage(greenTeamPlayers.get(index1).getName() + " just tagged " + greenTeamPlayers.get(index2).getName() + ". Green team self-injury!" + "\n");
                messageTextArea.append(greenTeamPlayers.get(index1).getCodeName() + " just tagged " + greenTeamPlayers.get(index2).getCodeName() + ". Green team self-injury!" + "\n");
            }

            else if (greenID.contains(playerTagging) && redID.contains(playerTagged))
            {
                int index1 = greenID.indexOf(playerTagging);
                int index2 = redID.indexOf(playerTagged);
                // System.out.println("Green team tagged red team!!");
                // addMessage(greenTeamPlayers.get(index1).getName() + " just scored for the green team! Tagged "+ redTeamPlayers.get(index2).getName() + "\n");
                messageTextArea.append(greenTeamPlayers.get(index1).getCodeName() + " just scored for the green team! Tagged "+ redTeamPlayers.get(index2).getCodeName() + "\n");
                greenTeamPlayers.get(index1).addToScore(100);
                redTeamPlayers.get(index2).subToScore(10);
                playersWhoTagged.add(greenTeamPlayers.get(index1).getName());
                updateGui();
            }

             else if (redID.contains(playerTagging) && greenID.contains(playerTagged))
            {
                int index1 = redID.indexOf(playerTagging);
                int index2 = greenID.indexOf(playerTagged);
                // System.out.println("Red team tagged green team!");
                // addMessage(redTeamPlayers.get(index1).getName() + " just scored for the red team! Tagged " + greenTeamPlayers.get(index2).getName() + "\n");
                messageTextArea.append(redTeamPlayers.get(index1).getCodeName() + " just scored for the red team! Tagged " + greenTeamPlayers.get(index2).getCodeName() + "\n");
                redTeamPlayers.get(index1).addToScore(100);
                greenTeamPlayers.get(index2).subToScore(10);
                playersWhoTagged.add(redTeamPlayers.get(index1).getName());
                updateGui();

            }

            else if (redID.contains(playerTagging) && redID.contains(playerTagged)) {
                
                int index1 = redID.indexOf(playerTagging);
                int index2 = redID.indexOf(playerTagged);
                // System.out.println("Red team self-injury!");
                //  addMessage(redTeamPlayers.get(index1).getName() + " just tagged " + redTeamPlayers.get(index2).getName() + ". Red team self-injury!" + "\n");
                messageTextArea.append(redTeamPlayers.get(index1).getCodeName() + " just tagged " + redTeamPlayers.get(index2).getCodeName() + ". Red team self-injury!" + "\n");
            }
        //     taggingTimer.setInitialDelay(getRandomInterval());
        // taggingTimer.restart();
            
       }
        

    private void loadEquipmentIdsFromFile(ArrayList<Integer> greenID,ArrayList<Integer> redID,ArrayList<Integer> allID) {
        try (BufferedReader reader = new BufferedReader(new FileReader("greenteamequip.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                int equipmentId = Integer.parseInt(line.trim());
                greenID.add(equipmentId);
                allID.add(equipmentId);
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }

        try (BufferedReader reader = new BufferedReader(new FileReader("redteamequip.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                int equipmentId = Integer.parseInt(line.trim());
                redID.add(equipmentId);
                allID.add(equipmentId);
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private static class Player {
        private String codeName;
        private String FullName;
        private int score;
        
        private ArrayList<Integer> idsforGreenteam;
        private ArrayList<Integer> idsforRedteam;

        public Player(String codeName, int score, String FullName) {
            this.codeName = codeName;
            this.FullName = FullName;
            this.score = score;
        }

        public String getCodeName() {
            return codeName;
        }

        public String getName() {
            return FullName;
        }

        public int getScore() {
            return score;
        }

        public void addToScore(int points) {
            score += points;
        }

        public void subToScore(int points)
        {
            if (score == 0)
            {
                score = 0;
            }

            else{
                score -= points;
            }
        }

    
}

}




