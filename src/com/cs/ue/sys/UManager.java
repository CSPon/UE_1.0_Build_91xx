package com.cs.ue.sys;

public class UManager extends SysManager
{
	public static final byte WORLD_ISO = 0xA;
	public static final byte WORLD_TDV = 0XB;
	public static final byte DRAW_RULE_DIRECT = 0xA;
	public static final byte DRAW_RULE_RANGED = 0XB;
	
	private boolean running;
	
	private byte rate;
	
	/**
	 * Font ID is a unique number which has been given to a font texture, which buffered in via TextureLoader class.<br>
	 */
	protected static int fontid;
	
	public UManager(final short width, final short height)
	{
		this.width = width;
		this.height = height;
	}
	
	public void setTargetRate(final byte rate)
	{
		this.rate = rate;
	}
	
	public void start()
	{
		running = true;
	}
	
	public void stop()
	{
		running = false;
	}
	
	public boolean isRunning()
	{
		return this.running;
	}
	
	public void calcDelta()
	{
		calculateDelta();
	}
	
	public byte getRate()
	{
		return this.rate;
	}
}
