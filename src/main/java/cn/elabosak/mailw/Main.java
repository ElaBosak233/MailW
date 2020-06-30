package cn.elabosak.mailw;

import cn.elabosak.mailw.API.MailWAPI;
import cn.elabosak.mailw.Handler.MailWCmds;
import cn.elabosak.mailw.Handler.MailWControllerCmds;
import cn.elabosak.mailw.SQL.SQLite;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import cn.elabosak.mailw.Utils.sendEmail;

import java.io.File;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;

/**
 * @author ElaBosak
 * @date 2020/6/30
 */
public final class Main extends JavaPlugin {

//    public HashMap<String, String> data = new HashMap<>();

    @Override
    public void onEnable() {
        File file = new File(getDataFolder(),"config.yml");
        if(!getDataFolder().exists()){
            getDataFolder().mkdirs();
        }
        if(!file.exists()){
            this.saveDefaultConfig();
        }
        this.reloadConfig();
        if (getConfig().getString("MainSettings.Email") == null || getConfig().getString("MainSettings.Smtp") == null || getConfig().getString("MainSettings.Passwd") == null) {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED+"= Please Provide Necessary Infomation For MailW IN"+ChatColor.BLUE+" config.yml "+ChatColor.RED+"=");
            Bukkit.getServer().getPluginManager().disablePlugin(this);
        }
        File template = new File(getDataFolder()+"/template");
        if (!template.exists()) {
            template.mkdirs();
        }
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN+"= MailW Has Been Launched =");
        Bukkit.getPluginCommand("MailW").setExecutor(new MailWCmds());
        Bukkit.getPluginCommand("MailWController").setExecutor(new MailWControllerCmds());
    }

    @Override
    public void onDisable() {
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.BLUE+"= MailW Has Been Stopped =");
    }

}
