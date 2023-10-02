
import java.util.Timer;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.TimerTask;
import java.util.*;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

class Helper extends TimerTask
{
    public static int i = 0;
    public void run()
    {
        System.out.println("Timer ran " + ++i);
        if(i == 4)
        {
            synchronized(SplashJava.obj)
            {
                SplashJava.obj.notify();
            }
        }
    }
     
}

public class SplashJava extends playerEntry{

    protected static SplashJava obj = new SplashJava();
    public static void main(String[] args) throws InterruptedException {

        Timer timer = new Timer();
        TimerTask task = new Helper();
 
        //instance of date object for fixed-rate execution
        Date date = new Date();
 
        timer.scheduleAtFixedRate(task, date, 5000);
         
        System.out.println("Timer running");
        JFrame frame = new JFrame("Welcome to our game!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        
        try {
            frame.setContentPane(new JLabel((Icon) new ImageIcon(ImageIO.read(new File("SplashImage.jpg")))));
        } catch (IOException e) {
            e.printStackTrace();
        }

       
        frame.setVisible(true);
        
        synchronized(obj)
        {
            //make the main thread wait
            obj.wait();
             
            //once timer has scheduled the task 4 times,
            //main thread resumes
            //and terminates the timer
            timer.cancel();
             
            //purge is used to remove all cancelled
            //tasks from the timer'stack queue
            System.out.println(timer.purge());
        }
    
        
        

         frame.setVisible(false);

         playerEntry entryScreen = new playerEntry();
         entryScreen.Entry();
      
    };
    
}
