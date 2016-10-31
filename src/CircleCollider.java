
public class CircleCollider extends Collider {

	private double radius;
	
	public CircleCollider(SpaceObject owner, double radius) {
		super(owner);
		this.radius = radius;
	}

	@Override
	public boolean checkCollision(Ship enemy) {
		boolean collision = false;
		
		for(int i = 0; i < enemy.getNumberOfPoints(); i++)
		{
			if(this.getDistance(enemy.getPoint(i), owner.getCenter()) < radius)
			{
				collision = true;
				break;
			}
		}
		return collision;
	}

}
