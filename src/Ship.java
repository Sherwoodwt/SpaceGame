import java.awt.geom.*;

public abstract class Ship extends SpaceObject{

	protected double acceleration;
	protected Bullet[] bullets;
	
	public Ship(Rectangle2D.Double box, String filename)
	{
		super(box, filename);
		bullets = new Bullet[3];
	}
	
	protected abstract void handleInputs();
	
	public void update()
	{
		handleInputs();
		super.update();
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
		Bullet newBullet = new Bullet(new Rectangle2D.Double(box.x, box.y, box.width, box.height));
	}
}
