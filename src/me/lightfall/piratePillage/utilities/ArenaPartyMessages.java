package me.lightfall.piratePillage.utilities;

import net.md_5.bungee.api.ChatColor;

public final class ArenaPartyMessages {

	
	// # # # # # # # # # # # //
	// #	Arena/Party	   # //
	// # # # # # # # # # # # //
	
	/*
	 *	Normal message format:
	 *		ArenaPrefix + " " + ArenaSuffix;
	 *
	 *  Message format with player names:
	 *  ...prefix = ArenaPrefix + " " + ArenaPlayerPrefix;
	 *  ...suffix = ArenaPlayerSuffix + " " + ArenaSuffix;
	 */
	
	
	public static String
		ArenaPlayerPrefix = ChatColor.RESET.toString(),
		ArenaPlayerSuffix = ChatColor.GREEN.toString(),
		ArenaPrefix = ChatColor.DARK_RED + "[" + ChatColor.GOLD + "Party" + ChatColor.DARK_RED + "] " + ArenaPlayerSuffix,
		ArenaSuffix = "" + ArenaPlayerPrefix;
	;
	
	public static String
	ArenaFull = ArenaPrefix + "The arena is full." + ArenaSuffix,
	EnterPlayerName = ArenaPrefix + "You need to enter a player's name!" + ArenaSuffix,

	InviteSentToPrefix = ArenaPrefix + "Invite sent to " + ArenaPlayerPrefix,
	InviteSendToSuffix = ArenaPlayerSuffix + "." + ArenaSuffix,
	
	PartyFull = ArenaPrefix + "The party is full." + ArenaSuffix,
	PartyHasBeenDissolved = ArenaPrefix + "Your party has been dissolved!" + ArenaSuffix,
	PartyTooLarge = ArenaPrefix + "Your party is too large for this arena." + ArenaSuffix,
	PartyFullCannotInvite = ArenaPrefix + "Your party is full! You cannot invite any more players." + ArenaSuffix,
	
	PlayerAlreadyInParty = ArenaPrefix + "They are already in a party!" + ArenaSuffix,
	PlayerAlreadyInvited = ArenaPrefix + "They already have a party invite." + ArenaSuffix,
	PlayerHasJoinedPartyPrefix = ArenaPrefix + "" + ArenaPlayerPrefix,
	PlayerHasJoinedPartySuffix = ArenaPlayerSuffix + " has joined your party." + ArenaSuffix,
	PlayerNotFound = ArenaPrefix + "Player not found." + ArenaSuffix,
	PlayerHasLeftYourPartyPrefix = ArenaPrefix + "" + ArenaPlayerPrefix,
	PlayerHasLeftYourPartySuffix = ArenaPlayerSuffix + " has left your party." + ArenaSuffix,

	YouAlreadyInParty = ArenaPrefix + "You are already in a party!" + ArenaSuffix,
	YouCannotSendInvites = ArenaPrefix + "You are not the party leader. You cannot send invites." + ArenaSuffix,
	YouHaveBeenAddedToPrefix = ArenaPrefix + "You have been added to " + ArenaPlayerPrefix,
	YouHaveBeenAddedToSuffix = ArenaPlayerSuffix + "'s party!" + ArenaSuffix,
	YouHaveBeenInvitedToPartyPrefix = ArenaPrefix + "You have been invited to " + ArenaPlayerPrefix,
	YouHaveBeenInvitedToPartySuffix = ArenaPlayerSuffix + "'s party!" + ArenaSuffix,
	YouRemovedFromParty = ArenaPrefix + "You have been removed from your party." + ArenaSuffix,
	YouNotInParty = ArenaPrefix + "You are not in a party!" + ArenaSuffix,
	YouNotPartyLeader = ArenaPrefix + "You are not the party leader." + ArenaSuffix,
	
	YourInviteExpires = ArenaPrefix + "Your invite will expire in 5 seconds." + ArenaSuffix,
	YourPartyInviteExpired = ArenaPrefix + "Your invite has expired." + ArenaSuffix,
	
	
	MaxPartySizeIs = ArenaPrefix + "Max party size is " + ArenaSuffix,
	
	NoCurrentInvites = ArenaPrefix + "You have no invites waiting." + ArenaSuffix,

	PartyCreated = ArenaPrefix + "Party created!" + ArenaSuffix,
	UnableToCreateParty = ArenaPrefix + "Unable to create party." + ArenaSuffix
		
	;
	
	
	// # # # # # # # # # # # //
	
	
	
	
	
	private ArenaPartyMessages() {}
}
