import java.awt.Rectangle;
import java.awt.event.KeyEvent;

public class Player extends SpaceObject {

	protected double acceleration;
	
	public Player(Rectangle box)
	{
		super(box, "resource/greenShip.png");
	}
	
	protected void handleInputs()
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

	protected void applyAcceleration()
	{
		linearSpeed.x += acceleration * (Math.sin(angle));
		linearSpeed.y += acceleration * (Math.cos(angle));
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
	protected int[] setupButtons()
	{
		int[] buttons = {KeyEvent.VK_PERIOD, 
						 KeyEvent.VK_COMMA, 
						 KeyEvent.VK_Z, 
						 KeyEvent.VK_X};
		return buttons;
	}
}
