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
		int counter  = 0;
		Point2D.Double point = new Point2D.Double(box.x + box.width/2, box.y + box.height/2);
		for(int i =0; i < 3; i++)
		{
			Point2D.Double start = enemy.getPoint(i);
			Point2D.Double end = enemy.getPoint((i+1)%3);
			//cross product of end-start x point-start vectors
			double[] vec1 = new double[] {end.x-start.x, end.y-start.y};
			double[] vec2 = new double[] {point.x-start.x, point.y-start.y};
			double cross = vec1[0]*vec2[1] - vec1[1]*vec2[0];
			if(cross < 0)
				counter++;
		}
		return counter >= 3;
	}
	
	protected void applyAcceleration()
	{
		//does not apply
	}
}
