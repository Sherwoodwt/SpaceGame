import java.awt.geom.*;
import java.awt.*;

public class VaporTrail extends SpaceObject {
	
	private static final int MAX_COUNT=12;
	
	private int counter;

	public VaporTrail(Rectangle2D.Double box, double angle, Dimension screen)
	{
		super(box, 0, screen);
		setupImages();
	}
	
	public int update()
	{
		super.update();
		counter++;
		if(counter > MAX_COUNT)
			return DEAD;
		return ALIVE;
	}
	
	public void draw(Graphics g)
	{
		int value = (255/20) * (counter-1);
		g.setColor(new Color(value, value, value));
		g.fillRect((int)box.x, (int)box.y, (int)box.width, (int)box.height);
	}
	
	protected void setupImages()
	{
		picture = null;
	}
	
	protected void applyAcceleration()
	{
		
	}
}
