package com.company;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import com.t2o0n321.utils.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main extends JavaPlugin implements Listener, CommandExecutor {

    utils tools = new utils();

    Map<String, Location> dLoc = new HashMap<>();

    @Override
    public void onEnable(){
        getServer().getConsoleSender().sendMessage(tools.chat("&a[SimpleBack]: Plugin enabled !"));

        // register events
        getServer().getPluginManager().registerEvents(this,(Plugin) this);

        // register commands
        List<String> commandList = new ArrayList<>();
        commandList.add("back");
        for(int i = 0 ; i < commandList.size() ; i++){
            getCommand(commandList.get(i)).setExecutor(this::onCommand);
        }

    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent evnt){
        Player player = evnt.getEntity();
        Location player_death_loc = player.getLocation();
        this.dLoc.put(player.getName(),player_death_loc);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String args[]) {
        Player player = (Player)sender;

        if(!(player instanceof Player)) return true;

        else if(cmd.getName().equalsIgnoreCase("back") && args.length == 0){
            if(dLoc.containsKey(player.getName())){
                player.teleport(dLoc.get(player.getName()));
                player.sendMessage(tools.chat("&1You are back !"));
            }
            else{
                player.sendMessage(tools.chat("&cPlayer data doesn't exist !"));
            }
            return true;
        }

        return true;
    }

    @Override
    public void onDisable(){
        getServer().getConsoleSender().sendMessage(tools.chat("&c[SimpleBack]: Plugin disabled ...."));
    }
}
