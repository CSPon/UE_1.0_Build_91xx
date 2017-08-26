package com.cs.ue;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;

import java.nio.FloatBuffer;
import java.util.Random;

import org.lwjgl.BufferUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import com.cs.ue.disp.UScreen;
import com.cs.ue.sys.UManager;
import com.cs.ue.sys.uip.InputManager;
import com.cs.ue.sys.uip.UserKeyboard;
import com.cs.ue.util.texture.UTexture;

/**
 * Uniform Engine Version 1.0.91<br>
 * @see VersionLog
 * @author Charlie Shin
 *
 */
public abstract class UniformEngine
{
	// Move view setting and render setting to screen
	public static final String VERSION = "Uniform Engine 1 NX (v1.0.9100)";
	
	public static final int KEY_ESCAPE = Keyboard.KEY_ESCAPE;
	public static final int KEY_RETURN = Keyboard.KEY_RETURN;
	public static final int KEY_BACKSPACE = Keyboard.KEY_BACK;
	public static final int KEY_SPACE = Keyboard.KEY_SPACE;
	public static final int KEY_UP = Keyboard.KEY_UP;
	public static final int KEY_DOWN = Keyboard.KEY_DOWN;
	public static final int KEY_LEFT = Keyboard.KEY_LEFT;
	public static final int KEY_RIGHT = Keyboard.KEY_RIGHT;
	public static final int KEY_LSHIFT = Keyboard.KEY_LSHIFT;
	public static final int KEY_RSHIFT = Keyboard.KEY_RSHIFT;
	public static final int KEY_LCONTROL = Keyboard.KEY_LCONTROL;
	public static final int KEY_RCONTROL = Keyboard.KEY_RCONTROL;
	public static final int KEY_F1 = Keyboard.KEY_F1;
	public static final int KEY_F2 = Keyboard.KEY_F2;
	public static final int KEY_F3 = Keyboard.KEY_F3;
	public static final int KEY_F4 = Keyboard.KEY_F4;
	public static final int KEY_F5 = Keyboard.KEY_F5;
	public static final int KEY_F6 = Keyboard.KEY_F6;
	public static final int KEY_F7 = Keyboard.KEY_F7;
	public static final int KEY_F8 = Keyboard.KEY_F8;
	public static final int KEY_F9 = Keyboard.KEY_F9;
	public static final int KEY_F10 = Keyboard.KEY_F10;
	public static final int KEY_F11 = Keyboard.KEY_F11;
	public static final int KEY_F12 = Keyboard.KEY_F12;
	
	public UScreen display;
	public UManager manager;
	public static UTexture textures = new UTexture();
	public static UserKeyboard inputs;
	
	public static InputManager input;
	
	// Debug Call
	private boolean devmode = false;
	private int logicCalled, deltaLogicCalled;
	private int noiseBufferID;
	
	public static int scrWidth, scrHeight;
	
