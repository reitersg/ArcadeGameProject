import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
/**
 *Cow, the monster for the bonus level. 
 *Cows can move through tunnels, and they leave tunnels behind them. 
 *They still die when hit by a fireball.  
 */

public class Cow extends Actor  implements Runnable{

	final private int WIDTH = 98; 
	final private int HEIGHT = 95; 
	final private int MAP_WIDTH = 15; 
	final private int MAP_HEIGHT = 10; 
	
	private Coordinate myLocation; 
	private volatile boolean isAlive; 
	private Coordinate startingLocation; 
	
	public Cow(Coordinate current){
		this.myLocation = current;
		this.startingLocation = new Coordinate(current.getX(), current.getY());
		this.isAlive = true;
	}
	public void die(){
		this.isAlive = false;
	}
	public void move(){
		if (!Level.isPaused){
			int X = this.myLocation.getX();
			int Y = this.myLocation.getY();
			if(this.isAlive){
				Coordinate nextMovement = this
						.getBestMovement(Level.hero.getLocation());
				if (nextMovement == null){
					return;
			}
				if (Level.map[X][Y] instanceof FireBall
						|| Level.map[X][Y] instanceof Money) {
					this.die();
					Level.map[X][Y] = new Tunnel(new Coordinate(X, Y));
				}
				if (!(Level.map[X + nextMovement.getX()][Y + nextMovement.getY()] instanceof Hero)) {
					Level.map[X][Y] = new Dirt(new Coordinate(X, Y));
					this.myLocation.setCoordinate(X + nextMovement.getX(), Y
							+ nextMovement.getY());
					Level.map[X + nextMovement.getX()][Y + nextMovement.getY()] = this;
				}
				if (Level.map[X + nextMovement.getX()][Y + nextMovement.getY()] instanceof Hero)
					Level.hero.die();
				else if (Level.map[X + nextMovement.getX()][Y + nextMovement.getY()] instanceof Dirt) {
					Level.map[X][Y] = this;
	
				}
			}
		}
	}
	public ArrayList<Coordinate> possibleLocations() {
		int X = this.myLocation.getX();
		int Y = this.myLocation.getY();
		ArrayList<Coordinate> toReturn = new ArrayList<Coordinate>();
		if (!(X + 1 > this.MAP_WIDTH - 1) && 
				(Level.map[X+1][Y] instanceof Hero || 
						Level.map[X+1][Y] instanceof Tunnel) || 
						Level.map[X+1][Y] instanceof Dirt)
			toReturn.add(new Coordinate(1, 0));
		if (!(X - 1 < 0) && 
				(Level.map[X-1][Y] instanceof Hero || 
						Level.map[X-1][Y] instanceof Tunnel) || 
						Level.map[X-1][Y] instanceof Dirt)
			toReturn.add(new Coordinate(-1, 0));
		if (!(Y + 1 > this.MAP_HEIGHT - 1) && 
				(Level.map[X][Y+1] instanceof Hero || 
						Level.map[X][Y+1] instanceof Tunnel) || 
						Level.map[X][Y+1] instanceof Dirt)
			toReturn.add(new Coordinate(0, 1));
		if (!(Y - 1 < 0) && 
				(Level.map[X][Y-1] instanceof Hero || 
						Level.map[X][Y-1] instanceof Tunnel) || 
						Level.map[X][Y-1] instanceof Dirt)
			toReturn.add(new Coordinate(0, -1));
		return toReturn;
	}
	public Coordinate getBestMovement(Coordinate heroLocation) {
		Coordinate bestCoordinate = null;
		double distanceToHero = 100;

		int X = this.myLocation.getX();
		int Y = this.myLocation.getY();

		ArrayList<Coordinate> possibleLocations = this.possibleLocations();
		// System.out.println(possibleLocations);
		if (possibleLocations.size() == 1)
			bestCoordinate = possibleLocations.get(0);
		else {
			int HeroX = heroLocation.getX();
			int HeroY = heroLocation.getY();
			for (int i = 0; i < possibleLocations.size(); i++) {
				int differenceX = X + possibleLocations.get(i).getX() - HeroX;
				int differenceY = Y + possibleLocations.get(i).getY() - HeroY;
				double distance = Math.hypot(differenceX, differenceY);
				if (distance < distanceToHero) {
					distanceToHero = distance;
					bestCoordinate = possibleLocations.get(i);
				}
			}
		}
		return bestCoordinate;
	}
	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub.
		try {
			BufferedImage img = ImageIO.read(new File(
					"Images/Mini-Cow.jpg"));
			int w = img.getWidth(null);
			int h = img.getHeight(null);
			BufferedImage bi = new BufferedImage(10, 15,
					BufferedImage.TYPE_INT_ARGB);
			g.drawImage(img, this.myLocation.getX() * 98,
					94 * this.myLocation.getY(), null);
		} catch (IOException exception) {
			//
		}
	}

	public void run() {
		// TODO Auto-generated method stub.
		while (isAlive){
			move();
			try {
				Thread.sleep(1000);
				KeyboardHandler.level.repaint();
			} catch (InterruptedException exception) {
				// TODO Auto-generated catch-block stub.
				System.out.println("Out of Bounds");
			}
		}
	}

	
}
