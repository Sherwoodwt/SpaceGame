import java.awt.geom.*;
import java.awt.*;

public class VaporTrail implements Drawable{
	
	private static final int MAX_COUNT=4;
	private static final double WIDTH=2, HEIGHT=2;
	
	public static final int DEAD = 0, ALIVE = 1;
	
	private int counter;
	private Rectangle2D.Double box;

	public VaporTrail(Point2D.Double location)
	{
		box = new Rectangle2D.Double(location.x, location.y, WIDTH, HEIGHT);
		counter = 0;
	}
	
	public int update()
	{
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
}
