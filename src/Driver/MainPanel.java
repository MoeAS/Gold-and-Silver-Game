package Driver;
import javax.swing.*;
import java.awt.*;

//this code is the main panel

public class MainPanel extends JPanel
{
 
    
    public static JButton btn1;
    public MainPanel()
    {
       
        NorthPanel p1 = new NorthPanel();
        CenterPanel p2 = new CenterPanel();
        SouthPanel p3 = new SouthPanel();
        setLayout(new BorderLayout());
        add(p1,BorderLayout.NORTH);
        add(p2,BorderLayout.CENTER);
        add(p3,BorderLayout.SOUTH);
       
    }
    
   
}
