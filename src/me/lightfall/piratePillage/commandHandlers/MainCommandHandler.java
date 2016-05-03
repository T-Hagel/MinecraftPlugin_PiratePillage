package me.lightfall.piratePillage.commandHandlers;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MainCommandHandler implements CommandExecutor {

	public static MainCommandHandler handler = new MainCommandHandler();

	private MainCommandHandler() {
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			
		}
		return false;
	}

}
