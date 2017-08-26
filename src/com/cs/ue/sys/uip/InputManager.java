package com.cs.ue.sys.uip;

public class InputManager
{
	public UserKeyboard keyboard;
	public UserMouse mouse;
	
	public InputManager(short width, short height)
	{		
		keyboard = new UserKeyboard(width, height);
		mouse = new UserMouse(width, height);
	}
	
	public void poll()
	{
		pollKeys();
		pollMouse();
	}
	
	private void pollKeys()
	{	
		keyboard.poll();
	}
	
	private void pollMouse()
	{
		mouse.poll();
	}
}
