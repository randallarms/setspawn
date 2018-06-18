// =========================================================================
// |SET SPAWN v1.0 | for Minecraft v1.12
// | by Kraken | Link TBA
// | code inspired by various Bukkit & Spigot devs -- thank you.
// =========================================================================

package com.kraken.setspawn;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.WeakHashMap;

import org.bukkit.Bukkit;

public class Main extends JavaPlugin implements Listener {
	
	File spawnsFile;
	FileConfiguration spawns;
	
	//Lang vars
	public static String VERSION = "1.0";
	String language;
	ArrayList<String> languages = new ArrayList<String>();
	Messages messenger;
	
	//Class vars
	MainListener listener;
	
	//Options
	WeakHashMap<String, Boolean> options = new WeakHashMap<>();
	
	//Enable
    @Override
    public void onEnable() {
    	
    	getLogger().info("Loading options...");
		
		//Copies the default config.yml from within the .jar if "plugins/SetSpawn/config.yml" does not exist
		getConfig().options().copyDefaults(true);
		
		//Language/Messages handler class construction
		languages.add("english");
		loadMessageFiles();
		language = getConfig().getString("language");
		messenger = new Messages(this, "english");
		
		//Spawns file
		spawnsFile = new File("plugins/SetSpawn/spawns.yml");
	  	spawns = YamlConfiguration.loadConfiguration(spawnsFile);
		
		//General plugin management
    	PluginManager pm = getServer().getPluginManager();
    	listener = new MainListener(this, language);
		pm.registerEvents(listener, this);
		
		//Language setting
		setLanguage(language);
		
	    //Loading default settings into options
    	setOption( "enabled", getConfig().getBoolean("enabled") );
    	setOption( "permissions", getConfig().getBoolean("permissions") );
    	setOption( "silentMode", getConfig().getBoolean("silentMode") );
    	silencer( options.get("silentMode") );
    	
    	getLogger().info("Finished loading!");
			
    }
    
    //Disable
    @Override
    public void onDisable() {
        getLogger().info("Disabling...");
    }
    
    //Messages
    public void msg(Player player, String cmd) {
    	messenger.makeMsg(player, cmd);
    }
    
    public void consoleMsg(String cmd) {
    	messenger.makeConsoleMsg(cmd);
    }
    
    //Setting methods
    //Options setting
    public void setOption(String option, boolean setting) {
    	getConfig().set(option, setting);
    	saveConfig();
    	options.put(option, setting);
    	listener.setOption(option, setting);
    	getLogger().info(option + " setting: " + setting );
    }
    
    //Type setting
    public void setType(String setting) {
    	getConfig().set("type", setting);
    	saveConfig();
    	getLogger().info("Type set to: " + setting );
    }
    
    //Language setting
    public void setLanguage(String language) {
    	this.language = language;
    	getConfig().set("language", language);
    	saveConfig();
    	listener.setLanguage(language);
    	messenger.setLanguage(language);
    	getLogger().info( "Language: " + language.toUpperCase() );
    }
    
	public void loadMessageFiles() {
		for (String lang : languages) {
		    File msgFile = new File("plugins/SetSpawn/lang/", lang.toLowerCase() + ".yml");
		    if ( !msgFile.exists() ) {
		    	saveResource("lang/" + lang.toLowerCase() + ".yml", false);
		    }
		}
    }
    
    //Silent mode setting
    public void silencer(boolean silentMode) {
    	messenger.silence(silentMode);
    }
    
    //Save the player file
    public void saveCustomFile(FileConfiguration fileConfig, File file) {
    	try {
			fileConfig.save(file);
		} catch (IOException e) {
			System.out.println("Error saving custom config file: " + file.getName());
		}
    }
    
    //Set Spawn commands
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		//Command handling
		Commands commands = new Commands(this);
		String command = cmd.getName();
		
		//Player handling
		Player player;
		boolean isPlayer = sender instanceof Player;
		
		if (isPlayer) {
			player = (Player) sender;
		} else {
			player = Bukkit.getServer().getPlayerExact("none");
		}
		
		//Execute command & return
		return commands.execute(isPlayer, player, command, args);
		
	}
	
		
}
