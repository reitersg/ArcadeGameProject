import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JComponent;


/** 
 *This class reads the level file and puts each actor in the appropriate place 
 *in the 2D array that is the level map. 
 *It also starts the threads for each actor that moves on its own. 
 *
 */
//read everthing here
public class Level extends JComponent {

	private File levelFile;
	public static Actor[][] map;
	public static Hero hero;
	public static int GemNumber; 
	public static boolean isPaused; 

	public Hero getHero() {
		return this.hero;
	}

	public Level(File levelFile) {
		this.levelFile = levelFile;
		this.map = new Actor[15][10];
		int current;
		this.GemNumber = 0;
		this.isPaused = false; 
		ArrayList<Actor> actorsForThreads = new ArrayList<Actor>();

		Scanner scan = null;
		try {
			scan = new Scanner(this.levelFile);
		} catch (FileNotFoundException exception) {
			exception.printStackTrace();
		}
		//NEED TO FIX 
		

		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 15; j++) {
				current = scan.nextInt();
				if (current == 0)
					this.map[j][i] = new Tunnel(new Coordinate(j, i));
				else if (current == 1)
					this.map[j][i] = new Dirt(new Coordinate(j, i));
				else if (current == 2) {
					this.hero = new Hero(new Coordinate(j, i));
					this.map[j][i] = this.hero;
				}
				else if (current == 3) {
					Nobbin monster = new Nobbin (new Coordinate(j,i));
					this.map[j][i] = monster;
					actorsForThreads.add(monster);
				}
				else if (current == 4){
					Money money = new Money (new Coordinate(j,i));
					this.map[j][i] = money; 
					actorsForThreads.add(money);
				}
				else if (current == 5) {
					this.map[j][i] = new Gem(new Coordinate(j, i));
					this.GemNumber += 1;
				}
				else if (current == 6){
					Hobbin monster = new Hobbin(new Coordinate(j,i));
					this.map[j][i] = monster;
					actorsForThreads.add(monster);
				} 
				else if (current == 7) {
					Cow cow = new Cow(new Coordinate(j, i));
					this.map[j][i] = cow;
					actorsForThreads.add(cow);
				}
			}

		}

		scan.close();
		for (Actor actor : actorsForThreads){
			Runnable run = (Runnable) actor;
			Thread t = new Thread(run);
			t.start();
		}

	}
	public boolean getPaused(){
		return this.isPaused; 
	}

	private Actor[][] getMap() {
		return this.map;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		int width = this.getWidth() / 15;
		int height = this.getHeight() / 10;

		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 10; j++) {
				Coordinate current = new Coordinate(i * width, j * height);
				this.map[i][j].draw(g2);
			}
		}
	}

}
