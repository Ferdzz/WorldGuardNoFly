package com.ferdz.worldguardnofly;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.ferdz.worldguardnofly.handler.WGPlayerListener;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class WorldGuardNoFly extends JavaPlugin {

	public static WorldGuardNoFly instance;
	public static WorldGuardPlugin worldGuard;

	public static HashMap<String, ArrayList<String>> map; 
	
	public static String TOGGLED_MESSAGE = "";

	@Override
	public void onEnable() {
		worldGuard = getWorldGuard();
		if (worldGuard == null)
			return;

		map = new HashMap<String, ArrayList<String>>();

		this.saveDefaultConfig();
		
		// Configuration loading
		FileConfiguration fc = this.getConfig();
		ConfigurationSection cs = fc.getConfigurationSection("worlds");
		for (String world : cs.getKeys(false)) {
			World worldObj = Bukkit.getWorld(world);
			if (world == null) {
				this.getLogger().log(Level.WARNING, "The world " + world + " was not found, skipping.");
				continue;
			}

			List<String> regions = fc.getStringList("worlds." + world);
			if(regions == null || regions.isEmpty())
				continue;
			
			ArrayList<String> prRegions = new ArrayList<String>();
			for (String region : regions) {
				ProtectedRegion regionObj = worldGuard.getRegionManager(worldObj).getRegion(region);
				if (regionObj == null) {
					this.getLogger().log(Level.WARNING, "The region " + region + " was not found, skipping.");
				} else {
					prRegions.add(region);
				}
			}
			map.put(world, prRegions);
		}
		if(fc.getBoolean("toggledMessageEnabled"))
			TOGGLED_MESSAGE = fc.getString("toggledMessage");
	
		this.getServer().getPluginManager().registerEvents(new WGPlayerListener(), this);
		this.getCommand("worldguardnofly").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length == 0)
			sender.sendMessage(ChatColor.DARK_GREEN + "WorldGuardNoFly version " + ChatColor.GREEN + this.getDescription().getVersion() + ChatColor.DARK_GREEN + " is currently enabled");
		else if (args[0].equals("regions") && sender.hasPermission("worldguardnofly.command")) {
			for(Entry<String, ArrayList<String>> entry : map.entrySet()) {
				String s = ChatColor.AQUA + "World: " + ChatColor.GRAY + entry.getKey() + ChatColor.AQUA + "\n" + ChatColor.AQUA + "Regions: ";
				for (String region : entry.getValue()) {
					s += ChatColor.GRAY + region + ChatColor.GREEN + ", ";
				}
				s = s.substring(0, s.length() - 2);
				sender.sendMessage(s);
			}
		}
		return true;
	}

	private WorldGuardPlugin getWorldGuard() {
		Plugin plugin = getServer().getPluginManager().getPlugin("WorldGuard");
		if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
			getLogger().log(Level.SEVERE, "WorldGuard is not enabled on this server!");
			return null;
		}
		return (WorldGuardPlugin) plugin;
	}
}
