import java.awt.*;
import java.awt.geom.*;
import java.awt.geom.Point2D.Double;
import java.util.ArrayList;

public abstract class Ship extends SpaceObject{

	private static int MAX_BULLETS = 5;
	
	protected double acceleration;
	protected ArrayList<Bullet> bullets;
	
	public Ship(Rectangle2D.Double box, String filename)
	{
		super(box, 1.5, filename);
		bullets = new ArrayList<Bullet>();
	}
	
	protected abstract void handleInputs();
	
	@Override
	public int update()
	{
		super.update();
		handleInputs();
		ArrayList<Bullet> shitlist = new ArrayList<Bullet>();
		for(Bullet bullet : bullets)
		{
			int result = bullet.update();
			if(result == 0)
				shitlist.add(bullet);
		}
		for(Bullet bullet : shitlist)
		{
			bullets.remove(bullet);
		}
		shitlist = null;
		return 0;
	}
	
	@Override
	public void draw(Graphics g)
	{
		for(Bullet bullet : bullets)
		{
			bullet.draw(g);
		}
		super.draw(g);
	}
	
	protected void applyAcceleration()
	{
		linearSpeed.x += acceleration * (Math.sin(angle));
		linearSpeed.y += acceleration * (Math.cos(angle));
	}
	
	protected void normalize()
	{
		acceleration = 0;
		linearSpeed.x = linearSpeed.x * .99;
		linearSpeed.y = linearSpeed.y * .99;
	}
	
	protected void forward()
	{
		acceleration = INCREMENT;
	}
	
	protected void backward()
	{
		acceleration = -INCREMENT;
	}
	
	protected void rotateCounterClockwise()
	{
		angle -= ROTATION_INC;
	}
	
	protected void rotateClockwise()
	{
		angle += ROTATION_INC;
	}
	
	protected void shoot()
	{
		if(bullets.size() < MAX_BULLETS)
		{
			Point2D.Double bulletLocation = new Point2D.Double(box.x + box.width/2, box.y + box.height/2);
			rotatePoint(bulletLocation);
			Bullet newBullet = new Bullet(new Rectangle2D.Double(bulletLocation.x, bulletLocation.y, box.width/10, box.height/10), angle);
			bullets.add(newBullet);
		}
	}
}
