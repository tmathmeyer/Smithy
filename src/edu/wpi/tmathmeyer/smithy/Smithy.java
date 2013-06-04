package edu.wpi.tmathmeyer.smithy;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Location;

public final class Smithy extends JavaPlugin {
	
	private static Smithy instance;
	
	public static Smithy getInstance(){
		return instance;
	}
	
	
    @Override
    public void onEnable(){
    	instance = this;
    	
    	
    	
        // search for all available Smithies (from file)
    }
 
    @Override
    public void onDisable() {
        // write all smithy locations to file
    }
    
    
    public boolean isValidSmithy(Location cornerFloorA, Location cornerFloorB){
    	
    	//check for Smithy
    	
    	return false;
    }
}
