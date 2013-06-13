package edu.wpi.tmathmeyer.smithy;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

import org.bukkit.Location;
import org.bukkit.Material;

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
	
	
	public SmithyEntityMap getSmithyEntityMap(Location a, Location b) throws Exception {
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
	
	private SmithyEntityMap readSmithyEntityMap(File mapData) throws NumberFormatException, Exception{
		Scanner mapReader = new Scanner(mapData);
		int[] xyz = new int[3];
		SmithyEntityMap sem = new SmithyEntityMap(xyz);
		int pos = 0;
		for(String s : mapReader.nextLine().split(","))
		{
			xyz[pos++] = Integer.parseInt(s);
		}
		
		for(int height = 0; height < xyz[1]; height++)
		{
			for(int lat = 0; lat < xyz[0]; lat++)
			{
				pos = 0;
				for(String s : mapReader.nextLine().split(","))
				{
					sem.setMaterial(lat, height, pos++, Material.getMaterial(Integer.parseInt(s)));
				}
			}
		}
		
		return sem;
	}
}
