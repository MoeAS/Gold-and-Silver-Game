package project;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import java.awt.Cursor;
import java.sql.SQLException;
import java.util.Random;
import java.awt.Color;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;

public class Game extends JFrame implements KeyListener, MouseListener, ActionListener {

	private JFrame gamescreenFrame;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	int state, goldScore, silverScore;
	static String playerID;
	int initGold, initSilver;
	int[] goal; int guesses;
	
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Game frame = new Game(playerID);
					frame.gamescreenFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	 int [] generateRandomGoal() {
			int[] used = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
			int[] goal = {0, 0, 0, 0};
			Random random = new Random();
			for(int i = 0; i < 4; i++) {
				int x = Math.abs(random.nextInt() % 10);
				while(used[x] == 1 || (i + x) == 0) {x = Math.abs(random.nextInt() % 10);}
				goal[i] = x; used[x] = 1;
			}
			return goal;
		}
	
	/**
	 * Create the frame.
	 * initialize gold and silver
	 */
	 
	 public Game(String play){
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Mohammed\\Desktop\\istockphoto-1199272550-1024x1024.jpg"));
		setTitle("Single Player Gamemode");
		 playerID = play; guesses = 0;
			goldScore = 0; silverScore = 0;
			goal = generateRandomGoal(); 
			
			try {
				String queryGold = "SELECT Gold FROM users WHERE Username=?";
				Connection connection = MyConnection.getConnection();
				PreparedStatement st = connection.prepareStatement(queryGold);
				st.setString(1, play); ResultSet rs = st.executeQuery(); rs.next();
				initGold = rs.getInt(1);
					
				String querySilver = "SELECT Silver FROM users WHERE Username=?";
				st = connection.prepareStatement(querySilver);
				st.setString(1, play); rs = st.executeQuery(); rs.next();
				initSilver = rs.getInt(1); connection.close();
			}catch(SQLException e) {System.out.println(e);}
			
			initialize();

		}
	 
