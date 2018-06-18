package com.kraken.setspawn;

import java.util.WeakHashMap;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class Commands {
	
	  Main plugin;
	  String VERSION;
	  
	  String language;
	  Messages messenger;
	  
	  //Options
	  WeakHashMap<String, Boolean> options = new WeakHashMap<>();
	
  //Constructor
	public Commands(Main plugin) {
		
		this.plugin = plugin;
		this.VERSION = Main.VERSION;
		this.options = plugin.options;
		
		this.language = plugin.language;
		this.messenger = new Messages(plugin, language);
	
	}
	
  //Commands
	public boolean execute(boolean isPlayer, Player player, String command, String[] args) {
		
		switch (command) {
		
			//Command: spawn
			case "spawn":
			case "setspawn":
				
				switch (args.length) {
				
					case 0:
					
						if ( !player.hasPermission("spawn.spawn") && plugin.options.get("permissions") ) {
							plugin.msg(player, "errorIllegalCommand");
							return true;
						}
						
						if ( command.equalsIgnoreCase("spawn") ) {
							player.teleport( LocSerialization.getLocationFromString( plugin.spawns.getString( "spawn.spawns." + plugin.spawns.getString("spawn.world") ) ) );
						} else if ( !isPlayer ) {
							plugin.consoleMsg("errorIllegalCommand");
						} else {
							plugin.msg(player, "errorIllegalCommand");
						}
						
						return true;
						
					case 1:
						
						switch ( args[0].toLowerCase() ) {
						
						  //Command: spawn version     
			    			case "version":
			    			
								if ( !isPlayer ) {
									plugin.consoleMsg("cmdVersion");
								} else {
									plugin.msg(player, "cmdVersion");
								}
								
				                return true;
			                
				          //Command: spawn set
			    			case "set":
			    				
			    				if ( !isPlayer ) {
									plugin.consoleMsg("errorPlayerCommand");
								} else {
									
									if ( !player.hasPermission("spawn.set") && plugin.options.get("permissions") ) {
										plugin.msg(player, "errorIllegalCommand");
										return true;
									}
									
						        	Location loc = player.getLocation();
						        	World world = player.getWorld();
				        			plugin.spawns.set("spawn.world", world.getName());
				        			plugin.spawns.set("spawn.spawns." + world.getName(), LocSerialization.getStringFromLocation(loc));
				        			world.setSpawnLocation((int) loc.getX(), (int) loc.getY(), (int) loc.getZ());
				        			plugin.saveCustomFile(plugin.spawns, plugin.spawnsFile);
									plugin.msg(player, "cmdSetSpawn");
									return true;
									
								}
				                
			                default:
					
								if ( !isPlayer ) {
									plugin.consoleMsg("errorCommandFormat");
								} else {
									plugin.msg(player, "errorIllegalCommand");
								}
								
				                return true;
			                	
						}
						
					case 2:
						
						//Check if sender is a player and if that player has OP perms
						if (isPlayer) {
							
							if ( !player.isOp() ) {
								plugin.msg(player, "errorIllegalCommand");
								return true;
							}
							
						}
						
						//Command handling switch
						switch ( args[0].toLowerCase() ) {
						
							//Command: spawn enable
							case "enable":
							case "enabled":
								
								switch ( args[1].toLowerCase() ) {
								
									case "true":
									case "enable":
									case "enabled":
									case "on":
									case "cierto":
										plugin.setOption("enabled", true);
										
										if ( !isPlayer ) {
											plugin.consoleMsg("cmdPluginEnabled");
										} else {
											plugin.msg(player, "cmdPluginEnabled");
										}
										
										return true;
										
									case "false":
									case "disable":
									case "disabled":
									case "off":
									case "falso":
										plugin.setOption("enabled", false);
										
										if ( !isPlayer ) {
											plugin.consoleMsg("cmdPluginDisabled");
										} else {
											plugin.msg(player, "cmdPluginDisabled");
										}
										
										return true;
								
								  //Enable command error handling
									default: 
										
										if ( !isPlayer ) {
											plugin.consoleMsg("errorCommandFormat");
										} else {
											plugin.msg(player, "errorEnableFormat");
										}
										
										return true;
								
								}
						
							//Command: spawn language
							case "language":
							case "lang":
								
								String lang = args[1].toLowerCase();
								
								//Language command handling
								if ( plugin.languages.contains( lang ) ) {
									
									plugin.setLanguage(lang);
									
									if ( !isPlayer ) {
										plugin.consoleMsg("cmdLanguageSet");
									} else {
										plugin.msg(player, "cmdLang");
									}
									
									return true;
								
								//Language command error handling
								} else {
									
									if ( !isPlayer ) {
										plugin.consoleMsg("errorLanguageSet");
									} else {
										plugin.msg(player, "errorLangNotFound");
									}
									
									return true;
								}
								
							//Command: spawn silentMode
							case "silentmode":
								
								switch ( args[1].toLowerCase() ) {
								
									case "true":
									case "enable":
									case "enabled":
									case "on":
									case "cierto":
										plugin.setOption("silentMode", true);
										plugin.silencer(true);
										
										if ( !isPlayer ) {
											plugin.consoleMsg("cmdSilentModeOn");
										} else {
											plugin.msg(player, "cmdSilentOn");
										}
										
										return true;
										
									case "false":
									case "disable":
									case "disabled":
									case "off":
									case "falso":
										plugin.setOption("silentMode", false);
										plugin.silencer(false);
										
										if ( !isPlayer ) {
											plugin.consoleMsg("cmdSilentModeOff");
										} else {
											plugin.msg(player, "cmdSilentOff");
										}
										
										return true;
								
								  //Silentmode command error handling
									default: 
										
										if ( !isPlayer ) {
											plugin.consoleMsg("errorCommandFormat");
										} else {
											plugin.msg(player, "errorSilentModeFormat");
										}
										
										return true;
								
								}
							
			        	    //Command: spawn perms
			        	    case "perms":
			        	    case "permissions":
			        	    case "perm":
			        	    case "permission":
			        	    		
			        	    		switch ( args[1].toLowerCase() ) {
			        	    		
			        	    			case "on":
			        	    			case "cierto":
			        	    			case "enable":
			        	    			case "enabled":
			        	    			case "true":
			        	    				plugin.setOption("permissions", true);
											
											if ( !isPlayer ) {
												plugin.consoleMsg("cmdPermsEnabled");
											} else {
												plugin.msg(player, "cmdPermsEnabled");
											}
											
			        	    				return true;
			        	    				
			        	    			case "off":
			        	    			case "disable":
			        	    			case "disabled":
			        	    			case "false":
			        	    			case "falso":
			        	    				plugin.setOption("permissions", false);
											
											if ( !isPlayer ) {
												plugin.consoleMsg("cmdPermsDisabled");
											} else {
												plugin.msg(player, "cmdPermsDisabled");
											}
											
			        	    				return true;
			        	    			
			        	    		//Perms command error handling
			        	    			default:
			        	    				if ( !isPlayer ) {
			        	    					plugin.consoleMsg("errorArgumentFormat");
			        	    				} else {
			        	    					plugin.msg(player, "errorPermsFormat");
			        	    				}
			        	    				
			        	        	    	return true;
			        	        	    	
			        	    		}
			        	    	
						}
								
					default:
						if (isPlayer) {
							plugin.msg(player, "errorIllegalCommand");
						} else {
							plugin.consoleMsg("errorCommandFormat");
						}
						return true;
						
				}
						
			}
		
			return true;
				
		}
	
}
