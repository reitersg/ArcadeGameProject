import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
/**
 * Broken money happens when a MoneyBag falls more than 1 space. 
 * It can be picked up by the hero, but other than that, it does nothing. 
 */
public class BrokenMoney extends Actor {

	private Coordinate myLocation;

	public BrokenMoney(Coordinate current) {
		this.myLocation = current;
	}


	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub.

		try {
			BufferedImage img = ImageIO.read(new File(
					"Images/broken-money-bag.jpg"));
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
