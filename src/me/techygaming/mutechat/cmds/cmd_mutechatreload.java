package me.techygaming.mutechat.cmds;

import me.techygaming.mutechat.Main;
import me.techygaming.mutechat.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class cmd_mutechatreload implements CommandExecutor {

    private Main plugin;

    public cmd_mutechatreload(Main plugin){
        this.plugin = plugin;
        plugin.getCommand("mutechatreload").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender instanceof Player){
            if (!sender.hasPermission(plugin.getConfig().getString("permission-nodes.admin"))){
                sender.sendMessage(Utils.chat("&cYou do not have permission to use this command."));
                return false;
            }
        }

        plugin.reloadConfig();
        plugin.reloadBlockedWords();
        plugin.reloadMessages();

        sender.sendMessage(Utils.chat("&a[MuteChat] Plugin successfully reloaded."));
        return true;
    }
}
