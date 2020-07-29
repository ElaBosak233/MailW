package cn.elabosak.mailw;

import cn.elabosak.mailw.api.MailWApi;
import cn.elabosak.mailw.commands.MailWCmds;
import cn.elabosak.mailw.commands.MailWControllerCmds;
import cn.elabosak.mailw.sql.PlayerEmail;
import cn.elabosak.mailw.sql.MailWSettings;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author ElaBosak
 * @date 2020/7/1
 */
public final class MailW extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        File template = new File(getDataFolder()+"/template");
        if (!template.exists()) {
            template.mkdirs();
        }
        try {
            Connection con = MailWApi.getConnection();
            if (MailWSettings.initTable(con)) {
                if (!MailWApi.isMailWSet(con)) {
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
            Connection con = MailWApi.getConnection();
            if(PlayerEmail.createTable(con)) {
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
        Bukkit.getPluginCommand("MailWController").setExecutor(new MailWControllerCmds(this));
    }

//    @EventHandler
//    public void onPlayerJoin(PlayerJoinEvent event) throws SQLException {
//        Connection con = MailWApi.getConnection();
//        if (MailWApi.getPlayerName(con, event.getPlayer().getUniqueId().toString()) != event.getPlayer().getName()) {
//            PlayerEmail sqlite = new PlayerEmail();
//            sqlite.update(con, event.getPlayer().getName(), event.getPlayer().getUniqueId(), MailWApi.getPlayerEmail(con, event.getPlayer().getUniqueId().toString()));
//            con.close();
//        } else {
//            con.close();
//        }
//    }

    @Override
    public void onDisable() {
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.BLUE+"= MailW Has Been Stopped =");
    }
}
