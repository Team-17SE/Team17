
import javax.swing.*;

import java.awt.event.*;

import java.awt.*;

public class playerEntry{
    static String name; // Name inputted
     static int id; // ID inputtd
    
    public void Entry(){
        
        //Creating a whole GUI Frame
        JFrame frame = new JFrame("Welcome to our game!"); 
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        //Setting up the panel used to collect user ID
        JPanel IDpanel = new JPanel();
        JLabel IDlabel = new JLabel("Enter your ID: ");
        JTextField IDtxt = new JTextField(10);
        JButton enter = new JButton("Enter");
        
        //Adding all my panel together
        IDpanel.add(IDlabel);
        IDpanel.add(IDtxt);
        IDpanel.add(enter);

        //Setting up the panel used to collect user name
        JPanel Namepanel = new JPanel();
        JLabel Namelabel = new JLabel("Enter your name: ");
        
        JTextField Nametxt = new JTextField(10);
        JButton enter1 = new JButton("Enter");

        //Collecting my panel together
        Namepanel.add(Namelabel);
        Namepanel.add(Nametxt);
        Namepanel.add(enter1);
        
        //Making the ID panel visible 
        frame.getContentPane().add(BorderLayout.CENTER, IDpanel);
        frame.setVisible(true);

        //Using Action Listner to collect the inputed id after pressing enter
        enter.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent arg0) {
                setID(Integer.parseInt(IDtxt.getText()));
                JLabel ShowID = new JLabel("Player ID: " + getID());
                IDpanel.setVisible(false);
                frame.getContentPane().add(BorderLayout.CENTER, Namepanel);
                frame.getContentPane().add(BorderLayout.SOUTH, ShowID);
            }

         });

         //Using Action Listner to collect the inputted Name after pressing enter 
         enter1.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent arg0) {
                setName(Nametxt.getText());
                JLabel ShowName = new JLabel("Player Name: " + getName());
                Namepanel.setVisible(false);
                frame.getContentPane().add(BorderLayout.CENTER, ShowName);
              //  frame.setVisible(false);
            }

         });
    }

    //Method to set ID
    public void setID(int ID)
    {
        id = ID;
    }

    //Method to retrive Id
    public int getID()
    {
        return id;
    }

    //Method to set Name
    public void setName(String Name)
    {
        name = Name;
    }

    //Method to retrive name
    public String getName()
    {
        return name;
    }

}