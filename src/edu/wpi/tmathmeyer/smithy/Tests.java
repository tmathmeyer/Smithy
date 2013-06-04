package edu.wpi.tmathmeyer.smithy;

public class Tests {
	public static void main(String[] args){
		Integer[][][] a = {{{1,2,3},{4,5,6},{7,8,9},{10,11,12}}};
		print(a);
		System.out.println("\n\n\n\n\n");
		
		print(SmithyEntityMap.rotate(90, a));
		
		
	}
	
	public static void print(Object[][][] objects){
		for(int h = 0; h < objects.length; h++)
		{
			for(int x = 0; x < objects[h].length; x++)
			{
				for(int y = 0; y < objects[h][x].length; y++)
				{
					System.out.print(objects[h][x][y] + ",");
				}
				System.out.println();
			}
			System.out.println("\n");
		}
	}
}
