package project;

import java.sql.*;
import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;


import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.Closeable;
import java.awt.event.ActionEvent;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Toolkit;

public class MainMenu implements ActionListener {

	private static JFrame frmMainMenu;
	
	public  void setVisible(boolean b) { frmMainMenu.setVisible(true); }

	/**
	 * Create the application for the specific user
	 * 
	 * @param user the username of the player
	 * @param first a boolean that indicates if it's the first login of the day
	 */
	public MainMenu(String user, boolean first) {
		//Checks if its the first log in of the day
		if(first){
			//The rules of the game states that a player gets 50 gold a day coins for each log in
			try{
				//Gets the number of gold coins the player has already in his account
				String queryGold = "SELECT Gold FROM users WHERE Username=?";
				Connection connection = MyConnection.getConnection();
				PreparedStatement st = connection.prepareStatement(queryGold);
				st.setString(1, user); ResultSet rs = st.executeQuery(); rs.next();
				int gold = rs.getInt(1);
				
				//Updates the number of gold coins by adding 50 to the current count
				String updateGold = "UPDATE users SET Gold=? WHERE Username=?";
				st = connection.prepareStatement(updateGold); gold += 50;
				st.setString(1, "" + gold); st.setString(2, user);
				st.executeUpdate(); connection.close();
			}catch(SQLException e) {System.out.println(e);}
		}
		
		//Gets the information required to initialize the application's main menu
		String fn = new String(""), ln = new String(""); 
		int gold = 0, silver = 1;
		try{
			//Gets the firstName, lastName, #Gold, #Silver of the player
			String queryFirst = "SELECT FirstName FROM users WHERE Username=?";
			String queryLast = "SELECT LastName FROM users WHERE Username=?";
			String queryGold = "SELECT Gold FROM users WHERE Username=?";
			String querySilver = "SELECT Silver FROM users WHERE Username=?";
      
      
			Connection connection = MyConnection.getConnection();
			PreparedStatement st = connection.prepareStatement(queryFirst); st.setString(1, user);
			ResultSet rs = st.executeQuery(); rs.next(); fn = rs.getString(1);
			st = connection.prepareStatement(queryLast); st.setString(1, user);
			rs = st.executeQuery(); rs.next(); ln = rs.getString(1); 
			st = connection.prepareStatement(queryGold); st.setString(1, user);
			rs = st.executeQuery(); rs.next(); gold = rs.getInt(1); 
			st = connection.prepareStatement(querySilver); st.setString(1, user);
			rs = st.executeQuery(); rs.next(); silver = rs.getInt(1); 
			connection.close();
	      
		}catch(SQLException e) {System.out.println(e);}
    
		//Calls the function that will initialize the frame
		initialize(fn, ln, gold, silver);
	}

