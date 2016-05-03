package me.lightfall.piratePillage.utilities;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.lightfall.piratePillage.arena.Arena;
import me.lightfall.piratePillage.gamemode.GamemodeFullException;
import me.lightfall.piratePillage.player.PlayerProfile;

public class MainEventHandler implements Listener {

	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent e) {
		playerLogin(e.getPlayer());
	}
	
	
	public static void playerLogin(Player player) {
		// Create player profile and add to Globals
		PlayerProfile profile = new PlayerProfile(player);
		Globals.setProfile(player.getUniqueId(), profile);
		// Add to lobby
		try {
			Globals.lobby.addPlayer(profile);
		} catch (GamemodeFullException ex) {
			// Lobby should never be full
		}
	}
	
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		playerQuit(e.getPlayer());
	}
	
	public static void playerQuit(Player player) {
		PlayerProfile profile = Globals.getProfile(player.getUniqueId());
		Arena arena = profile.getArena();
		arena.removePlayer(profile);
		//TODO playerQuit: do stat update
		
	}

}
