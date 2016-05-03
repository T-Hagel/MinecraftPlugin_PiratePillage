package me.lightfall.piratePillage.gamemode;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public abstract class CommunicationHandler implements Listener {

	protected Gamemode gamemode;

	public CommunicationHandler(Gamemode gamemode) {
		this.gamemode = gamemode;
	}

	@EventHandler
	public abstract void onPlayerChat(AsyncPlayerChatEvent e);
}
