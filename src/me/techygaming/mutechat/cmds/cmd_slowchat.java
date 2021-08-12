package me.techygaming.mutechat.cmds;

import me.techygaming.mutechat.Main;
import me.techygaming.mutechat.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class cmd_slowchat implements CommandExecutor {

    private Main plugin;
    public static boolean isChatSlowed = false;
    public static int slowSeconds;

    public cmd_slowchat(Main plugin){
        this.plugin = plugin;
        plugin.getCommand("slowchat").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender instanceof Player){
            if (!sender.hasPermission(plugin.getConfig().getString("permission-nodes.slowchat"))){
                sender.sendMessage(Utils.chat("&cYou do not have permission to use this command."));
                return false;
            }
        }

        if (args.length == 0){
            if (isChatSlowed){
                isChatSlowed = false;
                Bukkit.broadcastMessage(Utils.chat(plugin.getMessagesYml().getString("slow-chat.disable.broadcast")
                        .replace("%player%", sender.getName())));
                for (Player currentPlayer : Bukkit.getOnlinePlayers()){
                    if (currentPlayer.hasPermission(plugin.getConfig().getString("permission-nodes.staff-notifications"))){
                        currentPlayer.sendMessage(Utils.chat(plugin.getMessagesYml().getString("slow-chat.disable.staff")
                                .replace("%player%", sender.getName())));
                    }
                }
                return true;
            } else{
                sender.sendMessage(Utils.chat("&cUsage: /slowchat <Seconds>"));
                return false;
            }

        }

        if (args.length == 1){
            try{
                slowSeconds = Integer.parseInt(args[0]);
            } catch(IllegalArgumentException e){
                sender.sendMessage(Utils.chat("&cPlease supply a valid number."));
                return false;
            }


            if (!isChatSlowed){
                isChatSlowed = true;
                Bukkit.broadcastMessage(Utils.chat(plugin.getMessagesYml().getString("slow-chat.enable.broadcast")
                        .replace("%player%", sender.getName())
                        .replace("%seconds%", args[0])));
                for (Player currentPlayer : Bukkit.getOnlinePlayers()){
                    if (currentPlayer.hasPermission(plugin.getConfig().getString("permission-nodes.staff-notifications"))){
                        currentPlayer.sendMessage(Utils.chat(plugin.getMessagesYml().getString("slow-chat.enable.staff")
                                .replace("%player%", sender.getName())
                                .replace("%seconds%", args[0])));
                    }
                }
                return true;
            } else{
                isChatSlowed = false;
                Bukkit.broadcastMessage(Utils.chat(plugin.getMessagesYml().getString("slow-chat.disable.broadcast")
                        .replace("%player%", sender.getName())));
                for (Player currentPlayer : Bukkit.getOnlinePlayers()){
                    if (currentPlayer.hasPermission(plugin.getConfig().getString("permission-nodes.staff-notifications"))){
                        currentPlayer.sendMessage(Utils.chat(plugin.getMessagesYml().getString("slow-chat.disable.staff")
                                .replace("%player%", sender.getName())));
                    }
                }
                return true;
            }


        }

        sender.sendMessage(Utils.chat("&cUsage: /slowchat <Seconds>"));
        return false;

    }
}
