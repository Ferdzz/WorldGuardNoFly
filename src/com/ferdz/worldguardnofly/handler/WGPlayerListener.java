package com.ferdz.worldguardnofly.handler;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import com.ferdz.worldguardnofly.WorldGuardNoFly;

public class WGPlayerListener implements Listener {

	@EventHandler(priority = EventPriority.HIGH)
	public void playerMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if (p.hasPermission("worldguardnofly.bypass") || p.getGameMode() != (GameMode.SURVIVAL) || p == null)
			return;

		if (p.isFlying()) {
			for (int i = 0; i < WorldGuardNoFly.map.size(); i++) {
				ArrayList<String> regions = WorldGuardNoFly.map.get(p.getWorld().getName());
				if(regions == null)
					continue;
				
				Location l = p.getLocation();
				for (String protectedRegion : regions) {
					if (WorldGuardNoFly.worldGuard.getRegionManager(p.getWorld()).getRegion(protectedRegion).contains(l.getBlockX(), l.getBlockY(), l.getBlockZ())) {
						p.setFlying(false);
						if (WorldGuardNoFly.TOGGLED_MESSAGE != "")
							p.sendMessage(ChatColor.translateAlternateColorCodes('&', WorldGuardNoFly.TOGGLED_MESSAGE.replace("%player%", p.getDisplayName()).replace("%world%", p.getWorld().getName()).replace("%region%", protectedRegion)));
						return;
					}
				}
			}
		}
	}
}
