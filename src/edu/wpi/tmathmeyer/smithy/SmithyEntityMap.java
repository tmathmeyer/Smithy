package edu.wpi.tmathmeyer.smithy;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Location;

public class SmithyEntityMap {
	private Material[][][] map;
	private HashMap<Material, Integer> count = new HashMap<Material, Integer>();
	
	public Material setMaterial(int height, int lat, int lon, Material n) throws Exception
	{
		if (map[height][lat][lon] == null)
		{
			throw new Exception("there is no map");
		}
		Material temp = map[height][lat][lon];
		map[height][lat][lon] = n;
		
		this.incrementItem(n, count, 1);
		this.decrimentItem(temp, count, 1);
		
		return temp;
	}
	
	public int incrementItem(Material mat, HashMap<Material, Integer> map, int count)
	{
		if (mat == null)
		{
			return 0;
		}
		Integer c = map.get(mat);
		map.put(mat, c+count);
		return c+count;
	}
	
	public int decrimentItem(Material mat, HashMap<Material, Integer> map, int count)
	{
		if (mat == null)
		{
			return 0;
		}
		Integer c = map.get(mat);
		map.put(mat, c-count);
		return c-count;
	}
	
	public HashMap<Material, Integer> construct(Inventory i) throws Exception
	{
		HashMap<Material, Integer> comparison = new HashMap<Material, Integer>();
		ItemStack[] stacks = i.getContents();
		for(ItemStack stack : stacks)
		{
			Material m = stack.getType();
			if (m.equals(Material.AIR));
			else
			{
				this.incrementItem(m, comparison, stack.getAmount());
			}
		}
		
		for (Map.Entry<Material,Integer> entry : count.entrySet())
		{
		    Material key = entry.getKey();
		    Integer required = entry.getValue();
		    Integer has = comparison.get(required);
		    if (required == null);
		    else if (has == null)
		    {
		    	throw new Exception("not enough "+key.toString());
		    }
		    else if (required <= has)
	    	{
	    		throw new Exception ("not enough "+key.toString());
	    	}
		    else{
		    	comparison.put(key, has-required);
		    }
		}
		
		return comparison;
	}
	
	
	public void Build(Chest c, Location backCorner, Location frontCorner) throws Exception{
		int a_x = backCorner.getBlockX();
		int a_y = backCorner.getBlockY();
		int a_z = backCorner.getBlockZ();
		int b_x = frontCorner.getBlockX();
		int b_y = frontCorner.getBlockY();
		int b_z = frontCorner.getBlockZ();
		int x_sign = b_x - a_x > 0 ? 1 : -1;
		int z_sign = b_z - a_z > 0 ? 1 : -1;
		if (a_y != b_y)
		{
			throw new Exception("corners need to be at the same level");
		}
		else if ((b_x - a_x) * x_sign != this.map[0].length)
		{
			throw new Exception("Dimension Error (x)");
		}
		else if ((b_z - a_z) * z_sign != this.map[0][0].length)
		{
			throw new Exception("Dimenstion Error (y)");
		}
		try{
			HashMap<Material, Integer> leftovers =  construct(c.getInventory());
			c.getInventory().clear();
			for (Map.Entry<Material,Integer> entry : leftovers.entrySet())
			{
				c.getInventory().addItem(new ItemStack(entry.getKey(), entry.getValue()));
			}
		}
		catch(Exception e){
			throw e;
		}
		for(int h = 0; h < this.map.length; h++)
		{	
			for(int x = 0; x < this.map[0].length; x++)
			{
				for(int y = 0; y < this.map[0][0].length; y++)
				{
					int act_x = a_x + ( x * x_sign );
					int act_y = a_y + h;
					int act_z = a_z + ( y * z_sign );
					
					Location blockLocation = new Location(backCorner.getWorld(), act_x, act_y, act_z);
					blockLocation.getBlock().setType(map[h][x][y]);
				}
			}
		}
	}
	
	/*
	public static Object[][][] rotate(int degree, Object[][][] map){
		degree = (90 * (degree / 90)) % 360;
		Object[][][] result = map;
		while(degree > 0)
		{
			result = rotate(result);
			degree -= 90;
		}
		return result;
	}
	
	public static Object[][][] rotate(Object[][][] map){
		Object[][][] result = new Object[map.length][map[0][0].length][map[0].length];
		for(int h = 0; h < result.length; h++)
		{
			for(int x = 0; x < result[h].length; x++)
			{
				for(int y = 0; y < result[h][x].length; y++)
				{
					result[h][x][y] = map[h][map[h].length - y - 1][x];
				}
			}
		}
		return result;
	}
	*/
}


