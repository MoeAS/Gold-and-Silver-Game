package Driver;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class SouthPanel extends JPanel
{
    private JLabel lbl;
   public SouthPanel()
   {
       lbl = new JLabel("Users Database");
       add(lbl,BorderLayout.CENTER);
    }
}
