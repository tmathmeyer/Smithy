package edu.wpi.tmathmeyer.smithy;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Location;

public final class Smithy extends JavaPlugin {
 
    @Override
    public void onEnable(){
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
