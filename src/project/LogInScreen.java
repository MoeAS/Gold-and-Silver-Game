package project;

import java.awt.EventQueue;

import java.sql.*;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import javax.swing.JTextField;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;
import java.awt.event.KeyListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.KeyEvent;
import java.awt.SystemColor;
import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;
import java.awt.Cursor;
import java.awt.Toolkit;

public class LogInScreen implements KeyListener, ActionListener, FocusListener {

	private JFrame frmLogIn;
	private static JTextField usernametext;
	private JPasswordField passwordtext;
  
  /**
	 * @param t The time we wish to convert to a date
	 * @return the Date in format: <br/> yyyy MM dd HH mm ss
	 */
	private String getDate(LocalDateTime t) {
		return DateTimeFormatter.ofPattern("yyyy MM dd HH mm ss").format(t);
	}
  
	/**
	 * Checks whether the last log in date was the previous day or before
	 * @param lastDate the date of the last log in
	 * @return 0 if the last log in and the current one were made the same day, 1 otherwise
	 */
    private int checkDate(String lastDate) {
    	String curDate = getDate(LocalDateTime.now());
		String[] cur = curDate.split("\\s");
		String[] last = lastDate.split("\\s");
		if(!cur[0].equals(last[0])) {return 1;}
		if(!cur[1].equals(last[1])) {return 1;}
		if(!cur[2].equals(last[2])) {return 1;}
		return 0;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LogInScreen window = new LogInScreen();
					window.frmLogIn.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LogInScreen() {initialize();}
  
	/**
	* Checks if the given data are valid for a SignIn <br/>
	* First it checks if the username is present <br/>
	* Then it checks the password matches the username <br/>
	* Finally if everything is correct it check if this is the first LogIn of the day
	* 
	* @return
	* <li> -1 if there is empty fields
    * <li> -2 if the username/password doesn't match
	* <li> 0 if it's not the first correct login of the day
	* <li> 1 if it's the first correct login of the day
	*/
	private int verifyFields(){
		//Gets the data from the GUI
		String uname=usernametext.getText();
		String pass=new String(passwordtext.getPassword());
		
		//Checks for empty fields
		if(uname.isEmpty() || pass.isEmpty()){return -1;}
		
		String lastDate = new String("");
		String curDate = getDate(LocalDateTime.now());
		try {
			//Query the last log in date of the user
			String query = "SELECT LocalDateTime FROM users WHERE Username=? AND Password=?";
			Connection connection = MyConnection.getConnection();
			PreparedStatement st = connection.prepareStatement(query);
			st.setString(1, uname);	st.setString(2, pass);
			ResultSet rs = st.executeQuery();
			
			//checks if the username and password matches
			if(!rs.next()) {return -2;}
			//Here we are sure of a username/password match
			
			lastDate = rs.getString(1);
      
			//Updates the last log in date of the user since he's currently logging in
			query = "UPDATE users SET LocalDateTime=? WHERE Username=?";
			st = connection.prepareStatement(query);
			st.setString(1, curDate); st.setString(2, uname);
			st.executeUpdate(); connection.close();
		}catch(SQLException e) {System.out.println(e);}
		
		//Checks wether its the first log in of the day
		return checkDate(lastDate);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	
	private static String uname;
	public static String getuname()
	{	uname = usernametext.getText();
	    return uname;
	}
		
	
	private void initialize() {
		frmLogIn = new JFrame();
		frmLogIn.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Mohammed\\Desktop\\260-2603183_app-icon-set-login-icon-comments-sign-in-icon-png.png"));
		frmLogIn.getContentPane().setForeground(Color.BLUE);
		frmLogIn.setTitle("Log In");
		frmLogIn.getContentPane().setBackground(SystemColor.desktop);
		frmLogIn.setBounds(100, 100, 522, 300);
		frmLogIn.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel username = new JLabel("Username:");
		username.setForeground(new Color(255, 255, 255));
		username.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		JLabel password = new JLabel("Password:");
		password.setForeground(new Color(255, 255, 255));
		password.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		usernametext = new JTextField();
		usernametext.addFocusListener(this);
		usernametext.setColumns(10);
		
		passwordtext = new JPasswordField();
		passwordtext.addKeyListener(this);
		passwordtext.setColumns(10);
		
		JButton loginbtn = new JButton("Log In");
		loginbtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		loginbtn.setForeground(new Color(0, 0, 0));
		loginbtn.setBackground(new Color(255, 222, 173));
    
		loginbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
        int x = verifyFields();
        if(x == -1){ JOptionPane.showMessageDialog(frmLogIn, "Please Fill in Empty Fields"); return;}
        if(x == -2){ JOptionPane.showMessageDialog(frmLogIn, "Invalid Username / Password"); return;}
        String uname = usernametext.getText();
        MainMenu mm = new MainMenu(uname, x == 1);
        mm.setVisible(true); frmLogIn.dispose();
      }
		});
		
		JCheckBox chckbxNewCheckBox = new JCheckBox("Show Password");
		chckbxNewCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chckbxNewCheckBox.isSelected()) {
					passwordtext.setEchoChar((char)0);
				}
				else {
					passwordtext.setEchoChar('*');
				}
			}
		});
		chckbxNewCheckBox.setBackground(Color.LIGHT_GRAY);
		
		JButton Register = new JButton("SignUp");
		Register.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		Register.addActionListener(this);
		Register.setBackground(new Color(255, 192, 203));
		
		JLabel lblLogin = new JLabel("SIGN IN");
		lblLogin.setForeground(new Color(255, 255, 255));
		lblLogin.setFont(new Font("Tahoma", Font.PLAIN, 34));
		GroupLayout groupLayout = new GroupLayout(frmLogIn.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(94)
							.addComponent(loginbtn, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
							.addGap(120)
							.addComponent(Register, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(78)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
									.addComponent(password)
									.addPreferredGap(ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
									.addComponent(passwordtext, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
									.addGap(6)
									.addComponent(chckbxNewCheckBox))
								.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
									.addComponent(username, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(usernametext, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
									.addGap(123)))))
					.addGap(26))
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap(194, Short.MAX_VALUE)
					.addComponent(lblLogin, GroupLayout.PREFERRED_SIZE, 144, GroupLayout.PREFERRED_SIZE)
					.addGap(168))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblLogin, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(username, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
						.addComponent(usernametext, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(28)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(3)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(passwordtext, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(password)))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(2)
							.addComponent(chckbxNewCheckBox)))
					.addGap(39)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(loginbtn)
						.addComponent(Register, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(43, Short.MAX_VALUE))
		);
		frmLogIn.getContentPane().setLayout(groupLayout);
	}
	
	/**
	 * Handles the event of pressing on the Sign Up button	 * 
	 */
	public void actionPerformed(ActionEvent e) {
		Registration frmSignup = new Registration();
		frmSignup.setVisible(true);
		frmLogIn.dispose();
	}

	/**
	 * Shows the LogIn Frame
	 */
	public void setVisible(boolean b) { frmLogIn.setVisible(true); }

	@Override
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	public static boolean dispose() {
		LogInScreen.dispose();
		// TODO Auto-generated method stub
		return false;
	}

	public static boolean exit() {
		LogInScreen.exit();
		// TODO Auto-generated method stub
		return false;
	}
}