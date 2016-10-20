import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;


public abstract class Weapon extends SpaceObject{

	protected Ship enemy;
	
	private int lifetime;
	private int maxLife;
	
	public Weapon(Rectangle2D.Double box, double angle, Ship enemy, Dimension screen, int maxLife)
	{
		super(box, angle, screen);
		this.enemy = enemy;
		this.maxLife = maxLife;
		setupImages();
	}
	
	public int update()
	{
		super.update();
		boolean hit = checkCollideWithEnemy();
		if(hit)
		{
			enemy.hit();
			return DEAD;
		}
		lifetime++;
		if(lifetime < maxLife)
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
}
