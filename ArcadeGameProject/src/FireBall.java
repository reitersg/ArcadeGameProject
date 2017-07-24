import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 *FireBalls occure when the player presses space, it is the weapon of the game. 
 */
public class FireBall extends Actor implements Runnable {
	private static final int HEIGHT = 98;
	private static final int WIDTH = 15;
	final private int MAP_WIDTH = 15;
	final private int MAP_HEIGHT = 10;
	
	Coordinate myLocation;
	int velocity;
	public String direction;
	private volatile boolean isAlive;

	public FireBall(Coordinate coordinate, String direction) {

		this.myLocation = coordinate;
		this.velocity = 1;
		this.direction = direction;
		isAlive = true;
	}
	
	/**
	 *FireBalls move in whatever direction the Hero is facing.
	 *If a FireBall hits Dirt, a Gem, or Money, or the edge of the board. it dies. 
	 *If a FireBall hits a Nobbin or a Hobbin, it dies and it takes out the monster with it. 
	 */

	public void move() {
		if (!Level.isPaused){
			int X = this.myLocation.getX();
			int Y = this.myLocation.getY();
			if (this.direction.equals("up")) {
				if (Y-1 < 0
						|| Level.map[X][Y - 1] instanceof Dirt
						|| Level.map[X][Y - 1] instanceof Gem
						|| Level.map[X][Y - 1] instanceof Money) {
					this.die();
					Level.map[X][Y] = new Tunnel(new Coordinate(X, Y));

				} else if (Level.map[X][Y - 1] instanceof Hobbin) {
					((Hobbin) (Level.map[X][Y - 1])).die();
					this.die();
					Level.map[X][Y] = new Tunnel(new Coordinate(X, Y));
					Level.map[X][Y - 1] = new Tunnel(new Coordinate(X, Y - 1));
	
				} else if (Level.map[X][Y - 1] instanceof Nobbin) {
					((Nobbin) (Level.map[X][Y - 1])).die();
					this.die();
					Level.map[X][Y] = new Tunnel(new Coordinate(X, Y));
					Level.map[X][Y - 1] = new Tunnel(new Coordinate(X, Y - 1));
					
				} else {
					this.myLocation.setCoordinate(this.myLocation.getX(),
							this.myLocation.getY() - this.velocity);
					Level.map[X][Y] = new Tunnel(new Coordinate(X, Y));
					Level.map[X][Y - 1] = this;
	
				}
			} else if (this.direction.equals("down")) {
				if ( Y+1 > this.MAP_HEIGHT - 1
						|| Level.map[X][Y + 1] instanceof Dirt
						|| Level.map[X][Y + 1] instanceof Gem
						|| Level.map[X][Y + 1] instanceof Money) {
					this.die();
					Level.map[X][Y] = new Tunnel(new Coordinate(X, Y));
				} else if (Level.map[X][Y + 1] instanceof Hobbin) {
					((Hobbin) (Level.map[X][Y + 1])).die();
					this.die();
					Level.map[X][Y] = new Tunnel(new Coordinate(X, Y));
					Level.map[X][Y + 1] = new Tunnel(new Coordinate(X, Y + 1));
					
	
				} else if (Level.map[X][Y + 1] instanceof Nobbin) {
					((Nobbin) (Level.map[X][Y + 1])).die();
					this.die();
					Level.map[X][Y] = new Tunnel(new Coordinate(X, Y));
					Level.map[X][Y + 1] = new Tunnel(new Coordinate(X, Y + 1));
					
				} else {
					this.myLocation.setCoordinate(this.myLocation.getX(),
							this.myLocation.getY() + this.velocity);
					Level.map[X][Y] = new Tunnel(new Coordinate(X, Y));
					Level.map[X][Y + 1] = this;
				}
	
			} else if (this.direction.equals("left")) {
				if (X-1 < 0
						|| Level.map[X - 1][Y] instanceof Dirt
						|| Level.map[X - 1][Y] instanceof Gem
						|| Level.map[X - 1][Y] instanceof Money) {
					this.die();
					Level.map[X][Y] = new Tunnel(new Coordinate(X, Y));
					
				} else if (Level.map[X - 1][Y] instanceof Hobbin) {
					((Hobbin) (Level.map[X - 1][Y])).die();
					this.die();
					Level.map[X][Y] = new Tunnel(new Coordinate(X, Y));
					Level.map[X - 1][Y] = new Tunnel(new Coordinate(X - 1, Y));
					
				} else if (Level.map[X - 1][Y] instanceof Nobbin) {
					
					((Nobbin) (Level.map[X - 1][Y])).die();
					this.die();
					Level.map[X][Y] = new Tunnel(new Coordinate(X, Y));
					Level.map[X - 1][Y] = new Tunnel(new Coordinate(X - 1, Y));
				
				} else {
					this.myLocation.setCoordinate(this.myLocation.getX()
							- this.velocity, this.myLocation.getY());
					Level.map[X][Y] = new Tunnel(new Coordinate(X, Y));
					Level.map[X - 1][Y] = this;
				}
			} else if (this.direction.equals("right")) {
	
				if (X+1 > this.MAP_WIDTH - 1
						|| Level.map[X + 1][Y] instanceof Dirt
						|| Level.map[X + 1][Y] instanceof Gem
						|| Level.map[X + 1][Y] instanceof Money) {
					this.die();
					Level.map[X][Y] = new Tunnel(new Coordinate(X, Y));

				} else if (Level.map[X + 1][Y] instanceof Hobbin) {
					((Hobbin) (Level.map[X + 1][Y])).die();
					this.die();
					Level.map[X][Y] = new Tunnel(new Coordinate(X, Y));
					Level.map[X + 1][Y] = new Tunnel(new Coordinate(X + 1, Y));
	
				} else if (Level.map[X + 1][Y] instanceof Nobbin) {
					((Nobbin) (Level.map[X + 1][Y])).die();
					this.die();
					Level.map[X][Y] = new Tunnel(new Coordinate(X, Y));
					Level.map[X + 1][Y] = new Tunnel(new Coordinate(X + 1, Y));
	
				} else {
					this.myLocation.setCoordinate(this.myLocation.getX()
							+ this.velocity, this.myLocation.getY());
					Level.map[X][Y] = new Tunnel(new Coordinate(X, Y));
					Level.map[X + 1][Y] = this;
	
				}
			}
		}

	}

	public void die() {
		this.isAlive = false;
	}

	@Override
	public void draw(Graphics g) {
		try {
			BufferedImage img = ImageIO.read(new File("Images/Fireball.jpg"));
			int w = img.getWidth(null);
			int h = img.getHeight(null);
			BufferedImage bi = new BufferedImage(10, 15,
					BufferedImage.TYPE_INT_ARGB);
			g.drawImage(img, this.myLocation.getX() * 98,
					95 * this.myLocation.getY(), null);
		} catch (IOException exception) {
			// TODO Auto-generated catch-block stub.
		}
	
	}

	public void run() {
		// TODO Auto-generated method stub.
		try {
			while (isAlive) {
				move();
				Thread.sleep(100);
				KeyboardHandler.level.repaint();
			}
		} catch (InterruptedException exception) {
				// TODO Auto-generated catch-block stub.
	
		}
	}
}
