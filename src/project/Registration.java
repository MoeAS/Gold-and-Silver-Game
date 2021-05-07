package project;
import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.LineBorder;

import java.sql.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Registration implements ActionListener, MouseListener {

	private JFrame frmSignup;
	private JTextField FNtextField;
	private JTextField LNtextField;
	private JTextField UNtextField;
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;
	private JRadioButton Male, Female;
	private ButtonGroup btn;
	
	public Registration() { initialize(); }
  
    /**
	* Checks if the password given satisfies some requirements <br/>
	* 1) Password consists of at least 8 characters <br/>
	* 2) Password contains at least 1 digit, 1 UC & 1 LC letter
	* 
	* @param password the password of the user
	* @return False if some requirement is not satisfied and True otherwise
	*/
	private boolean checkPassword(String password){
	    if(password.length() < 8){return false;}
	    boolean d = false, u = false, l = false;
	    for(int i = 0; i < password.length(); i++){
	    	char c = password.charAt(i);
	        if(Character.isDigit(c)){d = true;}
	        if(Character.isUpperCase(c)){u = true;}
	        if(Character.isLowerCase(c)){l = true;}
	    }
	    return (d && u && l);
	}
  
    /**
	* Checks if the given data are valid for a SignUp <br/>
	* First it checks if the username is taken <br/>
	* Then it checks the password
	* 
	* @return 
	* <li>-1 if there are missing fields
	* <li>-2 if password is not confirmed correctly
	* <li>-3 if username is already taken
	* <li>-4 if password is invalid
	* <li> 0 if everything is correct
	*/
	public int verifyFields() {
		//Gets the data from the GUI
		String fn = FNtextField.getText();
		String ln = LNtextField.getText();
		String un = UNtextField.getText();
		String pass = new String(passwordField.getPassword());
		String rpass = new String(passwordField_1.getPassword());
		
		//Checks for empty fields
		if(fn.isEmpty() || ln.isEmpty() || un.isEmpty() || pass.isEmpty() || rpass.isEmpty()) {return -1;}
		
		//Checks if the "Password" field matches the "Confirm Password" field
		if(!pass.equals(rpass)){return -2;}
		
		try {
			Connection connection = MyConnection.getConnection();			
			String query = "SELECT * FROM users WHERE Username=?";
			PreparedStatement st = connection.prepareStatement(query);
			st.setString(1, un); ResultSet rs = st.executeQuery();
			
			//Checks if the username is already taken
			if(rs.next()) {return -3;}
			connection.close();
		}catch(SQLException e) {System.out.println(e);}
		
		//Checks if the password entered obeys the password criteria
		if(!checkPassword(pass)) {return -4;}
		return 0;		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSignup = new JFrame();
		frmSignup.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Mohammed\\Desktop\\register-11-370665.png"));
		frmSignup.getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		frmSignup.getContentPane().setBackground(SystemColor.inactiveCaption);
		frmSignup.setTitle("SignUp");
		frmSignup.setBounds(100, 100, 590, 389);
		frmSignup.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel FN = new JLabel("First Name:");
		
		JLabel LN = new JLabel("Last Name:");
		
		JLabel UN = new JLabel("Enter Username:");
		
		JLabel PASS = new JLabel("Enter Password:");
		
		JLabel RPASS = new JLabel("Retype Password:");
		
		JButton SignUp = new JButton("SignUp");
		SignUp.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		SignUp.addActionListener(this);
		SignUp.setForeground(new Color(255, 255, 255));
		SignUp.setBackground(new Color(240, 128, 128));
		
		FNtextField = new JTextField();
		FNtextField.setColumns(10);
		
		LNtextField = new JTextField();
		LNtextField.setColumns(10);
		
		UNtextField = new JTextField();
		UNtextField.setColumns(10);
		
		passwordField = new JPasswordField();
		
		passwordField_1 = new JPasswordField();
		
		JLabel lblSignUp = new JLabel("Sign Up");
		lblSignUp.setForeground(Color.BLACK);
		lblSignUp.setBackground(Color.WHITE);
		lblSignUp.setFont(new Font("Tahoma", Font.PLAIN, 32));
		
		JLabel lblNewLabel = new JLabel("Gender:");
		
		Male = new JRadioButton("Male");
		Female = new JRadioButton("Female");
		btn = new ButtonGroup();
		btn.add(Male);
		btn.add(Female);
		
		JLabel back = new JLabel(">>Already have an account? Log In!");
		back.setOpaque(true);
		back.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		back.setBorder(new LineBorder(Color.WHITE));
		back.addMouseListener(this);
		back.setFont(new Font("Tahoma", Font.BOLD, 12));
		back.setForeground(Color.RED);
		
		JLabel lblNewLabel_1 = new JLabel("<html><ul>" +
		        "<li>at least one uppercase letter</li>" +             
		        "<li>at least one lowercase letter</li>" + 
		        "<li>at least one number</li>" + "<li>at least 8 characters</li>" +
		        "</ul><html>");
		
		JLabel lblNewLabel_2 = new JLabel("Password should include:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		
		
		
		GroupLayout groupLayout = new GroupLayout(frmSignup.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(176)
							.addComponent(lblSignUp, GroupLayout.PREFERRED_SIZE, 122, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(98)
							.addComponent(back))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(34)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
									.addComponent(UN, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addGroup(groupLayout.createSequentialGroup()
										.addComponent(PASS, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addGap(11))
									.addComponent(LN, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE)
									.addComponent(RPASS, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 131, GroupLayout.PREFERRED_SIZE))
								.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
								.addComponent(FN))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(SignUp)
								.addGroup(groupLayout.createSequentialGroup()
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addGroup(groupLayout.createSequentialGroup()
											.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
												.addComponent(passwordField_1, GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
												.addComponent(UNtextField, GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
												.addComponent(LNtextField, GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
												.addComponent(FNtextField, GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
												.addComponent(passwordField, GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE))
											.addGap(70))
										.addGroup(groupLayout.createSequentialGroup()
											.addComponent(Male)
											.addGap(18)
											.addComponent(Female)
											.addGap(87)))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(lblNewLabel_2)
										.addGroup(groupLayout.createSequentialGroup()
											.addGap(16)
											.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 139, GroupLayout.PREFERRED_SIZE)))
									.addGap(95)))))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(lblSignUp, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(FNtextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(FN))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(LN)
						.addComponent(LNtextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(UN)
						.addComponent(UNtextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(PASS)
						.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(RPASS)
						.addComponent(passwordField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(12)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel)
						.addComponent(Male)
						.addComponent(Female))
					.addPreferredGap(ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
					.addComponent(SignUp)
					.addGap(18)
					.addComponent(back)
					.addContainerGap())
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(153, Short.MAX_VALUE)
					.addComponent(lblNewLabel_2)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblNewLabel_1)
					.addGap(72))
		);
		frmSignup.getContentPane().setLayout(groupLayout);
	}
  
	/**
	 * Handles the event of pressing the Sign Up button
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		//Gets the data from the GUI
		String fn = FNtextField.getText();
		String ln = LNtextField.getText();
		String un = UNtextField.getText();
		String pass = new String(passwordField.getPassword());
		String gender = new String("Male");
		
		//Updates the gender accordingly
		if (Female.isSelected()) { gender = new String("Female"); }
		
		//Calls verify fields to check the validity of the entered data
		int x = verifyFields();
		
		//Output any possible error
		if(x == -1){ JOptionPane.showMessageDialog(frmSignup, "Please Fill in Empty Fields"); return; }
	    if(x == -2){ JOptionPane.showMessageDialog(frmSignup, "Passwords Don't Match"); return; }
	    if(x == -3){ JOptionPane.showMessageDialog(frmSignup, "Username is already taken"); return; }
	    if(x == -4){ JOptionPane.showMessageDialog(frmSignup, "Passwords is not valid"); return; }
	    //Here we are sure that verifyFields() returned 0 so registration went smoothly
				
		try {
			//Adds a row to the database representing the player that just created an account
			String registerUserQuery = "INSERT INTO users(FirstName, LastName, Username, Password, Gender, LocalDateTime, Gold, Silver, SingleGames, HighScore) VALUES (?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement ps = MyConnection.getConnection().prepareStatement(registerUserQuery);
			ps.setString(1, fn); ps.setString(2, ln); ps.setString(3, un);
			ps.setString(4, pass); ps.setString(5, gender); 
			ps.setString(6, "0000 00 00 00 00 00"); ps.setInt(7, 0); ps.setInt(8, 0);ps.setInt(9, 0);ps.setInt(10, 0);
			if(ps.executeUpdate() != 0) {
					JOptionPane.showMessageDialog(frmSignup, "Sign Up successful!");
					LogInScreen lis = new LogInScreen(); lis.setVisible(true);
					frmSignup.dispose(); 
			}
		} catch (SQLException e1) { e1.printStackTrace(); }
	}
	
	/**
	 * Shows the Registration Frame
	 */
	public void setVisible(boolean b) { frmSignup.setVisible(true); }
	
	/**
	 * Handles the event of pressing the "Back" Button
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		LogInScreen lis = new LogInScreen();
		lis.setVisible(true);
		frmSignup.dispose();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}