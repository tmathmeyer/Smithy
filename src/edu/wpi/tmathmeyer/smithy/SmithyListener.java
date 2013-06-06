package edu.wpi.tmathmeyer.smithy;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import org.bukkit.Location;
import org.bukkit.Material;

public class SmithyListener implements Listener{
	
	private static SmithyListener instance;

	public HashMap<Player, ClickSet> playerClickSets = new HashMap<Player, ClickSet>();
	
	private SmithyListener()
	{
		
	}
	
	public static SmithyListener getInstance()
	{
		if (instance == null) instance = new SmithyListener();
		return instance;
	}
	
	public void onPlayerInteract(PlayerInteractEvent event) {
		if (event.equals(Action.LEFT_CLICK_BLOCK))
		{
			Player p = event.getPlayer();
			ItemStack holding = p.getItemInHand();
			if (holding.getType().equals(Material.GOLD_PICKAXE))
			{
				if (event.getClickedBlock().getType().equals(Material.CHEST))
				{
					ClickSet s = this.playerClickSets.get(p);
					if (s == null)
					{
						s = new ClickSet();
						this.playerClickSets.put(p,  s);
						p.sendMessage("you have not selected the corners for this smithy yet. please click on the two corner blocks in order to select the locations.");
					}
					else if (s.getCount() == 0)
					{
						p.sendMessage("you have not selected the corners for this smithy yet. please click on the two corner blocks in order to select the locations.");
					}
					else if (s.getCount() == 1)
					{
						p.sendMessage("you have only selected one corner for this smithy. please select the other corner first.");
					}
					else
					{
						//build the smithy
					}
				}
				else
				{
					ClickSet s = this.playerClickSets.get(p);
					if (s == null)
					{
						s = new ClickSet();
						this.playerClickSets.put(p,  s);
					}
					int result = s.add(event.getClickedBlock().getLocation());
					p.sendMessage("you have added point "+result+" of two for the smithy.");
				}
			}
		}
	}
	
	public void disable() {
		HandlerList.unregisterAll(this);
	}
	
	
	
	
	
	
	private class ClickSet {
		Location click1 = null;
		Location click2 = null;
		
		public ClickSet(){};
		
		public int add(Location l){
			if (click1 == null)
			{
				click1 = l;
				return 1;
			}
			else if(click2 == null)
			{
				click2 = l;
				return 2;
			}
			else
			{
				this.reset();
				return 0;
			}
		}
		
		public int getCount(){
			return (click1==null?0:1) + (click2==null?0:1);
		}
		
		public void reset(){
			click1 = null;
			click2 = null;
		}
	}
}
