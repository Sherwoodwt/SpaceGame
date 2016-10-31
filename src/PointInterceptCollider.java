import java.awt.geom.Point2D;


public class PointInterceptCollider extends Collider {

	public PointInterceptCollider(SpaceObject owner)
	{
		super(owner);
	}
	
	@Override
	public boolean checkCollision(Ship enemy)
	{
		boolean collision = false;
		
		for(int i = 0; i < owner.getNumberOfPoints(); i++)
		{
			if(this.checkCrossForPoint(owner.getPoint(i), enemy))
			{
				collision = true;
				break;
			}
		}
		return collision;
	}
}
