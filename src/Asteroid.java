import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D.Double;
import java.util.Random;

public class Asteroid extends SpaceObject {

	public Asteroid(Double box, Dimension screen) {
		super(box, 6, screen);
		Random randy = new Random();
		angle = randy.nextDouble() * 2 * Math.PI;
		linearSpeed.x = Math.sin(angle) * (randy.nextInt((int)this.maxSpeed)+.01);
		linearSpeed.y = Math.cos(angle) * (randy.nextInt((int)this.maxSpeed)+.01);
	}

	@Override
	protected void applyAcceleration() {
		//NOT IMPLMENTED
	}

	@Override
	protected void setupImages() {
		picture = readImage("asteroid.png");
	}

	@Override
	protected void setupPoints() {
		points = new Point2D.Double[1];
		points[0] = new Point2D.Double();
	}

	@Override
	protected void updatePoints() {
		points[0].x = box.x + box.width/2;
		points[0].y = box.y + box.height/2;
	}

	@Override
	protected void setupCollider() {
		this.collider = new CircleCollider(this, this.box.width-20);
	}
	
}
