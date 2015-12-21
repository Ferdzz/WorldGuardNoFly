package com.ferdz.worldguardnofly;

import java.util.ArrayList;

import org.bukkit.World;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class ConfigWorldRegion {
	public World world;
	public ArrayList<ProtectedRegion> regions;
	
	public ConfigWorldRegion(World world) {
		this.world = world;
		this.regions = new ArrayList<ProtectedRegion>();
	}
}
