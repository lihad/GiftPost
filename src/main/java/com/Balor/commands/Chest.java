/*This file is part of GiftPost .

    GiftPost is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    GiftPost is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with GiftPost.  If not, see <http://www.gnu.org/licenses/>.*/
package com.Balor.commands;

import static com.Balor.utils.Display.chestKeeper;

import com.Balor.bukkit.GiftPost.GiftPostWorker;
import com.Balor.utils.Stacker;
import com.aranai.virtualchest.VirtualChest;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * 
 * @author Balor (aka Antoine Aflalo)
 */
public class Chest implements GPCommand {

	/**
	 * Execute commands
	 * 
	 * @param gp
	 * @param sender
	 * @param args
	 */
	public void execute(GiftPostWorker gpw, CommandSender sender, String[] args) {
		Player p = (Player) sender;
		VirtualChest v = null;
		if (args != null && args.length == 2) {
			String pName = p.getName();
			String chestName = args[1].toLowerCase();
			if (args[1].contains(":") && gpw.hasPerm((Player) sender, "giftpost.admin.open")) {
				String info[] = args[1].split(":");
				pName = info[0];
				chestName = info[1];
			}

			v = gpw.getChest(pName, chestName);
		} else
			v = gpw.getDefaultChest(p.getName());

		if (v != null) {
			if (gpw.economyCheck(p, "iConomy-openchest-price")) {
				if (gpw.getConfig().getString("auto-sort", "true").matches("true"))
					Stacker.sortChest(v);
				if (gpw.getConfig().getString("auto-stack", "true").matches("true"))
					Stacker.stackChest(v);
				if (gpw.getConfig().getString("auto-sort", "true").matches("true")
						&& gpw.getConfig().getString("auto-stack", "true").matches("true"))
					Stacker.sortChest(v);
				v.openChest((Player) sender);
			}
		} else if (args != null && args.length == 2)
			p.sendMessage(chestKeeper() + ChatColor.RED
					+ "You don't have this chest. To buy one type " + ChatColor.GOLD
					+ "/gp buy (large|normal) " + args[1].toLowerCase());
		else
			p.sendMessage(chestKeeper() + ChatColor.RED
					+ "You don't have a chest. To buy one type " + ChatColor.GOLD
					+ "/gp buy (large|normal) nameOfTheChest");
	}

	/**
	 * Validate a command to check if it should be executed
	 * 
	 * @param lwc
	 * @param command
	 * @param args
	 * @return
	 */
	public boolean validate(GiftPostWorker gpw, CommandSender sender, String[] args) {
		return ((gpw.hasFlag(args, "c") || gpw.hasFlag(args, "chest")))
				&& gpw.hasPerm((Player) sender, getPermName())
				&& GiftPostWorker.getInstance().getConfig().getString("only-sign", "false")
						.equals("false");
	}

	/**
	 * @return the name of the perm to add in the permFile.
	 */
	public String getPermName() {
		return "giftpost.chest.everywhere";
	}

	public String getHelp() {
		return ChatColor.GOLD + "/gp c (ChestName OR nothing)" + ChatColor.WHITE
				+ ": to open your chest if you don't set a ChestName, open your default chest.\n";
	}

}
