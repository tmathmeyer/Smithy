package edu.wpi.tmathmeyer.smithy;

import java.io.File;
import java.util.HashMap;

import org.bukkit.Location;

public class SmithyFileManager {
	
	private static SmithyFileManager instance;
	
	public static SmithyFileManager getInstance(){
		if (instance == null)
		{
			instance = new SmithyFileManager();
		}
		return instance;
	}
	
	private SmithyFileManager(){
		
	}
	
	private HashMap<SmithyEntityMap.Dimension, SmithyEntityMap> entitySizeMap = new HashMap<SmithyEntityMap.Dimension, SmithyEntityMap>();
	
	
	public SmithyEntityMap getSmithyEntityMap(Location a, Location b) throws Exception{
		int width = a.getBlockX() - b.getBlockX();
		int length = a.getBlockZ() - b.getBlockZ();
		
		width *=  width > 0 ? 1 : -1;
		length *= length > 0 ? 1 : -1;
		
		SmithyEntityMap.Dimension d = SmithyEntityMap.makeDimenstion(width, length);
		
		File mapData = new File(Smithy.getInstance().getDataFolder(), "x"+d.getLat()+"z"+d.getLon()+".smd");
		
		SmithyEntityMap cached = entitySizeMap.get(d);
		
		if (cached == null)
		{
			SmithyEntityMap readResult = this.readSmithyEntityMap(mapData);
			entitySizeMap.put(d, readResult);
			return readResult;
		}
		else
		{
			return cached;
		}
		
	}
	
	private SmithyEntityMap readSmithyEntityMap(File mapData){
		return null;
	}
}
