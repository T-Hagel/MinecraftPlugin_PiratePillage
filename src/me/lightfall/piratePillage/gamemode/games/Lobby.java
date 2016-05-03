package me.lightfall.piratePillage.gamemode.games;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import me.lightfall.piratePillage.arena.Arena;
import me.lightfall.piratePillage.gamemode.CommunicationHandler;
import me.lightfall.piratePillage.gamemode.Gamemode;
import me.lightfall.piratePillage.gamemode.GamemodeFullException;
import me.lightfall.piratePillage.gamemode.PartyTooLargeException;
import me.lightfall.piratePillage.gamemode.communications.FreeCommunication;
import me.lightfall.piratePillage.party.Party;
import me.lightfall.piratePillage.player.PlayerProfile;
import net.md_5.bungee.api.ChatColor;

public class Lobby extends Gamemode {

	private List<Player> players;
	private CommunicationHandler cHandler;
	
	public Lobby(Arena arena) {
		super(arena);
		players = new ArrayList<Player>();
		cHandler = new FreeCommunication(this);
		arena.registerCommunicationHandler(cHandler);
	}

	@Override
	protected CommunicationHandler getCommunicationHandler() {
		return cHandler;
	}

	@Override
	public void addPlayer(Player p) throws GamemodeFullException {
		players.add(p);
	}

	@Override
	public void removePlayer(Player p) {
		players.remove(p);
	}

	@Override
	public void addParty(Party party) throws GamemodeFullException, PartyTooLargeException {
		for(PlayerProfile player : party.getPlayers())
		{
			players.add(player.getPlayer());
		}
	}

	@Override
	public List<Player> getPlayers() {
		return players;
	}

	@Override
	public List<String> getSignMessage() {
		List<String> message = new ArrayList<String>();
		message.add("");
		message.add(ChatColor.BOLD + "" + ChatColor.GOLD + "Lobby");
		message.add(players.size() + " players");
		return message;
	}

}
