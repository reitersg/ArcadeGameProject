import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
/** 
 *Handles Keyboard inputs. 
 *Pressing the arrow keys will move the hero in the direction of the arrow key
 *Pressing p will pause or unpause the game, depending on its previous state. 
 *Pressing u or d will switch to the next or previous level, respectively. 
 *Pressing b will start the bonus level. 
 */

public class KeyboardHandler implements KeyListener {
	private final int FRAME_WIDTH = 1400;
	private final int FRAME_HEIGHT = 900;
	public static JPanel levelPanel;
	public static LevelManager manager;
	public static Level level;
	private JFrame frame;
	
	public KeyboardHandler (JPanel levelPanel, LevelManager manager, Level level, JFrame frame){
		this.levelPanel = levelPanel;
		this.manager = manager;
		this.level = level; 
		this.frame = frame;
		
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (e.getKeyChar() == 'h'){
			this.level.isPaused = !this.level.isPaused; 
			helpPane();
			this.level.isPaused = !this.level.isPaused;
			
			
		}
		if (e.getKeyChar() == 'p'){
			this.level.isPaused = !this.level.isPaused; 	
		}
		if (e.getKeyChar() == 'u'){
			System.out.println(e.getKeyChar());
			Level nextLevel = new Level(this.manager.nextLevel());
			nextLevel.setPreferredSize(new Dimension(this.FRAME_WIDTH, this.FRAME_HEIGHT));
			this.level.removeAll();
			this.levelPanel.add(nextLevel);
			this.level.repaint();
			this.level.revalidate();
			this.level.hero = nextLevel.hero;

			
		}
		else if (e.getKeyChar() == 'd'){
			System.out.println(e.getKeyChar());
			Level prevLevel = new Level(this.manager.previousLevel());
			prevLevel.setPreferredSize(new Dimension(this.FRAME_WIDTH, this.FRAME_HEIGHT));
			this.level.removeAll();
			this.levelPanel.add(prevLevel);
			this.level.repaint();
			this.level.revalidate();
			this.level.hero = prevLevel.hero;
		
		}
		else if (e.getKeyChar() == 'b'){
			Level bonus = new Level(this.manager.getBonusLevel());
			bonus.setPreferredSize(new Dimension(this.FRAME_WIDTH, this.FRAME_HEIGHT));
			this.level.removeAll();
			this.levelPanel.add(bonus);
			this.level.repaint();
			this.level.revalidate();
			this.level.hero = bonus.hero;
			
		}
		
		else if (key == KeyEvent.VK_DOWN)	{
			this.level.getHero().move(0,1);
			this.level.repaint();
			this.level.hero.setDirection("down");
			//hero.move(0,-1);
		}
		else if (key == KeyEvent.VK_UP){
			this.level.getHero().move(0, -1);
			this.level.repaint();
			this.level.hero.setDirection("up");
			//hero.move(0,1);
			
		}
		else if (key == KeyEvent.VK_LEFT){
			this.level.getHero().move(-1, 0);
			this.level.repaint();
			this.level.hero.setDirection("left");
			//hero.move(-1,0);
			
		}
		else if (key == KeyEvent.VK_RIGHT){
			this.level.getHero().move(1, 0);
			this.level.repaint();
			this.level.hero.setDirection("right");
			//hero.move(1,0);
		} else if(key == KeyEvent.VK_SPACE){
			this.level.getHero().shootBall();
			this.level.repaint();
		}
			
			
	}
	
	public void helpPane(){
		String message = "Digger! \n Press the arrow keys to move the Digger, "
				+ "and hit space to fire the weapon. \n Pick up gems to score points. "
				+ "\n Avoid monsters, if they catch you, you lose a life. "
				+ "\n Collect all the gems to beat the game!";
		JOptionPane.showMessageDialog(null, message);
	}

	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub.
		//ignore
	}

	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub.
		//ignore
	}

}
