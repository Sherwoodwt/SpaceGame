import java.awt.Rectangle;
import java.awt.event.KeyEvent;

public class Player extends Ship {
	
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
	
	public void keyPressed(int keyCode)
	{
		buttons.keyDown(keyCode);
	}
	
	public void keyReleased(int keyCode)
	{
		buttons.keyUp(keyCode);
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
