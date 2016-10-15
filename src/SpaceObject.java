import java.awt.*;
import java.awt.geom.*;
import java.io.*;
import java.awt.event.KeyEvent;

import javax.imageio.ImageIO;

public abstract class SpaceObject {

	public static final int FORWARD=0, BACKWARD=1, LEFT=2, RIGHT=3, SHOOT=4;
	public static final double MAXSPEED = 2, INCREMENT = .05, ROTATION_INC = .03;
	
	protected Rectangle2D.Double box;
	protected Point2D.Double linearSpeed;
	protected double angle;
	protected Image picture;
	
	public SpaceObject(Rectangle2D.Double box, String filename)
	{
		this.box = box;
		this.linearSpeed = new Point2D.Double(0, 0);
		try{
			picture = ImageIO.read(new File(filename));
		} catch(IOException e){
			System.err.println("Image not found. Game will exit");
			System.exit(0);
		}
	}
	
	protected abstract void applyAcceleration();
	
	public void update()
	{
		changeLocation();
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
		if(absoluteSpeed > MAXSPEED)
		{
			linearSpeed.x += (MAXSPEED-absoluteSpeed) * (linearSpeed.x/absoluteSpeed);
			linearSpeed.y += (MAXSPEED-absoluteSpeed) * (linearSpeed.y/absoluteSpeed);
			absoluteSpeed = MAXSPEED;
		}
		box.x += linearSpeed.x;
		box.y -= linearSpeed.y;
	}
}
