package me.techygaming.mutechat.cmds;

import me.techygaming.mutechat.Main;
import me.techygaming.mutechat.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class cmd_mutechat implements CommandExecutor {

    private Main plugin;
    public static boolean isChatMuted = false;

    public cmd_mutechat (Main plugin){
        this.plugin = plugin;
        plugin.getCommand("mutechat").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender instanceof Player){
            if (!sender.hasPermission(plugin.getConfig().getString("permission-nodes.mutechat"))){
                sender.sendMessage(Utils.chat("&cYou do not have permission to use this command."));
                return false;
            }
        }

        if (!isChatMuted){
            isChatMuted = true;
            Bukkit.broadcastMessage(Utils.chat(plugin.getMessagesYml().getString("mute-chat.mute.broadcast").replace("%player%", sender.getName())));
            for (Player currentPlayer : Bukkit.getOnlinePlayers()){
                if (currentPlayer.hasPermission(plugin.getConfig().getString("permission-nodes.staff-notifications"))){
                    currentPlayer.sendMessage(Utils.chat(plugin.getMessagesYml().getString("mute-chat.mute.staff").replace("%player%", sender.getName())));
                }
            }
            return true;
        } else{
            isChatMuted = false;
            Bukkit.broadcastMessage(Utils.chat(plugin.getMessagesYml().getString("mute-chat.unmute.broadcast").replace("%player%", sender.getName())));
            for (Player currentPlayer : Bukkit.getOnlinePlayers()){
                if (currentPlayer.hasPermission(plugin.getConfig().getString("permission-nodes.staff-notifications"))){
                    currentPlayer.sendMessage(Utils.chat(plugin.getMessagesYml().getString("mute-chat.unmute.staff").replace("%player%", sender.getName())));
                }
            }
            return true;
        }
    }
}
