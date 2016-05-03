package me.lightfall.piratePillage.arena;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.craftbukkit.v1_8_R2.block.CraftSign;

import me.lightfall.piratePillage.gamemode.CommunicationHandler;
import me.lightfall.piratePillage.gamemode.GamemodeFullException;
import me.lightfall.piratePillage.gamemode.PartyTooLargeException;
import me.lightfall.piratePillage.party.Party;
import me.lightfall.piratePillage.player.PlayerProfile;
import me.lightfall.piratePillage.utilities.Globals;
import net.md_5.bungee.api.ChatColor;

/**
 * Arenas are used to separate players into gamemodes and the lobby.
 * Assumes that each player is in an arena at any given time, even
 * if in the lobby.
 * <br/>
 * Adding a party to the arena only adds the players and sets their arenas.
 * Any code adding a party NEEDS to handle dissolving the party after successful
 * addition to the arena.
 * 
 * @author Tanner Hagel
 *
 */
public class Arena {

	private static int ID = 0;
	private ArenaGamemodeInfo gamemode;
	private int id;
	private Sign sign;
	private ArenaSignListener listener;

	public Arena() {
		this.id = ID++;
		this.listener = new ArenaSignListener(this);
		Globals.plugin.getServer().getPluginManager().registerEvents(listener, Globals.plugin);
	}
	
	public Arena(Sign sign) {
		this();
		this.sign = sign;
	}
	
	public Arena(Block sign) {
		this(new CraftSign(sign));
	}

	public void registerCommunicationHandler(CommunicationHandler comHandler) {
		if(comHandler != null)
			Globals.plugin.getServer().getPluginManager().registerEvents(comHandler, Globals.plugin);
	}
	
	public void addPlayer(PlayerProfile profile) throws GamemodeFullException {
		gamemode.addPlayer(profile.getPlayer());
		profile.setArena(this);
	}
	
	public void addParty(Party party) throws GamemodeFullException, PartyTooLargeException {
		gamemode.addParty(party);
		for(PlayerProfile profile : party.getPlayers())
		{
			profile.setArena(this);
		}
	}
	
	/**
	 * Removes player from the gamemode
	 * @param profile
	 */
	public void removePlayer(PlayerProfile profile) {
		gamemode.removePlayer(profile.getPlayer());
	}
	
	public void setGamemode(ArenaGamemodeInfo gamemode) {
		this.gamemode = gamemode;
	}
	
	/**
	 * Returns the used ArenaGamemodeInfo instance
	 */
	public ArenaGamemodeInfo getGamemodeInfo() {
		return this.gamemode;
	}
	
	/**
	 * Gets the arena's sign block
	 * @return Sign object or null if one isn't set
	 */
	public Sign getSign() {
		if(sign == null)
			return null;
		if(sign.getBlock().getType() == Material.WALL_SIGN || sign.getBlock().getType() == Material.SIGN_POST)
			return sign;
		sign = null;
		return null;
	}
	
	/**
	 * Sets the arena's sign. Only one sign per arena
	 * @param sign Sign to set
	 */
	public void setSign(Sign sign) {
		this.sign = sign;
	}
	
	/**
	 * Sets the arena's sign. Only one sign per arena
	 * @param sign Sign to set
	 */
	public void setSign(Block sign) {
		setSign(new CraftSign(sign));
	}
	
	public List<String> getSignMessage() {
		List<String> message = new ArrayList<String>();
		message.add(ChatColor.BOLD + "Arena " + id);
		List<String> gmMsg = gamemode.getSignMessage();
		if(gmMsg != null)
			message.addAll(1, gmMsg);
		return message;
	}

}
