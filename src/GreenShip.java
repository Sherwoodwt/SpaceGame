import java.awt.Dimension;
import java.awt.Image;
import java.awt.geom.Rectangle2D.Double;


public class GreenShip extends Ship {

	public GreenShip(Double box, String controlFile, Dimension screen) {
		super(box, controlFile, screen);
	}

	
	@Override
	protected void setupImages()
	{
		imageList = new Image[3];
		imageList[NEUTRAL] = readImage("greenShip.png");
		imageList[SHOOTING] = readImage("greenShipShoot.png");
		imageList[BLOWUP] = readImage("explosion.png");
		picture = imageList[NEUTRAL];
	}
}
