package me.lightfall.piratePillage.gamemode.communications;

import java.util.List;
import java.util.logging.Level;

import org.bukkit.craftbukkit.v1_8_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import me.lightfall.piratePillage.gamemode.CommunicationHandler;
import me.lightfall.piratePillage.gamemode.Gamemode;
import me.lightfall.piratePillage.utilities.Globals;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_8_R2.IChatBaseComponent;
import net.minecraft.server.v1_8_R2.PacketPlayOutChat;
import net.minecraft.server.v1_8_R2.IChatBaseComponent.ChatSerializer;

public class FreeCommunication extends CommunicationHandler {
	
	public FreeCommunication(Gamemode gamemode) {
		super(gamemode);
	}

	@Override
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e) {
		List<Player> players = super.gamemode.getPlayers();
		if(!players.contains(e.getPlayer()))
			return;

//		e.getRecipients().clear();
//		e.getRecipients().addAll(players);
		
		e.setCancelled(true);
		Globals.plugin.getLogger().log(Level.INFO, "[" + e.getPlayer().getDisplayName() + "] " + e.getMessage());
		for(Player player : players)
		{
			player.sendMessage("[" + e.getPlayer().getDisplayName() + "] " + e.getMessage());
		}
		
	}

}
