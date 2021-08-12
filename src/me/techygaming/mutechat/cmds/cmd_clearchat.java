package me.techygaming.mutechat.cmds;

import me.techygaming.mutechat.Main;
import me.techygaming.mutechat.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class cmd_clearchat implements CommandExecutor {

    private Main plugin;

    public cmd_clearchat(Main plugin){
        this.plugin = plugin;
        plugin.getCommand("clearchat").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender instanceof Player){
            if (!sender.hasPermission(plugin.getConfig().getString("permission-nodes.clearchat"))){
                sender.sendMessage(Utils.chat("&cYou do not have permission to use this command."));
                return false;
            }
        }

        if (args.length == 0){

            for (int i = 0; i < 100; i++){
                Bukkit.broadcastMessage(" ");
                Bukkit.broadcastMessage(Utils.chat("&c &f"));
            }

            Bukkit.broadcastMessage(Utils.chat(plugin.getMessagesYml().getString("clear-chat.broadcast")
                    .replace("%player%", sender.getName())));
            for (Player currentPlayer : Bukkit.getOnlinePlayers()){
                if (currentPlayer.hasPermission(plugin.getConfig().getString("permission-nodes.staff-notifications"))){
                    currentPlayer.sendMessage(Utils.chat(plugin.getMessagesYml().getString("clear-chat.staff")
                            .replace("%player%", sender.getName())));
                }
            }

            return true;
        }

        sender.sendMessage(Utils.chat("&cUsage: /clearchat"));
        return false;

    }
}
