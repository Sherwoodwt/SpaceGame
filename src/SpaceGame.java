import java.util.Timer;

public class SpaceGame {

	public static final int FRAME_RATE = 60;
	public static final int SKIP_TICKS = 1000 / FRAME_RATE;
	public static final int MAX_SKIP = 5;
	
	private static GameBox box;
	private static boolean isRunning;
	private static Timer timer;
	
	public static void main(String[] args)
	{
		box = new GameBox();
		isRunning = true;
		timer = new Timer();
		timer.schedule(new Loop(), 0, 1000/60);
	}
	
	private static class Loop extends java.util.TimerTask
	{
		@Override
		public void run() {
			box.update();
			box.draw();
			
			if(!isRunning)
			{
				timer.cancel();
			}
		}
	}
}
