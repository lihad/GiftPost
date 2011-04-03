/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Balor.bukkit.GiftPost;

import com.Balor.commands.*;
import java.util.logging.Logger;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.Plugin;
import static com.Balor.utils.Display.sendHelp;
//Permissions imports
import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

/**
 *
 * @author Antoine
 */
public class GiftPost extends JavaPlugin
{

    public static final Logger log = Logger.getLogger("Minecraft");
    private GiftPostWorker gpw;
    /**
     * Permission plugin
     */
    private static PermissionHandler Permissions = null;

    /**
     * Checks that Permissions is installed.
     */
    private void setupPermissions()
    {

        Plugin perm_plugin = this.getServer().getPluginManager().getPlugin("Permissions");
        PluginDescriptionFile pdfFile = this.getDescription();

        if (Permissions == null)
            if (perm_plugin != null)
            {
                //Permissions found, enable it now
                this.getServer().getPluginManager().enablePlugin(perm_plugin);
                Permissions = ((Permissions) perm_plugin).getHandler();
                log.info("[" + pdfFile.getName() + "]" + " (version " + pdfFile.getVersion() + ") Enabled with Permissions.");
            } else
            {
                log.info("[" + pdfFile.getName() + "]" + " (version " + pdfFile.getVersion() + ") Enables without Permissions.");
                log.info("[" + pdfFile.getName() + "]" + " Commands are free for all");
            }
    }

    private void registerCommand(Class<?> clazz)
    {
        try
        {
            GPCommand command = (GPCommand) clazz.newInstance();
            gpw.getCommands().add(command);
        } catch (InstantiationException e)
        {
            e.printStackTrace();
        } catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
    }

    void registerCommands()
    {
        registerCommand(Chest.class);
    }

    @Override
    public void onEnable()
    {
        setupPermissions();
        gpw = new GiftPostWorker(Permissions);
        registerCommands();
    }

    @Override
    public void onDisable()
    {
        PluginDescriptionFile pdfFile = this.getDescription();
        log.info("[" + pdfFile.getName() + "]" + " Plugin Disabled. (version" + pdfFile.getVersion() + ")");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args)
    {
        if (!(sender instanceof Player))
        {
            sender.sendMessage(ChatColor.RED + "You have to be a player!");
            return true;
        } else
        {
            if (args.length == 0)
            {
                sendHelp(sender);
                return true;
            }
            int i = gpw.getCommands().size();
            for (GPCommand cmd : gpw.getCommands())
            {
                if (!cmd.validate(gpw, sender, args))
                {
                    i--;
                    continue;
                }
                try
                {
                    cmd.execute(gpw, sender, args);
                } catch (Exception e)
                {
                    log.info("A GiftPost command threw an exception!");
                    e.printStackTrace();
                }

                return true;
            }
            if(i==0)
                sendHelp(sender);
        }
        return true;
    }
}