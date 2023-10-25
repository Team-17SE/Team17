import java.awt.BorderLayout;
import java.awt.FlowLayout;


import javax.swing.*;

import java.awt.event.*;

import java.awt.*;


public class Game {

    private playerEntry Player;
    private SplashJava splash;
    private final int time = 3000;
    private Timer timer;
    private JFrame splashFrame;
    private ActionScreen actionScreen;


    Game() {
        
    }
    public void execute()
    {
        splashFrame = SplashScreen().Splash();

        Timer timer = new Timer(time, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                splashFrame.setVisible(false);
             ActionScreen playAction = new ActionScreen();

            }
        });
        timer.setRepeats(false); 
        timer.start();

    }

    private SplashJava SplashScreen() 
    {
        SplashJava splash = new SplashJava();
        return splash;
    }
    
    public void pEntryorTeamList()
    {
        JFrame frame = new JFrame("Welcome to our game!"); 
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JLabel entryLabel = new JLabel("Press this button for new player entry");
        JButton entryButton = new JButton("Enter");

        centerPanel.add(entryLabel);
        centerPanel.add(entryButton);

        JLabel listLabel = new JLabel("Press this button for team list");
        JButton listButton = new JButton("Enter");


        centerPanel.add(listLabel);
        centerPanel.add(listButton);
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.setVisible(true);
       
        frame.setLocationRelativeTo(null);

         entryButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent arg0) {
                frame.setVisible(false);
            }

         });

         listButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent arg0) {
                frame.setVisible(false);
                actionScreen = new ActionScreen();
            }

         });


    }


}


