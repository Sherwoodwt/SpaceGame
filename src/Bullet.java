import java.awt.geom.Rectangle2D;

public class Bullet extends SpaceObject{

	private int lifetime;
	
	public Bullet(Rectangle2D.Double box)
	{
		super(box, "resource/bullet.png");
		lifetime = 10;
	}
	
	protected void applyAcceleration()
	{
		//does not apply
	}
}
