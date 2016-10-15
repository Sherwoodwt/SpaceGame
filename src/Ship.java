import java.awt.*;
import java.awt.geom.*;
import java.awt.geom.Point2D.Double;
import java.util.ArrayList;

public abstract class Ship extends SpaceObject{

	protected double acceleration;
	protected ArrayList<Bullet> bullets;
	
	public Ship(Rectangle2D.Double box, String filename)
	{
		super(box, filename);
		bullets = new ArrayList<Bullet>();
	}
	
	protected abstract void handleInputs();
	
	@Override
	public void update()
	{
		handleInputs();
		super.update();
		for(Bullet bullet : bullets)
		{
			bullet.update();
		}
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
		Point2D.Double bulletLocation = new Point2D.Double(box.x + box.width/2, box.y + box.height/2);
		Bullet newBullet = new Bullet(new Rectangle2D.Double(bulletLocation.x, bulletLocation.y, box.width/10, box.height/10), angle);
		bullets.add(newBullet);
	}
}
