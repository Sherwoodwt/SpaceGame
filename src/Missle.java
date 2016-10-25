import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.util.ArrayList;


public class Missle extends Weapon {
	
	private ArrayList<VaporTrail> vaporTrails;

	public Missle(Double box, double angle, ArrayList<Ship> enemies, Dimension screen)
	{
		super(box, 6, enemies, screen, 500);
		box.width = 10;
		box.height = 15;
		vaporTrails = new ArrayList<VaporTrail>();
		this.angle = angle;
	}

	@Override
	protected void applyAcceleration() {
		Ship closest = findClosest(this.getCenter());
		Point2D.Double enemyCenter = closest.getCenter();
		Point2D.Double myCenter = this.getCenter();
		double xDist = enemyCenter.x - myCenter.x;
		double yDist = enemyCenter.y - myCenter.y;
		double goalAngle = Math.atan2(xDist, -yDist);
		changeAngle(goalAngle);
		//CHANGE ACCELERATION TO MATCH NEW ANGLE
		acceleration = INCREMENT;
		linearSpeed.x += acceleration * (Math.sin(angle));
		linearSpeed.y += acceleration * (Math.cos(angle));
	}
	
	private void changeAngle(double goalAngle)
	{
		if(goalAngle < 0)
			goalAngle = 2*Math.PI + goalAngle;
		double difference = Math.abs(goalAngle - angle);
		if((goalAngle < angle && difference < Math.PI) || (goalAngle > angle && difference >= Math.PI))
		{
			rotateCounterClockwise();
			rotateCounterClockwise();
		}
		else if((goalAngle > angle && difference < Math.PI) || (goalAngle < angle && difference >= Math.PI))
		{
			rotateClockwise();
			rotateClockwise();
		}
	}

	@Override
	protected void setupImages() {
		picture = readImage("missle.png");
	}
	
	@Override
	protected void setupCollider()
	{
		this.collider = new PointInterceptCollider(this);
	}
	
	@Override
	public void draw(Graphics g)
	{
		for(VaporTrail v : vaporTrails)
		{
			v.draw(g);
		}
		super.draw(g);
	}
	
	@Override
	public int update()
	{
		int result = super.update();
		updateVaporTrails();
		return result;
	}

	private void updateVaporTrails()
	{
		for(int i = 0; i < points.length; i++)
		{
			vaporTrails.add(new VaporTrail(new Point2D.Double(points[i].x, points[i].y)));
		}
		
		ArrayList<VaporTrail> shitlist = new ArrayList<VaporTrail>();
		for(VaporTrail v : vaporTrails)
		{
			int result = v.update();
			if(result == DEAD)
				shitlist.add(v);
		}
		for(VaporTrail v : shitlist)
		{
			vaporTrails.remove(v);
		}
		shitlist = null;
	}
	
	private Ship findClosest(Point2D.Double myPoint)
	{
		Ship target = enemies.get(0);
		double currentShortest = -1;
		for(Ship enemy : enemies)
		{
			double dist = myPoint.distance(enemy.getCenter());
			if(currentShortest == -1 || dist < currentShortest)
			{
				currentShortest = dist;
				target = enemy;
			}
		}
		return target;
	}

	@Override
	protected void setupPoints() 
	{
		points = new Point2D.Double[3];
		for(int i = 0; i < points.length; i++)
			points[i] = new Point2D.Double();
	}

	@Override
	protected void updatePoints() 
	{
		points[0].x = box.x+box.width/2;
		points[0].y = box.y;
		points[1].x = box.x;
		points[1].y = box.y+box.height;
		points[2].x = box.x+box.width;
		points[2].y = box.y+box.height;
	}
}
