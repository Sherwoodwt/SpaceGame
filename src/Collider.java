import java.awt.geom.Point2D;


public abstract class Collider {

	protected SpaceObject owner;
	
	public Collider(SpaceObject owner)
	{
		this.owner = owner;
	}
	
	public abstract boolean checkCollision(Ship enemy);
	
	private boolean crossProduct(Point2D.Double myPoint, Point2D.Double enemyBase, Point2D.Double enemyEnd)
	{
		double[] myVec = new double[] {myPoint.x - enemyBase.x, myPoint.y - enemyBase.y};
		double[] enemyVec = new double[] {enemyEnd.x - enemyBase.x, enemyEnd.y - enemyBase.y};
		double cross = myVec[0] * enemyVec[1] - enemyVec[0] * myVec[1];
		return cross > 0;
	}
	
	protected boolean checkCrossForPoint(Point2D.Double myPoint, Ship enemy)
	{
		int counter = 0;
		for(int j = 0; j < enemy.getNumberOfPoints(); j++)
		{
			Point2D.Double enemyBase = enemy.getPoint(j);
			Point2D.Double enemyEnd = enemy.getPoint((j+1)%enemy.getNumberOfPoints());
			if(this.crossProduct(myPoint, enemyBase, enemyEnd))
			{
				counter++;
			}
		}
		return counter >= enemy.getNumberOfPoints();
	}
}