	 /*
	  * Game Logic
	  * */
		int playStep(int[] play, int goal[]) {
			for(int i = 0; i < 4; i++) {
				if(play[i] < 0 || play[i] > 9) {return -2;}
				for(int j =  i + 1; j < 4; j++)
					if(play[i] == play[j]) return -2;
			}
			
			int correctIn = 0, correctOut = 0; guesses++;
			for(int i = 0; i < 4; i++) {
				for(int j = 0; j < 4; j++) {
					if(play[i] != goal[j]) {continue;}
					if(i == j) {correctIn++; break;}
					correctOut++; break;
				}
			}
			initGold -= 5;
			if(correctIn == 4) {
				initGold += goldScore + 50;
				initSilver += silverScore;
				try {				
					String updateGold = "UPDATE users SET Gold=? WHERE Username=?";
					Connection connection = MyConnection.getConnection();
					PreparedStatement st = connection.prepareStatement(updateGold);
					st.setString(1, "" + initGold); st.setString(2, playerID);
					st.executeUpdate(); 
						
					String updateSilver = "UPDATE users SET Silver=? WHERE Username=?";
					st = connection.prepareStatement(updateSilver);
					st.setString(1, "" + initSilver); st.setString(2, playerID);
					st.executeUpdate();
						
					String querySingleGames = "SELECT SingleGames FROM users WHERE Username=?";
					st = connection.prepareStatement(querySingleGames);
					st.setString(1, playerID); ResultSet rs = st.executeQuery(); rs.next();
					int games = rs.getInt(1); games++;
						
					String updateSingleGames = "UPDATE users SET SingleGames=? WHERE Username=?";
					st = connection.prepareStatement(updateSingleGames);
					st.setString(1, "" + games); st.setString(2, playerID);
					st.executeUpdate(); 
					
					String queryHighScore = "SELECT HighScore FROM users WHERE Username=?";
					st = connection.prepareStatement(queryHighScore);
					st.setString(1, playerID); rs = st.executeQuery(); rs.next();
					int hs = rs.getInt(1); hs = (hs < guesses) ? hs : guesses;
						
					String updateHighScore = "UPDATE users SET HighScore=? WHERE Username=?";
					st = connection.prepareStatement(updateHighScore);
					st.setString(1, "" + hs); st.setString(2, playerID);
					st.executeUpdate(); connection.close();
						
				}catch(SQLException e) {System.out.println(e);}
				return 1;
			}
			if(initGold < 5) {
				initGold += goldScore + correctIn; 
				initSilver += silverScore + correctOut;
				try {				
					String updateGold = "UPDATE users SET Gold=? WHERE Username=?";
					Connection connection = MyConnection.getConnection();
					PreparedStatement st = connection.prepareStatement(updateGold);
					st.setString(1, "" + initGold); st.setString(2, playerID);
					st.executeUpdate(); 
						
					String updateSilver = "UPDATE users SET Silver=? WHERE Username=?";
					st = connection.prepareStatement(updateSilver);
					st.setString(1, "" + initSilver); st.setString(2, playerID);
					st.executeUpdate();
						
					String querySingleGames = "SELECT SingleGames FROM users WHERE Username=?";
					st = connection.prepareStatement(querySingleGames);
					st.setString(1, playerID); ResultSet rs = st.executeQuery(); rs.next();
					int games = rs.getInt(1); games++;
						
					String updateSingleGames = "UPDATE users SET SingleGames=? WHERE Username=?";
					st = connection.prepareStatement(updateSingleGames);
					st.setString(1, "" + games); st.setString(2, playerID);
					st.executeUpdate(); connection.close();
						
				}catch(SQLException e) {System.out.println(e);}
				return -1;
			}
			goldScore += correctIn; silverScore += correctOut;
			return 0;
		}
		


	 
	public void initialize() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 449, 422);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(152, 251, 152));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		textField = new JTextField();
		textField.addKeyListener(new KeyListener() {	//can only input digits, not letters
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				char c = e.getKeyChar();
				if(!(Character.isDigit(c)) || (c == KeyEvent.VK_BACK_SPACE) || (c== KeyEvent.VK_DELETE)) {
					e.consume();
				}
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		textField.setColumns(10);
		textField.setDocument(new CharacLimit(1)); //limits inputs to 1 digit
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setDocument(new CharacLimit(1));
		textField_1.addKeyListener(new KeyListener() {	//can only input digits, not letters
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				char c = e.getKeyChar();
				if(!(Character.isDigit(c)) || (c == KeyEvent.VK_BACK_SPACE) || (c== KeyEvent.VK_DELETE)) {
					e.consume();
				}
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setDocument(new CharacLimit(1));
		textField_2.addKeyListener(new KeyListener() {	//can only input digits, not letters
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				char c = e.getKeyChar();
				if(!(Character.isDigit(c)) || (c == KeyEvent.VK_BACK_SPACE) || (c== KeyEvent.VK_DELETE)) {
					e.consume();
				}
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setDocument(new CharacLimit(1));
		textField_3.addKeyListener(new KeyListener() {	//can only input digits, not letters
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				char c = e.getKeyChar();
				if(!(Character.isDigit(c)) || (c == KeyEvent.VK_BACK_SPACE) || (c== KeyEvent.VK_DELETE)) {
					e.consume();
				}
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		JLabel lblNewLabel = new JLabel("Guess the 4 digit Number!");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		JButton btnNewButton = new JButton("Submit!");
		btnNewButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		JLabel lblNewLabel_1 = new JLabel("Gold coins collected:");
		
		JLabel lblSilverCoinsCollected = new JLabel("Silver coins collected:");
		
		JLabel lblNewLabel_2 = new JLabel(""+goldScore);
		
		JLabel label = new JLabel(""+silverScore);
		
		btnNewButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {	//when submit, call play function which calls the logic of the game
				// TODO Auto-generated method stub
				int [] alk = new int[4]; 
				int tf = Integer.parseInt(textField.getText());		//get inputs of user
				int tf1 = Integer.parseInt(textField_1.getText());
				int tf2 = Integer.parseInt(textField_2.getText());
				int tf3 = Integer.parseInt(textField_3.getText());
				
				alk[0] = tf;
				alk[1] = tf1;
				alk[2] = tf2;
				alk[3] = tf3;
			
				int x = playStep(alk, goal);
				lblNewLabel_2.setText(Integer.toString(goldScore));
				label.setText(Integer.toString(silverScore));
				if (x == 1) {JOptionPane.showMessageDialog(gamescreenFrame, "Correct!"); 
				MainMenu mm = new MainMenu(LogInScreen.getuname(), false);
				mm.setVisible(true);
				gamescreenFrame.dispose();}
				if (x == -1) {JOptionPane.showMessageDialog(gamescreenFrame, "Need more Gold coins to guess!");}
			}
		});
		JLabel lblNewLabel_3 = new JLabel("Exit Game!");
		lblNewLabel_3.addMouseListener(this);
		lblNewLabel_3.addKeyListener(this);
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_3.setForeground(new Color(255, 0, 0));
		lblNewLabel_3.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		JLabel lblNewLabel_4 = new JLabel("Keep trying till you get a pop-up message!");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 13));
		
		JLabel lblNewLabel_5 = new JLabel("1 Gold coin for each right answer! so watch out for the gold coin counter!");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.BOLD, 11));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(116)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(16)
									.addComponent(textField, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
									.addGap(35)
									.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
									.addGap(32)
									.addComponent(textField_3, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))
								.addComponent(lblNewLabel)))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(167)
							.addComponent(btnNewButton)
							.addGap(177))
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblNewLabel_3)
							.addGap(153)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(lblNewLabel_1)
									.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(lblNewLabel_2, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE))
								.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
									.addComponent(lblSilverCoinsCollected)
									.addGap(18)
									.addComponent(label, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE)))))
					.addContainerGap())
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap(57, Short.MAX_VALUE)
					.addComponent(lblNewLabel_4)
					.addGap(90))
				.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel_5)
					.addContainerGap(29, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_3)
						.addComponent(lblNewLabel_2)
						.addComponent(lblNewLabel_1))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(label)
						.addComponent(lblSilverCoinsCollected))
					.addGap(61)
					.addComponent(lblNewLabel_5)
					.addPreferredGap(ComponentPlacement.RELATED, 85, Short.MAX_VALUE)
					.addComponent(lblNewLabel)
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(textField_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(btnNewButton)
					.addGap(49)
					.addComponent(lblNewLabel_4))
		);
		contentPane.setLayout(gl_contentPane);
	}
	public void keyPressed(KeyEvent e) {
	}
	public void keyReleased(KeyEvent e) {
	}
	public void keyTyped(KeyEvent e) {
	}
	public void mouseClicked(MouseEvent e) {
			MainMenu mm = new MainMenu(LogInScreen.getuname(), false);
			mm.setVisible(true);
			gamescreenFrame.dispose();
	}
	public void mouseEntered(MouseEvent e) {
	}
	public void mouseExited(MouseEvent e) {
	}
	public void mousePressed(MouseEvent e) {
	}
	public void mouseReleased(MouseEvent e) {
	}
	public void actionPerformed(ActionEvent e) {
		}
	}
