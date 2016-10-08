public class ButtonManager {

	private boolean[] isPressed;
	private int[] keyCodes;
	
	public ButtonManager(int[] keyCodes)
	{
		this.keyCodes = keyCodes;
		isPressed = new boolean[keyCodes.length];
	}
	
	public void keyDown(int keyCode)
	{
		for(int i = 0; i < keyCodes.length; i++)
		{
			if(keyCodes[i] == keyCode)
			{
				isPressed[i] = true;
				break;
			}
		}
	}
	
	public void keyUp(int keyCode)
	{
		for(int i = 0; i < keyCodes.length; i++)
		{
			if(keyCodes[i] == keyCode)
			{
				isPressed[i] = false;
				break;
			}
		}
	}
	
	public boolean isDown(int index)
	{
		return isPressed[index];
	}
}
