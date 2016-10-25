import java.awt.*;
import java.awt.geom.*;
import java.awt.geom.Point2D.Double;
import java.io.*;
import java.util.ArrayList;
import java.awt.event.KeyEvent;

import javax.imageio.ImageIO;

public abstract class SpaceObject implements Drawable {

	public static final double INCREMENT = .25, ROTATION_INC = .1;
	
	public static int DEAD=0, ALIVE=1;
	
	protected Rectangle2D.Double box;
	protected Point2D.Double linearSpeed;
	protected double angle;
	protected double acceleration;
	protected Image picture;
	protected double maxSpeed;
	protected Dimension screenDimensions;
	protected int state;
	protected Collider collider;
	
	protected ArrayList<Ship> enemies;
	
	protected Point2D.Double[] points;
	
	public SpaceObject(Rectangle2D.Double box, double maxSpeed, Dimension screen)
	{
		this.box = box;
		this.linearSpeed = new Point2D.Double(0, 0);
		this.maxSpeed = maxSpeed;
		this.screenDimensions = screen;
		this.setupImages();
		this.setupPoints();
		this.updatePoints();
		this.setupCollider();
		this.state = ALIVE;
		enemies = new ArrayList<Ship>();
	}
	
	protected abstract void applyAcceleration();
	protected abstract void setupImages();
	protected abstract void setupPoints();
	protected abstract void updatePoints();
	protected abstract void setupCollider();
	
	public int update()
	{
		changeLocation();
		wrapScreen();
		updatePoints();
		rotatePoints();
		for(Ship enemy : enemies)
		{
			if(collider.checkCollision(enemy))
			{
				enemy.hit();
				this.state = DEAD;
			}
		}
		return state;
	}
	
	public void draw(Graphics g)
	{
		Graphics2D g2 = (Graphics2D)g;
		g2.rotate(angle, box.x+(box.width/2), box.y+(box.height/2));
		g2.drawImage(picture, (int)box.x, (int)box.y, (int)box.width, (int)box.height, null);
		g2.rotate(-angle, box.x+(box.width/2), box.y+(box.height/2));
	}

	private void changeLocation()
	{
		applyAcceleration();
		double absoluteSpeed = Math.sqrt((linearSpeed.x*linearSpeed.x)+(linearSpeed.y*linearSpeed.y));
		if(absoluteSpeed > maxSpeed)
		{
			linearSpeed.x += (maxSpeed-absoluteSpeed) * (linearSpeed.x/absoluteSpeed);
			linearSpeed.y += (maxSpeed-absoluteSpeed) * (linearSpeed.y/absoluteSpeed);
			absoluteSpeed = maxSpeed;
		}
		box.x += linearSpeed.x;
		box.y -= linearSpeed.y;
	}
	
	private void rotatePoints()
	{
		for(int i = 0; i < points.length; i++)
		{
			rotatePoint(points[i]);
		}
	}
	
	protected void rotatePoint(Point2D.Double point)
	{
		double centerX = box.x+box.width/2;
		double centerY = box.y+box.height/2;
		point.x = point.x - centerX;
		point.y = point.y - centerY;
		double rotatedX = (point.x*Math.cos(angle)) - (point.y*Math.sin(angle));
		double rotatedY = (point.y*Math.cos(angle)) + (point.x*Math.sin(angle));
		point.x = rotatedX + centerX;
		point.y = rotatedY + centerY;
	}
	
	
	protected void wrapScreen()
	{
		if(box.x + box.width > screenDimensions.width)
			box.x = 0;
		else if(box.x < 0)
			box.x = screenDimensions.width - box.width;
		if(box.y + box.height > screenDimensions.height)
			box.y = 0;
		else if(box.y < 0)
			box.y = screenDimensions.height - box.height;
	}
	
	protected Image readImage(String fileName)
	{
		Image picture = null;
		try{
			picture = ImageIO.read(new File("resource/"+fileName));
		} catch(IOException e){
			System.err.println("Image not found. Game will exit");
			System.err.println(e.toString());
			System.exit(0);
		}
		return picture;
	}
	
	protected void accelerateForward()
	{
		acceleration = INCREMENT;
	}
	
	protected void accelerateBackward()
	{
		acceleration = -INCREMENT;
	}
	
	protected void rotateCounterClockwise()
	{
		angle -= ROTATION_INC;
		if(angle < 0)
			angle = (2*Math.PI) + angle;
	}
	
	protected void rotateClockwise()
	{
		angle = (angle + ROTATION_INC) % (2*Math.PI);
	}
	
	public Point2D.Double getCenter()
	{
		return new Point2D.Double(box.x+box.width/2, box.y+box.height/2);
	}
	
	public Point2D.Double getPoint(int i)
	{
		return points[i];
	}
	
	public int getNumberOfPoints()
	{
		return points.length;
	}
	
	public void addEnemy(Ship s)
	{
		enemies.add(s);
	}
}
