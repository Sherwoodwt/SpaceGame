import java.awt.*;
import java.awt.event.KeyEvent;

public class SpaceObject {

	public static final int FORWARD=0, BACKWARD=1, LEFT=2, RIGHT=3;
	public static final double MAXSPEED = 5, INCREMENT = .1;
	
	private Rectangle box;
	private ButtonManager buttons;
	private double speed;
	private double angle;
	
	public SpaceObject(Rectangle box)
	{
		this.box = box;
		this.buttons = new ButtonManager(setupButtons());
	}
	
	public void update()
	{
		handleInputs();
		changeLocation();
	}
	
	public void draw(Graphics g)
	{
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(Color.GREEN);
		g2.rotate(angle, box.x+(box.width/2), box.y+(box.height/2));
		g2.fill(box);
		g2.rotate(-angle, box.x+(box.width/2), box.y+(box.height/2));
	}
	
	private void handleInputs()
	{
		if(buttons.isDown(FORWARD) && speed < MAXSPEED)
			speed+=INCREMENT;
		else if(buttons.isDown(BACKWARD) && speed > -MAXSPEED)
			speed-=INCREMENT;
		else
			normalizeSpeed();
		if(buttons.isDown(LEFT))
			angle-=INCREMENT;
		else if(buttons.isDown(RIGHT))
			angle+=INCREMENT;
	}
	
	private void changeLocation()
	{
		double speedX = speed * (Math.sin(degreeToRadian(angle)));
		double speedY = speed * (Math.cos(degreeToRadian(angle)));
		box.x += speedX;
		box.y += speedY;
	}
	
	public void keyPressed(int keyCode)
	{
		buttons.keyDown(keyCode);
	}
	
	public void keyReleased(int keyCode)
	{
		buttons.keyUp(keyCode);
	}
	
	private void normalizeSpeed()
	{
		if(speed > 0)
			speed-=INCREMENT;
		if(speed < 0)
			speed+=INCREMENT;
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
