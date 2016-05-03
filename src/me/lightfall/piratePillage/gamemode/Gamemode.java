package me.lightfall.piratePillage.gamemode;

import java.util.List;

import org.bukkit.entity.Player;

import me.lightfall.piratePillage.arena.Arena;
import me.lightfall.piratePillage.arena.ArenaGamemodeInfo;
import me.lightfall.piratePillage.party.Party;

/**
 * Everything a gamemode needs to have
 * 
 * @author Lightfall
 *
 */
public abstract class Gamemode implements ArenaGamemodeInfo {

	protected Arena arena;
	
	public Gamemode(Arena arena) {
		this.arena = arena;
	}

	protected abstract CommunicationHandler getCommunicationHandler();

	/**
	 * Adds player to gamemode
	 * @param p	Player to add
	 * @return 
	 * @return True is player added successfully, false otherwise
	 * @throws GamemodeFullException
	 */
	public abstract void addPlayer(Player p) throws GamemodeFullException;
	

	public abstract void removePlayer(Player p);

	/**
	 * Adds Party to gamemode
	 * @param p	Party to add
	 * @return 
	 * @return True is Party added successfully, false otherwise
	 * @throws GamemodeFullException
	 */
	public abstract void addParty(Party p) throws GamemodeFullException, PartyTooLargeException;
	
	public abstract List<Player> getPlayers();
	
	public abstract List<String> getSignMessage();
}
