package Driver;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

//this code has the menu bars

public class NorthPanel extends JPanel implements ActionListener
{
    private JMenuBar bar;
    private JMenu file;
    private JMenuItem close;
    private JToolBar toolbar;
    private JLabel lblclose;
    public static JFrame frame;
   public NorthPanel()
   {
       
       bar = new JMenuBar();
       file = new JMenu("File");
       close = new JMenuItem("Close");
       close.addActionListener(this);
       
       
       bar.add(file);
       file.add(close);
       
       toolbar = new JToolBar();
       toolbar.setPreferredSize(new Dimension(20,20));
       
       lblclose = new JLabel("Exit");
       toolbar.add(lblclose);
       
       setLayout(new BorderLayout());
       add(bar,BorderLayout.NORTH);
       add(toolbar,BorderLayout.CENTER);
    }
	public void actionPerformed(ActionEvent e) {
		frame = new JFrame();
		frame.dispose();
	}
}
