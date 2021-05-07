package Driver;
import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

import javax.swing.*;

import com.mysql.jdbc.MySQLConnection;
import com.mysql.jdbc.MysqlIO;

//I took this from Dr. Matta & edited it to work on our database as it looks cleaner
//run after Signing Up
//this code runs the frame

public class Driver extends MysqlIO
{
    public Driver(String arg0, int arg1, Properties arg2, String arg3, MySQLConnection arg4, int arg5, int arg6)
			throws IOException, SQLException {
		super(arg0, arg1, arg2, arg3, arg4, arg5, arg6);
		// TODO Auto-generated constructor stub
	}
	public static JFrame frame;
    public static void main(String[] args)
    {
        frame = new JFrame("Users");
        frame.setSize(new Dimension(1156, 500));
        frame.getContentPane().add(new MainPanel());
        frame.setVisible(true);
    }

}