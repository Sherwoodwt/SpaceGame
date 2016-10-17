import java.awt.geom.*;
import java.awt.Dimension;

public class Bullet extends SpaceObject{
	
	private static int MAX_LIFE = 200;

	private int lifetime;
	private Ship enemy;
	
	public Bullet(Rectangle2D.Double box, double angle, Ship enemy, Dimension screen)
	{
		super(box, 3, "resource/bullet.png", screen);
		this.enemy = enemy;
		linearSpeed.x = Math.sin(angle) * 20;
		linearSpeed.y = Math.cos(angle) * 20;
		lifetime = 0;
	}
	
	@Override
	public int update()
	{
		super.update();
		boolean hit = checkCollideWithEnemy();
		if(hit)
			return DEAD;
		lifetime++;
		if(lifetime < MAX_LIFE)
			return ALIVE;
		return DEAD;
	}
	
	private boolean checkCollideWithEnemy()
	{
		boolean hit = false;
		Point2D.Double point = new Point2D.Double(box.x + box.width/2, box.y + box.height/2);
		
		return hit;
	}
	
	protected void applyAcceleration()
	{
		//does not apply
	}
}
