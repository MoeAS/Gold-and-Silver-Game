package Driver;
import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.DefaultTableModel;

//this panel is used to create all the columns and get the data from mysql database and inserts them onto the table

public class CenterPanel extends JPanel
{ static JTable table;

	
    static String[] columnNames = {"id","FirstName","LastName","Username","Password","Gender","LocalDateTime","Gold","Silver", "Single Games", "HighScore"};
    public static JButton btn1;
    public CenterPanel()
    {
        btn1 = new JButton("Click");
        btn1.addActionListener(new Listener());
        
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(columnNames);
        table = new JTable();
        table.setModel(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setFillsViewportHeight(true);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con  =DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/users?useSSL=true","root","");
                
                
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM `users`");
            
            
            while(rs.next())
            {
                int id = rs.getInt(1);
                String fname = rs.getString(2);
                String lname = rs.getString(3);
                String uname = rs.getString(4);
                String pass = rs.getString(5);
                String gender = rs.getString(6);
                String LDT = rs.getString(7);
                String Gold = rs.getString(8);
                String Silver = rs.getString(9);
                String SingleGames = rs.getString(10);
                String HighScore = rs.getString(11);
                model.addRow(new Object[]{id, fname, lname, uname, pass, gender, LDT, Gold, Silver, SingleGames, HighScore});
            }
            
              con.close();
        }catch(Exception err){}
        setLayout(new BorderLayout());
        add(scroll,BorderLayout.CENTER);
        //add(btn1);
    }
    
     private class Listener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            Object source = e.getSource();
            
            if(source == btn1)
            {
                JDialog d = new JDialog(Driver.frame,"Login");
                d.setSize(500,300);
                d.setVisible(true);
            }
        }
    }
}
