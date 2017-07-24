import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

/**
 * Dirt does nothing, can be turned into a Tunnel. 
 */
public class Dirt extends Actor {

	private Color color;
	private Coordinate myLocation;

	public Dirt(Coordinate current) {
		this.color = new Color(139, 69, 13);
		this.myLocation = current;
	}

	public Color getColor() {
		return this.color;
	}

	@Override
	public void draw(Graphics g) {

		Color c = this.getColor();
		g.setColor(c);
		Rectangle2D.Double shape = new Rectangle2D.Double(
				this.myLocation.getX() * 98, this.myLocation.getY() * 95, 98,
				95);

		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(c);
		g2.fill(shape);
		//g2.setColor(Color.BLACK);
		//g2.draw(shape);
	}
}
