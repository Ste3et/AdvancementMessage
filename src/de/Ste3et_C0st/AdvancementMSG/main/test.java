package de.Ste3et_C0st.AdvancementMSG.main;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.java.JavaPlugin;

import de.Ste3et_C0st.AdvancementMSG.main.AdvancementAPI.FrameType;

public class test extends JavaPlugin{
	
	private static test instance;
	
	public void onEnable(){
		instance = this;
		getCommand("ad").setExecutor(new command());
	}
	
	public void onDisable(){
		
	}
	
	public void send(String title, String description, MaterialData material, Player player){
		AdvancementAPI test = new AdvancementAPI(new NamespacedKey(getInstance(), "story/" + UUID.randomUUID().toString()))
        .withFrame(FrameType.CHALLANGE)
        .withTrigger("minecraft:impossible")
        .withIcon(material)
        .withTitle(title)
        .withDescription(description)
        .withAnnouncement(false)
		.withBackground("minecraft:textures/blocks/bedrock.png");
		test.loadAdvancement();
		test.sendPlayer(player);
		
		Bukkit.getScheduler().runTaskLater(getInstance(), new Runnable() {
			@Override
			public void run() {
				test.delete(player);
			}
		}, 1);
		
	}
	
	public static test getInstance() {
		return instance;
	}
}