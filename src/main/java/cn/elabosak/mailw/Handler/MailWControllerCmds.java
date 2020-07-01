package cn.elabosak.mailw.Handler;

import cn.elabosak.mailw.API.MailWAPI;
import cn.elabosak.mailw.Main;
import cn.elabosak.mailw.SQL.SETTINGS;
import cn.elabosak.mailw.Utils.removeEmailAccount;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.SQLException;
import cn.elabosak.mailw.Utils.base64;

/**
 * @author ElaBosak
 */
public class MailWControllerCmds implements CommandExecutor{

    Main plugin;

    @Override
    public boolean onCommand (CommandSender sender, Command cmd, String lable, String[] args) {
        if (args.length == 0) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                if (p.hasPermission("MailW.admin")) {
                    p.sendMessage(ChatColor.RED+"§lPlease Type SubCommand");
                    return true;
                } else {
                    sender.sendMessage(ChatColor.RED+"§lYou Don't Have Permission");
                    return true;
                }
            } else {
                sender.sendMessage(ChatColor.RED+"§lYou Must Do This As A Player");
                return true;
            }
        }
        if(args[0].equalsIgnoreCase("remove")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                if (p.hasPermission("MailW.admin")) {
                    if (args.length == 1) {
                        p.sendMessage(ChatColor.RED+"§lPlease Type A Correct Target To Continue");
                        return true;
                    } else {
                        Player target = Bukkit.getPlayer(args[1]);
                        if (target.getUniqueId() != null && new removeEmailAccount().remove(target.getUniqueId())) {
                            p.sendMessage(ChatColor.GREEN+"§lEmail Removed Successful");
                            return true;
                        }else {
                            p.sendMessage(ChatColor.RED+"§lEmail Removed Failed");
                            return true;
                        }
                    }
                } else {
                    sender.sendMessage(ChatColor.RED+"§lYou Don't Have Permission");
                    return true;
                }
            } else {
                sender.sendMessage(ChatColor.RED+"§lYou Must Do This As A Player");
                return true;
            }
        }
        if (args[0].equalsIgnoreCase("set")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                if (p.hasPermission("MailW.admin")) {
                    if (args.length == 5) {
                        try {
                            SETTINGS sqlite = new SETTINGS();
                            Connection con = SETTINGS.getConnection();
                            String password = base64.encoder(args[4]);
                            //ToDo 等待查错
                            if (SETTINGS.selectEmail(con) != null || SETTINGS.selectSMTP(con) != null || SETTINGS.selectPORT(con) != null || SETTINGS.selectPASSWD(con) != null) {
                                SETTINGS.updateTable(con);
                            }
                            sqlite.insert(con, args[1], args[2], args[3], password);
                            con.close();
                            p.sendMessage(ChatColor.GREEN+"§lMailW Set Successfully");
                            return true;
                        } catch (SQLException e) {
                            p.sendMessage(ChatColor.RED+"§lMailW Set Failed");
                            return true;
                        }
                    } else {
                        p.sendMessage(ChatColor.RED+"§lPlease Type The Correct Targets! You Need Type "+ChatColor.AQUA+"/MailWController <Email> <Smtp> <Port> <Passwd>");
                    }
                }
            } else {
                sender.sendMessage(ChatColor.RED+"§lYou Must Do This As A Player");
            }
        }
        if (args[0].equalsIgnoreCase("test")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                if (p.hasPermission("MailW.admin")) {
                    try {
                        Connection con = SETTINGS.getConnection();
                        if (SETTINGS.selectEmail(con) == null || SETTINGS.selectSMTP(con) == null || SETTINGS.selectPORT(con) == null || SETTINGS.selectPASSWD(con) == null) {
                            p.sendMessage(ChatColor.BLUE+"= Please Init The MailW By Using"+ChatColor.GOLD+" /MailWController set <Email> <Smtp> <Port> <Passwd> "+ChatColor.BLUE+"=");
                            con.close();
                            return true;
                        } else if (args.length == 1) {
                            p.sendMessage(ChatColor.RED+"§lPlease Type A Correct Target To Continue");
                            return true;
                        } else {
                            Player target = Bukkit.getPlayer(args[1]);
                            try {
                                if (MailWAPI.sendEmail(target, "MailW", "Hi! This is = MailW =", "<h1>Hello "+target.getName()+",</h1><p> This is = MailW =, If you receive this email, it means that you have bound successfully. The sending of this email is directly controlled by the administrator, not automatically. It is only used by the administrator for self-test.<p>")) {
                                    target.sendMessage(ChatColor.GREEN+"§lThe operator sent a test email to your mailbox, please check it~");
                                    return true;
                                } else {
                                    p.sendMessage(ChatColor.RED+"§lTest Email Sent Failed");
                                    return true;
                                }
                            } catch (Exception e) {
                                p.sendMessage(ChatColor.RED+"§lTest Email Sent Failed");
                                return true;
                            }
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else {
                    p.sendMessage(ChatColor.RED+"§lYou Don't Have Permission");
                    return true;
                }
            } else {
                sender.sendMessage(ChatColor.RED+"§lYou Must Do This As A Player");
                return true;
            }
        }
        //ToDo 更多命令
        return false;
    }
}
