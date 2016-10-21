import java.awt.geom.*;
import java.awt.Dimension;
import java.util.ArrayList;

public class Bullet extends Weapon{
	
	public Bullet(Rectangle2D.Double box, double angle, ArrayList<Ship> enemies, Dimension screen)
	{
		super(box, 3, enemies, screen, 200);
		linearSpeed.x = Math.sin(angle) * 20;
		linearSpeed.y = Math.cos(angle) * 20;
	}
	
	@Override
	protected void applyAcceleration()
	{
		//does not apply
	}
	
	@Override
	protected void setupCollider()
	{
		this.collider = new PointInterceptCollider(this);
	}
	
	@Override
	protected void setupImages()
	{
		picture = readImage("bullet.png");
	}
	
	@Override
	public void setupPoints()
	{
		points = new Point2D.Double[1];
		points[0] = new Point2D.Double();
	}
	
	@Override
	public void updatePoints()
	{
		points[0].x = box.x + box.width/2;
		points[0].y = box.y + box.height/2;
	}
}
