import javax.swing.*;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.awt.event.*;

public class GameBox extends JPanel{

	private static final int WIDTH=500, HEIGHT=500;
	
	private SpaceObject spaceObj;
	
	public GameBox()
	{
		super();
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.setFocusable(true);
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		spaceObj = new SpaceObject(new Rectangle(200, 200, 50, 50));
		this.addKeyListener(new ButtonListener());
		
		frame.getContentPane().add(this);
		frame.pack();
		frame.setVisible(true);
	}
	
	@Override
	public void update(Graphics g)
	{
		spaceObj.update();
	}
	
	@Override
	public void paint(Graphics g)
	{
		BufferedImage bfImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		Graphics bg = bfImage.getGraphics();
		this.update(bg);
		g.setColor(Color.BLACK);
		g.fillRect(0,  0, WIDTH, HEIGHT);
		spaceObj.draw(bg);
		g.drawImage(bfImage, 0, 0, WIDTH, HEIGHT, null);
		repaint();
	}

	
	private class ButtonListener implements KeyListener
	{
		public void keyPressed(KeyEvent e)
		{
			spaceObj.keyPressed(e.getKeyCode());
		}
		
		public void keyReleased(KeyEvent e)
		{
			spaceObj.keyReleased(e.getKeyCode());
		}
		
		public void keyTyped(KeyEvent e){}
	}
}
