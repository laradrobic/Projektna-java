package projektnaJava;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class GameOver extends JDialog {
	public GameOver(JFrame parent, int score) {
		super(parent, "Game Over", true);
		setLayout(new BorderLayout());
		setSize(350, 200);
		setLocationRelativeTo(parent);
		setLayout(new BorderLayout());
		
		//Barve
		Color bacground = new Color(240, 240, 240);
		Color button = new Color(70, 130, 180);
		Color text = new Color(30, 30, 30);
		
		//panel za sppročilo
		JPanel messagePanel = new JPanel();
		messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.Y_AXIS));
		
		/*
		JLabel gameOverLabel = new JLabel("❌ Game Over!", SwingConstants.CENTER);
		gameOverLabel.setFont(new Font("Arial", Font.BOLD, 20));
		gameOverLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		*/
		
		ImageIcon originalIcon = new ImageIcon(getClass().getResource("game-over.png"));
		Image originalImage = originalIcon.getImage();
		Image resized = originalImage.getScaledInstance(70, 70, Image.SCALE_SMOOTH);
		
		ImageIcon resizedIcon = new ImageIcon(resized);
		
		JLabel gameOverLabel = new JLabel(resizedIcon);
		gameOverLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JLabel scoreLabel = new JLabel("Final score: " + score, SwingConstants.CENTER);
		scoreLabel.setFont(new Font("Arial", Font.BOLD, 20));
		scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JPanel buttonPanel = new JPanel();
		JButton playAgain = new JButton("Play Again");
		JButton exitButton = new JButton("Exit");
		
		messagePanel.add(Box.createVerticalGlue()); //prostor zgoraj
		messagePanel.add(gameOverLabel);
		messagePanel.add(Box.createRigidArea(new Dimension(0, 10))); //razmik med vrsticami
		messagePanel.add(scoreLabel);
		messagePanel.add(Box.createVerticalGlue()); //prostor zgoraj
		
		
		add(messagePanel, BorderLayout.CENTER);
		
		playAgain.setBackground(button);
		playAgain.setForeground(Color.WHITE);
		exitButton.setBackground(button);
		exitButton.setForeground(Color.WHITE);
		
		
		buttonPanel.add(playAgain);
		buttonPanel.add(exitButton);
		
		add(buttonPanel, BorderLayout.SOUTH);
		
		playAgain.addActionListener(e -> {
			dispose();
		});
		
		exitButton.addActionListener(e ->{
			System.exit(0);
		});
		
	}
}
