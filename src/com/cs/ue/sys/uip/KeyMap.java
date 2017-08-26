package com.cs.ue.sys.uip;

import org.lwjgl.input.Keyboard;

public class KeyMap
{
	private int keyID = -1;
	public boolean isKeyDown = false;
	public boolean isKeyPressed = false;
	public boolean isKeyReleased = false;
	
	public KeyMap(int keyID)
	{
		setKeyID(keyID);
	}
	
	public int getKeyID()
	{
		return keyID;
	}
	
	public void setKeyID(int keyID)
	{
		this.keyID = keyID;
	}
	
	public void update()
	{
		if(keyID > -1)
		{
			isKeyDown = Keyboard.isKeyDown(keyID);
		}
		else isKeyDown = false;
	}
}
