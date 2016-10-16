import java.awt.*;
import java.awt.geom.*;
import java.io.*;
import java.awt.event.KeyEvent;

import javax.imageio.ImageIO;

public abstract class SpaceObject {

	public static final int FORWARD=0, BACKWARD=1, LEFT=2, RIGHT=3, SHOOT=4;
	public static final double INCREMENT = .05, ROTATION_INC = .03;
	
	protected Rectangle2D.Double box;
	protected Point2D.Double linearSpeed;
	protected double angle;
	protected Image picture;
	protected double maxSpeed;
	
	public SpaceObject(Rectangle2D.Double box, double maxSpeed, String filename)
	{
		this.box = box;
		this.linearSpeed = new Point2D.Double(0, 0);
		this.maxSpeed = maxSpeed;
		try{
			picture = ImageIO.read(new File(filename));
		} catch(IOException e){
			System.err.println("Image not found. Game will exit");
			System.exit(0);
		}
	}
	
	protected abstract void applyAcceleration();
	
	public int update()
	{
		changeLocation();
		return 0;
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
	
	protected void rotatePoint(Point2D.Double point)
	{
		point.x += box.height/2 * Math.sin(angle);
		point.y -= box.height/2 * Math.cos(angle);
	}
}
