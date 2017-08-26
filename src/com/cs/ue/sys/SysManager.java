package com.cs.ue.sys;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

import org.lwjgl.Sys;

/**
 * Handles both user inputs game times
 * @author Charlie Shin
 *
 */
public abstract class SysManager implements HandlerI
{
	protected float delta;
	
	// Frame Rate
	protected long lastfps, lastframe;
	protected long fps, max_fps, min_fps, cur_fps;
	protected String fpswatch;
	
	// Memory
	protected String memory;
	
	// Display Property
	protected short width, height;

	@Override
	public void clear()
	{
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}

	@Override
	public long getTime()
	{
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}

	@Override
	public float calculateDelta()
	{
		long time = getTime();
		delta = (time - lastframe);
		lastframe = time;
		
		return delta;
	}
	
	@Override
	public float getDelta()
	{
		return this.delta;
	}

	@Override
	public void startClock()
	{
		calculateDelta();
		lastfps = getTime();
	}

	@Override
	public void getFPS()
	{
		if(getTime() - lastfps > 1000)
		{
			fpswatch = "FPS: " + fps;
			cur_fps = fps;
			if(fps <= min_fps)
				min_fps = fps;
			if(fps > max_fps)
			{
				min_fps = max_fps;
				max_fps = fps;
			}
			fps = 0;
			lastfps += 1000;
		}
		fps++;
	}

	@Override
	public long getFramePerSecond()
	{
		return cur_fps;
	}

	@Override
	public String getFPSString()
	{
		return fpswatch;
	}

	@Override
	public void getMemory()
	{
		int maxMem = (int) (Runtime.getRuntime().maxMemory() / 1048576L);
		int totalMem = (int) (Runtime.getRuntime().totalMemory() / 1048576L);
		int freeMem = (int) (Runtime.getRuntime().freeMemory() / 1048576L);
		int usedMem = totalMem - freeMem;
		
		int usedPercentage = (int) (((float)usedMem / (float)totalMem) * 100);
		int freePercentage = (int) (((float)freeMem / (float)totalMem) * 100);
		int allocPercentage = (int) (((float)totalMem / (float)maxMem) * 100);
		
		memory = "Used Memory : " + usedMem + "MB (" + usedPercentage + "%) Free Memory : " + freeMem + "MB (" + freePercentage + "%)\n" +
				"Allocated : " + totalMem + "MB/" + maxMem + "MB (" + allocPercentage + "%)";
	}

	@Override
	public String devMode()
	{
		return memory;
	}
}
