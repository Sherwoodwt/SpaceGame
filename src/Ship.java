import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.*;
import java.awt.geom.Point2D.Double;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class Ship extends SpaceObject{

	public static final int FORWARD=0, BACKWARD=1, LEFT=2, RIGHT=3, SHOOT=4;
	private static int MAX_BULLETS = 5, SHOOT_LIMIT = 20;
	
	protected double acceleration;
	protected ArrayList<Bullet> bullets;
	private int shootCounter;
	private boolean shootReady;
	protected ButtonManager buttons;
	
	
	public Ship(Rectangle2D.Double box, String imageFile, String controlFile)
	{
		super(box, 1.5, imageFile);
		bullets = new ArrayList<Bullet>();
		buttons = new ButtonManager(setupButtons(controlFile));
	}
	
	@Override
	public int update()
	{
		super.update();
		handleInputs();
		if(shootCounter < SHOOT_LIMIT)
			shootCounter++;
		ArrayList<Bullet> shitlist = new ArrayList<Bullet>();
		for(Bullet bullet : bullets)
		{
			int result = bullet.update();
			if(result == 0)
				shitlist.add(bullet);
		}
		for(Bullet bullet : shitlist)
		{
			bullets.remove(bullet);
		}
		shitlist = null;
		return 0;
	}
	
	@Override
	public void draw(Graphics g)
	{
		for(Bullet bullet : bullets)
		{
			bullet.draw(g);
		}
		super.draw(g);
	}
	
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
	
	protected void forward()
	{
		acceleration = INCREMENT;
	}
	
	protected void backward()
	{
		acceleration = -INCREMENT;
	}
	
	protected void rotateCounterClockwise()
	{
		angle -= ROTATION_INC;
	}
	
	protected void rotateClockwise()
	{
		angle += ROTATION_INC;
	}
	
	protected void shoot()
	{
		shootReady = false;
		if(bullets.size() < MAX_BULLETS)
		{
			Point2D.Double bulletLocation = new Point2D.Double(box.x + box.width/2, box.y + box.height/2);
			rotatePoint(bulletLocation);
			Bullet newBullet = new Bullet(new Rectangle2D.Double(bulletLocation.x, bulletLocation.y, box.width/10, box.height/10), angle);
			bullets.add(newBullet);
			shootCounter = 0;
		}
	}
	
	private boolean canShoot()
	{
		return shootCounter >= SHOOT_LIMIT && shootReady;
	}
	
	private void handleInputs()
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
		if(buttons.isDown(SHOOT) && canShoot())
			shoot();
		else if(!buttons.isDown(SHOOT))
			shootReady = true;
	}
	
	/*
	 * Later will read from config file
	 */
	private int[] setupButtons(String controlFile)
	{
		Scanner fin = null;
		int[] buttons = new int[5];
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
	
	public void keyPressed(int key)
	{
		buttons.keyDown(key);
	}
	
	public void keyReleased(int key)
	{
		buttons.keyUp(key);
	}
}
