package me.techygaming.mutechat.listeners;

import me.techygaming.mutechat.Main;
import me.techygaming.mutechat.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import me.techygaming.mutechat.cmds.*;


public class listener_onChat implements Listener {

    private static Main plugin;

    public listener_onChat (Main plugin){
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public static void onChat(AsyncPlayerChatEvent e){
        Player plr = e.getPlayer();
        if (!cmd_mutechat.isChatMuted){
            return;
        }

        if (plr.hasPermission(plugin.getConfig().getString("permission-nodes.mutechat-bypass"))){
            return;
        }

        e.setCancelled(true);
        plr.sendMessage(Utils.chat(plugin.getMessagesYml().getString("mute-chat.mute.chat-fail")));
    }

}
