package de.Ste3et_C0st.AdvancementMSG.main;

import java.util.Arrays;

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
		if(cmd.getName().equalsIgnoreCase("ad")){
			//ad say <material> <player> <msg>
			if(args.length > 1){
				if(args[0].equalsIgnoreCase("say")){
					if(args.length >= 3){
						MaterialData material = null;
						try{
							if(args[1].contains(":")){
								Integer i = Integer.parseInt(args[1].split(":")[0]);
								material = new MaterialData(Material.getMaterial(i), (byte) Integer.parseInt(args[1].split(":")[1]));
							}else{
								material = new MaterialData(Material.getMaterial(args[1]), (byte) 0);
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
						test.getInstance().send(title, "505 Title not found", material, (Player) sender);
					}
				}
			}
			return true;
		}
		return false;
	}

}
