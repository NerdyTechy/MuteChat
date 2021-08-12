package me.techygaming.mutechat.listeners;

import me.techygaming.mutechat.Main;
import me.techygaming.mutechat.cmds.cmd_mutechat;
import me.techygaming.mutechat.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;


public class listener_onChatSwear implements Listener {

    private static Main plugin;

    public listener_onChatSwear(Main plugin){
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public static void onChat(AsyncPlayerChatEvent e){
        Player plr = e.getPlayer();
        String message = e.getMessage().toLowerCase();

        if (plr.hasPermission(plugin.getConfig().getString("permission-nodes.chat-filter-bypass"))){return;}

        ArrayList<String> blockedWords = (ArrayList<String>) plugin.getBlockedWordsYml().getStringList("blocked-words");

        for (String currentWord : blockedWords){
            if (message.contains(currentWord.toLowerCase())){
                e.setCancelled(true);
                plr.sendMessage(Utils.chat(plugin.getMessagesYml().getString("chat-filter.blocked-msg")));

                for (Player currentPlayer : Bukkit.getOnlinePlayers()){
                    if (currentPlayer.hasPermission(plugin.getConfig().getString("permission-nodes.staff-notifications"))){
                        currentPlayer.sendMessage(Utils.chat(plugin.getMessagesYml().getString("chat-filter.staff-notification")
                                .replace("%player%", plr.getName()).replace("%message%", message)));
                    }
                }
                return;
            }
        }

        blockedWords.clear();
    }

}
