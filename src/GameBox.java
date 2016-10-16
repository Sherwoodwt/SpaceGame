import javax.swing.*;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

public class GameBox extends JPanel{

	private static final int WIDTH=1000, HEIGHT=600;
	
	private Ship ship;
	
	public GameBox()
	{
		super();
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.setFocusable(true);
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		ship = new Ship(new Rectangle2D.Double(200, 200, 20, 20), "resource/greenShip.png");
		this.addKeyListener(new ButtonListener());
		
		frame.getContentPane().add(this);
		frame.pack();
		frame.setVisible(true);
	}
	
	@Override
	public void update(Graphics g)
	{
		ship.update();
	}
	
	@Override
	public void paint(Graphics g)
	{
		BufferedImage bfImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		Graphics bg = bfImage.getGraphics();
		this.update(bg);
		g.setColor(Color.BLACK);
		g.fillRect(0,  0, WIDTH, HEIGHT);
		ship.draw(bg);
		g.drawImage(bfImage, 0, 0, WIDTH, HEIGHT, null);
		repaint();
	}

	
	private class ButtonListener implements KeyListener
	{
		public void keyPressed(KeyEvent e)
		{
			ship.keyPressed(e.getKeyCode());
		}
		
		public void keyReleased(KeyEvent e)
		{
			ship.keyReleased(e.getKeyCode());
		}
		
		public void keyTyped(KeyEvent e){}
	}
}
