import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;


public abstract class Weapon extends SpaceObject{
	
	private int lifetime;
	private int maxLife;
	
	public Weapon(Rectangle2D.Double box, double angle, ArrayList<Ship> enemies, Dimension screen, int maxLife)
	{
		super(box, angle, screen);
		this.enemies = enemies;
		this.maxLife = maxLife;
	}
	
	public int update()
	{
		super.update();
		lifetime++;
		if(lifetime < maxLife)
			return ALIVE;
		return DEAD;
	}
}
