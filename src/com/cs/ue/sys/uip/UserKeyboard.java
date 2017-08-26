package com.cs.ue.sys.uip;

import java.util.HashMap;
import java.util.Map;

import org.lwjgl.input.Keyboard;

public class UserKeyboard
{
	private HashMap<String, KeyMap> assigned;
	
	private short width, height;
	
	public UserKeyboard(short width, short height)
	{
		setWidth(width);
		setHeight(height);
		
		try
		{
			Keyboard.create();
			Keyboard.enableRepeatEvents(true);
		}
		catch(Exception err)
		{
			err.printStackTrace();
			System.exit(1);
		}
		
		assigned = new HashMap<String, KeyMap>();
	}
	
	public void poll()
	{
		// Reset keys
		for(Map.Entry<String, KeyMap> E : assigned.entrySet())
		{
			E.getValue().isKeyDown = false;
			E.getValue().isKeyPressed = false;
			E.getValue().isKeyReleased = false;
		}
		
		// Check key down
		for(Map.Entry<String, KeyMap> E : assigned.entrySet())
		{
			if(Keyboard.isKeyDown(E.getValue().getKeyID()))
			{
				E.getValue().isKeyDown = true;
				E.getValue().isKeyPressed = false;
				E.getValue().isKeyReleased = false;
			}
		}
		
		Keyboard.poll();
		
		while(Keyboard.next())
		{
			int key = Keyboard.getEventKey();
			
			if(getKey(key) != null)
			{
				if(Keyboard.getEventKeyState())
				{
					if(!Keyboard.isRepeatEvent())
					{
						getKey(key).isKeyDown = false;
						getKey(key).isKeyPressed = true;
						getKey(key).isKeyReleased = false;
					}
				}
				else
				{
					getKey(key).isKeyDown = false;
					getKey(key).isKeyPressed = false;
					getKey(key).isKeyReleased = true;
				}
			}
		}
	}
	
	public boolean assignKey(String name, int target)
	{
		if(Keyboard.getKeyName(target) != null)
		{
			KeyMap newKey = new KeyMap(target);
			assigned.put(name, newKey);
			return true;
		}
		else return false;
	}
	
	public void removeKey(String name)
	{
		assigned.remove(name);
	}
	
	public boolean isKeyDown(String name)
	{
		return assigned.get(name).isKeyDown;
	}
	
	public boolean isKeyPressed(String name)
	{
		return assigned.get(name).isKeyPressed;
	}
	
	public boolean isKeyRelease(String name)
	{
		return assigned.get(name).isKeyReleased;
	}
	
	private KeyMap getKey(int key)
	{
		for(Map.Entry<String, KeyMap> E : assigned.entrySet())
			if(E.getValue().getKeyID() == key)
				return E.getValue();
		
		return null;
	}

	public short getWidth() {
		return width;
	}

	public void setWidth(short width) {
		this.width = width;
	}

	public short getHeight() {
		return height;
	}

	public void setHeight(short height) {
		this.height = height;
	}
}
