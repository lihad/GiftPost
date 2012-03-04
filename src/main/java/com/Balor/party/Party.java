/************************************************************************
 * This file is part of GiftPost.									
 *																		
 * GiftPost is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by	
 * the Free Software Foundation, either version 3 of the License, or		
 * (at your option) any later version.									
 *																		
 * GiftPost is distributed in the hope that it will be useful,	
 * but WITHOUT ANY WARRANTY; without even the implied warranty of		
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the			
 * GNU General Public License for more details.							
 *																		
 * You should have received a copy of the GNU General Public License
 * along with GiftPost.  If not, see <http://www.gnu.org/licenses/>.
 ************************************************************************/
package com.Balor.party;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.aranai.virtualchest.ChestType;
import com.aranai.virtualchest.VirtualChest;
import com.aranai.virtualchest.VirtualLargeChest;

/**
 * @author Balor (aka Antoine Aflalo)
 * 
 */
public class Party implements ConfigurationSerializable {
	private final String name;
	private final VirtualChest chest;
	private final Set<String> players = new HashSet<String>();
	private String owner;
	private long emptyTimestamp = -1;
	static {
		ConfigurationSerialization.registerClass(Party.class);
	}

	/**
	 * @param name
	 * @param chest
	 */
	public Party(String name, ChestType type) {
		super();
		this.name = name;
		if (type == ChestType.NORMAL)
			this.chest = new VirtualChest(name);
		else
			this.chest = new VirtualLargeChest(name);
	}

	public void addPlayer(String player) {
		players.add(player);
	}

	public void removePlayer(String player) throws OwnerLeavingException {
		if (player.equals(owner))
			throw new OwnerLeavingException();
		players.remove(player);
		if (players.isEmpty())
			emptyTimestamp = System.currentTimeMillis();
	}

	public boolean hasAccess(Player player) {
		return players.contains(player.getName());
	}

	public void openChest(Player p) {
		chest.openChest(p);
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the chest
	 */
	public VirtualChest getChest() {
		return chest;
	}

	/**
	 * @return the players
	 */
	public Set<String> getPlayers() {
		return players;
	}

	/**
	 * @return the owner
	 */
	public String getOwner() {
		return owner;
	}

	/**
	 * @param owner
	 *            the owner to set
	 */
	public void setOwner(String owner) {
		if (players.contains(owner))
			this.owner = owner;
	}

	/**
	 * @return the emptyTimestamp
	 */
	public long getEmptyTimestamp() {
		return emptyTimestamp;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.bukkit.configuration.serialization.ConfigurationSerializable#serialize
	 * ()
	 */
	public Map<String, Object> serialize() {
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		result.put("name", name);
		result.put("owner", owner);
		result.put("players", players);
		result.put("timeStamp", emptyTimestamp);
		result.put("chestType", chest.getType());
		ItemStack[] items = chest.getContents();
		List<ItemStack> itemList = new ArrayList<ItemStack>(items.length);
		for (ItemStack item : items)
			if (item != null)
				itemList.add(item);
		result.put("items", itemList);
		return result;
	}

	@SuppressWarnings("unchecked")
	public static Party deserialize(Map<String, Object> map) {
		Party result = new Party((String) map.get("name"), (ChestType) map.get("chestType"));
		for (String player : (Collection<String>) map.get("players"))
			result.addPlayer(player);
		result.setOwner((String) map.get("owner"));
		result.emptyTimestamp = Long.parseLong(map.get("timeStamp").toString());
		Collection<ItemStack> items = (Collection<ItemStack>) map.get("items");
		for (ItemStack item : items)
			result.getChest().addItem(item);
		return result;
	}
}
