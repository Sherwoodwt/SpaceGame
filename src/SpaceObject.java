import java.awt.*;
import java.awt.geom.*;
import java.io.*;
import java.awt.event.KeyEvent;

import javax.imageio.ImageIO;

public abstract class SpaceObject {

	public static final double INCREMENT = .05, ROTATION_INC = .03;
	
	protected Rectangle2D.Double box;
	protected Point2D.Double linearSpeed;
	protected double angle;
	protected Image picture;
	protected double maxSpeed;
	protected Dimension screenDimensions;
	
	public SpaceObject(Rectangle2D.Double box, double maxSpeed, String imageFile, Dimension screen)
	{
		this.box = box;
		this.linearSpeed = new Point2D.Double(0, 0);
		this.maxSpeed = maxSpeed;
		this.screenDimensions = screen;
		try{
			picture = ImageIO.read(new File(imageFile));
		} catch(IOException e){
			System.err.println("Image not found. Game will exit");
			System.exit(0);
		}
	}
	
	protected abstract void applyAcceleration();
	
	public int update()
	{
		changeLocation();
		wrapScreen();
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
}