	/**
	 * Initialize the contents of the frame.
	 * @param first the first name of the player
	 * @param last the last name of the player
	 * @param gold the number of gold coins in the player's account
	 * @param silver the number of silver coins in the player's account
	 */
	private void initialize(String first, String last, int gold, int silver) {
		frmMainMenu = new JFrame();
		frmMainMenu.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Mohammed\\Desktop\\istockphoto-1199272550-1024x1024.jpg"));
		frmMainMenu.setTitle("Main Menu");
		frmMainMenu.getContentPane().setBackground(new Color(240, 230, 140));
		frmMainMenu.setBounds(100, 100, 450, 450);
		frmMainMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel Firstname = new JLabel(first);
		
		JLabel Lastname = new JLabel(last);
		
		JLabel lblNewLabel = new JLabel("Gold: ");
		
		JLabel lblNewLabel_1 = new JLabel("Silver: ");
		
		JLabel Gold_coins = new JLabel("" + gold);
		
		JLabel Silver_coins = new JLabel("" + silver);
		
		JButton Logout = new JButton("Log Out");
		Logout.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		Logout.addActionListener(this);
		
		JButton btnStatistics = new JButton("Statistics");
		btnStatistics.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnStatistics.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				int single = 0;
				int high = 0;
				try {
					String uname = LogInScreen.getuname();
					Connection connection = MyConnection.getConnection();
					String querySingleGames = "SELECT SingleGames FROM users WHERE Username=?";
					PreparedStatement st;
					st = connection.prepareStatement(querySingleGames);
					st.setString(1, uname); ResultSet rs = st.executeQuery(); rs.next(); single = rs.getInt("SingleGames");
					
					String queryHighScore = "SELECT HighScore FROM users WHERE Username=?";
					st = connection.prepareStatement(queryHighScore);
					st.setString(1, uname); rs = st.executeQuery(); rs.next(); high = rs.getInt("HighScore"); connection.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				JOptionPane.showMessageDialog(btnStatistics, "Games Played: "+single+"\nHigh Score: "+high);
			}
		});
		
		
		
		JButton btnPlay = new JButton("Play");
		btnPlay.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnPlay.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
					SingleMulti sm = new SingleMulti();
					sm.setVisible(true);
					frmMainMenu.dispose();
				
			}
		});
		
		JButton btnConvert = new JButton("Convert");
		btnConvert.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnConvert.addActionListener(new ActionListener() {
			/*
			 * Convert 10 Silver coins to 1 Gold coin
			 * */
			boolean exchangeSilverGold(String user, int g) {
				try {
					String querySilver = "SELECT Silver FROM users WHERE Username=?";
					Connection connection = MyConnection.getConnection();
					PreparedStatement st = connection.prepareStatement(querySilver);
					st.setString(1, user); ResultSet rs = st.executeQuery(); rs.next();
				
					int silver = rs.getInt(1); if(silver/10 < 1) { return false; }
					
					String queryGold = "SELECT Gold FROM users WHERE Username=?";
					st = connection.prepareStatement(queryGold);
					st.setString(1, user); rs = st.executeQuery(); rs.next();
					int gold = rs.getInt(1);
					
					gold += 1; silver -= 10;
					
					String updateGold = "UPDATE users SET Gold=? WHERE Username=?";
					st = connection.prepareStatement(updateGold);
					st.setString(1, "" + gold); st.setString(2, user);
					st.executeUpdate();
					
					String updateSilver = "UPDATE users SET Silver=? WHERE Username=?";
					st = connection.prepareStatement(updateSilver);
					st.setString(1, "" + silver); st.setString(2, user);
					st.executeUpdate(); connection.close();
				}
				catch(SQLException e) {System.out.println(e);}
				return true;
			}
			
			/* 
			 * When Convert Button is pressed
			 * */
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String uname = LogInScreen.getuname();
				boolean x = exchangeSilverGold(uname, gold);
				if (x==true) {JOptionPane.showMessageDialog(frmMainMenu, "Converted");}
				if (x==false) {JOptionPane.showMessageDialog(frmMainMenu, "cannot convert, need more silver coins");}
				
				/*
				 * Update coins real time
				 * */
				try {
					Connection connection = MyConnection.getConnection();
					String queryGold = "SELECT Gold FROM users WHERE Username=?";
					PreparedStatement st;
					st = connection.prepareStatement(queryGold);
					st.setString(1, uname); ResultSet rs = st.executeQuery(); rs.next(); int gold = rs.getInt("Gold"); Gold_coins.setText(Integer.toString(gold));
					
					String querySilver = "SELECT Silver FROM users WHERE Username=?";
					st = connection.prepareStatement(querySilver);
					st.setString(1, uname); rs = st.executeQuery(); rs.next(); int silver = rs.getInt("Silver"); Silver_coins.setText(Integer.toString(silver)); connection.close(); 
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		
		JLabel lblNewLabel_2 = new JLabel("Convert 10 Silver Coins to 1 Gold Coin using the convert button!");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 13));

		
		
		GroupLayout groupLayout = new GroupLayout(frmMainMenu.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(Firstname)
								.addComponent(Lastname))
							.addPreferredGap(ComponentPlacement.RELATED, 586, Short.MAX_VALUE)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(lblNewLabel)
									.addPreferredGap(ComponentPlacement.UNRELATED))
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(lblNewLabel_1)
									.addGap(5)))
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(Silver_coins, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(Gold_coins, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(179)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(Logout, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(btnStatistics, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(btnPlay, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
					.addContainerGap())
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(341, Short.MAX_VALUE)
					.addComponent(btnConvert)
					.addGap(22))
				.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel_2)
					.addContainerGap(37, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(Firstname)
						.addComponent(Gold_coins)
						.addComponent(lblNewLabel))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(Lastname)
						.addComponent(Silver_coins)
						.addComponent(lblNewLabel_1))
					.addGap(18)
					.addComponent(btnConvert)
					.addGap(52)
					.addComponent(lblNewLabel_2)
					.addGap(42)
					.addComponent(btnPlay)
					.addGap(18)
					.addComponent(btnStatistics)
					.addGap(18)
					.addComponent(Logout)
					.addContainerGap(112, Short.MAX_VALUE))
		);
		frmMainMenu.getContentPane().setLayout(groupLayout);}
	/**
	 * Handles the event of pressing log out
	 */
	public void actionPerformed(ActionEvent e) {
		LogInScreen frmLogIn = new LogInScreen();
		frmLogIn.setVisible(true);
		frmMainMenu.dispose();
	}
}

