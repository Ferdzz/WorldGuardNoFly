package com.ferdz.worldguardnofly;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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

	public static ArrayList<ConfigWorldRegion> map; // TODO: Study performance of this solution VS ArrayList<HashMap<World, Region>>

	@Override
	public void onEnable() {
		worldGuard = getWorldGuard();
		if (worldGuard == null)
			return;

		map = new ArrayList<ConfigWorldRegion>();

		FileConfiguration fc = this.getConfig();
		ConfigurationSection cs = fc.getConfigurationSection("worlds");
		for (String world : cs.getKeys(false)) {
			ConfigWorldRegion worldRegion = new ConfigWorldRegion(Bukkit.getWorld(world));
			List<String> regions = fc.getStringList("worlds." + world);
			for (String region : regions) {
				worldRegion.regions.add(worldGuard.getRegionManager(worldRegion.world).getRegion(region));
			}
			map.add(worldRegion);
		}

		this.getServer().getPluginManager().registerEvents(new WGPlayerListener(), this);
		this.getCommand("worldguardnofly").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length == 0)
			sender.sendMessage(ChatColor.DARK_GREEN + "WorldGuardNoFly version " + ChatColor.GREEN + this.getDescription().getVersion() + ChatColor.DARK_GREEN + " is currently enabled");
		else if (args[0].equals("regions")) {
			for (ConfigWorldRegion configWorldRegion : map) {
				String s = ChatColor.AQUA + "World: " + ChatColor.GRAY + configWorldRegion.world.getName() + ChatColor.AQUA + "\n" + ChatColor.AQUA + "Regions: ";
				for (ProtectedRegion region : configWorldRegion.regions) {
					s += ChatColor.GRAY + region.getId() + ChatColor.GREEN + ", ";
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