	/**
	 * Creates screen and displays it.<br><br>
	 * In order to have specified screen, parameter must contains 7 parameters<br>
	 * <b>First</b> parameter determines full screen mode. This is boolean value.<br>
	 * <b>Second</b> parameter determines screen width. This is short value.<br>
	 * <b>Third</b> parameter determines screen ratio(ie. 16:9). This is float value.<br>
	 * <b>Fourth</b> parameter determines desired frame rate(or target frame rate). This is byte value.<br>
	 * <b>Fifth</b> parameter determines option for vertical sync. This is boolean value.<br>
	 * <b>Sixth and Seventh</b> parameter determines starting position of the window. These are short values.<br>
	 * X and Y position of the window is ignored if screen is set to full screen mode.<br>
	 * If there is no specified parameters, screen will be adjusted to 1024*576 with 60fps, full screen disabled.<br><br>
	 * <b>TIPS ON HOW TO SET SCREEN RATIO</b><br>
	 * To set screen ratio, divide height ratio by width ratio.<br>
	 * For example, 16:9 screen ratio will be determined by dividing 9 by 16(0.5625).<br>
	 * @param args arguments passed from .bat file.
	 */
	public void init(String[] args)
	{
		if(args.length > 0 && args.length < 8)
		{
			boolean fullscreen = Boolean.valueOf(args[0]);
			short width = Short.valueOf(args[1]);
			float ratio = Float.valueOf(args[2]);
			byte rate = Byte.valueOf(args[3]);
			boolean vsync = Boolean.valueOf(args[4]);
			short xPos = Short.valueOf(args[5]);
			short yPos = Short.valueOf(args[6]);
			
			display = new UScreen(width, ratio, vsync, xPos, yPos);
			display.setTitle(VERSION);
			display.setDisplayMode(display.setFullScreen(fullscreen));
			
			manager = new UManager(display.getWidth(), display.getHeight());
			manager.setTargetRate(rate);
			
			scrWidth = display.getWidth();
			scrHeight = display.getHeight();
			
		}
		else
		{
			display = new UScreen((short) 1024, 0.5625f, true, (short) 8, (short) 8);
			display.setTitle(VERSION);
			display.setFullScreen(false);
			
			manager = new UManager(display.getWidth(), display.getHeight());
			manager.setTargetRate((byte) 60);
			
			scrWidth = display.getWidth();
			scrHeight = display.getHeight();
		}
	}
	
	/**
	 * Performs internal process which developer does not need to perform. 
	 */
	protected void run()
	{
		while(manager.isRunning())
		{
			preRender();
			
			logicCheck();
			input();
			update();
			render();
			
			postRender();
		}
		cleanup();
		display.destroy();
		System.exit(0);
	}
	
	/**
	 * Updates keyboard/mouse related variables.<br>
	 * Call this method before render() method.
	 */
	private void input()
	{
		input.poll();
		//manager.poll();
		keyboard();
		mouse();
	}
	
	/**
	 * Updates graphics-related variables.<br>
	 * Call this method before render() method.
	 */
	public abstract void update();
	
	/**
	 * Updates internal calculations.<br>
	 * <b>IMPORTANT!</b> Do not use this method to run gfx-related updates.<br>
	 * Method is called per-frame wise.
	 */
	public abstract void logic();
	
	/**
	 * Update internal calculations.<br>
	 * <b>IMPORTANT!</b> Do not use this method to run gfx-related updates.<br>
	 * Method is called per-second wise.
	 */
	public abstract void deltaLogic();
	
	/**
	 * Handles keyboard actions.<br>
	 * Game developers must handle this part since Uniform Engine only supports rendering.
	 */
	public abstract void keyboard();
	
	/**
	 * Handles mouse actions.<br>
	 * Game developers must handle this part since Uniform Engine only supports rendering.
	 */
	public abstract void mouse();
	
	/**
	 * Cleans up before destroying screen.<br>
	 * Cleanup() method must be called before destroy() method and after run() loop.
	 */
	public abstract void cleanup();
	
	/**
	 * Renders graphics data into screen.
	 */
	public abstract void render();
	
	public boolean renderDevMode()
	{
		if(devmode)
		{
			String data = "*Developer Mode(Demonstration) " + VERSION + "*\n" +
					"" + getFPS() + " Delta : " + manager.getDelta() + "\n" + 
					"" + getUsage() + "\n" +
					"" + "Logic Count : " + logicCalled + "\n" + "Delta Count : " + deltaLogicCalled;
		
			glBlendFunc(GL_ONE_MINUS_DST_COLOR, GL_ONE_MINUS_SRC_COLOR);
			renderString(data.toString(), 0, 0, 16);
			glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
			
			renderNoise();
		}
		
		return devmode;
	}
	
