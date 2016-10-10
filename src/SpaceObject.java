import java.awt.*;
import java.awt.geom.*;
import java.io.*;
import java.awt.event.KeyEvent;

import javax.imageio.ImageIO;

public class SpaceObject {

	public static final int FORWARD=0, BACKWARD=1, LEFT=2, RIGHT=3;
	public static final double MAXSPEED = 2, INCREMENT = .05, ROTATION_INC = .03;
	
	private Rectangle2D.Double box;
	private ButtonManager buttons;
	private double acceleration;
	private Point2D.Double linearSpeed;
	private double angle;
	private Image picture;
	
	public SpaceObject(Rectangle box)
	{
		this.box = new Rectangle2D.Double(box.x, box.y, box.width, box.height);
		this.linearSpeed = new Point2D.Double(0, 0);
		this.buttons = new ButtonManager(setupButtons());
		try{
			picture = ImageIO.read(new File("resource/greenShip.png"));
		} catch(IOException e){
			System.err.println("Image not found. Game will exit");
			System.exit(0);
		}
	}
	
	public void update()
	{
		handleInputs();
		changeLocation();
	}
	
	public void draw(Graphics g)
	{
		Graphics2D g2 = (Graphics2D)g;
		g2.rotate(angle, box.x+(box.width/2), box.y+(box.height/2));
		g2.drawImage(picture, (int)box.x, (int)box.y, (int)box.width, (int)box.height, null);
		g2.rotate(-angle, box.x+(box.width/2), box.y+(box.height/2));
	}
	
	private void handleInputs()
	{
		if(buttons.isDown(FORWARD))
			acceleration = INCREMENT;
		else if(buttons.isDown(BACKWARD))
			acceleration = -INCREMENT;
		else
		{
			acceleration = 0;
			normalize();
		}
		if(buttons.isDown(LEFT))
			angle-=ROTATION_INC;
		else if(buttons.isDown(RIGHT))
			angle+=ROTATION_INC;
	}
	
	private void changeLocation()
	{
		linearSpeed.x += acceleration * (Math.sin(angle));
		linearSpeed.y += acceleration * (Math.cos(angle));
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
	
	private void normalize()
	{
		linearSpeed.x = linearSpeed.x * .99;
		linearSpeed.y = linearSpeed.y * .99;
	}
	
	public void keyPressed(int keyCode)
	{
		buttons.keyDown(keyCode);
	}
	
	public void keyReleased(int keyCode)
	{
		buttons.keyUp(keyCode);
	}
	
	private double degreeToRadian(double degree)
	{
		return (degree * Math.PI)/180.0;
	}
	
	/*
	 * Later will read from config file
	 */
	private int[] setupButtons()
	{
		int[] buttons = {KeyEvent.VK_PERIOD, 
						 KeyEvent.VK_COMMA, 
						 KeyEvent.VK_Z, 
						 KeyEvent.VK_X};
		return buttons;
	}
}
