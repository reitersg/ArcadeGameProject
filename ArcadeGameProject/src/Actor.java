import java.awt.Graphics;

/**
 *The abstract class is extended by everything in the map.
 */
public abstract class Actor {
	
	private Coordinate myLocation;
	
	public abstract void draw(Graphics g);
	
	
}
