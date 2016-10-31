import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.geom.Rectangle2D.Double;

public class GameBox extends JPanel{

	private static final int WIDTH=1000, HEIGHT=600;
	private static final int PLAY=0, PAUSE=1, SCORE=2;
	private static final int SHIP_KILL_LIMIT=50, SHOW_SCORE_LIMIT=200;
	
	private Dimension screenDimensions;
	private Ship ship1;
	private Ship ship2;
	private Asteroid asteroid;
	private int state;
	private int score1, score2;
	
	private int shipKilledCounter, showScoreCounter;
	
	public GameBox()
	{
		super();
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.setFocusable(true);
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		screenDimensions = new Dimension(WIDTH, HEIGHT);
		resetShips();
		
		this.addKeyListener(new ButtonListener());
		
		frame.getContentPane().add(this);
		frame.pack();
		frame.setVisible(true);
	}
	
	public void update()
	{
		if(state == PLAY)
		{
			int state1 = ship1.update();
			int state2 = ship2.update();
			asteroid.update();
			if(state1 == Ship.DEAD && state2 == Ship.ALIVE)
			{
				score2 += shipKilled();
			}
			else if(state2 == Ship.DEAD && state1 == Ship.ALIVE)
			{
				score1 += shipKilled();
			}
			else if(state1 == Ship.DEAD || state2 == Ship.DEAD)
			{
				shipKilled();
			}
		}
		else if(state == SCORE)
		{
			showScore();
		}
	}
	
	public void draw()
	{
		Graphics g = this.getGraphics();
		BufferedImage bfImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		Graphics bg = bfImage.getGraphics();
		this.update(bg);
		bg.setColor(Color.BLACK);
		bg.fillRect(0,  0, WIDTH, HEIGHT);
		if(state == PLAY)
		{
			ship1.draw(bg);
			ship2.draw(bg);
			asteroid.draw(bg);
		}
		else if(state == PAUSE)
		{
			bg.setColor(Color.WHITE);
			bg.drawString("Press ESCAPE to unpause", WIDTH/2, HEIGHT/2);
			bg.drawString("Press Q to quit", WIDTH/2, HEIGHT/2 + 40);
		}
		else if(state == SCORE)
		{
			bg.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));
			bg.setColor(Color.GREEN);
			bg.drawString("Green: " + score1, WIDTH/4, HEIGHT/2);
			bg.setColor(Color.BLUE);
			bg.drawString("Blue: " + score2, WIDTH/3 * 2, HEIGHT/2);
		}
		g.drawImage(bfImage, 0, 0, WIDTH, HEIGHT, null);
	}
	
	private void pause()
	{
		if(state == PLAY)
			state = PAUSE;
		else if(state == PAUSE)
			state = PLAY;
	}
	
	private int shipKilled()
	{
		int scoreIncrease = 0;
		if(shipKilledCounter < SHIP_KILL_LIMIT)
		{
			shipKilledCounter++;
		}
		else
		{
			shipKilledCounter = 0;
			state = SCORE;
			scoreIncrease++;
		}
		return scoreIncrease;
	}
	
	private void showScore()
	{
		if(showScoreCounter < SHOW_SCORE_LIMIT)
		{
			showScoreCounter++;
		}
		else
		{
			showScoreCounter = 0;
			resetShips();
			state = PLAY;
		}
	}
	
	private void resetShips()
	{
		ship1 = new GreenShip(new Rectangle2D.Double(WIDTH/4, HEIGHT/2, 20, 20), "resource/player1config.cnfg", screenDimensions);
		ship2 = new BlueShip(new Rectangle2D.Double(WIDTH*3/4, HEIGHT/2, 20, 20), "resource/player2config.cnfg", screenDimensions);
		ship1.addEnemy(ship2);
		ship2.addEnemy(ship1);
		
		Random randy = new Random();
		asteroid = new Asteroid(new Rectangle2D.Double(randy.nextInt(WIDTH), randy.nextInt(HEIGHT), 50, 50), screenDimensions);
		asteroid.addEnemy(ship1);
		asteroid.addEnemy(ship2);
	}

	
	private class ButtonListener implements KeyListener
	{
		public void keyPressed(KeyEvent e)
		{
			if(state == PAUSE && e.getKeyCode() == KeyEvent.VK_Q)
			{
				System.exit(1);
			}
			if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
				pause();
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
