package me.lightfall.piratePillage.gamemode.games;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import me.lightfall.piratePillage.arena.Arena;
import me.lightfall.piratePillage.gamemode.GamemodeFullException;
import me.lightfall.piratePillage.gamemode.PartyTooLargeException;
import me.lightfall.piratePillage.party.Party;
import net.md_5.bungee.api.ChatColor;

public class LimitedLobby extends Lobby {

	private int maxSize;
	
	public LimitedLobby(Arena arena, int maxSize) {
		super(arena);
		this.maxSize = maxSize;
	}
	
	@Override
	public void addPlayer(Player p) throws GamemodeFullException {
		if(super.getPlayers().size() < maxSize)
			super.addPlayer(p);
		else
			throw new GamemodeFullException();
	}

	@Override
	public void addParty(Party party) throws GamemodeFullException, PartyTooLargeException {
		int open = maxSize - super.getPlayers().size();
		if(party.size() <= open)
			super.addParty(party);
		else if(open == 0)
			throw new GamemodeFullException();
		else
			throw new PartyTooLargeException();
		
	}

	@Override
	public List<String> getSignMessage() {
		List<String> message = new ArrayList<String>();
		message.add("");
		message.add(ChatColor.BOLD + "" + ChatColor.GOLD + "Lobby");
		message.add(super.getPlayers().size() + " / " + maxSize);
		return message;
	}

}
