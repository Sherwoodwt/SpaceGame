
public class SpaceGame {

	public static final int FRAME_RATE = 60;
	public static final int SKIP_TICKS = 1000 / FRAME_RATE;
	public static final int MAX_SKIP = 5;
	
	public static void main(String[] args)
	{
		GameBox box = new GameBox();
		
		boolean running = true;
		int numberLoops = 0;
		double interpolation = 0;
		long time = System.nanoTime();
		
		while(running)
		{
			numberLoops = 0;
			
			/*
			 * update and get user input. No more than MAX_SKIP times in a row,
			 * and you only do it every 20 milliseconds (1000/60).
			 * 
			 * Basically, if 20 milliseconds hasn't gone by, just draw again.
			 * If 20 milliseconds has gone by, just update again, but only 5 times before a draw is
			 * required.
			 */
			while(System.nanoTime() > time && numberLoops < MAX_SKIP)
			{
				box.update();
				try{
					Thread.sleep(1);
				}catch(Exception e){
					System.err.println("game broken");
				}
				
				time += SKIP_TICKS;
				numberLoops++;
			}
			
			/*
			 * interpolation is a value to draw the inbetween-update position of objects.
			 * Current time - time is less than 0, but absolute value is less than SKIP_TICKS, so
			 * interpolation is basically what portion of SKIP_TICKS amount of time has passed.
			 */
			interpolation = (double)((System.nanoTime() -time) + SKIP_TICKS)/((double)(SKIP_TICKS));
			box.draw(interpolation);
		}
	}
}
