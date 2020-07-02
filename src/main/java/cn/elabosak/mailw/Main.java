package cn.elabosak.mailw;

import cn.elabosak.mailw.Handler.MailWCmds;
import cn.elabosak.mailw.Handler.MailWControllerCmds;
import cn.elabosak.mailw.SQL.EMAIL;
import cn.elabosak.mailw.SQL.SETTINGS;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.Properties;

import static cn.elabosak.mailw.Utils.sendEmail.*;

/**
 * @author ElaBosak
 * @date 2020/7/1
 */
public final class Main extends JavaPlugin {

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
        File template = new File(getDataFolder()+"/template");
        if (!template.exists()) {
            template.mkdirs();
        }
        try {
            Connection con = SETTINGS.getConnection();
            if (SETTINGS.initTable(con)) {
                if (SETTINGS.selectEmail(con) == null || SETTINGS.selectSMTP(con) == null || SETTINGS.selectPORT(con) == null || SETTINGS.selectPASSWD(con) == null) {
                    Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.BLUE+"= Please Init The MailW By Using /MailWController set <Email> <Smtp> <Port> <Passwd> =");
                }
                con.close();
            } else {
                con.close();
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED+"= SQLite ERR =");
                getPluginLoader().disablePlugin(this);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            EMAIL sqlite = new EMAIL();
            Connection con = sqlite.getConnection();
            if(sqlite.createTable(con)) {
                con.close();
            } else {
                con.close();
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED+"= SQLite ERR =");
                getPluginLoader().disablePlugin(this);
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
