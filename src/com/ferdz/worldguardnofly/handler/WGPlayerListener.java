package com.ferdz.worldguardnofly.handler;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import com.ferdz.worldguardnofly.ConfigWorldRegion;
import com.ferdz.worldguardnofly.WorldGuardNoFly;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class WGPlayerListener implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void playerMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if(p.hasPermission("worldguardnofly.bypass"))
			return;
		
		if (p.isFlying()) {
			for (ConfigWorldRegion cworld : WorldGuardNoFly.map) {
				if (p.getWorld().getName().equals(cworld.world.getName())) {
					for (ProtectedRegion region : cworld.regions) {
						Location l = p.getLocation();
						if (region.contains(l.getBlockX(), l.getBlockY(), l.getBlockZ())) {
							p.setFlying(false);
						}
					}
				}
			}
		}
	}
}
