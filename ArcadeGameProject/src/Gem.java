import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
/**
 *Gems dont do much, they must be picked up by the hero for points. 
 *They just exist, thats all they do. 
 */
public class Gem extends Actor {

	private Coordinate myLocation;

	public Gem(Coordinate current) {
		this.myLocation = current;
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub.
		
	try {
		BufferedImage img = ImageIO.read(new File("Images/Ice_Gem_Sprite.png"));
		int w = img.getWidth(null);
		int h = img.getHeight(null);
		BufferedImage bi = new
		BufferedImage(10, 15, BufferedImage.TYPE_INT_ARGB);
		g.drawImage(img, this.myLocation.getX()*98, 95*this.myLocation.getY(), null);
	} catch (IOException exception) {
		// TODO Auto-generated catch-block stub.
	}

	}}
