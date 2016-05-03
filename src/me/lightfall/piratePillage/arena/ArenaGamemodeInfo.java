package me.lightfall.piratePillage.arena;

import java.util.List;

import org.bukkit.entity.Player;

import me.lightfall.piratePillage.gamemode.GamemodeFullException;
import me.lightfall.piratePillage.gamemode.PartyTooLargeException;
import me.lightfall.piratePillage.party.Party;

/**
 * Everything an arena will need from it's gamemode
 * 
 * @author Lightfall
 *
 */
public interface ArenaGamemodeInfo {

	/**
	 * Adds player to gamemode
	 * @param p	Player to add
	 * @return 
	 * @return True is player added successfully, false otherwise
	 * @throws GamemodeFullException
	 */
	public abstract void addPlayer(Player p) throws GamemodeFullException;
	
	/**
	 * Adds Party to gamemode
	 * @param p	Party to add
	 * @return 
	 * @return True is Party added successfully, false otherwise
	 * @throws GamemodeFullException
	 */
	public abstract void addParty(Party p) throws GamemodeFullException, PartyTooLargeException;

	public abstract void removePlayer(Player p);
	
	public abstract List<String> getSignMessage();
}
