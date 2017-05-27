package de.Ste3et_C0st.AdvancementMSG.main;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;

public class command implements CommandExecutor  {

	@SuppressWarnings({ "deprecation", "unused" })
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		//ad say <material> <player> <msg>
		if(cmd.getName().equalsIgnoreCase("ad")){
			if(args.length > 1){
				if(args[0].equalsIgnoreCase("say")){
					if(args.length >= 4){
						MaterialData material = null;
						try{
							if(args[1].contains(":")){
								Integer i = Integer.parseInt(args[1].split(":")[0]);
								material = new MaterialData(Material.getMaterial(i), (byte) Integer.parseInt(args[1].split(":")[1]));
							}else{
								material = new MaterialData(Material.getMaterial(Integer.parseInt(args[1])), (byte) 0);
							}
						}catch(NumberFormatException ex){
							material = new MaterialData(Material.getMaterial(args[1]));
						}
						
						if(material == null){
							material = new MaterialData(Material.DIAMOND);
						}
						
						String title = "";
						for(String str : Arrays.copyOfRange(args, 3, args.length)){
							title += str + " ";
						}
						
						
						title = title.substring(0, title.length() - 1);
						title = ChatColor.translateAlternateColorCodes('&', title);
						
						if(args[2].equalsIgnoreCase("global")){
							Player[] player = new Player[Bukkit.getOnlinePlayers().size()];
							player = Bukkit.getOnlinePlayers().toArray(player);
							test.getInstance().send(title, "505 Title not found", material, player);
						}else{
							Player player = Bukkit.getPlayer(args[2]);
							if(player == null || !player.isOnline()){
								sender.sendMessage("the player " + args[2] + " is not online");
								return true;
							}else{
								test.getInstance().send(title, "505 Title not found", material, player);
							}
						}
					}
				}
			}
			return true;
		}
		return false;
	}

}
