import java.awt.geom.*;
import java.awt.Dimension;

public class Bullet extends Weapon{
	
	public Bullet(Rectangle2D.Double box, double angle, Ship enemy, Dimension screen)
	{
		super(box, 3, enemy, screen, 200);
		linearSpeed.x = Math.sin(angle) * 20;
		linearSpeed.y = Math.cos(angle) * 20;
	}
	
	protected void applyAcceleration()
	{
		//does not apply
	}
	
	protected void setupImages()
	{
		picture = readImage("bullet.png");
	}
}
