package me.techygaming.mutechat.listeners;

import me.techygaming.mutechat.Main;
import me.techygaming.mutechat.UpdateChecker;
import me.techygaming.mutechat.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class listener_onJoin implements Listener {

    private Main plugin;

    public listener_onJoin(Main plugin){
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player plr = e.getPlayer();

        if (!plugin.getConfig().getBoolean("update-notification")){
            return;
        }

        if (plr.hasPermission(plugin.getConfig().getString("permission-nodes.staff-notifications"))){

            if (UpdateChecker.updateAvailable){
                plr.sendMessage(Utils.chat("&c&lMuteChat &r&8> &fUpdate Available!"));
                plr.sendMessage(Utils.chat("&c&lMuteChat &r&8> &7Download from: &fhttps://www.spigotmc.org/resources/mutechat.77871/"));
            }
            return;


        }

    }

}
