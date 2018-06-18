package com.kraken.setspawn;

import java.util.WeakHashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class MainListener implements Listener {
	
	Main plugin;
	WeakHashMap<String, Boolean> options = new WeakHashMap<>();
	String language;
	
    public MainListener(Main plugin, String language) {
  	  
  	  	this.plugin = plugin;
  	  	this.language = language;
  	  
    }
    
    public void setOption(String option, boolean setting) {
    	options.put(option, setting);
    }
    
    public void setLanguage(String language) {
    	this.language = language;
    }
	
	@EventHandler
    public void onLogin(PlayerJoinEvent event) {
		
		Player player = (Player) event.getPlayer();
		String spawnLocStr = plugin.spawns.getString( "spawn.spawns." + plugin.spawns.getString("spawn.world") );
		
		//First join spawn
		if ( !player.hasPlayedBefore() && spawnLocStr != null ) {
			player.teleport( LocSerialization.getLocationFromString( spawnLocStr ) );
		}
        
    }
	
	@EventHandler
	public void onDeath(PlayerRespawnEvent event) {
		
		Player player = (Player) event.getPlayer();
		String spawnLocStr = plugin.spawns.getString( "spawn.spawns." + plugin.spawns.getString("spawn.world") );
		
		//Teleport back to default spawn
		if ( spawnLocStr != null ) {
			plugin.getServer().getScheduler().scheduleSyncDelayedTask( plugin, new Runnable() {
				@Override
				public void run() {
					player.teleport( LocSerialization.getLocationFromString( spawnLocStr ) );
				}
			}, 2);
		}
		
	}
    
}
