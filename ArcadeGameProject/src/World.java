import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *This is where the game runs. The world holds the JPanels which the levels run on, as well as the KeyListener. 
 *The world also holds the variables for score and lives. 
 */
public class World extends JPanel{
	private final int FRAME_WIDTH = 1400;
	private final int FRAME_HEIGHT = 900;
	private JFrame frame;
	private JPanel levelPanel; 
	private JPanel statusPanel;
	public static JLabel livesLabel;
	public static JLabel scoreLabel;
	private LevelManager manager; 
	public static int score = 0;
	public static int lives = 3;

	public World(JFrame myFrame) {
		this.frame = myFrame; 
		this.levelPanel = new JPanel();
		this.manager = new LevelManager();
		Level newLevel = new Level(this.manager.initialize()); 
		newLevel.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		this.levelPanel.add(newLevel);
			
		KeyboardHandler k = new KeyboardHandler (this.levelPanel, this.manager, newLevel, myFrame);		
		
		this.statusPanel = new JPanel();
		this.livesLabel = new JLabel("Lives left: " + lives);
		this.scoreLabel = new JLabel("Score: " + score);
		
		this.statusPanel.add(this.livesLabel, BorderLayout.NORTH);
		this.statusPanel.add(this.scoreLabel, BorderLayout.NORTH);
		System.out.println(score);
		
		this.frame.add(this.statusPanel, BorderLayout.NORTH);
		this.frame.add(newLevel, BorderLayout.CENTER);
		this.frame.addKeyListener(k);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setVisible(true);
		

	}

}
