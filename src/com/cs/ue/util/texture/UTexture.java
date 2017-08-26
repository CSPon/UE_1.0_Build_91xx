package com.cs.ue.util.texture;

import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

/**
 * Handles world textures and can be used to load custom texture pack(limit 1)<br>
 * @author Charlie Shin
 *
 */
public class UTexture
{	
	private HashMap<String, UTexturePack> textures;
	
	public UTexture()
	{
		textures = new HashMap<String, UTexturePack>();
	}
	
	public void addPack(String target, String format, String key, float resolution, int tilesetSize)
	{
		if(!textures.containsKey(key))
		{
			Texture newTexture = loadTexture(target, format);
			UTexturePack newPack = null;
			if(newTexture != null)
				newPack = new UTexturePack(newTexture, resolution, tilesetSize);
			
			textures.put(key, newPack);
		}
	}
	
	public UTexturePack getPack(String key)
	{
		return textures.get(key);
	}
	
	public void loadPack(String key)
	{
		glBindTexture(GL_TEXTURE_2D, -1);
		glBindTexture(GL_TEXTURE_2D, textures.get(key).ID);
	}
	public void unloadPack()
	{
		glBindTexture(GL_TEXTURE_2D, -1);
	}
	
	/**
	 * Gets x-location of texture with specified ID.<br>
	 * ID starts from 0, ends with whatever number of textures there is in one texture file(exclusive).
	 * @param id ID of the texture, from 0.
	 * @param size Length of one side of the texture file.
	 * @return float value of x position of desired texture.
	 */
	public float getX(int id, float size)
	{
		return id % size;
	}
	
	/**
	 * Gets y-location of texture with specified ID.<br>
	 * ID starts from 0, ends with whatever number of textures there is in one texture file(exclusive).
	 * @param id ID of the texture, from 0.
	 * @param size Length of one side of the texture file.
	 * @return float value of y position of desired texture.
	 */
	public float getY(int id, float size)
	{
		return id / size;
	}
	
	private Texture loadTexture(String dir, String format)
	{
		try
		{
			return TextureLoader.getTexture(format, new FileInputStream(dir), GL_NEAREST);
		}
		catch(FileNotFoundException err){return null;}
		catch(IOException err){return null;}
	}
	
	/**
	 * Releases texture data from memory, cleaning up.
	 */
	public void cleanup()
	{
		Iterator<Map.Entry<String, UTexturePack>> it = textures.entrySet().iterator();
		while(it.hasNext())
		{
			Map.Entry<String, UTexturePack> entry = it.next();
			entry.getValue().removePack();
			it.remove();
		}
		
		textures.clear();
	}
}
