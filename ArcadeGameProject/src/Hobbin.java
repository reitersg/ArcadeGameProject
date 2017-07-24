import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

/**
 *One of the two monsters. Hobbins have the ability to move through dirt, 
 *and they always move towards the hero. They are killed when the hero hits them with 
 *a fireball or if they are hit by a falling Money object. 
 *There is also a 5% chance that a Hobbin will spontaneously turn into a Nobbin 
 *with each move. 
 *
 */

public class Hobbin extends Actor implements Runnable {
	final private int WIDTH = 98;
	final private int HEIGHT = 95;
	final private int MAP_WIDTH = 15;
	final private int MAP_HEIGHT = 10;

	private int x_velocity;
	private int y_velocity;
	private Coordinate myLocation;
	private volatile boolean isAlive;
	private Coordinate startingLocation;

	public Hobbin(Coordinate current) {

		this.myLocation = current;
		this.startingLocation = new Coordinate(current.getX(), current.getY());
		this.isAlive = true;

	}
	public void die() {
		this.isAlive = false;
	}
	

	public void move() {
		if (!Level.isPaused){
			int X = this.myLocation.getX();
			int Y = this.myLocation.getY();
			// System.out.println(rand);
			// switch to Nobbin (5% chance)
			double rand = Math.random();
			if (rand < .05) {
				// System.out.println("Switching to Nobbin");
				this.isAlive = false;
				Nobbin nobbin = new Nobbin(new Coordinate(X, Y));
				Level.map[X][Y] = nobbin;
				Runnable run = nobbin;
				Thread t = new Thread(run);
				t.start();
			}
			
			if(this.isAlive){
				Coordinate nextMovement = this
						.getBestMovement(Level.hero.getLocation());
				if (nextMovement == null)
					return;
				// System.out.println("Current Location: " + this.myLocation);
				// System.out.println("Next Location: " + nextMovement);
				// now actual movement
				if (Level.map[X][Y] instanceof FireBall
						|| Level.map[X][Y] instanceof Money) {
					this.die();
					Level.map[X][Y] = new Tunnel(new Coordinate(X, Y));
				}
				if (!(Level.map[X + nextMovement.getX()][Y + nextMovement.getY()] instanceof Hero)) {
					Level.map[X][Y] = new Tunnel(new Coordinate(X, Y));
					this.myLocation.setCoordinate(X + nextMovement.getX(), Y
							+ nextMovement.getY());
					Level.map[X + nextMovement.getX()][Y + nextMovement.getY()] = this;
				}
				if (Level.map[X + nextMovement.getX()][Y + nextMovement.getY()] instanceof Hero)
					Level.hero.die();
				else if (Level.map[X + nextMovement.getX()][Y + nextMovement.getY()] instanceof Dirt) {
					Level.map[X + nextMovement.getX()][Y + nextMovement.getY()] = this;
					Level.map[X][Y] = new Tunnel(new Coordinate(X, Y));
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
		try {
			BufferedImage img = ImageIO.read(new File(
					"Images/Buffalo's Face.jpg"));
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
		while (this.isAlive) {
			this.move();
			try {
				Thread.sleep(500);
				KeyboardHandler.level.repaint();
			} catch (InterruptedException exception) {
				exception.printStackTrace();
			}
		}
		
	}
}