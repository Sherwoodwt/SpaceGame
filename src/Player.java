import java.awt.geom.Rectangle2D;
import java.awt.event.KeyEvent;

public class Player extends Ship {
	
	protected ButtonManager buttons;

	public Player(Rectangle2D.Double box)
	{
		super(box, "resource/greenShip.png");
		buttons = new ButtonManager(setupButtons());
	}
	
	protected void handleInputs()
	{
		if(buttons.isDown(FORWARD))
			forward();
		else if(buttons.isDown(BACKWARD))
			backward();
		else
			normalize();
		if(buttons.isDown(LEFT))
			rotateCounterClockwise();
		else if(buttons.isDown(RIGHT))
			rotateClockwise();
		if(buttons.isDown(SHOOT))
			shoot();
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
	private int[] setupButtons()
	{
		int[] buttons = {KeyEvent.VK_PERIOD, 
						 KeyEvent.VK_COMMA, 
						 KeyEvent.VK_Z, 
						 KeyEvent.VK_X,
						 KeyEvent.VK_SHIFT};
		return buttons;
	}
}
