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

package com.Balor.utils;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 *
 * @author Balor
 */
public class Display {
   public static void sendHelp(CommandSender sender)
    {
        sender.sendMessage(ChatColor.AQUA+"Gift Post \n"
                + "-------\n"
                +ChatColor.GOLD +"/gp chest "+
                ChatColor.WHITE+": to open your chest\n"
               +ChatColor.GOLD  + "/gp send player "+
               ChatColor.WHITE+": send the content of your chest to the player's chest.");
    }

}