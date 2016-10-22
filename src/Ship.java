import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.*;
import java.awt.geom.Point2D.Double;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public abstract class Ship extends SpaceObject{

	public static final int FORWARD=0, BACKWARD=1, LEFT=2, RIGHT=3, SHOOT=4, MISSLE=5;
	private static int MAX_BULLETS = 5, SHOOT_LIMIT = 20, MAX_MISSLES = 1;
	protected static int NEUTRAL=0, SHOOTING=1, BLOWUP=2;
	
	protected ArrayList<Weapon> weapons;
	private int shootCounter;
	private boolean shootReady;
	protected ButtonManager buttons;
	
	private int numMissles;
	
	protected int imageState;
	protected Image[] imageList;
	
	private String imageFileColor;
	
	
	public Ship(Rectangle2D.Double box, String controlFile, Dimension screen)
	{
		super(box, 1.5, screen);
		weapons = new ArrayList<Weapon>();
		buttons = new ButtonManager(setupButtons(controlFile));
		numMissles = 3;
	}
	
	@Override
	public int update()
	{
		if(state == ALIVE)
		{
			state = super.update();
			if(state == DEAD)
				this.hit();
			handleInputs();
			
			if(shootCounter < SHOOT_LIMIT)
				shootCounter++;
			else if(imageState == SHOOTING)
				imageState = NEUTRAL;
			
			updateWeapons();
			
		}
		return state;
	}
	
	@Override
	public void draw(Graphics g)
	{
		picture = imageList[imageState];
		for(Weapon weapon : weapons)
		{
			if(weapon.state != DEAD)
				weapon.draw(g);
		}
		super.draw(g);
	}
	
	@Override
	protected void applyAcceleration()
	{
		linearSpeed.x += acceleration * (Math.sin(angle));
		linearSpeed.y += acceleration * (Math.cos(angle));
	}
	
	protected void normalize()
	{
		acceleration = 0;
		linearSpeed.x = linearSpeed.x * .99;
		linearSpeed.y = linearSpeed.y * .99;
	}
	
	protected void updateWeapons()
	{
		ArrayList<Weapon> shitlist = new ArrayList<Weapon>();
		for(Weapon weapon : weapons)
		{
			int result = weapon.update();
			if(result == 0)
				shitlist.add(weapon);
		}
		for(Weapon weapon : shitlist)
		{
			weapons.remove(weapon);
		}
		shitlist = null;
	}
	
	protected void shoot(Weapon weapon)
	{
		shootReady = false;
		if(weapons.size() < MAX_BULLETS)
		{
			imageState = SHOOTING;
			weapons.add(weapon);
			shootCounter = 0;
		}
	}
	
	protected void shootBullet()
	{
		Point2D.Double bulletLocation = new Point2D.Double(box.x + box.width/2, box.y);
		rotatePoint(bulletLocation);
		Bullet newBullet = new Bullet(new Rectangle2D.Double(bulletLocation.x, bulletLocation.y, box.width/10, box.height/10), angle, enemies, screenDimensions);
		shoot(newBullet);
	}
	
	protected void shootMissle()
	{
		if(numMissles > 0)
		{
			Point2D.Double missleLocation = new Point2D.Double(box.x + box.width/2, box.y);
			rotatePoint(missleLocation);
			Missle newMissle = new Missle(new Rectangle2D.Double(missleLocation.x, missleLocation.y, box.width/2, box.height/2), angle, enemies, screenDimensions);
			shoot(newMissle);
			numMissles--;
		}
	}
	
	private boolean canShoot()
	{
		return shootCounter >= SHOOT_LIMIT && shootReady;
	}
	
	private void handleInputs()
	{
		if(buttons.isDown(FORWARD))
			accelerateForward();
		else if(buttons.isDown(BACKWARD))
			accelerateBackward();
		else
			normalize();
		if(buttons.isDown(LEFT))
			rotateCounterClockwise();
		else if(buttons.isDown(RIGHT))
			rotateClockwise();
		if(buttons.isDown(SHOOT) && canShoot())
			shootBullet();
		else if(buttons.isDown(MISSLE) && canShoot())
			shootMissle();
		else if(!buttons.isDown(SHOOT) && !buttons.isDown(MISSLE))
			shootReady = true;
	}
	
	private int[] setupButtons(String controlFile)
	{
		Scanner fin = null;
		int[] buttons = new int[6];
		try{
			fin = new Scanner(new File(controlFile));
			int counter = 0;
			while(fin.hasNext())
			{
				String line = fin.nextLine();
				String[] split = line.split(":");
				buttons[counter] = KeyEvent.getExtendedKeyCodeForChar(split[1].charAt(0));
				counter++;
			}
		} catch(Exception e){
			System.out.println("ERROR LOADING CONTROLS FROM "+controlFile);
		} finally{
			if(fin != null)
				fin.close();
		}
		return buttons;
	}
	
	@Override
	protected void setupCollider()
	{
		this.collider = new PointInterceptCollider(this);
	}
	
	@Override
	protected void setupPoints()
	{
		points = new Point2D.Double[3];
		for(int i = 0; i < points.length; i++)
			points[i] = new Point2D.Double();
	}
	
	@Override
	protected void updatePoints()
	{
		points[0].x = box.x+box.width/2;
		points[0].y = box.y;
		points[1].x = box.x;
		points[1].y = box.y+box.height;
		points[2].x = box.x+box.width;
		points[2].y = box.y+box.height;
	}
	
	public void keyPressed(int key)
	{
		buttons.keyDown(key);
	}
	
	public void keyReleased(int key)
	{
		buttons.keyUp(key);
	}
	
	public void hit()
	{
		state = DEAD;
		imageState = BLOWUP;
	}
}
