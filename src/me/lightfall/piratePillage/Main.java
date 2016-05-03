package me.lightfall.piratePillage;

import java.sql.SQLException;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import me.lightfall.piratePillage.arena.Arena;
import me.lightfall.piratePillage.commandHandlers.MainCommandHandler;
import me.lightfall.piratePillage.commandHandlers.PartyCommandHandler;
import me.lightfall.piratePillage.gamemode.games.LimitedLobby;
import me.lightfall.piratePillage.gamemode.games.Lobby;
import me.lightfall.piratePillage.party.Party;
import me.lightfall.piratePillage.player.PlayerProfile;
import me.lightfall.piratePillage.utilities.Globals;
import me.lightfall.piratePillage.utilities.MainEventHandler;
import me.lightfall.piratePillage.utilities.SQLConnection;

public class Main extends JavaPlugin {

	public Main() {

	}

	@Override
	public void onEnable() {
		Globals.plugin = this;
		Globals.lobby = new Arena();
		Globals.lobby.setGamemode(new Lobby(Globals.lobby));

		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvents(new MainEventHandler(), this);
		getCommand("pp").setExecutor(MainCommandHandler.handler);
		getCommand("party").setExecutor(PartyCommandHandler.handler);
		
		// Update arena signs
		new BukkitRunnable() {
			@Override
			public void run() {
				updateSigns();
			}
		}.runTaskTimerAsynchronously(this, 0, 20 * 1 /* Update every second (20 ticks)*/);
		
		
		// Run "loginEvent" for everyone currently on
		for(Player player : Bukkit.getOnlinePlayers())
			MainEventHandler.playerLogin(player);
				
		String
		url = "127.0.0.7:3306",
		user = "root",
		password = "dragon",
		database = "mc";
		
		try {
			Globals.database = new SQLConnection(url, user, password, database);
			Globals.database.connect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onDisable() {
		Globals.database = null;
	}
	

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(args.length == 0) {
				
			}
			if(args.length > 0) {
				PlayerProfile profile = Globals.getProfile(player.getUniqueId());
				Party party = profile.getParty();
				Location loc = ((Player) sender).getLocation();
				Material mat = loc.getBlock().getType();
				switch(args[0].toLowerCase()) {
				case "lobby":
					if(mat == Material.WALL_SIGN || mat == Material.SIGN_POST || mat == Material.SIGN) {
						Globals.lobby.setSign(loc.getBlock());
					}
				break;
				case "new":
					if(mat == Material.WALL_SIGN || mat == Material.SIGN_POST || mat == Material.SIGN) {
						Arena arena = new Arena();
						if(args.length >= 2)
							arena.setGamemode(new LimitedLobby(arena, Integer.parseInt(args[1])));
						else
							arena.setGamemode(new Lobby(arena));
						arena.setSign(loc.getBlock());
						Globals.arenas.add(arena);
						player.sendMessage("Arena created");
					}
				break;
				case "leave":
					MainEventHandler.playerQuit(player);
				break;
				default:
				}
			}
		}
		return false;
	}
	
	
	private void updateSigns() {
		if(Globals.lobby.getSign() != null)
		{
			List<String> msg = Globals.lobby.getSignMessage();
			int i = 0;
			Sign sign = Globals.lobby.getSign();
			for(String string : msg) {
				sign.setLine(i++, string);
			}
			sign.update();
		}
		
		for(Arena arena : Globals.arenas) {
			if(arena.getSign() != null) {
				List<String> msg = arena.getSignMessage();
				int i = 0;
				Sign sign = arena.getSign();
				for(String string : msg) {
					sign.setLine(i++, string);
				}
				sign.update();
			}
		}
		
	}

}
