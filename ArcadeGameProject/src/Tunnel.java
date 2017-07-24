import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

/** 
 *Like Dirt, Tunnel objects don't do much. They exist so other objets can move around. 
 */
public class Tunnel extends Actor {

	private Color color;
	private Coordinate myLocation;

	public Tunnel(Coordinate coordinate) {
		this.color = Color.BLACK;
		this.myLocation = coordinate;
	}

	public Color getColor() {
		// TODO Auto-generated method stub.
		return this.color;
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub.
		Color c = this.getColor();
		g.setColor(c);
		Rectangle2D.Double shape = new Rectangle2D.Double(
				this.myLocation.getX() * 98, this.myLocation.getY() * 95, 98,
				95);

		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(c);
		g2.fill(shape);
		g2.setColor(Color.BLACK);
		g2.draw(shape);
	}

}
