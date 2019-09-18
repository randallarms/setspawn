# SetSpawn

Set spawn for your Minecraft world with a simple command. Multi-world support may come in the future. Plugin for Bukkit on Minecraft 1.12!

SetSpawn on GitHub: https://github.com/randallarms/setspawn

commands: 

     spawn:
        description: Controls the Set Spawn plugin.
        usage: /<command> <argument>
        
config:

    LOADED MARKER | Dummy value to check against at start-up, don't change!
    ENABLED | If true, Set Spawn is in effect.
    LANGUAGE | Currently supported languages: English only (for now!)
    SILENT MODE | If true, most messages will not be relayed to players.
    PERMISSIONS REQUIREMENT | If true, players must have the proper permissions to use basic Set Spawn commands.
    
permissions:

     spawn.*:
        description: Permission for all Set Spawn commands.
     spawn.spawn:
        description: Permission for teleporting back to your default spawn by command.
     spawn.nospawn:
        description: Permission for preventing a player from teleporting to default spawn on death.
     spawn.set:
        description: Permission to set the main default spawn by commands.

Do you want to help us improve this plugin? You can! Everyone welcome to contribute on GitHub by utilizing the Flow to branch their own version, make changes (commits), and submit their changes as a "pull request" to be considered for merging with the master file.

Please feel free to voice criticism to better the project, as well. Got a problem? Bug, glitch, complaint? Visit the Issues page and let me know, please: https://github.com/randallarms/loginbonus/issues
