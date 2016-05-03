package me.lightfall.piratePillage.arena;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import me.lightfall.piratePillage.gamemode.GamemodeFullException;
import me.lightfall.piratePillage.gamemode.PartyTooLargeException;
import me.lightfall.piratePillage.party.Party;
import me.lightfall.piratePillage.party.PartyPlayerNotFoundException;
import me.lightfall.piratePillage.player.PlayerProfile;
import me.lightfall.piratePillage.utilities.Globals;
import me.lightfall.piratePillage.utilities.ArenaPartyMessages;

public class ArenaSignListener implements Listener {

	private Arena arena;
	
	public ArenaSignListener(Arena arena) {
		this.arena = arena;
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		if(arena == null || arena.getSign() == null || !e.getAction().equals(Action.RIGHT_CLICK_BLOCK))
			return;
		if(e.getClickedBlock().getLocation().equals(arena.getSign().getLocation())) {
			// Player clicked on the arena's sign
			
			PlayerProfile profile = Globals.getProfile(e.getPlayer().getUniqueId());
			// Already in this arena!
			if(profile.getArena() == arena)
				return;
			
			Party party = profile.getParty();
			if(party != null) {
				if(party.getLeader() == profile) {
					// Party leader tried to join the arena
					try {
						arena.addParty(party);
						party.dissolve(true);
					} catch (GamemodeFullException ex) {
						e.getPlayer().sendMessage(ArenaPartyMessages.ArenaFull);
						return;
					} catch (PartyTooLargeException ex) {
						e.getPlayer().sendMessage(ArenaPartyMessages.PartyTooLarge);
						return;
					}
					
				} else {
					// Player is not party leader
					try {
						arena.addPlayer(profile);
						party.remove(profile);
						e.getPlayer().sendMessage(ArenaPartyMessages.YouRemovedFromParty);
					} catch (GamemodeFullException ex) {
						e.getPlayer().sendMessage(ArenaPartyMessages.ArenaFull);
						return;
					} catch (PartyPlayerNotFoundException ex) {
						// Party couldn't find this player - no big deal
						//	Do nothing
					}
					
				}
			} else {
				// No party, just add them to the arena
				try {
					arena.addPlayer(profile);
				} catch (GamemodeFullException e1) {
					e.getPlayer().sendMessage(ArenaPartyMessages.ArenaFull);
					return;
				}
			}
		}
	}
}
