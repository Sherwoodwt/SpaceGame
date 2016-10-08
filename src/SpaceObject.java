import java.awt.*;
import java.awt.event.KeyEvent;

public class SpaceObject {

	public static final int FORWARD=0, BACKWARD=1, LEFT=2, RIGHT=3;
	
	private Rectangle box;
	private ButtonManager buttons;
	
	public SpaceObject(Rectangle box)
	{
		this.box = box;
		this.buttons = new ButtonManager(setupButtons());
	}
	
	public void update()
	{
		if(buttons.isDown(FORWARD))
			box.y--;
		if(buttons.isDown(BACKWARD))
			box.y++;
		if(buttons.isDown(LEFT))
			box.x--;
		if(buttons.isDown(RIGHT))
			box.x++;
	}
	
	public void draw(Graphics g)
	{
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(Color.GREEN);
		g2.fill(box);
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
						 KeyEvent.VK_X};
		return buttons;
	}
}
