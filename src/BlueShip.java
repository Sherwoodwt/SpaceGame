import java.awt.Dimension;
import java.awt.Image;
import java.awt.geom.Rectangle2D.Double;


public class BlueShip extends Ship {

	public BlueShip(Double box, String controlFile, Dimension screen) {
		super(box, controlFile, screen);
	}

	@Override
	protected void setupImages() {
		imageList = new Image[3];
		imageList[NEUTRAL] = readImage("blueShip.png");
		imageList[SHOOTING] = readImage("blueShipShoot.png");
		imageList[BLOWUP] = readImage("explosion.png");
		picture = imageList[NEUTRAL];
	}

}
