import java.util.Vector;
import java.util.stream.Stream;

import javax.swing.*;
import java.net.*;
import java.io.*;
import java.awt.*;
import java.io.FileWriter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


public class playerEntry{
    private String Info; // ID inputtd
    private ActionScreen action;
    
    playerEntry(JTextArea greenTeamTextArea, JTextArea redTeamTextArea)
    {
        GreenTeamAdd(greenTeamTextArea, redTeamTextArea);
    }
    public void GreenTeamAdd(JTextArea greenTeamTextArea, JTextArea redTeamTextArea){
    
    
      JFrame frame = new JFrame("Green Team add"); 
        // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        // frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        JPanel IDpanel = new JPanel();
        JLabel IDlabel = new JLabel("Enter your Info(ID/FirstName/LastName/Code): ");
        JLabel label = new JLabel("(Press F2 to add to red team");
        JTextField IDtxt = new JTextField(50);
        JButton enter = new JButton("Enter");
        
        //Adding all my panel together
        IDpanel.add(IDlabel);
        IDpanel.add(IDtxt);
        IDpanel.add(enter);

        frame.add(IDpanel, BorderLayout.CENTER);
        frame.add(label, BorderLayout.SOUTH);
       // frame.add(ExitPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
       
        frame.setLocationRelativeTo(null);
        
         enter.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent arg0) {
                if (greenTeamFull()){
                String currentInfo = greenTeamTextArea.getText();
                // System.out.println(currentInfo);
                String information = setInfo(IDtxt.getText()); 
                saveUserInfoToFile(information);
                greenTeamTextArea.setText(currentInfo + information + '\n');
              //  System.out.println(information);
              //  action = new ActionScreen();
               frame.setVisible(false);
               greenTeamEquipAdd();
                }

                else {
                    frame.setVisible(false);
                    JFrame frame2 = new JFrame("Can't add to Green Team"); 
                    // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    JLabel label = new JLabel("Green team is full, can't add");
                    
                    frame2.add(label, BorderLayout.CENTER);
                    frame2.setSize(600, 600);
                    frame2.setLocationRelativeTo(null);

                    frame2.setVisible(true);
                }
            //    action = new ActionScreen();
            }

         });

         frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_F2) {
                    frame.setVisible(false);
                    redTeamAdd(greenTeamTextArea, redTeamTextArea);
                }
            }
        });

        frame.setFocusable(true);
        frame.requestFocusInWindow();
    
         
    }

    private void redTeamAdd(JTextArea greenTeamTextArea, JTextArea redTeamTextArea) {

      JFrame frame = new JFrame("Red Team add"); 
        frame.setSize(600, 600);
        JPanel IDpanel = new JPanel();
        JLabel IDlabel = new JLabel("Enter your Info(ID/FirstName/LastName/Code): ");
        JLabel label = new JLabel("(Press F2 to add to green team");
        JTextField IDtxt = new JTextField(50);
        JButton enter = new JButton("Enter");
        
        IDpanel.add(IDlabel);
        IDpanel.add(IDtxt);
        IDpanel.add(enter);

        frame.add(IDpanel, BorderLayout.CENTER);
        frame.add(label, BorderLayout.SOUTH);
        frame.setVisible(true);
       
        frame.setLocationRelativeTo(null);
        
         enter.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent arg0) {
                if (redTeamFull()){
                String currentInfo = redTeamTextArea.getText();
                String information = setInfo(IDtxt.getText()); 
                
                redTeamTextArea.setText(currentInfo + information + '\n');
                saveToRedFile(information);
               frame.setVisible(false);
               redTeamEquipAdd();
            }

            else {
                frame.setVisible(false);
                    JFrame frame2 = new JFrame("Can't add to Red Team"); 
                    JLabel label = new JLabel("Red team is full, can't add");
                    
                    frame2.add(label, BorderLayout.CENTER);
                    frame2.setSize(600, 600);
                    frame2.setLocationRelativeTo(null);

                    frame2.setVisible(true);
            }
        }
         });

         frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_F2) {
                    frame.setVisible(false);
                    GreenTeamAdd(greenTeamTextArea, redTeamTextArea);
                }
            }
        });

        frame.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
        frame.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);
        frame.setFocusable(true);
        frame.requestFocusInWindow();
    }
    private void saveUserInfoToFile(String userInfo) {
        try {
            FileWriter writer = new FileWriter("greenteam.txt", true);
            writer.write(userInfo + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveToRedFile(String userInfo)
    {
         try {
            FileWriter writer = new FileWriter("redteam.txt", true);
            writer.write(userInfo + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String setInfo(String info)
    {
        String information = new String();
         Info = info;

        for (int i = 0; i < Info.length(); i++)
        {
            
            if (Info.charAt(i) == '/'){
                information += " ";
                continue;
            }
            information += Info.charAt(i);
        }

        return information;
    }
private boolean greenTeamFull(){
    boolean decider = false;

    Vector<String> lines = new Vector<>();
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

    if (lines.size() < 15)
    {
        decider = true;
    }

    return decider;
}

private boolean redTeamFull(){
boolean decider = false;

    Vector<String> lines = new Vector<>();
     Path filePath = Path.of("redteam.txt");
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

    if (lines.size() < 15)
    {
        decider = true;
    }  

    return decider;
}

private void greenTeamEquipAdd(){
    JFrame frame = new JFrame("Green Team Equipment add");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(400, 200);

    JPanel panel = new JPanel();
    frame.add(panel);

    JLabel label = new JLabel("Enter Equipment ID: ");
    panel.add(label);

    JTextField equipmentIDField = new JTextField(10);
    panel.add(equipmentIDField);

    JButton sendButton = new JButton("Send");
    panel.add(sendButton);

    JTextArea responseArea = new JTextArea(5, 20);
    responseArea.setEditable(false);
    panel.add(responseArea);
    frame.setVisible(true);
    
    frame.setLocationRelativeTo(null);
    sendButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent arg0) {
                String equipID = equipmentIDField.getText().trim();
                if (!equipID.isEmpty()) {
                    // try {
                        int id = Integer.parseInt(equipID);
                        // sendEquipID(equipID, false);
                        storeGreenID(id);
                    // } catch (NumberFormatException ex) {
                    //     System.out.println("Invalid ID input. Please enter a valid integer.");
                    // } catch (IOException e) {
                    //     e.printStackTrace();
                    // }
                } else {
                    System.out.println("ID input is empty. Please enter a valid integer.");
                }
                frame.setVisible(false);
                
            }
         });

}
private void redTeamEquipAdd(){
    JFrame frame = new JFrame("Red Team Equipment add");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(400, 200);

    JPanel panel = new JPanel();
    frame.add(panel);

    JLabel label = new JLabel("Enter Equipment ID: ");
    panel.add(label);

    JTextField equipmentIDField = new JTextField(10);
    panel.add(equipmentIDField);

    JButton sendButton = new JButton("Send");
    panel.add(sendButton);

    JTextArea responseArea = new JTextArea(5, 20);
    responseArea.setEditable(false);
    panel.add(responseArea);
    frame.setVisible(true);
    
    frame.setLocationRelativeTo(null);
    sendButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent arg0) {
                String equipID = equipmentIDField.getText().trim();
                if (!equipID.isEmpty()) {
                    // try {
                        int id = Integer.parseInt(equipID);
                        // sendEquipID(equipID, false);
                        storeRedID(id);
                    // } catch (NumberFormatException ex) {
                    //     System.out.println("Invalid ID input. Please enter a valid integer.");
                    // } catch (IOException e) {
                    //     // TODO Auto-generated catch block
                    //     e.printStackTrace();
                    // }
                } else {
                    System.out.println("ID input is empty. Please enter a valid integer.");
                }
                frame.setVisible(false);
                
            }
         });

}
private void storeGreenID(int id)
{
    try (FileWriter writer = new FileWriter("greenteamequip.txt", true)) {
        writer.write(id + System.lineSeparator());
       
    } catch (IOException e) {
        e.printStackTrace();
    }
}
private void storeRedID(int id)
{
    try (FileWriter writer = new FileWriter("redteamequip.txt", true)) {
        writer.write(id + System.lineSeparator());
       
    } catch (IOException e) {
        e.printStackTrace();
    }
}

// private void sendEquipID(String id, boolean gameStarting) throws IOException
// {
//     DatagramSocket ds = new DatagramSocket();

// 	InetAddress ip = InetAddress.getLocalHost();
// 	byte buf[] = null;
//     int serverPort = 7500;
    
//     buf = id.getBytes();

//     DatagramPacket DpSend =
// 				new DatagramPacket(buf, buf.length, ip, serverPort);
    
//     ds.send(DpSend);
//     ds.close();
    

// }
}

