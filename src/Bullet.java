import java.awt.geom.*;

public class Bullet extends SpaceObject{

	private int lifetime;
	
	public Bullet(Rectangle2D.Double box, double angle)
	{
		super(box, "resource/bullet.png");
		linearSpeed.x = Math.sin(angle) * 10;
		linearSpeed.y = Math.cos(angle) * 10;
		lifetime = 30;
	}
	
	protected void applyAcceleration()
	{
		//does not apply
	}
}
