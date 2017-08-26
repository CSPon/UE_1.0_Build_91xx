package com.cs.ue.code;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.input.Keyboard;

import com.cs.ue.UniformEngine;
import com.cs.ue.util.math.vector.Vector;

public class Sample extends UniformEngine
{
	private Vector clickPos;
	
	public static void main(String[] args)
	{
		new Sample(args);
	}
	
	public Sample(String[] args)
	{
		init(args);
		
		setBackgroundColor(0, 0, 0);
		setDepthBuffer(-5, 5);
		debugNoise_grain = 0.05f;
		
		create();
		
		enableDevMode();
		
		input.mouse.setCursorPos(getScrWidth() / 2, getScrHeight() / 2);
		
		input.keyboard.assignKey("devMode", Keyboard.KEY_F11);
		input.keyboard.assignKey("exit", Keyboard.KEY_ESCAPE);
		
		clickPos = new Vector(0, 0, 0);
		
		run();
	}
	
	@Override
	public void update()
	{
	}

	@Override
	public void logic()
	{
	}

	@Override
	public void deltaLogic()
	{
	}

	@Override
	public void keyboard()
	{
		if(input.keyboard.isKeyPressed("exit"))
			stop();
		if(input.keyboard.isKeyPressed("devMode"))
			enableDevMode();
	}

	@Override
	public void mouse()
	{
		if(input.mouse.isButtonPressed(0))
		{
			clickPos.setI(input.mouse.pos.getI());
			clickPos.setJ(input.mouse.pos.getJ());
			clickPos.setK(0);
		}
		
		if(input.mouse.isButtonDown(0))
		{
			clickPos.setI(input.mouse.pos.getI());
			clickPos.setJ(input.mouse.pos.getJ());
			clickPos.setK(0);
		}
	}

	@Override
	public void cleanup()
	{
		textures.cleanup();
	}

	@Override
	public void render()
	{	
		glPushMatrix();
		{
			glTranslatef(clickPos.getI(), clickPos.getJ(), 0);
			glBindTexture(GL_TEXTURE_2D, 0);
			glColor3f(1, 1, 1);
			
			glBegin(GL_TRIANGLES);
			{
				glVertex2f(-5f, 5f); glVertex2f(5f, -5f); glVertex2f(-5f, -5f);
				glVertex2f(5f, -5f); glVertex2f(-5f, 5f); glVertex2f(5f, 5f);
			}
			glEnd();
		}
		glPopMatrix();
	}
}
