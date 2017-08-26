package com.cs.ue.util.texture;

import org.newdawn.slick.opengl.Texture;

/**
 * UTexturePack class<br>
 * UTexturePack class handles single texture pack, regardless of its size.
 * @author Charlie Shin
 *
 */
public class UTexturePack
{
	public int ID = -1;
	private int tilesetSize;
	private Texture texture;
	private float resolution;
	
	public UTexturePack(Texture texture, float resolution, int tilesetSize)
	{
		setTexture(texture);
		setResolution(resolution);
		setTilesetSize(tilesetSize);
		
		ID = getTexture().getTextureID();
	}
	
	public float getX(int id)
	{
		if(id >= 0 || id < tilesetSize)
			return id % (float) texture.getTextureWidth();
		else return -1;
	}
	
	public float getY(int id)
	{
		if(id >= 0 || id < tilesetSize)
			return id / (float) texture.getTextureWidth();
		else return -1;
	}
	
	public int getTilesetSize()
	{
		return tilesetSize;
	}

	public void setTilesetSize(int tilesetSize)
	{
		this.tilesetSize = tilesetSize;
	}

	public Texture getTexture()
	{
		return texture;
	}
	
	public void setTexture(Texture texture)
	{
		this.texture = texture;
	}
	
	public float getResolution()
	{
		return resolution;
	}
	
	public void setResolution(float resolution)
	{
		this.resolution = resolution;
	}
	
	public void removePack()
	{
		texture.release();
	}
}
