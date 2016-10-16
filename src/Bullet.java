import java.awt.geom.*;
import java.awt.Dimension;

public class Bullet extends SpaceObject{
	
	private static int MAX_LIFE = 200;

	private int lifetime;
	
	public Bullet(Rectangle2D.Double box, double angle, Dimension screen)
	{
		super(box, 3, "resource/bullet.png", screen);
		linearSpeed.x = Math.sin(angle) * 20;
		linearSpeed.y = Math.cos(angle) * 20;
		lifetime = 0;
	}
	
	@Override
	public int update()
	{
		super.update();
		lifetime++;
		if(lifetime < MAX_LIFE)
			return 1;
		return 0;
	}
	
	protected void applyAcceleration()
	{
		//does not apply
	}
}
