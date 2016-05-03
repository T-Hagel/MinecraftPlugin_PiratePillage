package me.lightfall.piratePillage.player;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.lightfall.piratePillage.arena.Arena;
import me.lightfall.piratePillage.party.AlreadyInPartyException;
import me.lightfall.piratePillage.party.AlreadyInvitedToPartyException;
import me.lightfall.piratePillage.party.NoCurrentInviteException;
import me.lightfall.piratePillage.party.Party;
import me.lightfall.piratePillage.party.PartyFullException;
import me.lightfall.piratePillage.utilities.ArenaPartyMessages;
import me.lightfall.piratePillage.utilities.Globals;

public class PlayerProfile {

	private Player player;
	private Party party;
	private Arena arena;
	private Party invite;

	public PlayerProfile(Player player) {
		this.player = player;
		this.party = null;
	}

	public String getName() {
		return player.getName();
	}

	public Player getPlayer() {
		return player;
	}

	public Party getParty() {
		return party;
	}

	public void setParty(Party party) {
		this.party = party;
	}

	public void removeParty() {
		this.party = null;
	}

	public void setArena(Arena arena) {
		if(arena != null && this.arena != arena) {
			if(this.arena != null) this.arena.removePlayer(this);
			this.arena = arena;
		}
	}

	public Arena getArena() {
		return this.arena;
	}
	
	public void inviteToParty(Party party) throws AlreadyInPartyException, AlreadyInvitedToPartyException {
		if(this.invite != null)
			throw new AlreadyInvitedToPartyException();
		if(this.party != null)
			throw new AlreadyInPartyException();
		
		this.invite = party;
		player.sendMessage(ArenaPartyMessages.YouHaveBeenInvitedToPartyPrefix + party.getLeader().getName() + ArenaPartyMessages.YouHaveBeenInvitedToPartySuffix);
		player.sendMessage(ArenaPartyMessages.YourInviteExpires);
		new BukkitRunnable() {

			@Override
			public void run() {
				if(invite != null) {
					player.sendMessage(ArenaPartyMessages.YourPartyInviteExpired);
					invite = null;
				}
			}
			
		}.runTaskLater(Globals.plugin, 20 * 5 /*Expires in 5 seconds (100 ticks)*/);
	}
	
	public void acceptInvite() throws NoCurrentInviteException, PartyFullException, AlreadyInPartyException {
		if(invite == null)
			throw new NoCurrentInviteException();
		Party party = invite;
		invite = null;
		party.add(this);
	}

}
