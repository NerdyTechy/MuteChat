package me.techygaming.mutechat.utils;

import me.techygaming.mutechat.Main;
import org.bukkit.ChatColor;

public class Utils {

    private static Main plugin;

    public static String chat (String s){
        return String.valueOf(ChatColor.translateAlternateColorCodes('&', s));
    }

}
