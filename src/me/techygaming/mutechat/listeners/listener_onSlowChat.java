package me.techygaming.mutechat.listeners;

import me.techygaming.mutechat.Main;
import me.techygaming.mutechat.cmds.cmd_mutechat;
import me.techygaming.mutechat.cmds.cmd_slowchat;
import me.techygaming.mutechat.utils.Utils;
import org.apache.commons.lang.time.DurationFormatUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.HashMap;

import static me.techygaming.mutechat.cmds.cmd_slowchat.slowSeconds;


public class listener_onSlowChat implements Listener {

    private Main plugin;

    private static HashMap<Player, Long> chatSlow = new HashMap<>();

    public listener_onSlowChat(Main plugin){
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onSlowChat(AsyncPlayerChatEvent e){
        Player plr = e.getPlayer();

        if (!cmd_slowchat.isChatSlowed){
            return;
        }

        if (plr.hasPermission(plugin.getConfig().getString("permission-nodes.slowchat-bypass"))){
            return;
        }

        if (chatSlow.containsKey(plr)){
            int slowSeconds = cmd_slowchat.slowSeconds;
            long timeRemaining = chatSlow.get(plr) + slowSeconds * 1000L - System.currentTimeMillis();
            if (timeRemaining > 0L){
                e.setCancelled(true);
                int waitSeconds = Integer.parseInt(DurationFormatUtils.formatDuration(timeRemaining, "s"));
                plr.sendMessage(Utils.chat(plugin.getMessagesYml().getString("slow-chat.enable.chat-fail")
                        .replace("%seconds%", waitSeconds + "")));
                return;
            }
            chatSlow.remove(plr);
        }

        chatSlow.put(plr, System.currentTimeMillis());
    }

}
