package cn.elabosak.mailw.handler;

import cn.elabosak.mailw.api.MailWApi;
import cn.elabosak.mailw.Main;
import cn.elabosak.mailw.sql.MailWSettings;
import cn.elabosak.mailw.utils.setEmailAccount;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

import cn.elabosak.mailw.utils.Base64Run;

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
                        if (target.getUniqueId() != null && new setEmailAccount().remove(target.getUniqueId())) {
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
                            MailWSettings sqlite = new MailWSettings();
                            Connection con = MailWSettings.getConnection();
                            String password = Base64Run.encoder(args[4]);
                            if (MailWSettings.selectEmail(con) != null || MailWSettings.selectSmtp(con) != null || MailWSettings.selectPort(con) != null || MailWSettings.selectPasswd(con) != null) {
                                MailWSettings.updateTable(con);
                            }
                            if(sqlite.insert(con, args[1], args[2], args[3], password)) {
                                con.close();
                                p.sendMessage(ChatColor.GREEN+"§lMailW Set Successfully");
                                return true;
                            } else {
                                p.sendMessage(ChatColor.RED+"§lMailW Set Failed");
                                return true;
                            }
                        } catch (SQLException e) {
                            p.sendMessage(ChatColor.RED+"§lMailW Set Failed");
                            return true;
                        }
                    } else {
                        p.sendMessage(ChatColor.RED+"§lPlease Type The Correct Targets! You Need Type "+ChatColor.AQUA+"/MailWController <Email> <Smtp> <Port> <Passwd>");
                        return true;
                    }
                }
            } else {
                sender.sendMessage(ChatColor.RED+"§lYou Must Do This As A Player");
                return true;
            }
        }
        if (args[0].equalsIgnoreCase("test")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                if (p.hasPermission("MailW.admin")) {
                    try {
                        Connection con = MailWSettings.getConnection();
                        if (MailWSettings.selectEmail(con) == null || MailWSettings.selectSmtp(con) == null || MailWSettings.selectPort(con) == null || MailWSettings.selectPasswd(con) == null) {
                            p.sendMessage(ChatColor.BLUE+"= Please Init The MailW By Using"+ChatColor.GOLD+" /MailWController set <Email> <Smtp> <Port> <Passwd> "+ChatColor.BLUE+"=");
                            con.close();
                            return true;
                        } else if (args.length == 1) {
                            p.sendMessage(ChatColor.RED+"§lPlease Type A Correct Target To Continue");
                            return true;
                        } else {
                            Player target = Bukkit.getPlayer(args[1]);
                            try {
                                if (MailWApi.sendEmail(target, "MailW", "Hi! This is = MailW =", "<h1>Hello "+target.getName()+",</h1><p> This is = MailW =, If you receive this email, it means that you have bound successfully. The sending of this email is directly controlled by the administrator, not automatically. It is only used by the administrator for self-test.<p>")) {
                                    target.sendMessage(ChatColor.GREEN+"§lThe operator sent a test email to your mailbox, please check it~");
                                    return true;
                                } else {
                                    p.sendMessage(ChatColor.RED+"§lTest Email Sent Failed, Player Does NOT Exist OR Player Is NOT Set = MailW =");
                                    return true;
                                }
                            } catch (Exception e) {
                                p.sendMessage(ChatColor.RED+"§lTest Email Sent Failed, Player Does NOT Exist OR Player Is NOT Set = MailW =");
                                return true;
                            }
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                        return true;
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
        if (args[0].equalsIgnoreCase("send")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                if (p.hasPermission("MailW.admin")) {
                    try {
                        Connection con = MailWSettings.getConnection();
                        if (MailWSettings.selectEmail(con) == null || MailWSettings.selectSmtp(con) == null || MailWSettings.selectPort(con) == null || MailWSettings.selectPasswd(con) == null) {
                            p.sendMessage(ChatColor.BLUE+"= Please Init The MailW By Using"+ChatColor.GOLD+" /MailWController set <Email> <Smtp> <Port> <Password> "+ChatColor.BLUE+"=");
                            con.close();
                            return true;
                        } else if (args.length < 3) {
                            p.sendMessage(ChatColor.RED+"§lPlease Type The Correct Targets To Continue "+ChatColor.GOLD+" /MailWController send <Player> <Template> ");
                            return true;
                        } else {
                            Player target = Bukkit.getPlayer(args[1]);
                            File file = new File("plugins/MailW/template/"+args[2]+"/index.html");
                            if (file.exists()) {
                                try {
                                    String read = MailWApi.readHtml(file);
                                    if (!Objects.equals(read, "err")) {
                                        String sd = MailWApi.getSender(read);
                                        String tt = MailWApi.getTitle(read);
                                        String content = MailWApi.getContent(read,target);
                                        Bukkit.getServer().getConsoleSender().sendMessage(sd+"\n"+tt+"\n"+content);
                                        if (MailWApi.sendEmail(target, sd,tt,content)) {
                                            p.sendMessage(ChatColor.GREEN+"§lMail Sent Successfully");
                                            return true;
                                        } else {
                                            p.sendMessage(ChatColor.RED+"§lEmail Sent Failed, Player Does NOT Exist OR Player Is NOT Set = MailW =");
                                            return true;
                                        }
                                    } else {
                                        p.sendMessage(ChatColor.RED+"§lEmail Sent Failed, HTML Parsing Error");
                                        return true;
                                    }
                                } catch (Exception e) {
                                    p.sendMessage(ChatColor.RED+"§lEmail Sent Failed, Player Does NOT Exist OR Player Is NOT Set = MailW =");
                                    return true;
                                }
                            } else {
                                p.sendMessage(ChatColor.RED+"§lNo Such Template");
                                return true;
                            }
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                        return true;
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
