import java.awt.geom.*;

public class Bullet extends SpaceObject{
	
	private static int MAX_LIFE = 300;

	private int lifetime;
	
	public Bullet(Rectangle2D.Double box, double angle)
	{
		super(box, "resource/bullet.png");
		linearSpeed.x = Math.sin(angle) * 10;
		linearSpeed.y = Math.cos(angle) * 10;
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
