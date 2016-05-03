package me.lightfall.piratePillage.utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.plugin.Plugin;

import me.lightfall.piratePillage.arena.Arena;
import me.lightfall.piratePillage.player.PlayerProfile;

public final class Globals {

	public static Plugin plugin;
	
	public static Arena lobby;
	
	public static SQLConnection database;

	/* Player profiles */
	private static Map<UUID, PlayerProfile> playerProfiles = new HashMap<UUID, PlayerProfile>();

	public static PlayerProfile getProfile(UUID uuid) {
		return playerProfiles.get(uuid);
	}

	public static void setProfile(UUID uuid, PlayerProfile profile) {
		playerProfiles.put(uuid, profile);
	}

	public static void clearProfile(UUID uuid) {
		playerProfiles.remove(uuid);
	}
	/* * * * * * * * * */
	
	public static List<Arena> arenas = new ArrayList<Arena>();

	private Globals() {
	}

}