	/**
	 * Creates screen.<br>
	 * Creates screen then starts the thread.
	 */
	public void create()
	{
		display.create();
		
		input = new InputManager(display.getWidth(), display.getHeight());
		
		//manager.initInput();
		//inputs = manager.getKeyHandler();
		manager.startClock();
		
		manager.start();
		
		textures = new UTexture();
		//textures.addPack("lib/font.png", "png", "font", 16, 256);
		textures.addPack("lib/font.bmp", "bmp", "font", 32, 512);
		textures.addPack("lib/tex/texturepack.png", "png", "default", 16, 256);
		
		noiseBufferID = glGenBuffers();
	}
	
	/**
	 * Checks if there is another processor available for multi-threading.
	 * @return true if processor is available, false if not.
	 */
	public int isThreadAvailable()
	{
		return Runtime.getRuntime().availableProcessors();
	}
	
	/**
	 * Performs pre-render, such as screen cleaning and delta-calculations.
	 */
	public void preRender()
	{
		manager.calcDelta();
		manager.clear();
	}
	
	private int delta = 0;
	private void logicCheck()
	{
		int deltaTotal = 0;
		if(isThreadAvailable() > 1)
		{
			deltaTotal = isThreadAvailable() * (int)manager.getFramePerSecond();
			for(int i = 0; i < isThreadAvailable(); i ++)
			{
				new Logic().run();
			}
		}
		else
		{
			logic(); logicCalled++;
			deltaTotal = (int)manager.getFramePerSecond();
			delta++;
		}
		
		if(delta > deltaTotal)
		{
			deltaLogic();
			deltaLogicCalled++;
			delta = 0;
		}
	}
	
	/**
	 * Performs post-render, such as frame sync and memory usage check.
	 */
	public void postRender()
	{
		glColor3f(1.f, 1.f, 1.f);
		renderDevMode();
		
		renderPointer(1f, 1f, 1f);
		
		Display.update();
		if(manager.getRate() > 0)
			Display.sync(manager.getRate());
		
		manager.getFPS();
		manager.getMemory();
	}
	
	/**
	 * Gets frame rate as String data.
	 * @return String data of frame rate.
	 */
	public String getFPS()
	{
		return manager.getFPSString();
	}
	
	/**
	 * Gets memory usage as String data.
	 * @return String data of memory usage.
	 */
	public String getUsage()
	{
		return manager.devMode();
	}
	
	/**
	 * Renders string onto the screen on desired position.
	 * @param string String to be rendered.
	 * @param x integer value of x position.
	 * @param y integer value of y position.
	 * @param size integer value of font size.
	 */
	public static void renderString(Object string, int x, int y, int size)
	{
		String[] lines = string.toString().split("\n");
		
		for(int i = 0; i < lines.length; i++)
		{
			char[] line = lines[i].toCharArray();
			int accum = 0;
			
			glPushMatrix();
			glTranslatef(-(scrWidth / 2), -(scrHeight / 2), 0);
			textures.loadPack("font");
			for(char c : line)
			{
				glPushMatrix();
				glTranslatef(x + accum, y + (i * size), 0);
				
				float startX = ((int)c % 16) * 8;
				float endX = startX + 8;
				
				float startY = ((int)c / 16) * 8;
				float endY = startY + 8;
				
				startX /= 128;
				startY /= 128;
				endX /= 128;
				endY /= 128;
				
				glBegin(GL_QUADS);			
					glTexCoord2f(startX, startY);
					glVertex3i(0, 0, 0);
					
					glTexCoord2f(endX, startY);
					glVertex3i(size, 0, 0);
					
					glTexCoord2f(endX, endY);
					glVertex3i(size, size, 0);
					
					glTexCoord2f(startX, endY);
					glVertex3i(0, size, 0);
				glEnd();
				glPopMatrix();
				
				accum += (size / 2);
			}
			textures.unloadPack();
			glPopMatrix();
		}
	}
	
	// From UDisplay
	public void setTitle(String title)
	{
		display.setTitle(title);
	}
	public void setFullScreen(boolean fullscreen)
	{
		display.setFullScreen(fullscreen);
	}
	/**
	 * @see UScreen#setDepthBuffer(float, float)
	 */
	public void setDepthBuffer(float zNear, float zFar)
	{
		display.setDepthBuffer(zNear, zFar);
	}
	public short getScrWidth()
	{
		return display.getWidth();
	}
	public short getScrHeight()
	{
		return display.getHeight();
	}
	public void setBackgroundColor(float r, float g, float b)
	{
		display.setBackgroundColor(r, g, b);
	}
	
