import javax.swing.*;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.geom.Rectangle2D.Double;

public class GameBox extends JPanel{

	private static final int WIDTH=1000, HEIGHT=600;
	
	private Ship ship1;
	private Ship ship2;
	
	public GameBox()
	{
		super();
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.setFocusable(true);
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		ship1 = new Ship(new Rectangle2D.Double(200, 200, 20, 20), "resource/greenShip.png", "resource/player1config.cnfg");
		ship2 = new Ship(new Rectangle2D.Double(400, 400, 20, 20), "resource/blueShip.png", "resource/player2config.cnfg");
		
		this.addKeyListener(new ButtonListener());
		
		frame.getContentPane().add(this);
		frame.pack();
		frame.setVisible(true);
	}
	
	@Override
	public void update(Graphics g)
	{
		ship1.update();
		ship2.update();
	}
	
	@Override
	public void paint(Graphics g)
	{
		BufferedImage bfImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		Graphics bg = bfImage.getGraphics();
		this.update(bg);
		g.setColor(Color.BLACK);
		g.fillRect(0,  0, WIDTH, HEIGHT);
		ship1.draw(bg);
		ship2.draw(bg);
		g.drawImage(bfImage, 0, 0, WIDTH, HEIGHT, null);
		repaint();
	}

	
	private class ButtonListener implements KeyListener
	{
		public void keyPressed(KeyEvent e)
		{
			ship1.keyPressed(e.getKeyCode());
			ship2.keyPressed(e.getKeyCode());
		}
		
		public void keyReleased(KeyEvent e)
		{
			ship1.keyReleased(e.getKeyCode());
			ship2.keyReleased(e.getKeyCode());
		}
		
		public void keyTyped(KeyEvent e){}
	}
}
