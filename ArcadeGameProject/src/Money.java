import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Money objects will only move if there is a tunnel below them, 
 * or if the hero pushes them. 
 */

public class Money extends Actor implements Runnable {

	final private int WIDTH = 98;
	final private int HEIGHT = 95;
	final private int MAP_WIDTH = 15;
	final private int MAP_HEIGHT = 10;

	private Coordinate myLocation;
	private volatile boolean isAlive;
	private int moveCount; 
	private boolean isMoving;
	private boolean broken = false;

	public Money(Coordinate current) {
		this.myLocation = current;
		this.isAlive = true;
		this.moveCount = 0;
		this.isMoving = false; 
	}

	// money falls on its own
	public void move() {
		if (!Level.isPaused){
			if(!this.broken){
				int X = this.myLocation.getX();
				int Y = this.myLocation.getY();
				if ((Y+1>=this.MAP_HEIGHT) || (this.moveCount > 1 && (Y+1<this.MAP_HEIGHT) && 
						(!(Level.map[X][Y+1] instanceof Tunnel)))) {//if it cant move again, & moveCount > 1
					// we can add an image of a broken open money bag,
					// but it should disappear a few seconds later.
					//System.out.println("Dying, moveCount = " + this.moveCount);
					this.isAlive = false;
					Level.map[X][Y] = new BrokenMoney(new Coordinate(X, Y));
					this.broken = true;
				}
				else if (!(Y + 1 >= this.MAP_HEIGHT) && Level.map[X][Y + 1] instanceof Tunnel) {// if it can move 1 space
					this.moveCount++;
					Level.map[X][Y] = new Tunnel(new Coordinate(X, Y));
					this.myLocation.setCoordinate(X, Y + 1);
					Level.map[X][Y + 1] = this;
					Y = this.myLocation.getY();
					this.isMoving = true;
					//System.out.println("Moving: moveCount = "+ this.moveCount);
				}
				else if ((Y+1 < this.MAP_HEIGHT) && Level.map[X][Y + 1] instanceof Hero && this.isMoving) {//if it will hit hero 
					Level.hero.die();
				}
				else if ((Y+1 >= this.MAP_HEIGHT) || !(Level.map[X][Y+1] instanceof Tunnel)){//if doesnt move this time
					this.moveCount = 0;
					this.isMoving = false;
					//System.out.println("not moving, moveCount = " + this.moveCount);
				}
			}
		}
	}

	// money is pushed
	public void push(int x) {
		int CurrentX = this.myLocation.getX();
		int CurrentY = this.myLocation.getY();
		if (CurrentY != this.MAP_HEIGHT - 1) {
			this.myLocation.setCoordinate(CurrentX + x, CurrentY);
			Level.map[CurrentX + x][CurrentY] = this;
		}

	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub.
		try {
			BufferedImage img = ImageIO.read(new File("Images/money-bags.png"));
			int w = img.getWidth(null);
			int h = img.getHeight(null);
			BufferedImage bi = new BufferedImage(10, 15,
					BufferedImage.TYPE_INT_ARGB);
			g.drawImage(img, this.myLocation.getX() * 98,
					94 * this.myLocation.getY(), null);
		} catch (IOException exception) {

		}
	}

	public void run() {
		while (this.isAlive) {
			this.move();
			try {
				Thread.sleep(500);
			} catch (InterruptedException exception) {
				exception.printStackTrace();
			}
	
		}
	}
}
