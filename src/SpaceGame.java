
public class SpaceGame {

	public static final int FRAME_RATE = 60;
	
	public static void main(String[] args)
	{
		GameBox box = new GameBox();
		boolean running = true;
		while(running)
		{
			box.update();
			box.draw();
			try
			{
				Thread.sleep(1000/FRAME_RATE);
			}
			catch(Exception e)
			{
				System.err.println(e.getMessage());
			}
		}
	}
}
