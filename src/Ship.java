import java.awt.*;

public abstract class Ship extends SpaceObject{

	protected double acceleration;
	
	public Ship(Rectangle box, String filename)
	{
		super(box, filename);
	}
	
	protected void applyAcceleration()
	{
		linearSpeed.x += acceleration * (Math.sin(angle));
		linearSpeed.y += acceleration * (Math.cos(angle));
	}
	
	protected void normalize()
	{
		linearSpeed.x = linearSpeed.x * .99;
		linearSpeed.y = linearSpeed.y * .99;
	}
}
