package me.techygaming.mutechat;

import me.techygaming.mutechat.cmds.cmd_clearchat;
import me.techygaming.mutechat.cmds.cmd_mutechat;
import me.techygaming.mutechat.cmds.cmd_mutechatreload;
import me.techygaming.mutechat.cmds.cmd_slowchat;
import me.techygaming.mutechat.listeners.listener_onChat;
import me.techygaming.mutechat.listeners.listener_onChatSwear;
import me.techygaming.mutechat.listeners.listener_onJoin;
import me.techygaming.mutechat.listeners.listener_onSlowChat;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class Main extends JavaPlugin {

    private File messagesYmlFile;
    private FileConfiguration messagesYml;

    private File blockedWordsYmlFile;
    private FileConfiguration blockedWordsYml;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        createMessagesYml();
        createBlockedWordsYml();

        new cmd_mutechat(this);
        new listener_onChat(this);

        new cmd_slowchat(this);
        new listener_onSlowChat(this);

        new cmd_clearchat(this);

        new listener_onJoin(this);

        new UpdateChecker(this, 77871);

        new listener_onChatSwear(this);

        new cmd_mutechatreload(this);

        int pluginId = 7293;
        Metrics metrics = new Metrics(this, pluginId);
    }

    public FileConfiguration getMessagesYml(){
        return this.messagesYml;
    }
    public FileConfiguration getBlockedWordsYml(){return this.blockedWordsYml;}

    public void createMessagesYml(){
        messagesYmlFile = new File(getDataFolder(), "messages.yml");
        if (!messagesYmlFile.exists()){
            messagesYmlFile.getParentFile().mkdirs();
            saveResource("messages.yml", false);
        }

        messagesYml = new YamlConfiguration();
        try{
            messagesYml.load(messagesYmlFile);
        } catch (IOException | InvalidConfigurationException e){
            e.printStackTrace();
        }
    }

    public void createBlockedWordsYml(){
        blockedWordsYmlFile = new File(getDataFolder(), "blocked-words.yml");
        if (!blockedWordsYmlFile.exists()){
            blockedWordsYmlFile.getParentFile().mkdirs();
            saveResource("blocked-words.yml", false);
        }

        blockedWordsYml = new YamlConfiguration();
        try{
            blockedWordsYml.load(blockedWordsYmlFile);
        } catch (IOException | InvalidConfigurationException e){
            e.printStackTrace();
        }
    }

    public void reloadMessages() {
        this.createMessagesYml();
    }

    public void reloadBlockedWords(){
        this.createBlockedWordsYml();
    }
}
