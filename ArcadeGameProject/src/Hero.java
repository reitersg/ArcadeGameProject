import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
/**
 *Hero of the game, only one exists.
 *The hero can move in any of the 4 cardinal directions, and can go through dirt. 
 *They are killed when they are hit by a falling Money, or if they are touched by 
 *the monsters (Hobbin or Nobbin) 
 *A hero can't move off the screen. 
 *The hero also shoots the fireball when the user presses space. 
 */
public class Hero extends Actor {
	final private int WIDTH = 98;
	final private int HEIGHT = 95;
	final private int MAP_WIDTH = 15;
	final private int MAP_HEIGHT = 10;
	private Color color;
	private Coordinate myLocation;
	public static String direction;
	private Coordinate startingLocation;

	public Hero(Coordinate coordinate) {
		this.color = Color.BLUE;
		this.myLocation = coordinate;
		this.direction = "up";
		this.startingLocation = new Coordinate(coordinate.getX(),
				coordinate.getY());
	}

	public void move(int x, int y) {
		// System.out.println("starting move at " + this.myLocation);
		if(!Level.isPaused){
			try {
				int X = this.myLocation.getX();
				int Y = this.myLocation.getY();
				if (Level.map[X + x][Y + y] instanceof Money
						&& Y + y == this.MAP_HEIGHT - 1
						|| Level.map[X + x][Y] instanceof Money
						&& Y == this.MAP_HEIGHT - 1) {
					World.score += 25;
					World.scoreLabel.setText("Score: " + World.score);
					Level.map[X][Y] = new Tunnel(new Coordinate(X, Y));
				}
				if (Level.map[X + x][Y] instanceof Money) {
					((Money) (Level.map[X + x][Y])).push(x);
				}
				if (Level.map[X + x][Y + y] instanceof BrokenMoney) {
					World.score += 25;
					World.scoreLabel.setText("Score: " + World.score);
					Level.map[X][Y] = new Tunnel(new Coordinate(X, Y));
					if ((X + x) < 0 || (X + x) > this.MAP_WIDTH - 1) {
						this.myLocation.setCoordinate(X, Y);
						Level.map[X][Y] = this;
					}
				 else if ((Y + y) < 0 || (Y + y) > this.MAP_HEIGHT - 1) {
					this.myLocation.setCoordinate(X, Y);
					Level.map[X][Y] = this;
				} else {
					this.myLocation.setCoordinate(X + x, Y + y);
					Level.map[X + x][Y + y] = this;
				}
			}
				if (Level.map[X + x][Y + y] instanceof Gem) {
					Level.GemNumber -= 1;
					World.score += 25;
					World.scoreLabel.setText("Score: " + World.score);
					Level.map[X][Y] = new Tunnel(new Coordinate(X, Y));
					if ((X + x) < 0 || (X + x) > this.MAP_WIDTH - 1) {
						this.myLocation.setCoordinate(X, Y);
						Level.map[X][Y] = this;
					} else if (Level.GemNumber == 0) {
						// figure out next level
						System.out.println("Yay! on to the next level.");
						Level nextLevel = new Level(
								KeyboardHandler.manager.nextLevel());
						nextLevel.setPreferredSize(new Dimension(1400, 900));
						KeyboardHandler.level.removeAll();
						KeyboardHandler.levelPanel.add(nextLevel);
						KeyboardHandler.level.repaint();
						KeyboardHandler.level.revalidate();
						KeyboardHandler.level.hero = nextLevel.hero;
	
					} else if ((Y + y) < 0 || (Y + y) > this.MAP_HEIGHT - 1) {
						this.myLocation.setCoordinate(X, Y);
						Level.map[X][Y] = this;
					} else {
						this.myLocation.setCoordinate(X + x, Y + y);
						Level.map[X + x][Y + y] = this;
					}
	
				} else if (Level.map[X + x][Y + y] instanceof Hobbin
						|| Level.map[X + x][Y + y] instanceof Nobbin) {
					this.die();
				} else {
	
					Level.map[X][Y] = new Tunnel(new Coordinate(X, Y));
					if ((X + x) < 0 || (X + x) > this.MAP_WIDTH - 1) {
						this.myLocation.setCoordinate(X, Y);
						Level.map[X][Y] = this;
					} else if ((Y + y) < 0 || (Y + y) > this.MAP_HEIGHT - 1) {
						this.myLocation.setCoordinate(X, Y);
						Level.map[X][Y] = this;
					} else {
						this.myLocation.setCoordinate(X + x, Y + y);
						Level.map[X + x][Y + y] = this;
	
					}
				}
	
				// System.out.println("now at " + this.myLocation);
			} catch (ArrayIndexOutOfBoundsException e) {
				System.out.println("Out of bounds.");
			}
		}
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getDirection() {
		return direction;
	}

	public void shootBall() {
		Coordinate location = this.myLocation;
		FireBall ball = new FireBall(new Coordinate(this.myLocation.getX(),
				this.myLocation.getY()), direction);
		Runnable run = ball;
		Thread t1 = new Thread(run);
		t1.start();
	}

	public void die() {
		System.out.println("You died!");
		// maybe add a little RIP image?
		World.lives -= 1;
		World.livesLabel.setText("Lives left: " + World.lives);
		// start from the beginning spot
		this.resetLocation();

		if (World.lives <= 0) {
			Level.isPaused = !Level.isPaused;
			losePane(World.score);
			new Main().launch();

			// other stuff
		}

	}
	public void losePane(int score){
		String message = "You lost! Your final score was " + score 
				+ "\n.Press ok to start a new game. ";
		JOptionPane.showMessageDialog(null, message);
	}

	public void resetLocation() {

		int x = this.myLocation.getX();
		int y = this.myLocation.getY();
		int startx = this.startingLocation.getX();
		int starty = this.startingLocation.getY();

		Level.map[x][y] = new Tunnel(new Coordinate(x, y));
		this.myLocation.setCoordinate(startx, starty);
		Level.map[startx][starty] = this;
	}

	public Coordinate getLocation() {
		return this.myLocation;
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub.
		// Color c = this.getColor();
		// System.out.println("drawing at location " + this.myLocation);
		try {
			BufferedImage img = ImageIO.read(new File("Images/Capture.png"));
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

}