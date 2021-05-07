package project;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.Cursor;
import java.awt.Color;
import java.awt.Font;
import javax.swing.border.LineBorder;
import javax.swing.JLabel;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Toolkit;

public class SingleMulti extends JFrame implements MouseListener, ActionListener {

	private JFrame smFrame;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	
	public SingleMulti() {
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Mohammed\\Desktop\\Game_Mode-01-512.png"));initialize();}
	/**
	 * Create the frame.
	 * @return 
	 */
	public void initialize() {
		smFrame = new JFrame();
		setTitle("Mode");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JButton btnSingle = new JButton("Single-Player Mode");
		btnSingle.addActionListener(this);
		btnSingle.setBorder(new LineBorder(Color.RED, 7));
		btnSingle.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnSingle.setForeground(Color.BLACK);
		btnSingle.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		JButton btnMultiplayerMode = new JButton("Multi-Player Mode");
		btnMultiplayerMode.setBorder(new LineBorder(Color.BLUE, 7));
		btnMultiplayerMode.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnMultiplayerMode.setForeground(Color.BLACK);
		btnMultiplayerMode.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnMultiplayerMode.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Multiplayer mp = new Multiplayer(LogInScreen.getuname());
				mp.setVisible(true);
				smFrame.dispose();
			}
		});
		
		JLabel lblNewLabel = new JLabel("<< Go Back");
		lblNewLabel.addMouseListener(this);
		lblNewLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(42)
							.addComponent(btnSingle, GroupLayout.PREFERRED_SIZE, 149, GroupLayout.PREFERRED_SIZE)
							.addGap(39)
							.addComponent(btnMultiplayerMode, GroupLayout.PREFERRED_SIZE, 149, GroupLayout.PREFERRED_SIZE))
						.addComponent(lblNewLabel))
					.addContainerGap(45, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(lblNewLabel)
					.addGap(50)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(btnMultiplayerMode, GroupLayout.PREFERRED_SIZE, 102, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnSingle, GroupLayout.PREFERRED_SIZE, 102, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(85, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
	}
	public void mouseClicked(MouseEvent e) {
		MainMenu mm = new MainMenu(LogInScreen.getuname(), false);
		mm.setVisible(true);
		smFrame.dispose();
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
		Game g = new Game(LogInScreen.getuname());
		g.setVisible(true);
		smFrame.dispose();
	}
}
