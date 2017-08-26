package com.cs.ue.sys.uip;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;

import com.cs.ue.util.math.vector.Vector;

public class UserMouse
{
	/*public Vector2f pos;
	public Vector2f delta;*/
	
	public Vector pos;
	public Vector delta;
	
	public int button;
	
	private short width, height;
	
	private KeyMap left, right;
	
	public UserMouse(short width, short height)
	{
		setWidth(width);
		setHeight(height);
		
		try
		{
			Mouse.create();
			Mouse.setGrabbed(true);
			
			pos = new Vector();
			delta = new Vector();
			
			button = -1;
			
			left = new KeyMap(0);
			right = new KeyMap(1);
		}
		catch (LWJGLException e)
		{
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public void setCursorPos(int x, int y)
	{
		pos.setI(x);
		pos.setJ(y);
		//Mouse.setCursorPosition(x, y);
	}
	
	public void poll()
	{	
		left.isKeyDown = false; left.isKeyPressed = false; left.isKeyReleased = false;
		right.isKeyDown = false; right.isKeyPressed = false; right.isKeyReleased = false;
		
		if(Mouse.isButtonDown(left.getKeyID()))
		{
			left.isKeyDown = true;
			left.isKeyPressed = false;
			left.isKeyReleased = false;
		}
		
		if(Mouse.isButtonDown(right.getKeyID()))
		{
			right.isKeyDown = true;
			right.isKeyPressed = false;
			right.isKeyReleased = false;
		}
		
		Mouse.poll();
		
		while(Mouse.next())
		{
			int button = Mouse.getEventButton();
			
			if(button == left.getKeyID())
			{
				if(Mouse.getEventButtonState())
				{
					left.isKeyDown = false;
					left.isKeyPressed = true;
					left.isKeyReleased = false;
				}
				else
				{
					left.isKeyDown = false;
					left.isKeyPressed = false;
					left.isKeyReleased = true;
				}
			}
			
			if(button == right.getKeyID())
			{
				if(Mouse.getEventButtonState())
				{
					right.isKeyDown = false;
					right.isKeyPressed = true;
					right.isKeyReleased = false;
				}
				else
				{
					right.isKeyDown = false;
					right.isKeyPressed = false;
					right.isKeyReleased = true;
				}
			}
		}
		
		pos.setI(Mouse.getX() - (width / 2));
		pos.setJ((height / 2) - Mouse.getY());
		delta.setI(Mouse.getDX() - (width / 2));
		delta.setJ((height / 2) - Mouse.getDY());
		button = Mouse.getEventButton();
	}
	
	public boolean isButtonDown(int button)
	{
		if(button == left.getKeyID())
			return left.isKeyDown;
		else if(button == right.getKeyID())
			return right.isKeyDown;
		else return false;
	}
	
	public boolean isButtonPressed(int button)
	{
		if(button == left.getKeyID())
			return left.isKeyPressed;
		else if(button == right.getKeyID())
			return right.isKeyPressed;
		else return false;
	}
	
	public boolean isButtonReleased(int button)
	{
		if(button == left.getKeyID())
			return left.isKeyReleased;
		else if(button == right.getKeyID())
			return right.isKeyReleased;
		else return false;
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
