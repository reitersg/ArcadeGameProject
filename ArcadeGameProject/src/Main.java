import javax.swing.JFrame;

/**
 *The arcade game starts here. 
 *This function creates the world and sets the lives and score.
 * @author Vibha Alangar, Scott Reiter, Enock Momanyi, Brett Jennings
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame myFrame = new JFrame();
		myFrame.setTitle("Digger");
		myFrame.setSize(1500, 1000);
		World myWorld = new World(myFrame);
		myFrame.add(myWorld);
		
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myFrame.setVisible(true);
	}

	public void launch() {
		World.lives = 3;
		World.livesLabel.setText("Lives: " + World.lives);
		World.score = 0;
		World.scoreLabel.setText("Score: " + World.score);
		JFrame myFrame = new JFrame();
		myFrame.setTitle("Digger");
		myFrame.setSize(1500, 1000);
		World myWorld = new World(myFrame);
		myFrame.add(myWorld);
		
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myFrame.setVisible(true);
		
		
	}


}
