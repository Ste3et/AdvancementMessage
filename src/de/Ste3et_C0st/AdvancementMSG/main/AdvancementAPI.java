package de.Ste3et_C0st.AdvancementMSG.main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.minecraft.server.v1_12_R1.Item;
import net.minecraft.server.v1_12_R1.MinecraftKey;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_12_R1.util.CraftMagicNumbers;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.List;
import java.util.Objects;

import com.google.common.collect.Lists;

/**
 * Created by charliej on 14/05/2017.
 * Edited by GiansCode
 */
public class AdvancementAPI {

	private NamespacedKey id;
    private String title, parent, trigger, icon, description, background, frame;
    private Integer subID = 0;
    private boolean announce;
    private List<ItemStack> items;

	public static enum FrameType{
		CHALLANGE("challenge"),
		GOAL("goal"),
		DEFAULT("task");
		
		private String str;
		
		FrameType(String str){
			this.str = str;
		}
		
		public String getName(){return this.str;}
	}
    
    AdvancementAPI(NamespacedKey id) {
        this.id = id;
        this.items = Lists.newArrayList();
        this.announce = true;
    }

    public NamespacedKey getID() {
        return id;
    }

    public String getIcon() {
        return icon;
    }

    public AdvancementAPI withIcon(String icon) {
        this.icon = icon;
        return this;
    }
    
    public AdvancementAPI withIcon(Material material){
    	this.icon = getMinecraftIDFrom(new ItemStack(material));
    	return this;
    }
    
    @SuppressWarnings("deprecation")
	public AdvancementAPI withIcon(MaterialData material){
    	this.icon = getMinecraftIDFrom(new ItemStack(material.getItemType()));
    	this.subID = (int) material.getData();
    	return this;
    }
    
    public AdvancementAPI withIconData(int subID){
    	this.subID = subID;
    	return this;
    }

    public String getDescription() {
        return description;
    }

    public AdvancementAPI withDescription(String description) {
        this.description = description;
        return this;
    }

    public String getBackground() {
        return background;
    }

    public AdvancementAPI withBackground(String url) {
        this.background = url;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public AdvancementAPI withTitle(String title) {
        this.title = title;
        return this;
    }

    public String getParent() {
        return parent;
    }

    public AdvancementAPI withParent(String parent) {
        this.parent = parent;
        return this;
    }

    public String getTrigger() {
        return trigger;
    }

    public AdvancementAPI withTrigger(String trigger) {
        this.trigger = trigger;
        return this;
    }

    public List<ItemStack> getItems() {
        return items;
    }

    public AdvancementAPI withItem(ItemStack is) {
        items.add(is);
        return this;
    }

    public String getFrame() {
        return frame;
    }

    public AdvancementAPI withFrame(FrameType frame) {
        this.frame = frame.getName();
        return this;
    }

    public boolean getAnnouncement(){
        return announce;
    }
    public AdvancementAPI withAnnouncement(boolean announce){
        this.announce = announce;
        return this;
    }

    @SuppressWarnings("unchecked")
	public String getJSON() {
        JSONObject json = new JSONObject();


        JSONObject icon = new JSONObject();
        icon.put("item", getIcon());
        icon.put("data", getIconSubID());

        JSONObject display = new JSONObject();
        display.put("icon", icon);
        display.put("title", getTitle());
        display.put("description", getDescription());
        display.put("background", getBackground());
        display.put("frame", getFrame());
        display.put("announce_to_chat", getAnnouncement());


        json.put("parent", getParent());

        JSONObject criteria = new JSONObject();
        JSONObject conditions = new JSONObject();
        JSONObject elytra = new JSONObject();

        JSONArray itemArray = new JSONArray();
        JSONObject itemJSON = new JSONObject();

        for(ItemStack i : getItems()) {
            itemJSON.put("item", "minecraft:"+ i.getType().name().toLowerCase());
            itemJSON.put("amount", i.getAmount());
            itemArray.add(itemJSON);
        }

        /**
         * TODO
         * define each criteria, for each criteria in list,
         * add items, trigger and conditions
         */

        conditions.put("items", itemArray);
        elytra.put("trigger", getTrigger());
        elytra.put("conditions", conditions);

        criteria.put("elytra", elytra);

        json.put("criteria", criteria);
        json.put("display", display);


        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String prettyJson = gson.toJson(json);

        return prettyJson;
    }
    
    private int getIconSubID(){
		return this.subID;
	}

	@SuppressWarnings("deprecation")
	public void loadAdvancement(){
    	CraftMagicNumbers.INSTANCE.loadAdvancement(getID(), getJSON());
    }
    
    @SuppressWarnings("deprecation")
	public void delete(Player ... player){
    	for(Player p : player){
    		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "advancement revoke " + p.getName() +" only " + getID());
    	}
    	CraftMagicNumbers.INSTANCE.removeAdvancement(getID());
    }
    
    public static String getMinecraftIDFrom(ItemStack stack) {
	    final int check = Item.getId(CraftItemStack.asNMSCopy(stack).getItem());
	    final MinecraftKey matching = Item.REGISTRY.keySet()
	        .stream()
	        .filter(key -> Item.getId(Item.REGISTRY.get(key)) == check)
	        .findFirst().orElse(null);
	    return Objects.toString(matching, null);
	}
    
    public void sendPlayer(Player ... player){
    	for(Player p : player){
    		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "advancement grant " + p.getName() +" only " + getID());
    	}
    }
}