package me.lightfall.piratePillage.commandHandlers;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.lightfall.piratePillage.party.AlreadyInPartyException;
import me.lightfall.piratePillage.party.AlreadyInvitedToPartyException;
import me.lightfall.piratePillage.party.NoCurrentInviteException;
import me.lightfall.piratePillage.party.Party;
import me.lightfall.piratePillage.party.PartyFullException;
import me.lightfall.piratePillage.party.PartyPlayerNotFoundException;
import me.lightfall.piratePillage.player.PlayerProfile;
import me.lightfall.piratePillage.utilities.Globals;
import me.lightfall.piratePillage.utilities.ArenaPartyMessages;

public class PartyCommandHandler implements CommandExecutor {

	public static PartyCommandHandler handler = new PartyCommandHandler();

	private PartyCommandHandler() {
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(args.length == 0) {
				createParty(player);
			}
			if(args.length > 0) {
				PlayerProfile profile = Globals.getProfile(player.getUniqueId());
				Party party = profile.getParty();
				switch(args[0].toLowerCase()) {
				case "leave":
					if(party == null)
						player.sendMessage(ArenaPartyMessages.YouNotInParty);
					else {
						try {
							party.remove(profile);
						} catch (PartyPlayerNotFoundException e) {
							player.sendMessage(ArenaPartyMessages.YouNotInParty);
						}
						player.sendMessage(ArenaPartyMessages.YouRemovedFromParty);
						if(party.getLeader() == null)
							party.dissolve(true);
					}
				break;
				case "list":
					if(party == null)
						player.sendMessage(ArenaPartyMessages.YouNotInParty);
					else {
						player.sendMessage(ArenaPartyMessages.ArenaPrefix + "Leader: " + ArenaPartyMessages.ArenaPlayerPrefix + party.getLeader().getName()
												+ ArenaPartyMessages.ArenaPlayerSuffix + ArenaPartyMessages.ArenaSuffix);
						for(PlayerProfile p : party.getPlayers()) {
							if(p != party.getLeader())
								player.sendMessage(ArenaPartyMessages.ArenaPrefix + " - " + ArenaPartyMessages.ArenaPlayerPrefix + p.getName()
								+ ArenaPartyMessages.ArenaPlayerSuffix + ArenaPartyMessages.ArenaSuffix);
						}
					}
				break;
				case "create":
					createParty(player);
				break;
				case "invite":
					if(party == null) {
						createParty(player);
						party = profile.getParty();
					}
					if(party.getLeader().getPlayer() != player) {
						player.sendMessage(ArenaPartyMessages.YouCannotSendInvites);
						return false;
					}
					if(party.isFull()) {
						player.sendMessage(ArenaPartyMessages.PartyFullCannotInvite);
						return false;
					}
					if(args.length < 2) {
						player.sendMessage(ArenaPartyMessages.EnterPlayerName);
						return false;
					} else {
						Player invitee = Bukkit.getPlayer(args[1]);
						if(invitee == null)
						{
							player.sendMessage(ArenaPartyMessages.PlayerNotFound);
							return false;
						}
						try {
							Globals.getProfile(invitee.getUniqueId()).inviteToParty(party);
							player.sendMessage(ArenaPartyMessages.InviteSentToPrefix + invitee.getName() + ArenaPartyMessages.InviteSendToSuffix);
						} catch (AlreadyInPartyException e) {
							player.sendMessage(ArenaPartyMessages.PlayerAlreadyInParty);
							return false;
						} catch (AlreadyInvitedToPartyException e) {
							player.sendMessage(ArenaPartyMessages.PlayerAlreadyInvited);
							return false;
						}
						return true;
					}
			//	break;
				case "accept":
					try {
						profile.acceptInvite();
						player.sendMessage(ArenaPartyMessages.YouHaveBeenAddedToPrefix + profile.getParty().getLeader().getName() + ArenaPartyMessages.YouHaveBeenAddedToSuffix);
					} catch (NoCurrentInviteException e) {
						player.sendMessage(ArenaPartyMessages.NoCurrentInvites);
						return false;
					} catch (PartyFullException e) {
						player.sendMessage(ArenaPartyMessages.PartyFull);
						return false;
					} catch (AlreadyInPartyException e) {
						player.sendMessage(ArenaPartyMessages.YouAlreadyInParty);
						return false;
					}
					return true;
			//	break;
				default:
					player.sendMessage("Unknown command");
					return false;
				}
			}
		}
		return true;
	}
		
	
	private void createParty(Player player) {
		PlayerProfile profile = Globals.getProfile(player.getUniqueId());
		if(profile.getParty() != null)
			player.sendMessage(ArenaPartyMessages.YouAlreadyInParty);
		else {
			try {
				new Party(6, profile);
				player.sendMessage(ArenaPartyMessages.PartyCreated);
			} catch(PartyFullException | AlreadyInPartyException e) {
				player.sendMessage(ArenaPartyMessages.UnableToCreateParty);
			}
		}
	}
}
