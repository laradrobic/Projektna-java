import javax.swing.*;
import java.awt.*;

@SuppressWarnings("serial")
public class GameOver extends JDialog {

	public GameOver(JFrame parent, int score, Runnable onReastart) {
		super(parent, "Game Over", true);
		setLayout(new BorderLayout());
		setSize(350, 200);
		setLocationRelativeTo(parent);
		setLayout(new BorderLayout());
		
		//Barve
		Color button = new Color(70, 130, 180);
		
		//panel za sporočilo
		JPanel messagePanel = new JPanel();
		messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.Y_AXIS));
		
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
		
		//gumbi
		playAgain.setBackground(button);
		playAgain.setForeground(Color.WHITE);
		exitButton.setBackground(button);
		exitButton.setForeground(Color.WHITE);
		
		buttonPanel.add(playAgain);
		buttonPanel.add(exitButton);
		
		add(buttonPanel, BorderLayout.SOUTH);
		
		playAgain.addActionListener(e -> {
			dispose();
			onReastart.run();
		});
		
		exitButton.addActionListener(e ->{
			System.exit(0);
			

		});		
	}
}