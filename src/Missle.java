import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D.Double;


public class Missle extends Weapon {

	public Missle(Double box, double angle, Ship enemy, Dimension screen)
	{
		super(box, angle, enemy, screen, 300);
	}

	@Override
	protected void applyAcceleration() {
		Point2D.Double enemyCenter = enemy.getCenter();
		Point2D.Double myCenter = this.getCenter();
		double xDist = enemyCenter.x - myCenter.x;
		double yDist = enemyCenter.y - myCenter.y;
		double goalAngle = Math.atan2(xDist, yDist);
		//CHANGE THE ANGLE
		if(goalAngle < angle)
			angle -= ROTATION_INC;
		else if(goalAngle > angle)
			angle += ROTATION_INC;
		//CHANGE ACCELERATION TO MATCH NEW ANGLE
		acceleration = INCREMENT;
		linearSpeed.x += acceleration * (Math.sin(angle));
		linearSpeed.y += acceleration * (Math.cos(angle));
	}

	@Override
	protected void setupImages() {
		picture = readImage("missle.png");
	}

	
}
