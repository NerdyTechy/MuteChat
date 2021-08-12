package me.techygaming.mutechat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.bukkit.plugin.java.JavaPlugin;

public class UpdateChecker {
    private URL checkURL;
    private String newVersion;
    private JavaPlugin plugin;
    public static boolean updateAvailable;

    public UpdateChecker(JavaPlugin plugin, int projectID) {
        this.plugin = plugin;
        newVersion = plugin.getDescription().getVersion();
        // Send the Starting Message to Console
        plugin.getServer().getConsoleSender().sendMessage("Checking for MuteChat updates...");
        try {
            checkURL = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + projectID);
            try {
                this.broadCastResult();
            } catch (Exception e) {
                // If the Server Does Not Have an Internet Connection
                plugin.getServer().getConsoleSender().sendMessage("Failed to check for MuteChat updates. (Unable to Connect to Spigot)");
            }
        }catch(MalformedURLException e) {
            // If Resource ID is Incorrect
            plugin.getServer().getConsoleSender().sendMessage("Failed to check for MuteChat updates. (Invalid Resource ID - Contact Developer)");
        }

    }

    // Gets Plugin From Spigot. If Unable to Check Version it Will Throw an Exception.
    public String getNewVersion() throws Exception {
        URLConnection con = checkURL.openConnection();
        newVersion = new BufferedReader(new InputStreamReader(con.getInputStream())).readLine();
        return newVersion;
    }

    // we compare the new and the online version to see what happens, it's a little bit hard to explain fully what this code does
    public int versionCompare(String paramString1, String paramString2) {
        String[] arrayOfString1 = paramString1.split("_")[0].split("\\.");
        String[] arrayOfString2 = paramString2.split("_")[0].split("\\.");
        int i = 0;
        while ((i < arrayOfString1.length) && (i < arrayOfString2.length) && (arrayOfString1[i].equals(arrayOfString2[i]))) {
            i++;
        }
        if ((i < arrayOfString1.length) && (i < arrayOfString2.length)) {
            int j = Integer.valueOf(arrayOfString1[i]).compareTo(Integer.valueOf(arrayOfString2[i]));

            return Integer.signum(j);
        }
        return Integer.signum(arrayOfString1.length - arrayOfString2.length);
    }

    // we send the console or the player or whoever we want to know that's there's or not an update
    public void broadCastResult() throws Exception {
        String publishedvers = this.getNewVersion();
        try {
            String pluginvers = plugin.getDescription().getVersion();
            switch (versionCompare(pluginvers,publishedvers)) {
                case 0:
                    plugin.getServer().getConsoleSender().sendMessage("You are using the last stable version of MuteChat. (" + publishedvers + ")");
                    updateAvailable = false;
                    // If == 0 the online published version is the same as the player is using
                    break;
                case 1:
                    plugin.getServer().getConsoleSender().sendMessage("Your version of MuteChat isn't published.");
                    plugin.getServer().getConsoleSender().sendMessage("Your version: " + pluginvers);
                    plugin.getServer().getConsoleSender().sendMessage("Latest published version: " + publishedvers);
                    updateAvailable = false;
                    // If == 1 the online version is old and you have a new one because you are maybe the developer
                    break;
                case - 1 :
                    plugin.getServer().getConsoleSender().sendMessage("MuteChat v" + publishedvers + " is now available.");
                    updateAvailable = true;
                    // If == -1 the version that he is using is old and he need to update
                    break;
                default:
                    plugin.getServer().getConsoleSender().sendMessage("Failed to check for MuteChat updates. (Unknown Error Occurred)");
                    updateAvailable = false;
            }
        } catch(Exception known) {
            plugin.getServer().getConsoleSender().sendMessage("Failed to check for MuteChat updates. (Unknown Error Occurred)");
            updateAvailable = false;
        }
    }
}