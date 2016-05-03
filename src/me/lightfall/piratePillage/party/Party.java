package me.lightfall.piratePillage.party;

import java.util.ArrayList;
import java.util.List;

import me.lightfall.piratePillage.player.PlayerProfile;
import me.lightfall.piratePillage.utilities.ArenaPartyMessages;

/**
 * Glorified list
 * 
 * @author Lightfall
 *
 */
public class Party {

	private List<PlayerProfile> party;
	private PlayerProfile leader;
	private int maxSize;

	public Party(int maxSize, PlayerProfile leader) throws PartyFullException, AlreadyInPartyException {
		this.party = new ArrayList<PlayerProfile>();
		this.leader = leader;
		this.maxSize = maxSize;
		add(leader);
	}

	public int size() {
		return party.size();
	}

	public void add(PlayerProfile player) throws PartyFullException, AlreadyInPartyException {
		if (party.size() < maxSize) {
			if(player.getParty() == null) {
				messageParty(ArenaPartyMessages.PlayerHasJoinedPartyPrefix + player.getName() + ArenaPartyMessages.PlayerHasJoinedPartySuffix);
				party.add(player);
				player.setParty(this);
			} else {
				throw new AlreadyInPartyException();
			}
		} else {
			throw new PartyFullException(ArenaPartyMessages.MaxPartySizeIs + maxSize);
		}
	}

	public void remove(PlayerProfile player) throws PartyPlayerNotFoundException {
		if (party.contains(player)) {
			party.remove(player);
			player.removeParty();
		} else {
			throw new PartyPlayerNotFoundException(player.getName() + " is not this this party");
		}
		if(leader == player)
			leader = null;
		else
			messageParty(ArenaPartyMessages.PlayerHasLeftYourPartyPrefix + player.getName() + ArenaPartyMessages.PlayerHasLeftYourPartySuffix);
	}
	
	public void dissolve(boolean displayMessage) {
		if(displayMessage)
			for(PlayerProfile profile : party) {
				profile.removeParty();
				profile.getPlayer().sendMessage(ArenaPartyMessages.PartyHasBeenDissolved);
			}
		party = null;
		leader = null;
	}

	public List<PlayerProfile> getPlayers() {
		return party;
	}
	
	public PlayerProfile getLeader() {
		return leader;
	}
	
	private void messageParty(String message) {
		for(PlayerProfile profile : party) {
			profile.getPlayer().sendMessage(message);
		}
	}
	
	public boolean isFull() {
		return (party.size() >= maxSize);
	}
}