	// From Manager
	protected void stop()
	{
		manager.stop();
	}
	public float getDelta()
	{
		return this.manager.getDelta();
	}
	public UManager getInput()
	{
		return this.manager;
	}
	
	private class Logic extends Thread
	{
		public void run()
		{
			if(devmode) bufferNoise();
			
			delta++;
			logicCalled++;
			logic();
		}
	}
	
	/**
	 * Renders default mouse pointer to screen using immediate method.<br>
	 * If there is any other mouse pointer to render, override this default method.
	 * @param r float value of red components.
	 * @param g float value of green components.
	 * @param b float value of blue components.
	 */
	public void renderPointer(float r, float g, float b)
	{
		glBlendFunc(GL_ONE_MINUS_DST_COLOR, GL_ZERO);
		
		glPushMatrix();
		//glTranslatef(manager.getMouseX(), manager.getMouseY(), 0);
		glTranslatef((float) input.mouse.pos.getI(), (float) input.mouse.pos.getJ(), 0);
		
		glBindTexture(GL_TEXTURE_2D, 0);
		glColor3f(r, g, b);
		glBegin(GL_LINES);
		{
			for(int i = -1; i <=1; i++)
			{
				glVertex2i(i, -16); glVertex2i(i, 16);
				glVertex2i(-16, i); glVertex2i(16, i);
			}
		}
		glEnd();
		glPopMatrix();
		
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	}
	
	public void enableDevMode()
	{
		devmode = (!devmode) ? true : false;
	}
	
	public float debugNoise_grain = 0.0f;
	private int grainTotal;
	
	private void bufferNoise()
	{
		//long start = System.currentTimeMillis();
		Random rnd = new Random();
		if(debugNoise_grain > 1) debugNoise_grain = 1;
		else if(debugNoise_grain < 0) debugNoise_grain = 0;
		
		grainTotal = (int) (debugNoise_grain * getScrWidth() * getScrHeight());
		
		FloatBuffer buffer = BufferUtils.createFloatBuffer(grainTotal * 3 * 3);
		
		for(int i = 0; i < grainTotal; i++)
		{
			buffer.put(rnd.nextInt(getScrWidth()) - (getScrWidth() / 2)); // x
			buffer.put(rnd.nextInt(getScrHeight()) - (getScrHeight() / 2)); // y
			buffer.put(0); // z
			
			buffer.put(rnd.nextFloat() % 1); // r
			buffer.put(rnd.nextFloat() % 1); // g
			buffer.put(rnd.nextFloat() % 1); // b
		}
		
		buffer.flip();
		glBindBuffer(GL_ARRAY_BUFFER, noiseBufferID);
		glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
		
		//glBindBuffer(GL_ARRAY_BUFFER, 0);
		//System.out.println("Duration: " + (System.currentTimeMillis() - start) + "ms");
	}
	
	public void renderNoise()
	{
		glPushMatrix();
		{
			glTranslatef(0, 0, 0);
			glEnableClientState(GL_VERTEX_ARRAY);
			glEnableClientState(GL_COLOR_ARRAY);
			{
				glBindBuffer(GL_ARRAY_BUFFER, noiseBufferID);
				
				glVertexPointer(3, GL_FLOAT, 0, 0);
				glColorPointer(3, GL_FLOAT, 3, 0);
				
				glDrawArrays(GL_POINTS, 0, grainTotal * 3 * 3);
				
				glBindBuffer(GL_ARRAY_BUFFER, 0);
			}
			glDisableClientState(GL_COLOR_ARRAY);
			glDisableClientState(GL_VERTEX_ARRAY);
		}
		glPopMatrix();
	}
}
